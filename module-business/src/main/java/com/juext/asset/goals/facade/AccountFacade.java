package com.juext.asset.goals.facade;

import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import com.juext.asset.goals.model.AccountInfo;
import com.juext.asset.goals.model.AccountItem;
import com.juext.asset.goals.model.AccountPageQueryRequest;
import com.juext.asset.goals.model.AccountSaveRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:56
 */
public interface AccountFacade {

    Coded save(AccountSaveRequest saveRequest);

    Coded update(AccountSaveRequest saveRequest);

    void delete(String AccountCode);

    AccountInfo getByCode(String code);

    List<AccountInfo> listByCodes(List<String> codes);

    QuerySection<AccountItem> page(AccountPageQueryRequest pageQueryRequest);
}
