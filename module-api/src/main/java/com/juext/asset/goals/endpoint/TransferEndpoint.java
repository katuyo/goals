package com.juext.shop.base.endpoint;


import org.featx.spec.model.*;
import com.juext.shop.base.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Excepts
 * @since 2020/4/11 22:23
 */
@RequestMapping("/transfer")
public interface TransferEndpoint {
    /**
     * Create or update a Transfer module,
     * if code not provided, persistent this module with all of the properties
     * else if code existed, update all of the properties as saveRequest provided
     * else Error entity not found
     * @param saveRequest TransferSaveRequest Transfer module with all properties
     * @return BaseResponse<Coded> The Base response structure with saved module's business code
     */
    @PostMapping
    @ResponseBody
    BaseResponse<Coded> save(@RequestBody TransferSaveRequest saveRequest);

    @PutMapping
    @ResponseBody
    BaseResponse<Coded> put(@RequestBody TransferSaveRequest saveRequest);

    @DeleteMapping
    @ResponseBody
    BaseResponse<Void> delete(@RequestParam String code);

    @GetMapping
    @ResponseBody
    BaseResponse<TransferInfo> getByCode(@RequestParam("code") String code);

    @GetMapping("/list")
    @ResponseBody
    ListResponse<TransferInfo> listByCode(@RequestParam("codes") List<String> codes);

    @GetMapping("/page")
    @ResponseBody
    PageResponse<TransferItem> page(@RequestBody TransferPageQueryRequest pageQueryRequest);

//    @GetMapping("/flow")
//    @ResponseBody
//    FlowResponse<TransferItem> flow(@RequestBody TransferFlowQueryRequest flowQueryRequest);
//
//    @GetMapping("/roll")
//    @ResponseBody
//    RollResponse<TransferItem> scroll(@RequestBody TransferFlowQueryRequest flowQueryRequest);
}
