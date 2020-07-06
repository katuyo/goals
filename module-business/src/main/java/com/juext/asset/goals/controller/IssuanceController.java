package com.juext.shop.base.controller;

import org.featx.spec.model.*;
import com.juext.shop.base.endpoint.IssuanceEndpoint;
import com.juext.shop.base.facade.IssuanceFacade;
import com.juext.shop.base.model.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:54
 */
@RestController
public class IssuanceController implements IssuanceEndpoint {

    @Resource
    private IssuanceFacade IssuanceFacade;

    @Override
    public BaseResponse<Coded> save(IssuanceSaveRequest saveRequest) {
        return BaseResponse.succeeded(IssuanceFacade.save(saveRequest));
    }

    @Override
    public BaseResponse<Coded> put(IssuanceSaveRequest saveRequest) {
        return BaseResponse.succeeded(IssuanceFacade.update(saveRequest));
    }

    @Override
    public BaseResponse<Void> delete(String code) {
        IssuanceFacade.delete(code);
        return BaseResponse.succeeded();
    }

    @Override
    public BaseResponse<IssuanceInfo> getByCode(String code) {
        return BaseResponse.succeeded(IssuanceFacade.getByCode(code));
    }

    @Override
    public ListResponse<IssuanceInfo> listByCode(List<String> codes) {
        return ListResponse.succeeded(IssuanceFacade.listByCodes(codes));
    }

    @Override
    public PageResponse<IssuanceItem> page(IssuancePageQueryRequest pageQueryRequest) {
        QuerySection<IssuanceItem> querySection = IssuanceFacade.page(pageQueryRequest);
        return PageResponse.succeeded(querySection.list())
                .page(pageQueryRequest.getPage())
                .total(querySection.getTotal());
    }

}
