package com.juext.asset.goals.endpoint;


import com.juext.asset.goals.model.IssuanceInfo;
import com.juext.asset.goals.model.IssuanceItem;
import com.juext.asset.goals.model.IssuancePageQueryRequest;
import com.juext.asset.goals.model.IssuanceSaveRequest;
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
@RequestMapping("/issuance")
public interface IssuanceEndpoint {
    /**
     * Create or update a Issuance module,
     * if code not provided, persistent this module with all of the properties
     * else if code existed, update all of the properties as saveRequest provided
     * else Error entity not found
     * @param saveRequest IssuanceSaveRequest Issuance module with all properties
     * @return BaseResponse<Coded> The Base response structure with saved module's business code
     */
    @PostMapping
    @ResponseBody
    BaseResponse<Coded> save(@RequestBody IssuanceSaveRequest saveRequest);

    @PutMapping
    @ResponseBody
    BaseResponse<Coded> put(@RequestBody IssuanceSaveRequest saveRequest);

    @DeleteMapping
    @ResponseBody
    BaseResponse<Void> delete(@RequestParam String code);

    @GetMapping
    @ResponseBody
    BaseResponse<IssuanceInfo> getByCode(@RequestParam("code") String code);

    @GetMapping("/list")
    @ResponseBody
    ListResponse<IssuanceInfo> listByCode(@RequestParam("codes") List<String> codes);

    @GetMapping("/page")
    @ResponseBody
    PageResponse<IssuanceItem> page(@RequestBody IssuancePageQueryRequest pageQueryRequest);

//    @GetMapping("/flow")
//    @ResponseBody
//    FlowResponse<IssuanceItem> flow(@RequestBody IssuanceFlowQueryRequest flowQueryRequest);
//
//    @GetMapping("/roll")
//    @ResponseBody
//    RollResponse<IssuanceItem> scroll(@RequestBody IssuanceFlowQueryRequest flowQueryRequest);
}
