package com.github.dadchain.service;

import com.github.dadchain.model.dto.TransferTxDto;
import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.BalanceDto;

import java.util.List;

public interface IAddressService {

    ResponseBean<List<BalanceDto>> queryAddressBalance(String address, String tokenType);

    ResponseBean<List<BalanceDto>> queryAddressBalanceByAssetName(String address, String assetName);

    ResponseBean queryAddressBalanceByAssetName4Onto(String address, String assetName);

    ResponseBean<PageResponseBean<List<TransferTxDto>>> queryTransferTxsByPage(String address, Long pageNumber, Long pageSize);
    ResponseBean<PageResponseBean<List<TransferTxDto>>> queryTransferTxsByPageV2(String address, Long pageNumber, Long pageSize);

    ResponseBean queryTransferTxsByTime(String address, String assetName, Long beginTime, Long endTime);

    ResponseBean queryTransferTxsByTime4Onto(String address, String assetName, Long beginTime, Long endTime, String addressType);

    ResponseBean queryTransferTxsByTimeAndPage4Onto(String address, String assetName, Long endTime, Integer pageSize, String addressType);

}
