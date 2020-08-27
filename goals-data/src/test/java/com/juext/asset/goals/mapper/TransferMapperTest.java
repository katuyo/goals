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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * @author Excepts
 * @since 2020/7/6 22:49
 */
@DisplayName("Mapper: Transfer")
@EnableAutoConfiguration
class TransferMapperTest extends SpringDataTestSuit {
    @Resource
    private TransferMapper transferMapper;

    @ParameterizedTest(name = "Insert Transfer")
    @CsvSource({"TSF00005"})
    void testInsert(String code) {
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
        transferEntity.setDeleted(foundTransfer.getDeleted());
        transferEntity.setCreatedAt(foundTransfer.getCreatedAt());
        transferEntity.setUpdatedAt(foundTransfer.getUpdatedAt());

        assertEquals(transferEntity, foundTransfer);
    }

    @Test
    @DisplayName("Delete Transfer")
    void testDelete() {
        assertEquals(0, transferMapper.delete("TSF00005", true));
        assertEquals(0, transferMapper.delete("TSF00005", false));

        assertEquals(1, transferMapper.delete("TSF00001", true));
        assertNull(transferMapper.selectByCode("TSF00001"));
        assertEquals(1, transferMapper.delete("TSF00001", false));
        assertNotNull(transferMapper.selectByCode("TSF00001"));

    }

    @Test@DisplayName("Query By Ref")
    void testSelectByReference() {
        assertNull(transferMapper.selectByReference("", ""));
        assertNotNull(transferMapper.selectByReference("Order", "ORD912hjr43hnfda"));
    }



    @Test
    @DisplayName("Query transfers by codes")
    void testSelectByCodes() {
        List<TransferEntity> list = transferMapper.selectByCodes(Lists.newArrayList("TSF00001", "TSF00002", "TSF00003", "TSF00004"));
        list.forEach(transferEntity -> {
            transferEntity.setCreatedAt(null);
            transferEntity.setUpdatedAt(null);
        });
        assertEquals(preparedIssuanceList(), list);
    }

    private List<TransferEntity> preparedIssuanceList() {
        TransferEntity transfer1 = new TransferEntity();
        transfer1.setId(1L);
        transfer1.setCode("TSF00001");
        transfer1.setName("Transfer1");
        transfer1.setType(1);
        transfer1.setFromAccountCode("ACT00001");
        transfer1.setToAccountCode("ACT00002");
        transfer1.setAmount(101.01);
        transfer1.setMatter(1);
        transfer1.setReference("Order");
        transfer1.setReferenceCode("ORD912hjr43hnfda");
        transfer1.setComment("comment1");
        transfer1.setDeleted(false);

        TransferEntity transfer2 = new TransferEntity();
        transfer2.setId(2L);
        transfer2.setCode("TSF00002");
        transfer2.setName("Transfer2");
        transfer2.setType(2);
        transfer2.setFromAccountCode("ACT00001");
        transfer2.setToAccountCode("ACT00002");
        transfer2.setAmount(202.02);
        transfer2.setMatter(2);
        transfer2.setReference("Campaign");
        transfer2.setReferenceCode("CPN3294ueh2jfda");
        transfer2.setComment("comment2");
        transfer2.setDeleted(false);

        TransferEntity transfer3 = new TransferEntity();
        transfer3.setId(3L);
        transfer3.setCode("TSF00003");
        transfer3.setName("Transfer3");
        transfer3.setType(3);
        transfer3.setFromAccountCode("ACT00001");
        transfer3.setToAccountCode("ACT00002");
        transfer3.setAmount(303.03);
        transfer3.setMatter(3);
        transfer3.setReference("Award");
        transfer3.setReferenceCode("AWD432j2fh92");
        transfer3.setComment("comment3");
        transfer3.setDeleted(false);

        TransferEntity transfer4 = new TransferEntity();
        transfer4.setId(4L);
        transfer4.setCode("TSF00004");
        transfer4.setName("Transfer4");
        transfer4.setType(4);
        transfer4.setFromAccountCode("ACT00001");
        transfer4.setToAccountCode("ACT00002");
        transfer4.setAmount(404.04);
        transfer4.setMatter(4);
        transfer4.setReference("Default");
        transfer4.setReferenceCode("DFT8329urh");
        transfer4.setComment("comment4");
        transfer4.setDeleted(false);
        return Lists.newArrayList(transfer1, transfer2, transfer3, transfer4);
    }

    @Test
    void testSelectByPage() {
        TransferCriteria transferCriteria = generate(TransferCriteria.class);
        transferMapper.selectByPage(transferCriteria, new PageRequest());
    }

    @Test
    void testCountByQuery() {
        TransferCriteria transferCriteria = generate(TransferCriteria.class);
        transferMapper.countByQuery(transferCriteria);
    }
}
