/*
 * Copyright (C) 2018 The dad Authors
 * This file is part of The dad library.
 *
 * The dad is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * The dad is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with The dad.  If not, see <http://www.gnu.org/licenses/>.
 */


package com.github.dadchain.controller;

import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.TxDetailDto;
import com.github.dadchain.model.dto.TxEventLogDto;
import com.github.dadchain.service.ITransactionService;
import com.github.dadchain.util.Helper;
import com.github.dadchain.aop.RequestLimit;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2018/3/15
 */
@Validated
@Slf4j
@RestController
@RequestMapping(value = "/v2")
public class TransactionController {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final ITransactionService transactionService;

    @Autowired
    public TransactionController(ITransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @ApiOperation(value = "Get latest transaction list")
    @GetMapping(value = "/latest-transactions")
    @Deprecated
    @ApiIgnore
    public ResponseBean<List<TxEventLogDto>> queryLatestTxs(@RequestParam("count") @Max(50) @Min(1) int count) {

        log.info("###{}.{} begin...", CLASS_NAME, Helper.currentMethod());

        ResponseBean rs = transactionService.queryLatestTxs(count);
        return rs;
    }

    @RequestLimit(count = 120)
    @ApiOperation(value = "Get transaction list by page")
    @GetMapping(value = "/transactions")
    @ApiIgnore
    @Deprecated
    public ResponseBean<PageResponseBean> queryTxsByPage(@RequestParam("page_size") @Min(1) @Max(20) Integer pageSize,
                                                         @RequestParam("page_number") @Min(1) Integer pageNumber) {

        log.info("###{}.{} begin...pageSize:{},pageNumber:{}", CLASS_NAME, Helper.currentMethod(), pageSize, pageNumber);

        ResponseBean rs = transactionService.queryTxsByPage(pageNumber, pageSize);
        return rs;
    }

    @ApiOperation(value = "Get latest transaction list")
    @GetMapping(value = "/latest-transactions-v2")
    public ResponseBean<List<TxEventLogDto>> queryLatestTxsByTxIndex(@RequestParam("count") @Max(50) @Min(1) Long count) {

        log.info("###{}.{} begin...", CLASS_NAME, Helper.currentMethod());

        ResponseBean rs = transactionService.queryLatestTxsByTxIndex(count);
        return rs;
    }

    @RequestLimit(count = 120)
    @ApiOperation(value = "Get transaction list by tx index v2, max_index -1 for first to get current max")
    @GetMapping(value = "/transactions-v2")
    public ResponseBean<PageResponseBean> queryTxsByTxIndex(@RequestParam("page_size") @Min(1) @Max(20) Long pageSize,
                                                         @RequestParam("min_index") @Min(0) Long maxIndex) {

        log.info("###{}.{} begin...pageSize:{},maxIndex:{}", CLASS_NAME, Helper.currentMethod(), pageSize, maxIndex);

        ResponseBean rs = transactionService.queryTxsByTxIndex(maxIndex, pageSize);
        return rs;
    }

//    @ApiOperation(value = "Get latest nonontid transaction list")
//    @GetMapping(value = "/latest-nonontid-transactions")
//    @ApiIgnore
//    public ResponseBean queryLatestNonontidTxs(@RequestParam("count") @Max(50) @Min(1) int count) {
//
//        log.info("###{}.{} begin...", CLASS_NAME, Helper.currentMethod());
//
//        ResponseBean rs = transactionService.queryLatestNonontidTxs(count);
//        return rs;
//    }
//
//    @ApiOperation(value = "Get nonontid transaction list by page")
//    @GetMapping(value = "/nonontid-transactions")
//    @ApiIgnore
//    public ResponseBean queryNonontidTxsByPage(@RequestParam("page_size") @Min(1) @Max(20) Integer pageSize,
//                                               @RequestParam("page_number") @Min(1) Integer pageNumber) {
//
//        log.info("###{}.{} begin...pageSize:{},pageNumber:{}", CLASS_NAME, Helper.currentMethod(), pageSize, pageNumber);
//
//        ResponseBean rs = transactionService.queryNonontidTxsByPage(pageNumber, pageSize);
//        return rs;
//    }


    @ApiOperation(value = "Get transaction detail by txhash")
    @GetMapping(value = "/transactions/{tx_hash}")
    public ResponseBean<TxDetailDto> queryTxnByHash(@PathVariable("tx_hash") @Length(min = 64, max = 64, message = "Incorrect transaction hash") String txHash) {

        log.info("###{}.{} begin...txHash:{}", CLASS_NAME, Helper.currentMethod(), txHash);

        ResponseBean rs = transactionService.queryTxDetailByHash(txHash);
        return rs;
    }


}
