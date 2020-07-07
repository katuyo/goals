package com.juext.asset.goals.storage.mapper;

import com.juext.asset.goals.storage.SpringDataTestSuit;
import com.juext.asset.goals.storage.entity.IssuanceEntity;
import com.juext.asset.goals.storage.query.IssuanceCriteria;
import org.assertj.core.util.Lists;
import org.featx.spec.model.PageRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Excepts
 * @since 2020/7/6 22:48
 */
@DisplayName("Mapper: Issuance")
@EnableAutoConfiguration
public class IssuanceMapperTest extends SpringDataTestSuit {
    @Resource
    private IssuanceMapper issuanceMapper;

    @ParameterizedTest(name = "Insert Issuance")
    @CsvSource({"ISC00005"})
    public void testInsert(String code) {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode(code);
        issuanceEntity.setAmount(19.9);
        issuanceEntity.setComment("Comment");
        issuanceMapper.insert(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode(code);
        assertEquals(foundIssuance, issuanceEntity);
    }

    @ParameterizedTest(name = "Upsert Issuance")
    @CsvSource({"ACT00005"})
    public void testUpsert(String code) {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode(code);
        issuanceEntity.setAmount(19.9);
        issuanceEntity.setComment("Comment");
        issuanceMapper.upsert(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode(code);
        assertEquals(foundIssuance, issuanceEntity);
    }

    @ParameterizedTest(name = "Update Account")
    @CsvSource({"ACT00005"})
    public void testUpdate(String code) {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode(code);
        issuanceEntity.setAmount(19.9);
        issuanceEntity.setComment("Comment");
        issuanceMapper.update(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode(code);
        assertEquals(foundIssuance, issuanceEntity);
    }

    @ParameterizedTest(name = "Delete Account")
    @CsvSource({"ACT00005"})
    public void testDelete(String code) {
        issuanceMapper.delete(code, true);
        Optional.ofNullable(issuanceMapper.selectByCode(code)).ifPresent(accountEntity -> {
            throw new RuntimeException("Should be null");
        });
    }

    @Test
    @DisplayName("Query accounts by codes")
    public void testSelectByCodes() {
        List<IssuanceEntity> list = issuanceMapper.selectByCodes(Lists.newArrayList("ACT00001", "ACT00002", "ACT00003", "ACT00004"));
        assertEquals(preparedIssuanceList(), list);
    }

    private List<IssuanceEntity> preparedIssuanceList() {
        IssuanceEntity issuance1 = new IssuanceEntity();
        issuance1.setId(1L);
        issuance1.setCode("ACT00001");
        issuance1.setName("Account 001");
        issuance1.setType(0);
        issuance1.setAmount(5000.00000001);
        issuance1.setComment("This is the 1st system account for issuance");
        issuance1.setDeleted(false);
        issuance1.setUpdatedAt(LocalDateTime.now());
        issuance1.setCreatedAt(LocalDateTime.now());

        return Lists.newArrayList(issuance1);
    }

    @Test
    public void testSelectByPage() {
        IssuanceCriteria issuanceCriteria = new IssuanceCriteria();
        issuanceMapper.selectByPage(issuanceCriteria, new PageRequest());
    }

    @Test
    public void testCountByQuery() {
        IssuanceCriteria issuanceCriteria = new IssuanceCriteria();
        issuanceMapper.countByQuery(issuanceCriteria);
    }
}
