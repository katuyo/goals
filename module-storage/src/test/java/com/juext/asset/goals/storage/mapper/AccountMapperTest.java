package com.juext.asset.goals.storage.mapper;

import com.juext.asset.goals.storage.SpringTestSuit;
import com.juext.asset.goals.storage.entity.AccountEntity;

import com.juext.asset.goals.storage.query.AccountCriteria;
import org.assertj.core.util.Lists;
import org.featx.spec.model.PageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import static org.junit.jupiter.api.Assertions.*;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author Cartoon Zhang
 * @since 2020/7/6 12:37 上午
 */
@DisplayName("Mapper: Account")
@EnableAutoConfiguration
public class AccountMapperTest extends SpringTestSuit {

    @Resource
    private AccountMapper accountMapper;

    @ParameterizedTest(name = "Insert Account")
    @CsvSource({"ACT00005"})
    public void testInsert(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(19.9);
        accountEntity.setComment("Comment");
        accountMapper.insert(accountEntity);

        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity, foundAccount);
    }

    @ParameterizedTest(name = "Upsert Account")
    @CsvSource({"ACT00005"})
    public void testUpsert(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(20.001);
        accountEntity.setComment("Comment Upsert");
        accountMapper.upsert(accountEntity);

        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity, foundAccount);
    }

    @ParameterizedTest(name = "Update Account")
    @CsvSource({"ACT00005"})
    public void testUpdate(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(21.002);
        accountEntity.setComment("Comment Update");
        accountMapper.upsert(accountEntity);

        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity, foundAccount);
    }

    @ParameterizedTest(name = "Delete Account")
    @CsvSource({"ACT00005"})
    public void testDelete(String code) {
        accountMapper.delete(code, true);
        Optional.ofNullable(accountMapper.selectByCode(code)).ifPresent(accountEntity -> {
            throw new RuntimeException("Should be null");
        });
    }

    @Test
    @DisplayName("Query accounts by codes")
    public void testSelectByCodes() {
        List<AccountEntity> list = accountMapper.selectByCodes(Lists.newArrayList("ACT00001", "ACT00002", "ACT00003", "ACT00004"));
        assertEquals(preparedAccountList(), list);
    }

    private List<AccountEntity> preparedAccountList() {
        AccountEntity account1 = new AccountEntity();
        account1.setId(1L);
        account1.setCode("ACT00001");
        account1.setName("Account 001");
        account1.setType(0);
        account1.setInventory(5000.00000001);
        account1.setComment("This is the 1st system account for issuance");
        account1.setDeleted(false);
        account1.setUpdatedAt(LocalDateTime.now());
        account1.setCreatedAt(LocalDateTime.now());

        AccountEntity account2 = new AccountEntity();
        account2.setId(2L);
        account2.setCode("ACT00002");
        account2.setName("Account 002");
        account2.setType(0);
        account2.setInventory(0.0);
        account2.setComment("This is the 2nd system account for archive payment");
        account2.setDeleted(false);
        account2.setUpdatedAt(LocalDateTime.now());
        account2.setCreatedAt(LocalDateTime.now());

        AccountEntity account3 = new AccountEntity();
        account3.setId(3L);
        account3.setCode("ACT00003");
        account3.setName("Account 003");
        account3.setType(1);
        account3.setInventory(0.0);
        account3.setComment("This is the 1st normal account");
        account3.setDeleted(false);
        account3.setUpdatedAt(LocalDateTime.now());
        account3.setCreatedAt(LocalDateTime.now());

        AccountEntity account4 = new AccountEntity();
        account4.setId(4L);
        account4.setCode("ACT00004");
        account4.setName("Account 004");
        account4.setType(1);
        account4.setInventory(0.0);
        account4.setComment("This is the 2nd normal account");
        account4.setDeleted(false);
        account4.setUpdatedAt(LocalDateTime.now());
        account4.setCreatedAt(LocalDateTime.now());
        return Lists.newArrayList(account1, account2, account3, account4);
    }

    @Test
    public void testSelectByPage() {
        AccountCriteria accountCriteria = new AccountCriteria();
        accountMapper.selectByPage(accountCriteria, new PageRequest());
    }

    @Test
    public void testCountByQuery() {
        AccountCriteria accountCriteria = new AccountCriteria();
        accountMapper.countByQuery(accountCriteria);
    }
}
