package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.IssuanceEntity;
import com.juext.asset.goals.mapper.IssuanceMapper;
import com.juext.asset.goals.query.IssuanceCriteria;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import org.featx.spec.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.juext.asset.goals.Constant.CODE_PREFIX_ISSUANCE;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;

/**
 * @author Excepts
 * @since 2020/4/12 14:27
 */
@Service
public class IssuanceServiceImpl implements IssuanceService {

    @Resource
    private IssuanceMapper issuancemapper;

    @Resource
    private IdGenerate idGenerate;

    @Override
    @Transactional
    public void save(IssuanceEntity issuanceEntity) {
        if (StringUtil.isBlank(issuanceEntity.getCode())) {
            issuanceEntity.setCode(String.format("%s%s", CODE_PREFIX_ISSUANCE,
                    Long.toString(idGenerate.nextId(), DEFAULT_RADIX)));
            issuancemapper.insert(issuanceEntity);
        } else {
            issuancemapper.upsert(issuanceEntity);
        }
    }

    @Override
    @Transactional
    public void update(IssuanceEntity issuanceEntity) {
        issuancemapper.update(issuanceEntity);
    }

    @Override
    public void delete(String code) {
        issuancemapper.delete(code, true);
    }

    @Override
    public IssuanceEntity findOne(String code) {
        return issuancemapper.selectByCode(code);
    }

    @Override
    public List<IssuanceEntity> listByCodes(final List<String> codes) {
        final List<String> codeList = StringUtil.dropBlank(codes);
        if (codeList.isEmpty()) {
            return Lists.newArrayList();
        }
        return Optional.of(issuancemapper.selectByCodes(codeList))
                .filter(CollectionUtil::isNotEmpty)
                .map(list -> list.stream().sorted(Comparator.comparingInt(a -> codeList.indexOf(a.getCode())))
                        .collect(Collectors.toList()))
                .orElseGet(Lists::newArrayList);
    }

    @Override
    @Transactional(readOnly = true)
    public QuerySection<IssuanceEntity> page(IssuanceCriteria criteria, PageRequest pageRequest) {
        List<IssuanceEntity> moduleEntities = issuancemapper.selectByPage(criteria, pageRequest);
        long count = issuancemapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
