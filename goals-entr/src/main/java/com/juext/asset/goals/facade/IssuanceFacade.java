package com.juext.asset.goals.facade;

import com.juext.asset.goals.spec.model.IssuanceInfo;
import com.juext.asset.goals.spec.model.IssuanceItem;
import com.juext.asset.goals.spec.model.IssuancePageQueryRequest;
import com.juext.asset.goals.spec.model.IssuanceSaveRequest;
import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;

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
