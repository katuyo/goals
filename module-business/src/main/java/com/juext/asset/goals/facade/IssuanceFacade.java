package com.juext.shop.base.facade;

import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import com.juext.shop.base.model.IssuanceInfo;
import com.juext.shop.base.model.IssuanceItem;
import com.juext.shop.base.model.IssuancePageQueryRequest;
import com.juext.shop.base.model.IssuanceSaveRequest;

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
