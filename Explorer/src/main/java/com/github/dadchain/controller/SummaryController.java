package com.github.dadchain.controller;

import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.BlockChainTpsDto;
import com.github.dadchain.model.dto.CurrentDto;
import com.github.dadchain.model.dto.NewAddressSummaryDto;
import com.github.dadchain.model.dto.TxDailySummaryDto;
import com.github.dadchain.service.impl.SummaryServiceImpl;
import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.util.Helper;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/v2/summary")
public class SummaryController {

    private final String CLASS_NAME = this.getClass().getSimpleName();

    private final SummaryServiceImpl summaryService;

    @Autowired
    public SummaryController(SummaryServiceImpl summaryService) {
        this.summaryService = summaryService;
    }


    @ApiOperation(value = "Get blockchain latest summary information: address count, block height, node count, tx count")
    @GetMapping(value = "/blockchain/latest-info")
    public ResponseBean<CurrentDto> getBlockChainLatestInfo() {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());
        return summaryService.getBlockChainLatestInfo();
    }

    @ApiOperation(value = "Get blockchain tps information")
    @GetMapping(value = "/blockchain/tps")
    public ResponseBean<BlockChainTpsDto> getBlockChainTps() {

        log.info("####{}.{} begin...", CLASS_NAME, Helper.currentMethod());

        return summaryService.getBlockChainTps();
    }

//    @ApiOperation(value = "Get blockchain daily summary information")
//    @GetMapping(value = "/blockchain/daily")
//    @ApiIgnore
//    public ResponseBean<PageResponseBean<DailySummaryDto>> getBlockChainSummary(@RequestParam("start_time") Long startTime,
//                                                                                @RequestParam("end_time") Long endTime) {
//
//        log.info("####{}.{} begin...start_time:{},end_time:{}", CLASS_NAME, Helper.currentMethod(), startTime, endTime);
//
//        if (Helper.isTimeRangeExceedLimit(startTime, endTime)) {
//            return new ResponseBean(ErrorInfo.TIME_RANGE_EXCEED.code(), ErrorInfo.TIME_RANGE_EXCEED.desc(), false);
//        }
//        return summaryService.getBlockChainDailySummary(startTime, endTime);
//    }
//
//
//    @ApiOperation(value = "Get contract daily summary information")
//    @GetMapping(value = "/contracts/{contract_hash}/daily")
//    @ApiIgnore
//    public ResponseBean getContractSummary(@PathVariable("contract_hash") @Length(min = 40, max = 40, message = "Incorrect contract hash") String contractHash,
//                                           @RequestParam("start_time") Long startTime,
//                                           @RequestParam("end_time") Long endTime) {
//
//        log.info("####{}.{} begin...start_time:{},end_time:{}", CLASS_NAME, Helper.currentMethod(), startTime, endTime);
//
//        if (Helper.isTimeRangeExceedLimit(startTime, endTime)) {
//            return new ResponseBean(ErrorInfo.TIME_RANGE_EXCEED.code(), ErrorInfo.TIME_RANGE_EXCEED.desc(), false);
//        }
//        return summaryService.getContractDailySummary(contractHash, startTime, endTime);
//    }

    @ApiOperation(value = "Get address new summary information")
    @GetMapping(value = "/address/new/summary")
    public ResponseBean<NewAddressSummaryDto> getNewAddressSummary(@RequestParam("start_time") Long startTime,
                                                                   @RequestParam("end_time") Long endTime) {

        log.info("####{}.{} begin...start_time:{},end_time:{}", CLASS_NAME, Helper.currentMethod(), startTime, endTime);

        if (Helper.isTimeRangeExceed100Days(startTime, endTime)) {
            return new ResponseBean(ErrorInfo.TIME_RANGE_EXCEED.code(), ErrorInfo.TIME_RANGE_EXCEED.desc(), false);
        }
        return summaryService.getAddressNewSummary(startTime, endTime);
    }

    @ApiOperation(value = "Get blockchain daily summary information")
    @GetMapping(value = "/transactions/daily")
    public ResponseBean<PageResponseBean<TxDailySummaryDto>> getTxDailySummary(@RequestParam("start_time") Long startTime,
                                                                               @RequestParam("end_time") Long endTime) {

        log.info("####{}.{} begin...start_time:{},end_time:{}", CLASS_NAME, Helper.currentMethod(), startTime, endTime);

        if (Helper.isTimeRangeExceedLimit(startTime, endTime)) {
            return new ResponseBean(ErrorInfo.TIME_RANGE_EXCEED.code(), ErrorInfo.TIME_RANGE_EXCEED.desc(), false);
        }
        return summaryService.getTxDailySummary(startTime, endTime);
    }
}
