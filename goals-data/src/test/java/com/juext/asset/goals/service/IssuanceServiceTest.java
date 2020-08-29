package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.IssuanceEntity;
import com.juext.asset.goals.mapper.IssuanceMapper;
import com.juext.asset.goals.query.IssuanceCriteria;
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

import static com.juext.asset.goals.Constant.CODE_PREFIX_ISSUANCE;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Excepts
 * @since 2020/7/7 0:34
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Service: Issuance")
public class IssuanceServiceTest {
    @InjectMocks
    private final IssuanceService issuanceService = new IssuanceServiceImpl();
    @Mock
    private IssuanceMapper issuanceMapper;
    @Mock
    private IdGenerate idGenerate;

    @Test
    void testSave() {
        when(issuanceMapper.insert(any(IssuanceEntity.class))).thenReturn(1);
        when(idGenerate.nextId()).thenReturn(123456789L);
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceService.save(issuanceEntity);
        issuanceEntity.setCode(String.format("%s%s", CODE_PREFIX_ISSUANCE,
                Long.toString(123456789L, DEFAULT_RADIX)));
        verify(issuanceMapper).insert(issuanceEntity);
        verify(idGenerate).nextId();
        clearInvocations(issuanceMapper, idGenerate);

        when(issuanceMapper.upsert(any(IssuanceEntity.class))).thenReturn(1);
        issuanceService.save(issuanceEntity);
        verify(issuanceMapper).upsert(issuanceEntity);
        clearInvocations(issuanceMapper);
    }

    @Test
    void testUpdate() {
        when(issuanceMapper.update(any(IssuanceEntity.class))).thenReturn(1);
        IssuanceEntity updatingIssuanceEntity = new IssuanceEntity();
        issuanceService.update(updatingIssuanceEntity);
        verify(issuanceMapper).update(updatingIssuanceEntity);
        clearInvocations(issuanceMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ISC00001", "", "ISC0002", "-"})
    void testDelete(String code) {
        when(issuanceMapper.delete(anyString(), anyBoolean())).thenReturn(1);
        issuanceService.delete(code);
        verify(issuanceMapper).delete(code, true);
        clearInvocations(issuanceMapper);
    }

    @ParameterizedTest
    @ValueSource(strings = {",", "ISC0001", ""})
    void testFindOne(String code) {
        IssuanceEntity foundIssuanceEntity = new IssuanceEntity();
        when(issuanceMapper.selectByCode(code)).thenReturn(foundIssuanceEntity);
        assertEquals(foundIssuanceEntity, issuanceService.findOne(code));
        verify(issuanceMapper).selectByCode(code);
        clearInvocations(issuanceMapper);
    }

    private IssuanceEntity toEntity(String code) {
        IssuanceEntity issuanceEntity = new IssuanceEntity();
        issuanceEntity.setCode(code);
        return issuanceEntity;
    }

    @ParameterizedTest
    @ValueSource(strings = {"", ",", "ISC32432,", "ISC0432,ISC3824",
            "ISC01032,ISC943232,ISC43208432,ISC43828,ISC84323,ISC889"})
    void testListByCodes(String codes) {
        List<String> issuanceCodes = Arrays.stream(codes.split(","))
                .filter(StringUtil::isNotBlank).collect(Collectors.toList());
        if (issuanceCodes.isEmpty()) {
            assertEquals(Lists.newArrayList(), issuanceService.listByCodes(issuanceCodes));
            return;
        }
        List<IssuanceEntity> issuanceEntities = issuanceCodes.parallelStream()
                .map(this::toEntity).collect(Collectors.toList());
        when(issuanceMapper.selectByCodes(issuanceCodes)).thenReturn(issuanceEntities);
        List<IssuanceEntity> resultList = issuanceCodes.stream().map(this::toEntity)
                .collect(Collectors.toList());
        assertEquals(resultList, issuanceService.listByCodes(issuanceCodes));
        verify(issuanceMapper).selectByCodes(issuanceCodes);
        clearInvocations(issuanceMapper);
    }

    @Test
    void testPage() {
        long count = 21L;

        when(issuanceMapper.countByQuery(isA(IssuanceCriteria.class))).thenReturn(count);
        List<IssuanceEntity> list = Lists.newArrayList();
        when(issuanceMapper.selectByPage(isA(IssuanceCriteria.class), isA(PageRequest.class)))
                .thenReturn(list);

        IssuanceCriteria criteria = new IssuanceCriteria();
        PageRequest pageRequest = new PageRequest();

        assertEquals(QuerySection.of(list).total(count),
                issuanceService.page(criteria, pageRequest));
        verify(issuanceMapper, times(1)).countByQuery(criteria);
        verify(issuanceMapper, times(1)).selectByPage(criteria, pageRequest);
        clearInvocations(issuanceMapper);
    }
}
