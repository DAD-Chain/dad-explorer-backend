package com.github.dadchain.service.impl;

import com.github.dadchain.mapper.*;
import com.github.dadchain.model.dto.*;
import com.github.dadchain.util.Helper;
import com.github.dadchain.config.ParamsConfig;
import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.service.ISummaryService;
import com.github.dadchain.util.ConstantParam;
import com.github.dadchain.util.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("SummaryService")
public class SummaryServiceImpl implements ISummaryService {

    private final ParamsConfig paramsConfig;
    private final TxEventLogMapper txEventLogMapper;
    private final DailySummaryMapper dailySummaryMapper;
    private final ContractDailySummaryMapper contractDailySummaryMapper;
    private final CurrentMapper currentMapper;
    private final AddressDailySummaryMapper addressDailySummaryMapper;
    private final TxDailySummaryMapper txDailySummaryMapper;
    private final AddressAllMapper addressAllMapper;


    @Autowired
    public SummaryServiceImpl(ParamsConfig paramsConfig, TxEventLogMapper txEventLogMapper,
                              DailySummaryMapper dailySummaryMapper, ContractDailySummaryMapper contractDailySummaryMapper,
                              CurrentMapper currentMapper, AddressDailySummaryMapper addressDailySummaryMapper,
                              TxDailySummaryMapper txDailySummaryMapper,
                              AddressAllMapper addressAllMapper) {
        this.paramsConfig = paramsConfig;
        this.txEventLogMapper = txEventLogMapper;
        this.dailySummaryMapper = dailySummaryMapper;
        this.contractDailySummaryMapper = contractDailySummaryMapper;
        this.currentMapper = currentMapper;
        this.addressDailySummaryMapper = addressDailySummaryMapper;
        this.txDailySummaryMapper = txDailySummaryMapper;
        this.addressAllMapper = addressAllMapper;

    }


    @Override
    public ResponseBean<CurrentDto> getBlockChainLatestInfo() {

        CurrentDto currentDto = currentMapper.selectSummaryInfo();
        currentDto.setNodeCount(currentDto.getBlockBookkeeperCount());

        Integer addressCount = addressAllMapper.selectAllAddressCount();
        currentDto.setAddressCount(addressCount);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), currentDto);

    }

    @Override
    public ResponseBean<BlockChainTpsDto> getBlockChainTps() {

        BlockChainTpsDto resultMap = new BlockChainTpsDto(calcTps(), paramsConfig.BLOCKCHAIN_MAX_TPS);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), resultMap);
    }

    /**
     * 计算当前tps
     *
     * @return
     */
    private String calcTps() {
        Long now = System.currentTimeMillis() / 1000;
        Integer txPerMin = txEventLogMapper.queryTxCount(now - 60, now);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format((double) (txPerMin) / 60);
    }


    @Override
    public ResponseBean getBlockChainDailySummary(Long startTime, Long endTime) {

        List<DailySummaryDto> dailySummaryDtos = dailySummaryMapper.selectSummaryByTime(startTime, endTime);
        if (dailySummaryDtos.size() > 0) {
            Map<String, BigDecimal> addrAndOntidCountMap = dailySummaryMapper.selectAddrAndOntIdTotal(startTime);
            if (Helper.isEmptyOrNull(addrAndOntidCountMap)) {
                dailySummaryDtos.get(0).setOntidTotal(0);
                dailySummaryDtos.get(0).setAddressTotal(0);
            } else {
                dailySummaryDtos.get(0).setAddressTotal(addrAndOntidCountMap.get("addressTotal").intValue());
                dailySummaryDtos.get(0).setOntidTotal(addrAndOntidCountMap.get("ontidTotal").intValue());
            }

            for (int i = 1; i < dailySummaryDtos.size(); i++) {
                DailySummaryDto dailySummaryDto = dailySummaryDtos.get(i);
                dailySummaryDto.setAddressTotal(dailySummaryDtos.get(i - 1).getAddressTotal() + dailySummaryDto.getNewAddressCount());
                dailySummaryDto.setOntidTotal(dailySummaryDtos.get(i - 1).getOntidTotal() + dailySummaryDto.getNewOntidCount());
            }
        }

        PageResponseBean pageResponseBean = new PageResponseBean(dailySummaryDtos, dailySummaryDtos.size());

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }


    @Override
    public ResponseBean getContractDailySummary(String contractHash, Long startTime, Long endTime) {

        List<ContractDailySummaryDto> contractDailySummaryDtos = contractDailySummaryMapper.selectDailySummaryByContractHash(contractHash, startTime, endTime);

        PageResponseBean pageResponseBean = new PageResponseBean(contractDailySummaryDtos, contractDailySummaryDtos.size());

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }

    @Override
    public ResponseBean<NewAddressSummaryDto> getAddressNewSummary(Long startTime, Long endTime) {
        Integer beginDay = startTime.intValue() / ConstantParam.DAILY_SECONDS;
        Integer endDay = endTime.intValue() / ConstantParam.DAILY_SECONDS;
        NewAddressSummaryDto summaryDto = addressAllMapper.selectAllAddressRangeCount(beginDay, endDay);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), summaryDto);
    }

    @Override
    public ResponseBean<PageResponseBean<TxDailySummaryDto>> getTxDailySummary(Long startTime, Long endTime) {
        Integer beginDay = startTime.intValue() / ConstantParam.DAILY_SECONDS;
        Integer endDay = endTime.intValue() / ConstantParam.DAILY_SECONDS;

        List<TxDailySummaryDto> summary = txDailySummaryMapper.getDailTxCountSummary(beginDay, endDay);
        Map<Integer, TxDailySummaryDto> dailyExist =
                summary.stream().collect(Collectors.toMap(daily->daily.getTimeDay(), daily->daily));

        List<TxDailySummaryDto> returnList = new ArrayList<>(endDay - beginDay + 1);
        for(Integer cur = beginDay; cur <= endDay; cur++){
            TxDailySummaryDto exist = dailyExist.get(cur * ConstantParam.DAILY_SECONDS);
            if (null != exist){
                returnList.add(exist);
            }else{
                TxDailySummaryDto fillDaily = new TxDailySummaryDto();
                fillDaily.setTimeDay(cur * ConstantParam.DAILY_SECONDS);
                fillDaily.setTxCount(0);
                returnList.add(fillDaily);
            }
        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), returnList);
    }
}
