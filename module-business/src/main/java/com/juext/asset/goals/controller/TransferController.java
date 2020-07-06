package com.juext.asset.goals.controller;

import org.featx.spec.model.*;
import com.juext.asset.goals.endpoint.TransferEndpoint;
import com.juext.asset.goals.facade.TransferFacade;
import com.juext.asset.goals.model.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:54
 */
@RestController
public class TransferController implements TransferEndpoint {

    @Resource
    private TransferFacade TransferFacade;

    @Override
    public BaseResponse<Coded> save(TransferSaveRequest saveRequest) {
        return BaseResponse.succeeded(TransferFacade.save(saveRequest));
    }

    @Override
    public BaseResponse<Coded> put(TransferSaveRequest saveRequest) {
        return BaseResponse.succeeded(TransferFacade.update(saveRequest));
    }

    @Override
    public BaseResponse<Void> delete(String code) {
        TransferFacade.delete(code);
        return BaseResponse.succeeded();
    }

    @Override
    public BaseResponse<TransferInfo> getByCode(String code) {
        return BaseResponse.succeeded(TransferFacade.getByCode(code));
    }

    @Override
    public ListResponse<TransferInfo> listByCode(List<String> codes) {
        return ListResponse.succeeded(TransferFacade.listByCodes(codes));
    }

    @Override
    public PageResponse<TransferItem> page(TransferPageQueryRequest pageQueryRequest) {
        QuerySection<TransferItem> querySection = TransferFacade.page(pageQueryRequest);
        return PageResponse.succeeded(querySection.list())
                .page(pageQueryRequest.getPage())
                .total(querySection.getTotal());
    }

}
