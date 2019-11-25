package com.github.dadchain.controller;

import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.FaucetSrcInfo;
import com.github.dadchain.service.IUserService;
import com.github.dadchain.service.ISmartCodeService;
import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.util.Helper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/v2/faucet")
public class FaucetController {
    private final String CLASS_NAME = this.getClass().getSimpleName();

    @Autowired
    IUserService userService;

    @Autowired
    ISmartCodeService ISmartCodeService;


    @ApiOperation(value = "Get a dad facuet token")
    @GetMapping("/dad/get")
    @NeedLogin
    public ResponseBean<String> getDadFaucet(@RequestParam(name = "to_address") String toAddress) throws Exception {
        String txHash = userService.checkDoDadFacuet(toAddress);
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txHash);
    }

    @ApiOperation(value = "Get a dad sender account info")
    @GetMapping("/dad/srcInfo")
    public ResponseBean<FaucetSrcInfo> getDadFaucetSrcInfo() throws Exception {
        FaucetSrcInfo info = ISmartCodeService.faucetSrcInfo();
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), info);
    }

    @ApiOperation(value = "check address format")
    @GetMapping(value = "/address/check/{address}")
    public ResponseBean<Boolean> checkAddress(@PathVariable("address") String address) {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), ISmartCodeService.validateAddress(address));
    }
}
