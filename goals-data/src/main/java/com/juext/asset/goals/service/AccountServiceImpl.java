package com.juext.asset.goals.service;

import com.google.common.collect.Lists;
import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.error.GoalsErrorEnum;
import com.juext.asset.goals.mapper.AccountMapper;
import com.juext.asset.goals.query.AccountCriteria;
import org.featx.spec.error.BusinessException;
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

import static com.juext.asset.goals.Constant.CODE_PREFIX_ACCOUNT;
import static com.juext.asset.goals.Constant.DEFAULT_RADIX;

/**
 * @author Excepts
 * @since 2020/4/12 14:27
 */
@Service
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountMapper accountMapper;

    @Resource
    private IdGenerate idGenerate;

    @Override
    @Transactional
    public void save(AccountEntity accountEntity) {
        if (StringUtil.isBlank(accountEntity.getCode())) {
            accountEntity.setCode(String.format("%s%s", CODE_PREFIX_ACCOUNT,
                    Long.toString(idGenerate.nextId(), DEFAULT_RADIX)));
            accountMapper.insert(accountEntity);
        } else {
            accountMapper.upsert(accountEntity);
        }
    }

    @Override
    @Transactional
    public void update(AccountEntity accountEntity) {
        accountMapper.update(accountEntity);
    }

    @Override
    public void delete(String code) {
        accountMapper.delete(code, true);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void transfer(String fromAccountCode, String toAccountCode, Double amount) {
        List<AccountEntity> accountEntities =
                accountMapper.selectByCodes(Lists.newArrayList(fromAccountCode, toAccountCode));
        if (accountEntities == null || accountEntities.size() < 2) {
            throw BusinessException.of(GoalsErrorEnum.ACCOUNT_NOT_FOUND);
        }
        AccountEntity fromEntity = accountEntities.get(0);
        if (fromEntity.getInventory() < amount) {
            throw BusinessException.of(GoalsErrorEnum.INVENTORY_NOT_ENOUGH);
        }
        AccountEntity toEntity = accountEntities.get(1);
        fromEntity.setInventory(fromEntity.getInventory() - amount);
        toEntity.setInventory(toEntity.getInventory() + amount);
        accountMapper.update(fromEntity);
        accountMapper.update(toEntity);
    }

    @Override
    public AccountEntity findOne(String code) {
        return accountMapper.selectByCode(code);
    }

    @Override
    public List<AccountEntity> listByCodes(final List<String> codes) {
        final List<String> codeList = StringUtil.dropBlank(codes);
        if (codeList.isEmpty()) {
            return Lists.newArrayList();
        }
        return Optional.of(accountMapper.selectByCodes(codeList))
                .filter(CollectionUtil::isNotEmpty)
                .map(list -> list.stream().sorted(Comparator.comparingInt(a -> codeList.indexOf(a.getCode())))
                        .collect(Collectors.toList()))
                .orElseGet(Lists::newArrayList);
    }

    @Override
    @Transactional(readOnly = true)
    public QuerySection<AccountEntity> page(AccountCriteria criteria, PageRequest pageRequest) {
        List<AccountEntity> moduleEntities = accountMapper.selectByPage(criteria, pageRequest);
        long count = accountMapper.countByQuery(criteria);
        return QuerySection.of(moduleEntities).total(count);
    }
}
