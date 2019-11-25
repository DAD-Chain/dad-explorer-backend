package com.github.dadchain.controller;

import com.github.dadchain.internal.dsp.DSPClient;
import com.github.dadchain.internal.dsp.model.*;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.service.ISmartCodeService;
import com.github.dadchain.util.Helper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@Slf4j
@RestController
@RequestMapping(value = "/v2/user/award")
public class UserAwardController {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    @Autowired
    DSPClient dspClient;

    @Autowired
    ISmartCodeService ISmartCodeService;

    @ApiOperation(value = "Get award list")
    @GetMapping(value = "/awards-list")
    public ResponseBean<AwardDetailDto> getAwardList(@RequestParam("page_size") @Min(1) @Max(20) Integer pageSize,
                                                     @RequestParam("page_number") @Min(1) Integer pageNumber,
                                                     @RequestParam("status") AwardStatus status,
                                                     @RequestParam("address") @Length(min = 34, max = 34, message = "Incorrect address format") String address) {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());
        GetAwardsListRequest request = new GetAwardsListRequest();
        request.setAddress(address).setPageNumber(pageNumber).setPageSize(pageSize).setStatus(status);
        return dspClient.getAwardList(request);
    }

    @ApiOperation(value = "Get task award")
    @PostMapping(value = "/get")
    public ResponseBean<GetAwardResponse> getAward(@RequestBody GetAwardRequest params) {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());
        params.validate();

        return dspClient.getAward(params);
    }

    @ApiOperation(value = "Get task award")
    @PostMapping(value = "/get/task")
    public ResponseBean<UserAwardRecord> getTask(@RequestBody GetAwardTaskRequest params) {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());
        params.validate();

        return dspClient.getTask(params);
    }
}
