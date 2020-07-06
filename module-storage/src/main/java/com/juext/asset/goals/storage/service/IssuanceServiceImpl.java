package com.juext.asset.goals.storage.service;

import com.google.common.collect.Lists;
import org.featx.spec.entity.AbstractUnified;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import org.featx.spec.util.StringUtil;
import com.juext.asset.goals.storage.entity.IssuanceEntity;
import com.juext.asset.goals.storage.mapper.IssuanceMapper;
import com.juext.asset.goals.storage.query.IssuanceCriteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Excepts
 * @since 2020/4/12 14:27
 */
@Service
public class IssuanceServiceImpl implements IssuanceService {

    @Resource
    private IssuanceMapper IssuanceMapper;

    @Resource
    private IdGenerate idGenerate;
    @Override
    @Transactional
    public void save(IssuanceEntity IssuanceEntity) {
        if (StringUtil.isBlank(IssuanceEntity.getCode())) {
            IssuanceEntity.setCode(String.format("%s%s", "DFM", Long.toString(idGenerate.nextId(), 36)));
            IssuanceMapper.insert(IssuanceEntity);
        } else {
            IssuanceMapper.upsert(IssuanceEntity);
        }
    }

    @Override
    @Transactional
    public void update(IssuanceEntity IssuanceEntity) {
        IssuanceMapper.update(IssuanceEntity);
    }

    @Override
    public void delete(String code) {
        IssuanceMapper.delete(code, true);
    }

    @Override
    public IssuanceEntity findOne(String code) {
        return IssuanceMapper.selectByCode(code);
    }

    @Override
    public List<IssuanceEntity> listByCodes(final List<String> codes) {
        final List<IssuanceEntity> result = Lists.newArrayList();
        if (CollectionUtil.isEmpty(codes)) {
            return result;
        }
        return Optional.of(IssuanceMapper.selectByCodes(codes))
                .filter(CollectionUtil::isNotEmpty)
                .map(entities -> entities.stream()
                        .collect(Collectors.toMap(AbstractUnified::getCode, Function.identity())))
                .map(map -> {
                    codes.forEach(c -> Optional.of(map.get(c)).ifPresent(result::add));
                    return result;
                }).orElse(result);
    }

    @Override
    @Transactional(readOnly = true)
    public QuerySection<IssuanceEntity> page(IssuanceCriteria criteria, PageRequest pageRequest) {
        List<IssuanceEntity> moduleEntities = IssuanceMapper.selectByPage(criteria, pageRequest);
        long count = IssuanceMapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
