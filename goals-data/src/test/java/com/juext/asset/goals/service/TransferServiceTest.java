package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.mapper.TransferMapper;
import com.juext.asset.goals.query.TransferCriteria;
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

import static com.juext.asset.goals.Constant.CODE_PREFIX_TRANSFER;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Excepts
 * @since 2020/7/7 0:35
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Service: Transfer")
public class TransferServiceTest {
    @InjectMocks
    private final TransferService transferService = new TransferServiceImpl();
    @Mock
    private TransferMapper transferMapper;
    @Mock
    private IdGenerate idGenerate;

    @Test
    void testSave() {
        when(transferMapper.insert(any(TransferEntity.class))).thenReturn(1);
        when(idGenerate.nextId()).thenReturn(123456780L);
        TransferEntity transferEntity = new TransferEntity();
        transferService.save(transferEntity);
        transferEntity.setCode(String.format("%s%s", CODE_PREFIX_TRANSFER,
                Long.toString(123456780L, DEFAULT_RADIX)));
        verify(transferMapper).insert(transferEntity);
        verify(idGenerate).nextId();
        clearInvocations(transferMapper, idGenerate);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ISC00001", "", "ISC0002", "-"})
    void testDelete(String code) {
        when(transferMapper.delete(anyString(), anyBoolean())).thenReturn(1);
        transferService.delete(code);
        verify(transferMapper).delete(code, true);
        clearInvocations(transferMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {",", "ISC0001", ""})
    void testFindOne(String code) {
        TransferEntity foundTransferEntity = new TransferEntity();
        when(transferMapper.selectByCode(code)).thenReturn(foundTransferEntity);
        assertEquals(foundTransferEntity, transferService.findOne(code));
        verify(transferMapper).selectByCode(code);
        clearInvocations(transferMapper);
    }

    private TransferEntity toEntity(String code) {
        TransferEntity transferEntity = new TransferEntity();
        transferEntity.setCode(code);
        return transferEntity;
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ",", "TSF32432,", "TSF0432,TSF3824",
            "TSF01032,TSF943232,TSF43208432,TSF43828,TSF84323,TSF889"})
    void testListByCodes(String codes) {
        List<String> transferCodes = Arrays.stream(codes.split(","))
                .filter(StringUtil::isNotBlank).collect(Collectors.toList());
        if (transferCodes.isEmpty()) {
            assertEquals(Lists.newArrayList(), transferService.listByCodes(transferCodes));
            return;
        }
        List<TransferEntity> transferEntities = transferCodes.parallelStream()
                .map(this::toEntity).collect(Collectors.toList());
        when(transferMapper.selectByCodes(transferCodes)).thenReturn(transferEntities);
        List<TransferEntity> resultList = transferCodes.stream().map(this::toEntity)
                .collect(Collectors.toList());
        assertEquals(resultList, transferService.listByCodes(transferCodes));
        verify(transferMapper).selectByCodes(transferCodes);
        clearInvocations(transferMapper);
    }

    @Test
    void testPage() {
        long count = 11L;

        when(transferMapper.countByQuery(isA(TransferCriteria.class))).thenReturn(count);
        List<TransferEntity> list = Lists.newArrayList();
        when(transferMapper.selectByPage(isA(TransferCriteria.class), isA(PageRequest.class)))
                .thenReturn(list);

        TransferCriteria criteria = new TransferCriteria();
        PageRequest pageRequest = new PageRequest();

        assertEquals(QuerySection.of(list).total(count),
                transferService.page(criteria, pageRequest));
        verify(transferMapper, times(1)).countByQuery(criteria);
        verify(transferMapper, times(1)).selectByPage(criteria, pageRequest);
        clearInvocations(transferMapper);
    }
}
