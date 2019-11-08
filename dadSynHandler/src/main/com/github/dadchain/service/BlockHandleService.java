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

package com.github.dadchain.service;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.dadchain.utils.Helper;
import com.github.ontio.common.Address;
import com.github.dadchain.model.dao.Block;
import com.github.dadchain.thread.TxHandlerThread;
import com.github.dadchain.utils.ConstantParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2018/3/14
 */
@Slf4j
@Service
public class BlockHandleService {

    private final TxHandlerThread txHandlerThread;

    @Autowired
    public BlockHandleService(TxHandlerThread txHandlerThread) {
        this.txHandlerThread = txHandlerThread;
    }

    /**
     * handle the block and the transactions in this block
     *
     * @param blockJson
     * @throws Exception
     */
    public void handleOneBlock(JSONObject blockJson, JSONArray txEventLogArray, Integer txCountCur) throws Exception {

        JSONObject blockHeader = blockJson.getJSONObject("Header");
        int blockHeight = blockHeader.getInteger("Height");
        int blockTime = blockHeader.getInteger("Timestamp");
        int blockKeepersCnt = blockHeader.getJSONArray("Bookkeepers").size();

        JSONArray txArray = blockJson.getJSONArray("Transactions");
        int txCountInOneBlock = txArray.size();
        log.info("{} run-------blockHeight:{},txCount:{}", Helper.currentMethod(), blockHeight, txCountInOneBlock);

        List<Future> futureList = new ArrayList<>();
        //asynchronize handle transaction
        Long txIndex = Long.valueOf(txCountCur);
        for (int i = 0; i < txCountInOneBlock; i++) {
            JSONObject txJson = (JSONObject) txArray.get(i);
            txJson.put("EventLog", txEventLogArray.get(i));
            Future future = txHandlerThread.asyncHandleTx(txJson, blockHeight, blockTime, i + 1, txIndex);
            txIndex += 1;
            futureList.add(future);
            //future.get();
        }
        //等待线程池里的线程都执行结束
        for (int j = 0; j < futureList.size(); j++) {
            futureList.get(j).get();
        }
        insertBlock(blockJson);
        ConstantParam.BLOCK_BOOKKEEPER_COUNT = blockKeepersCnt;
        ConstantParam.BATCHBLOCK_TX_COUNT += txCountInOneBlock;

        log.info("{} end-------height:{},txCount:{}", Helper.currentMethod(), blockHeight, txCountInOneBlock);
    }


    /**
     * 处理block
     *
     * @param blockJson
     */
    private void insertBlock(JSONObject blockJson) {
        String blockKeeperStr = "";
        StringBuilder sb = new StringBuilder(400);

        JSONObject blockHeader = blockJson.getJSONObject("Header");
        JSONArray blockKeepers = blockHeader.getJSONArray("Bookkeepers");
        blockKeepers.forEach(item -> {
            sb.append(Address.addressFromPubKey((String) item).toBase58());
            sb.append("&");
        });
        blockKeeperStr = sb.toString();
        if (Helper.isNotEmptyOrNull(blockKeeperStr)) {
            blockKeeperStr = blockKeeperStr.substring(0, blockKeeperStr.length() - 1);
        }

        Block block = Block.builder()
                .blockHash(blockJson.getString("Hash"))
                .blockSize(blockJson.getInteger("Size"))
                .prevHash(blockHeader.getString("PrevBlockHash"))
                .blockTime(blockHeader.getInteger("Timestamp"))
                .blockHeight(blockHeader.getInteger("Height"))
                .txsRoot(blockHeader.getString("TransactionsRoot"))
                .consensusData(blockHeader.getString("ConsensusData"))
                .txCount(blockJson.getJSONArray("Transactions").size())
                .bookkeepers(blockKeeperStr)
                .build();
         ConstantParam.BATCHBLOCKDTO.getBlocks().add(block);
    }

}
