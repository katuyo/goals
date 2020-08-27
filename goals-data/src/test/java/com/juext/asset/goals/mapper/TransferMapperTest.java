package com.juext.asset.goals.mapper;

import com.juext.asset.goals.SpringDataTestSuit;
import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.query.TransferCriteria;
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
 * @since 2020/7/6 22:49
 */
@DisplayName("Mapper: Transfer")
@EnableAutoConfiguration
public class TransferMapperTest extends SpringDataTestSuit {
    @Resource
    private TransferMapper transferMapper;

    @ParameterizedTest(name = "Insert Transfer")
    @CsvSource({"TSF00005"})
    public void testInsert(String code) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setCode(code);
        transferEntity.setName("");
        transferEntity.setType(1);
        transferEntity.setMatter(1);
        transferEntity.setFromAccountCode("ACT00001");
        transferEntity.setToAccountCode("ACT00002");
        transferEntity.setReference("Goals");
        transferEntity.setReferenceCode("Serialis Number");
        transferEntity.setAmount(19.9);
        transferEntity.setComment("Comment");
        transferMapper.insert(transferEntity);

        TransferEntity foundTransfer = transferMapper.selectByCode(code);
        assertEquals(foundTransfer, transferEntity);
    }

    @ParameterizedTest(name = "Upsert Transfer")
    @CsvSource({"TSF00005"})
    public void testUpsert(String code) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setCode(code);
        transferEntity.setAmount(19.9);
        transferEntity.setComment("Comment");
        transferMapper.upsert(transferEntity);

        TransferEntity foundTransfer = transferMapper.selectByCode(code);
        assertEquals(foundTransfer, transferEntity);
    }

    @ParameterizedTest(name = "Update Transfer")
    @CsvSource({"TSF00005"})
    public void testUpdate(String code) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setCode(code);
        transferEntity.setAmount(19.9);
        transferEntity.setComment("Comment");
        transferMapper.update(transferEntity);

        TransferEntity foundTransfer = transferMapper.selectByCode(code);
        assertEquals(foundTransfer, transferEntity);
    }

    @ParameterizedTest(name = "Delete Transfer")
    @CsvSource({"TSF00005"})
    public void testDelete(String code) {
        transferMapper.delete(code, true);
        Optional.ofNullable(transferMapper.selectByCode(code)).ifPresent(transferEntity -> {
            throw new RuntimeException("Should be null");
        });
    }

    @Test
    @DisplayName("Query transfers by codes")
    public void testSelectByCodes() {
        List<TransferEntity> list = transferMapper.selectByCodes(Lists.newArrayList("TSF00001", "TSF00002", "TSF00003", "TSF00004"));
        assertEquals(preparedIssuanceList(), list);
    }

    private List<TransferEntity> preparedIssuanceList() {
        TransferEntity transfer1 = new TransferEntity();
        transfer1.setId(1L);
        transfer1.setCode("TSF00001");
        transfer1.setName("Transfer 001");
        transfer1.setType(0);
        transfer1.setFromAccountCode("ACT00001");
        transfer1.setToAccountCode("ACT00002");
        transfer1.setAmount(5000.00000001);
        transfer1.setMatter(1);
        transfer1.setComment("This is a transfer");
        transfer1.setDeleted(false);
        transfer1.setUpdatedAt(LocalDateTime.now());
        transfer1.setCreatedAt(LocalDateTime.now());

        return Lists.newArrayList(transfer1);
    }

    @Test
    public void testSelectByPage() {
        TransferCriteria transferCriteria = generate(TransferCriteria.class);
        transferMapper.selectByPage(transferCriteria, new PageRequest());
    }

    @Test
    public void testCountByQuery() {
        TransferCriteria transferCriteria = generate(TransferCriteria.class);
        transferMapper.countByQuery(transferCriteria);
    }
}
