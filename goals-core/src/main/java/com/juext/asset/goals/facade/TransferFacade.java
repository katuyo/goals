package com.juext.asset.goals.facade;

import com.juext.asset.goals.spec.model.TransferInfo;
import com.juext.asset.goals.spec.model.TransferItem;
import com.juext.asset.goals.spec.model.TransferPageQueryRequest;
import com.juext.asset.goals.spec.model.TransferSaveRequest;
import org.featx.spec.model.Coded;
import org.featx.spec.model.QuerySection;

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
