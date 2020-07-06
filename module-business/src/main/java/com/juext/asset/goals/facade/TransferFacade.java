package com.juext.shop.base.facade;

import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;
import com.juext.shop.base.model.TransferInfo;
import com.juext.shop.base.model.TransferItem;
import com.juext.shop.base.model.TransferPageQueryRequest;
import com.juext.shop.base.model.TransferSaveRequest;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:56
 */
public interface TransferFacade {

    Coded save(TransferSaveRequest saveRequest);

    Coded update(TransferSaveRequest saveRequest);

    void delete(String TransferCode);

    TransferInfo getByCode(String code);

    List<TransferInfo> listByCodes(List<String> codes);

    QuerySection<TransferItem> page(TransferPageQueryRequest pageQueryRequest);
}
