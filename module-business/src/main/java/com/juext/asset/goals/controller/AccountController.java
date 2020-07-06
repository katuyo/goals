package com.juext.shop.base.controller;

import org.featx.spec.model.*;
import com.juext.shop.base.endpoint.AccountEndpoint;
import com.juext.shop.base.facade.AccountFacade;
import com.juext.shop.base.model.*;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/12 13:54
 */
@RestController
public class AccountController implements AccountEndpoint {

    @Resource
    private AccountFacade AccountFacade;

    @Override
    public BaseResponse<Coded> save(AccountSaveRequest saveRequest) {
        return BaseResponse.succeeded(AccountFacade.save(saveRequest));
    }

    @Override
    public BaseResponse<Coded> put(AccountSaveRequest saveRequest) {
        return BaseResponse.succeeded(AccountFacade.update(saveRequest));
    }

    @Override
    public BaseResponse<Void> delete(String code) {
        AccountFacade.delete(code);
        return BaseResponse.succeeded();
    }

    @Override
    public BaseResponse<AccountInfo> getByCode(String code) {
        return BaseResponse.succeeded(AccountFacade.getByCode(code));
    }

    @Override
    public ListResponse<AccountInfo> listByCode(List<String> codes) {
        return ListResponse.succeeded(AccountFacade.listByCodes(codes));
    }

    @Override
    public PageResponse<AccountItem> page(AccountPageQueryRequest pageQueryRequest) {
        QuerySection<AccountItem> querySection = AccountFacade.page(pageQueryRequest);
        return PageResponse.succeeded(querySection.list())
                .page(pageQueryRequest.getPage())
                .total(querySection.getTotal());
    }

}
