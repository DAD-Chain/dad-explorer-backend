package com.github.dadchain.controller;

import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.BalanceDto;
import com.github.dadchain.model.dto.TransferTxDto;
import com.github.dadchain.service.IAddressService;
import com.github.dadchain.util.Helper;
import com.github.dadchain.aop.RequestLimit;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/28
 */
@Validated
@Slf4j
@RestController
@RequestMapping(value = "/v2/addresses")
public class AddressController {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }


//    @RequestLimit(count = 120)
//    @ApiOperation(value = "Get address balance")
//    @GetMapping(value = "/{address}/{token_type}/balances")
//    @ApiIgnore
//    public ResponseBean<List<BalanceDto>> queryAddressBalance(@PathVariable("address") @Length(min = 34, max = 34, message = "Incorrect address format") String address,
//                                                              @PathVariable("token_type") @Pattern(regexp = "oep4|OEP4|oep5|OEP5|oep8|OEP8|native|NATIVE|ALL|all", message = "Incorrect token type") String tokenType) {
//
//        log.info("####{}.{} begin...address:{},token_type:{}", CLASS_NAME, Helper.currentMethod(), address, tokenType);
//
//        ResponseBean rs = addressService.queryAddressBalance(address, tokenType);
//        return rs;
//    }


    @RequestLimit(count = 120)
    @ApiOperation(value = "Get address balance by assetName")
    @GetMapping(value = "/{address}/balances")
    public ResponseBean<List<BalanceDto>> queryAddressBalanceByAssetName(@PathVariable("address") @Length(min = 34, max = 34, message = "Incorrect address format") String address,
                                                                         @RequestParam("asset_name") @Pattern(regexp = "dad") String assetName) {

        log.info("####{}.{} begin...address:{},assetName:{}", CLASS_NAME, Helper.currentMethod(), address, assetName);

        ResponseBean<List<BalanceDto>> rs = addressService.queryAddressBalanceByAssetName(address, assetName);
        return rs;
    }


    @RequestLimit(count = 120)
    @ApiOperation(value = "Get address transfer transaction list by params", notes = "(page_number+page_size)")
    @GetMapping(value = "/{address}/transactions/v2")
    public ResponseBean<PageResponseBean<List<TransferTxDto>>> queryAddressTransferTxsByPageV2(@PathVariable("address") @Length(min = 34, max = 34, message = "Incorrect address format") String address,
                                                                                               @RequestParam(name = "page_size") @Min(1) @Max(20) Long pageSize,
                                                                                               @RequestParam(name = "page_number") @Min(1) Long pageNumber) {

        log.info("####{}.{} begin...address:{}", CLASS_NAME, Helper.currentMethod(), address);

        return addressService.queryTransferTxsByPageV2(address, pageNumber, pageSize);
    }

    @RequestLimit(count = 120)
    @ApiOperation(value = "Get address transfer transaction list by params", notes = "(page_number+page_size)")
    @GetMapping(value = "/{address}/transactions")
    public ResponseBean<PageResponseBean<List<TransferTxDto>>> queryAddressTransferTxsByPage(@PathVariable("address") @Length(min = 34, max = 34, message = "Incorrect address format") String address,
                                                                                             @RequestParam(name = "page_size") @Min(1) @Max(20) Long pageSize,
                                                                                             @RequestParam(name = "page_number") @Min(1) Long pageNumber) {

        log.info("####{}.{} begin...address:{}", CLASS_NAME, Helper.currentMethod(), address);

        return addressService.queryTransferTxsByPage(address, pageNumber, pageSize);
    }
}
