package com.github.dadchain.service;

import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.*;

public interface ISummaryService {

    ResponseBean<CurrentDto> getBlockChainLatestInfo();

    ResponseBean<BlockChainTpsDto> getBlockChainTps();

    ResponseBean<PageResponseBean<DailySummaryDto>> getBlockChainDailySummary(Long startTime, Long endTime);

    ResponseBean getContractDailySummary(String contractHash, Long startTime, Long endTime);

    ResponseBean<NewAddressSummaryDto> getAddressNewSummary(Long startTime, Long endTime);

    ResponseBean<PageResponseBean<TxDailySummaryDto>> getTxDailySummary(Long startTime, Long endTime);
}
