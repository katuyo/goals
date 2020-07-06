package com.juext.asset.goals.facade;

import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import com.juext.asset.goals.model.IssuanceInfo;
import com.juext.asset.goals.model.IssuanceItem;
import com.juext.asset.goals.model.IssuancePageQueryRequest;
import com.juext.asset.goals.model.IssuanceSaveRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:56
 */
public interface IssuanceFacade {

    Coded save(IssuanceSaveRequest saveRequest);

    Coded update(IssuanceSaveRequest saveRequest);

    void delete(String IssuanceCode);

    IssuanceInfo getByCode(String code);

    List<IssuanceInfo> listByCodes(List<String> codes);

    QuerySection<IssuanceItem> page(IssuancePageQueryRequest pageQueryRequest);
}
