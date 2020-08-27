package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.mapper.TransferMapper;
import com.juext.asset.goals.query.TransferCriteria;
import org.featx.spec.entity.AbstractUnified;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
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
public class TransferServiceImpl implements TransferService {

    @Resource
    private TransferMapper transferMapper;

    @Resource
    private IdGenerate idGenerate;

    @Override
    @Transactional
    public void save(TransferEntity transferEntity) {
        transferEntity.setCode(String.format("%s%s", "DFM", Long.toString(idGenerate.nextId(), 36)));
        transferMapper.insert(transferEntity);
    }

    @Override
    public void delete(String code) {
        transferMapper.delete(code, true);
    }

    @Override
    public TransferEntity findOne(String code) {
        return transferMapper.selectByCode(code);
    }

    @Override
    public List<TransferEntity> listByCodes(final List<String> codes) {
        final List<TransferEntity> result = Lists.newArrayList();
        if (CollectionUtil.isEmpty(codes)) {
            return result;
        }
        return Optional.of(transferMapper.selectByCodes(codes))
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
    public QuerySection<TransferEntity> page(TransferCriteria criteria, PageRequest pageRequest) {
        List<TransferEntity> moduleEntities = transferMapper.selectByPage(criteria, pageRequest);
        long count = transferMapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
