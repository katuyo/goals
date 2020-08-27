package com.juext.asset.goals.mapper;

import com.juext.asset.goals.SpringDataTestSuit;
import com.juext.asset.goals.entity.IssuanceEntity;
import com.juext.asset.goals.query.IssuanceCriteria;
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

/**
 * @author Excepts
 * @since 2020/7/6 22:48
 */
@DisplayName("Mapper: Issuance")
@Transactional
@EnableAutoConfiguration
class IssuanceMapperTest extends SpringDataTestSuit {
    @Resource
    private IssuanceMapper issuanceMapper;

    @ParameterizedTest(name = "Insert Issuance")
    @CsvSource({"ISC00005"})
    void testInsert(String code) {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode(code);
        issuanceEntity.setName("");
        issuanceEntity.setType(1);
        issuanceEntity.setAccountCode("ACT00001");
        issuanceEntity.setAmount(19.9);
        issuanceEntity.setComment("Comment");
        issuanceMapper.insert(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode(code);
        issuanceEntity.setDeleted(false);
        foundIssuance.setCreatedAt(null);
        foundIssuance.setUpdatedAt(null);
        assertEquals(foundIssuance, issuanceEntity);
    }

    @Test@DisplayName("Upsert")
    void testUpsert() {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setName("issuanceNameName");
        issuanceEntity.setCode("ISC00004");
        issuanceEntity.setType(3);
        issuanceEntity.setAmount(300009.9);
        issuanceEntity.setComment("CommentComment");
        issuanceEntity.setAccountCode("ACT00001");
        issuanceMapper.upsert(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode("ISC00004");
        issuanceEntity.setId(foundIssuance.getId());
        issuanceEntity.setDeleted(foundIssuance.getDeleted());
        issuanceEntity.setCreatedAt(foundIssuance.getCreatedAt());
        issuanceEntity.setUpdatedAt(foundIssuance.getUpdatedAt());
        assertEquals(foundIssuance, issuanceEntity);
    }

    @Test@DisplayName("Update")
    void testUpdate() {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode("ISC00003");
        issuanceEntity.setAmount(91.9009);
        issuanceEntity.setAccountCode("ACT99991");
        issuanceEntity.setComment("tnemmoC");
        issuanceEntity.setName("CoolName");
        issuanceEntity.setType(8);
        issuanceMapper.update(issuanceEntity);

        IssuanceEntity foundIssuance = issuanceMapper.selectByCode("ISC00003");

        issuanceEntity.setCreatedAt(foundIssuance.getCreatedAt());
        issuanceEntity.setUpdatedAt(foundIssuance.getUpdatedAt());
        issuanceEntity.setId(foundIssuance.getId());
        issuanceEntity.setDeleted(foundIssuance.getDeleted());

        assertEquals(foundIssuance, issuanceEntity);
    }

    @ParameterizedTest(name = "Delete Account")
    @CsvSource({"ACT00005"})
    void testDelete(String code) {
        issuanceMapper.delete(code, true);
        Optional.ofNullable(issuanceMapper.selectByCode(code)).ifPresent(accountEntity -> {
            throw new RuntimeException("Should be null");
        });
    }

    @Test
    @DisplayName("Query issuance by codes")
    void testSelectByCodes() {
        List<IssuanceEntity> list = issuanceMapper.selectByCodes(Lists.newArrayList("ISC00001", "ISC00002", "ISC00003", "ISC00004"));
        list.forEach(issuanceEntity -> {
            issuanceEntity.setCreatedAt(null);
            issuanceEntity.setUpdatedAt(null);
        });
        assertEquals(preparedIssuanceList(), list);
    }

    private List<IssuanceEntity> preparedIssuanceList() {
        IssuanceEntity issuance1 = new IssuanceEntity();
        issuance1.setId(1L);
        issuance1.setCode("ISC00001");
        issuance1.setName("Issuance 001");
        issuance1.setType(0);
        issuance1.setAccountCode("ACT00001");
        issuance1.setAmount(5000.00000001);
        issuance1.setComment("This is the first goals issuance");
        issuance1.setDeleted(false);

        IssuanceEntity issuance2 = new IssuanceEntity();
        issuance2.setId(2L);
        issuance2.setCode("ISC00002");
        issuance2.setName("Issuance 002");
        issuance2.setType(1);
        issuance2.setAccountCode("ACT00002");
        issuance2.setAmount(6000.00000001);
        issuance2.setComment("This is the second goals issuance");
        issuance2.setDeleted(false);

        IssuanceEntity issuance3 = new IssuanceEntity();
        issuance3.setId(3L);
        issuance3.setCode("ISC00003");
        issuance3.setName("Issuance 003");
        issuance3.setType(0);
        issuance3.setAccountCode("ACT00003");
        issuance3.setAmount(7000.00000001);
        issuance3.setComment("This is the third goals issuance");
        issuance3.setDeleted(false);

        IssuanceEntity issuance4 = new IssuanceEntity();
        issuance4.setId(4L);
        issuance4.setCode("ISC00004");
        issuance4.setName("Issuance 004");
        issuance4.setType(1);
        issuance4.setAccountCode("ACT00004");
        issuance4.setAmount(9000.00000001);
        issuance4.setComment("This is the fourth goals issuance");
        issuance4.setDeleted(false);

        return Lists.newArrayList(issuance1, issuance2, issuance3, issuance4);
    }

    @Test
    void testSelectByPage() {
        IssuanceCriteria issuanceCriteria = generate(IssuanceCriteria.class);
        issuanceMapper.selectByPage(issuanceCriteria, new PageRequest());
    }

    @Test
    void testCountByQuery() {
        IssuanceCriteria issuanceCriteria = generate(IssuanceCriteria.class);
        issuanceMapper.countByQuery(issuanceCriteria);
    }
}
