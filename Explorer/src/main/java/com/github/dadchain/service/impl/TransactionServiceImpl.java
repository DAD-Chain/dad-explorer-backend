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


package com.github.dadchain.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.dadchain.util.EventLogParseUtil;
import com.github.dadchain.util.Helper;
import com.github.dadchain.mapper.CurrentMapper;
import com.github.dadchain.mapper.OntidTxDetailMapper;
import com.github.dadchain.mapper.TxDetailMapper;
import com.github.dadchain.mapper.TxEventLogMapper;
import com.github.dadchain.model.common.EventTypeEnum;
import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.CurrentDto;
import com.github.dadchain.model.dto.TransferTxDetailDto;
import com.github.dadchain.model.dto.TxDetailDto;
import com.github.dadchain.model.dto.TxEventLogDto;
import com.github.dadchain.service.ITransactionService;
import com.github.dadchain.util.ConstantParam;
import com.github.dadchain.util.ErrorInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service("TransactionService")
public class TransactionServiceImpl implements ITransactionService {

    private final TxDetailMapper txDetailMapper;
    private final CurrentMapper currentMapper;
    private final OntidTxDetailMapper ontidTxDetailMapper;
    private final TxEventLogMapper txEventLogMapper;

    @Autowired
    public TransactionServiceImpl(TxDetailMapper txDetailMapper, CurrentMapper currentMapper, OntidTxDetailMapper ontidTxDetailMapper, TxEventLogMapper txEventLogMapper) {
        this.txDetailMapper = txDetailMapper;
        this.currentMapper = currentMapper;
        this.ontidTxDetailMapper = ontidTxDetailMapper;
        this.txEventLogMapper = txEventLogMapper;
    }


    @Override
    @Deprecated
    public ResponseBean<List<TxEventLogDto>> queryLatestTxs(int count) {

        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectTxsByPage(0, count);
        txEventLogDtos.forEach(tx -> {
            if (tx.getAmount() != null) {
                tx.setAmount(tx.getAmount().divide(ConstantParam.DAD_DECIMAL));
            } else {
                tx.setAmount(BigDecimal.ZERO);
            }
        });

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txEventLogDtos);
    }

    @Override
    @Deprecated
    public ResponseBean<PageResponseBean> queryTxsByPage(int pageNumber, int pageSize) {

        int start = pageSize * (pageNumber - 1) < 0 ? 0 : pageSize * (pageNumber - 1);

        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectTxsByPage(start, pageSize);
        txEventLogDtos.forEach(tx -> {
            if (tx.getAmount() != null) {
                tx.setAmount(tx.getAmount().divide(ConstantParam.DAD_DECIMAL));
            } else {
                tx.setAmount(BigDecimal.ZERO);
            }
        });

        CurrentDto currentDto = currentMapper.selectSummaryInfo();

        PageResponseBean pageResponseBean = new PageResponseBean(txEventLogDtos, currentDto.getTxCount());

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }

    @Override
    @Deprecated
    public ResponseBean queryLatestNonontidTxs(int count) {

        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectNonontidTxsByPage(0, count);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txEventLogDtos);
    }

    @Override
    @Deprecated
    public ResponseBean queryNonontidTxsByPage(int pageNumber, int pageSize) {

        int start = pageSize * (pageNumber - 1) < 0 ? 0 : pageSize * (pageNumber - 1);

        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectNonontidTxsByPage(start, pageSize);

        CurrentDto currentDto = currentMapper.selectSummaryInfo();

        PageResponseBean pageResponseBean = new PageResponseBean(txEventLogDtos, currentDto.getTxCount() - currentDto.getOntidTxCount());

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }

    ///////////////==============///////////////////

    @Override
    public ResponseBean<TxDetailDto> queryTxDetailByHash(String txHash) {

        TxDetailDto txDetailDto = txDetailMapper.selectTxByHash(txHash);
        if (Helper.isEmptyOrNull(txDetailDto)) {
            return new ResponseBean(ErrorInfo.NOT_FOUND.code(), ErrorInfo.NOT_FOUND.desc(), false);
        }

        JSONObject detailObj = new JSONObject();
        int eventType = txDetailDto.getEventType();
        //转账or权限交易，获取转账详情
        if (EventTypeEnum.Transfer.getType() == eventType || EventTypeEnum.Auth.getType() == eventType) {

            List<TxDetailDto> txDetailDtos = txDetailMapper.selectTransferTxDetailByHash(txHash);

            txDetailDtos.forEach(item -> {
                //ONG转换好精度给前端
                String assetName = item.getAssetName();
                if (ConstantParam.DAD.equals(assetName)) {
                    item.setAmount(item.getAmount().divide(ConstantParam.DAD_DECIMAL));
                }
            });
            detailObj.put("transfers", txDetailDtos);
        }
        {
            String txEvtLogs = txEventLogMapper.selectTxEvtLogByHash(txDetailDto.getTxHash());
            if (txEvtLogs != null) {
                try {
                    JSONObject jobj = JSON.parseObject(txEvtLogs);
                    JSONArray notifyArray = jobj.getJSONArray("Notify");
                    if (null != notifyArray) {
                        notifyArray.forEach((notify -> EventLogParseUtil.parseNativeDADToken(notify)));
                        detailObj.put("notify", notifyArray);
                    }
                } catch (Exception e) {
                    //
                }
            }
        }
        txDetailDto.setDetail(detailObj);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txDetailDto);
    }

    @Override
    public ResponseBean<List<TxEventLogDto>> queryLatestTxsByTxIndex(Long count) {

        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectLastTxsByTxIndex(count);
        txEventLogDtos.forEach(tx -> {
            List<TransferTxDetailDto> transfers = EventLogParseUtil.buildTxDADTransfer(tx.getEventLog());
            if (transfers.size() > 0) {
                TransferTxDetailDto transfer = transfers.get(0);
                tx.setAmount(transfer.getAmount());
                tx.setFromAddress(transfer.getFromAddress());
                tx.setToAddress(transfer.getToAddress());
                tx.setAssetName(transfer.getAssetName());
            } else {
                tx.setAmount(BigDecimal.ZERO);
            }
        });

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txEventLogDtos);
    }

    @Override
    public ResponseBean<List<TxEventLogDto>> queryTxsByTxIndex(Long indexMax, Long pageSize) {
        List<TxEventLogDto> txEventLogDtos = txEventLogMapper.selectTxsByTxIndex(indexMax, pageSize);
        Collections.reverse(txEventLogDtos);
        for (TxEventLogDto tx : txEventLogDtos) {
            List<TransferTxDetailDto> transfers = EventLogParseUtil.buildTxDADTransfer(tx.getEventLog());
            if (transfers.size() > 0) {
                TransferTxDetailDto transfer = transfers.get(0);
                tx.setAmount(transfer.getAmount());
                tx.setFromAddress(transfer.getFromAddress());
                tx.setToAddress(transfer.getToAddress());
                tx.setAssetName(transfer.getAssetName());
            } else {
                tx.setAmount(BigDecimal.ZERO);
            }
        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), txEventLogDtos);
    }

}
