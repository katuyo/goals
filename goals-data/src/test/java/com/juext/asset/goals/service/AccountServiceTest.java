package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.mapper.AccountMapper;
import com.juext.asset.goals.param.AccountTransferParam;
import com.juext.asset.goals.query.AccountCriteria;
import org.featx.spec.error.BusinessException;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.StringUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.juext.asset.goals.Constant.CODE_PREFIX_ACCOUNT;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;
import static com.juext.asset.goals.error.GoalsErrorEnum.*;
import static org.featx.spec.enums.BusinessError.PARAMETER_LOST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author Excepts
 * @since 2020/7/7 0:34
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Service: Account")
public class AccountServiceTest {

    @InjectMocks
    private final AccountService accountService = new AccountServiceImpl();
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private IdGenerate idGenerate;

    @Test
    void testSave() {
        when(accountMapper.insert(any(AccountEntity.class))).thenReturn(1);
        when(idGenerate.nextId()).thenReturn(123456789L);
        AccountEntity accountEntity = new AccountEntity();
        accountService.save(accountEntity);
        accountEntity.setCode(String.format("%s%s", CODE_PREFIX_ACCOUNT,
                Long.toString(123456789L, DEFAULT_RADIX)));
        verify(accountMapper).insert(accountEntity);
        verify(idGenerate).nextId();
        clearInvocations(accountMapper, idGenerate);

        when(accountMapper.upsert(any(AccountEntity.class))).thenReturn(1);
        accountService.save(accountEntity);
        verify(accountMapper).upsert(accountEntity);
        clearInvocations(accountMapper);
    }

    @Test
    void testUpdate() {
        when(accountMapper.update(any(AccountEntity.class))).thenReturn(1);
        AccountEntity updatingAccountEntity = new AccountEntity();
        accountService.update(updatingAccountEntity);
        verify(accountMapper).update(updatingAccountEntity);
        clearInvocations(accountMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACT00001", "", "ACT0002", "-"})
    void testDelete(String code) {
        when(accountMapper.delete(anyString(), anyBoolean())).thenReturn(1);
        accountService.delete(code);
        verify(accountMapper).delete(code, true);
        clearInvocations(accountMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {"a,b,1.3", " , , ", "a, , ", " ,b, ", "a,b, ",
            "a,b,-13.05", "a,b,0.0", "a,b,11.06", "a,b,21.334"})
    void testTransfer(String code) {
        String[] params = code.split(",");
        String fromCode = params[0];
        String toCode = params[1];
        double amount = Double.parseDouble(StringUtil.isBlank(params[2]) ? "0" : params[2]);
        final AccountTransferParam param = AccountTransferParam.newInstance()
                .fromAccountCode(fromCode).toAccountCode(toCode).amount(amount);

        if (StringUtil.isAnyBlank(fromCode, toCode)) {
            assertThrows(BusinessException.class, () -> accountService.transfer(param), PARAMETER_LOST.getMessage());
            return;
        } else if (amount <= 0.0) {
            assertThrows(BusinessException.class, () -> accountService.transfer(param), TRANSFER_AMOUNT_NEGATIVE.getMessage());
            return;
        }

        when(accountMapper.selectByCodes(Lists.newArrayList(fromCode, toCode)))
                .thenReturn(null);
        assertThrows(BusinessException.class, () ->
                accountService.transfer(param), ACCOUNT_NOT_FOUND.getMessage());
        verify(accountMapper).selectByCodes(Lists.newArrayList(fromCode, toCode));
        clearInvocations(accountMapper);

        AccountEntity fromAccount = new AccountEntity();
        fromAccount.setCode(fromCode);
        fromAccount.setInventory(12.3456);
        AccountEntity toAccount = new AccountEntity();
        toAccount.setCode(toCode);
        toAccount.setInventory(0.8);
        when(accountMapper.selectByCodes(Lists.newArrayList(fromCode, toCode)))
                .thenReturn(Lists.newArrayList(fromAccount, toAccount));
        if (amount > fromAccount.getInventory()) {
            assertThrows(BusinessException.class, () -> accountService.transfer(param), INVENTORY_NOT_ENOUGH.getMessage());
            verify(accountMapper).selectByCodes(Lists.newArrayList(fromCode, toCode));
        } else {
            when(accountMapper.update(isA(AccountEntity.class))).thenReturn(1);
            accountService.transfer(param);
            verify(accountMapper).selectByCodes(Lists.newArrayList(fromCode, toCode));
            verify(accountMapper).update(fromAccount);
            verify(accountMapper).update(toAccount);
        }
        clearInvocations(accountMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {",", "ACT0001", ""})
    void testFindOne(String code) {
        AccountEntity foundAccountEntity = new AccountEntity();
        when(accountMapper.selectByCode(code)).thenReturn(foundAccountEntity);
        assertEquals(foundAccountEntity, accountService.findOne(code));
        verify(accountMapper).selectByCode(code);
        clearInvocations(accountMapper);
    }

    private AccountEntity toEntity(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        return accountEntity;
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ",", "ACT32432,", "ACT0432,ACT3824",
            "ACT01032,ACT943232,ACT43208432,ACT43828,ACT84323,ACT889"})
    void testListByCodes(String codes) {
        List<String> accountCodes = Arrays.stream(codes.split(","))
                .filter(StringUtil::isNotBlank).collect(Collectors.toList());
        if (accountCodes.isEmpty()) {
            assertEquals(Lists.newArrayList(), accountService.listByCodes(accountCodes));
            return;
        }
        List<AccountEntity> accountEntities = accountCodes.parallelStream()
                .map(this::toEntity).collect(Collectors.toList());
        when(accountMapper.selectByCodes(accountCodes)).thenReturn(accountEntities);
        List<AccountEntity> resultList = accountCodes.stream().map(this::toEntity)
                .collect(Collectors.toList());
        assertEquals(resultList, accountService.listByCodes(accountCodes));
        verify(accountMapper).selectByCodes(accountCodes);
        clearInvocations(accountMapper);
    }

    @Test
    void testPage() {
        long count = 11L;

        when(accountMapper.countByQuery(isA(AccountCriteria.class))).thenReturn(count);
        List<AccountEntity> list = Lists.newArrayList();
        when(accountMapper.selectByPage(isA(AccountCriteria.class), isA(PageRequest.class)))
                .thenReturn(list);

        AccountCriteria criteria = new AccountCriteria();
        PageRequest pageRequest = new PageRequest();

        assertEquals(QuerySection.of(list).total(count),
                accountService.page(criteria, pageRequest));
        verify(accountMapper, times(1)).countByQuery(criteria);
        verify(accountMapper, times(1)).selectByPage(criteria, pageRequest);
        clearInvocations(accountMapper);
    }
}
