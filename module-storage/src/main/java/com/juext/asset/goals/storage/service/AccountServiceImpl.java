package com.juext.shop.base.storage.service;

import com.google.common.collect.Lists;
import org.featx.spec.entity.AbstractUnified;
import org.featx.spec.feature.IdGenerate;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import org.featx.spec.util.StringUtil;
import com.juext.shop.base.storage.entity.AccountEntity;
import com.juext.shop.base.storage.mapper.AccountMapper;
import com.juext.shop.base.storage.query.AccountCriteria;
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
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper AccountMapper;

    @Resource
    private IdGenerate idGenerate;
    @Override
    @Transactional
    public void save(AccountEntity AccountEntity) {
        if (StringUtil.isBlank(AccountEntity.getCode())) {
            AccountEntity.setCode(String.format("%s%s", "DFM", Long.toString(idGenerate.nextId(), 36)));
            AccountMapper.insert(AccountEntity);
        } else {
            AccountMapper.upsert(AccountEntity);
        }
    }

    @Override
    @Transactional
    public void update(AccountEntity AccountEntity) {
        AccountMapper.update(AccountEntity);
    }

    @Override
    public void delete(String code) {
        AccountMapper.delete(code, true);
    }

    @Override
    public AccountEntity findOne(String code) {
        return AccountMapper.selectByCode(code);
    }

    @Override
    public List<AccountEntity> listByCodes(final List<String> codes) {
        final List<AccountEntity> result = Lists.newArrayList();
        if (CollectionUtil.isEmpty(codes)) {
            return result;
        }
        return Optional.of(AccountMapper.selectByCodes(codes))
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
    public QuerySection<AccountEntity> page(AccountCriteria criteria, PageRequest pageRequest) {
        List<AccountEntity> moduleEntities = AccountMapper.selectByPage(criteria, pageRequest);
        long count = AccountMapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
