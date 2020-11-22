package com.juext.asset.goals.facade;

import com.juext.asset.goals.endpoint.AccountEndpoint;
import com.juext.asset.goals.model.AccountInfo;
import com.juext.asset.goals.model.AccountItem;
import com.juext.asset.goals.model.AccountPageQueryRequest;
import com.juext.asset.goals.model.AccountSaveRequest;

import org.featx.spec.feature.ModelConvert;
import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import org.featx.spec.util.CollectionUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Excepts
 * @since 2020/4/13 0:09
 */
@Component
public class AccountFacadeImpl implements AccountFacade {

    @Resource
    private AccountEndpoint accountEndpoint;

    @Resource
    private ModelConvert modelConvert;

    @Override
    public Coded save(AccountSaveRequest saveRequest) {
        final Account account = modelConvert.convert(saveRequest, AccountEntity.class);
        accountEndpoint.save(AccountEntity);
        return AccountEntity::getCode;
    }

    @Override
    public Coded update(AccountSaveRequest saveRequest) {
        final AccountEntity AccountEntity = modelConvert.convert(saveRequest, AccountEntity.class);
        accountEndpoint.update(AccountEntity);
        return AccountEntity::getCode;
    }

    @Override
    public void delete(String AccountCode) {
        AccountService.delete(AccountCode);
    }

    @Override
    public AccountInfo getByCode(String code) {
        return Optional.of(accountEndpoint.findOne(code))
                .map(entity -> modelConvert.convert(entity, AccountInfo.class))
                .orElse(null);
    }

    @Override
    public List<AccountInfo> listByCodes(List<String> codes) {
        return Optional.of(accountEndpoint.listByCodes(codes))
                .filter(CollectionUtil::isNotEmpty)
                .map(l -> l.stream().map(e->modelConvert.convert(e, AccountInfo.class))
                                    .collect(Collectors.toList()))
                .orElse(null);
    }

    @Override
    public QuerySection<AccountItem> page(AccountPageQueryRequest pageQueryRequest) {
        return accountEndpoint.page(modelConvert.convert(pageQueryRequest, AccountCriteria.class), pageQueryRequest)
                .convertAsList(e->modelConvert.convert(e, AccountItem.class));
    }
}
