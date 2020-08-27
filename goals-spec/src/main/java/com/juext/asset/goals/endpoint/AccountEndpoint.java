package com.juext.asset.goals.endpoint;


import com.juext.asset.goals.model.AccountInfo;
import com.juext.asset.goals.model.AccountItem;
import com.juext.asset.goals.model.AccountPageQueryRequest;
import com.juext.asset.goals.model.AccountSaveRequest;
import org.featx.spec.model.BaseResponse;
import org.featx.spec.model.Coded;
import org.featx.spec.model.ListResponse;
import org.featx.spec.model.PageResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/11 22:23
 */
@RequestMapping("/account")
public interface AccountEndpoint {
    /**
     * Create or update a Account module,
     * if code not provided, persistent this module with all of the properties
     * else if code existed, update all of the properties as saveRequest provided
     * else Error entity not found
     * @param saveRequest AccountSaveRequest Account module with all properties
     * @return BaseResponse<Coded> The Base response structure with saved module's business code
     */
    @PostMapping
    @ResponseBody
    BaseResponse<Coded> save(@RequestBody AccountSaveRequest saveRequest);

    @PutMapping
    @ResponseBody
    BaseResponse<Coded> put(@RequestBody AccountSaveRequest saveRequest);

    @DeleteMapping
    @ResponseBody
    BaseResponse<Void> delete(@RequestParam String code);

    @GetMapping
    @ResponseBody
    BaseResponse<AccountInfo> getByCode(@RequestParam("code") String code);

    @GetMapping("/list")
    @ResponseBody
    ListResponse<AccountInfo> listByCode(@RequestParam("codes") List<String> codes);

    @GetMapping("/page")
    @ResponseBody
    PageResponse<AccountItem> page(@RequestBody AccountPageQueryRequest pageQueryRequest);

//    @GetMapping("/flow")
//    @ResponseBody
//    FlowResponse<AccountItem> flow(@RequestBody AccountFlowQueryRequest flowQueryRequest);
//
//    @GetMapping("/roll")
//    @ResponseBody
//    RollResponse<AccountItem> scroll(@RequestBody AccountFlowQueryRequest flowQueryRequest);
}
