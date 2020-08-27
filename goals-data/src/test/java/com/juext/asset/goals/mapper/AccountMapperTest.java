package com.juext.asset.goals.mapper;

import com.juext.asset.goals.SpringDataTestSuit;
import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.query.AccountCriteria;
import org.assertj.core.util.Lists;
import org.featx.spec.model.PageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author Cartoon Zhang
 * @since 2020/7/6 12:37 上午
 */
@DisplayName("Mapper: Account")
@EnableAutoConfiguration
@Transactional
public class AccountMapperTest extends SpringDataTestSuit {

    @Resource
    private AccountMapper accountMapper;

    @DisplayName("Insert Account")
    @ParameterizedTest
    @CsvSource({"ACT00005"})
    public void testInsert(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(19.9);
        accountEntity.setComment("Comment");
        assertEquals(1, accountMapper.insert(accountEntity));

        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity.getId(), foundAccount.getId());
        assertEquals(accountEntity.getCode(), foundAccount.getCode());
        assertEquals(accountEntity.getInventory(), foundAccount.getInventory());
        assertEquals(accountEntity.getComment(), foundAccount.getComment());
    }

    @DisplayName("Upsert Account")
    @ParameterizedTest
    @CsvSource({"ACT00001"})
    public void testUpsert(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(20.001);
        accountEntity.setComment("Comment Upsert");
        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertNotEquals(accountEntity.getInventory(), foundAccount.getInventory());
        assertNotEquals(accountEntity.getComment(), foundAccount.getComment());
        assertEquals(1, accountMapper.upsert(accountEntity));

        foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity.getInventory(), foundAccount.getInventory());
        assertEquals(accountEntity.getComment(), foundAccount.getComment());
    }

    @DisplayName("Update Account")
    @ParameterizedTest
    @CsvSource({"ACT00001"})
    public void testUpdate(String code) {
        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setCode(code);
        accountEntity.setInventory(21.002);
        accountEntity.setComment("Comment Update");
        AccountEntity foundAccount = accountMapper.selectByCode(code);
        assertNotEquals(accountEntity.getInventory(), foundAccount.getInventory());
        assertNotEquals(accountEntity.getComment(), foundAccount.getComment());

        assertEquals(1, accountMapper.upsert(accountEntity));

        foundAccount = accountMapper.selectByCode(code);
        assertEquals(accountEntity.getInventory(), foundAccount.getInventory());
        assertEquals(accountEntity.getComment(), foundAccount.getComment());
    }

    @DisplayName("Delete Account")
    @Test
    public void testDelete() {
        assertEquals(0, accountMapper.delete(null, true));
        assertEquals(0, accountMapper.delete("A1232132", true));
        assertEquals(1, accountMapper.delete("ACT00001", true));
        Optional.ofNullable(accountMapper.selectByCode("ACT00001")).ifPresent(accountEntity -> {
            throw new RuntimeException("Should be null");
        });
        assertEquals(1, accountMapper.delete("ACT00001", false));
        assertNotNull(accountMapper.selectByCode("ACT00001"));
    }

    @Test
    @DisplayName("Query accounts by codes")
    public void testSelectByCodes() {
        List<AccountEntity> list = accountMapper.selectByCodes(Lists.newArrayList("ACT00001", "ACT00002", "ACT00003", "ACT00004"));
        //Besides createAt and updateAt, expect equals
        list.forEach(accountEntity -> {
            accountEntity.setUpdatedAt(null);
            accountEntity.setCreatedAt(null);
        });
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

        AccountEntity account2 = new AccountEntity();
        account2.setId(2L);
        account2.setCode("ACT00002");
        account2.setName("Account 002");
        account2.setType(0);
        account2.setInventory(0.0);
        account2.setComment("This is the 2nd system account for archive payment");
        account2.setDeleted(false);

        AccountEntity account3 = new AccountEntity();
        account3.setId(3L);
        account3.setCode("ACT00003");
        account3.setName("Account 003");
        account3.setType(1);
        account3.setInventory(0.0);
        account3.setComment("This is the 1st normal account");
        account3.setDeleted(false);

        AccountEntity account4 = new AccountEntity();
        account4.setId(4L);
        account4.setCode("ACT00004");
        account4.setName("Account 004");
        account4.setType(1);
        account4.setInventory(0.0);
        account4.setComment("This is the 2nd normal account");
        account4.setDeleted(false);
        return Lists.newArrayList(account1, account2, account3, account4);
    }

    @Test
    @DisplayName("Query accounts page by criteria and limit")
    public void testSelectByPage() {
        AccountCriteria accountCriteria = generate(AccountCriteria.class);
        accountMapper.selectByPage(accountCriteria, new PageRequest());
    }

    @Test
    @DisplayName("Count accounts of criteria")
    public void testCountByQuery() {
        AccountCriteria accountCriteria = generate(AccountCriteria.class);
        accountMapper.countByQuery(accountCriteria);
    }
}
