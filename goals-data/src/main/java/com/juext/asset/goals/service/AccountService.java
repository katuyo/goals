package com.juext.asset.goals.service;

import com.juext.asset.goals.entity.AccountEntity;
import com.juext.asset.goals.query.AccountCriteria;
import org.featx.spec.model.PageRequest;
import org.featx.spec.model.QuerySection;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:57
 */
public interface AccountService {

    void save(AccountEntity AccountEntity);

    void update(AccountEntity AccountEntity);

    void delete(String code);

    AccountEntity findOne(String code);

    List<AccountEntity> listByCodes(List<String> codes);

    QuerySection<AccountEntity> page(AccountCriteria criteria, PageRequest pageRequest);
}
