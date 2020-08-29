package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.TransferEntity;
import com.juext.asset.goals.mapper.TransferMapper;
import com.juext.asset.goals.query.TransferCriteria;
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

import static com.juext.asset.goals.Constant.CODE_PREFIX_TRANSFER;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;

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
        transferEntity.setCode(String.format("%s%s", CODE_PREFIX_TRANSFER,
                Long.toString(idGenerate.nextId(), DEFAULT_RADIX)));
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
        final List<String> codeList = StringUtil.dropBlank(codes);
        if (codeList.isEmpty()) {
            return Lists.newArrayList();
        }
        return Optional.of(transferMapper.selectByCodes(codeList))
                .filter(CollectionUtil::isNotEmpty)
                .map(list -> list.stream().sorted(Comparator.comparingInt(a -> codeList.indexOf(a.getCode())))
                        .collect(Collectors.toList()))
                .orElseGet(Lists::newArrayList);
    }

    @Override
    @Transactional(readOnly = true)
    public QuerySection<TransferEntity> page(TransferCriteria criteria, PageRequest pageRequest) {
        List<TransferEntity> moduleEntities = transferMapper.selectByPage(criteria, pageRequest);
        long count = transferMapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
