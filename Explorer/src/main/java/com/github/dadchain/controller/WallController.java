package com.github.dadchain.controller;

import com.github.dadchain.internal.dsp.DSPClient;
import com.github.dadchain.internal.dsp.model.ADWallType;
import com.github.dadchain.internal.dsp.model.AdWallDetailDto;
import com.github.dadchain.internal.dsp.model.GetADWallRequest;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.util.Helper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Validated
@Slf4j
@RestController
@RequestMapping(value = "/v2/wall")
public class WallController {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    @Autowired
    DSPClient dspClient;

    @ApiOperation(value = "Get wall list")
    @GetMapping(value = "/get")
    public ResponseBean<AdWallDetailDto> get(@RequestParam("page_size") @Min(1) @Max(20) Integer pageSize,
                                             @RequestParam("page_number") @Min(1) Integer pageNumber,
                                             @RequestParam("type") ADWallType type) {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());
        GetADWallRequest request = new GetADWallRequest();
        request.setPageNumber(pageNumber).setPageSize(pageSize).setType(type);
        return dspClient.getAdWall(request);
    }
}
