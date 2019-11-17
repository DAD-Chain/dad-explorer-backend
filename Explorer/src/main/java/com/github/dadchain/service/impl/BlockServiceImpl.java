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

import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.util.Helper;
import com.github.dadchain.mapper.BlockMapper;
import com.github.dadchain.mapper.CurrentMapper;
import com.github.dadchain.mapper.TxEventLogMapper;
import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.BlockDto;
import com.github.dadchain.model.dto.BlockLastAvgGenerate;
import com.github.dadchain.model.dto.CurrentDto;
import com.github.dadchain.service.IBlockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service("BlockService")
public class BlockServiceImpl implements IBlockService {

    private final BlockMapper blockMapper;
    private final TxEventLogMapper txEventLogMapper;
    private final CurrentMapper currentMapper;

    @Autowired
    public BlockServiceImpl(BlockMapper blockMapper, TxEventLogMapper txEventLogMapper, CurrentMapper currentMapper) {
        this.blockMapper = blockMapper;
        this.txEventLogMapper = txEventLogMapper;
        this.currentMapper = currentMapper;
    }


    @Override
    public ResponseBean<List<BlockDto>> queryLatestBlocks(int amount) {
        List<BlockDto> blockDtos = blockMapper.selectBlocksByPage(0, amount);
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), blockDtos);
    }

    @Override
    public ResponseBean<PageResponseBean<List<CurrentDto>>> queryBlocksByPage(int pageSize, int pageNumber) {

        int start = pageSize * (pageNumber - 1) < 0 ? 0 : pageSize * (pageNumber - 1);
        List<BlockDto> blockDtos = blockMapper.selectBlocksByPage(start, pageSize);

        CurrentDto currentDto = currentMapper.selectSummaryInfo();
        //区块高度从0开始，区块个数=区块高度+1
        PageResponseBean<List<CurrentDto>> pageResponseBean = new PageResponseBean(blockDtos, currentDto.getBlockHeight() + 1);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }
    @Override
    public ResponseBean<PageResponseBean<List<CurrentDto>>> queryBlocksPageByMinHeight(int minBlockNo, int pageSize){
        List<BlockDto> blockDtos = blockMapper.selectBlocksPageByMinBlockNo(minBlockNo, pageSize);
        Collections.reverse(blockDtos);

        CurrentDto currentDto = currentMapper.selectSummaryInfo();
        //区块高度从0开始，区块个数=区块高度+1
        PageResponseBean<List<CurrentDto>> pageResponseBean = new PageResponseBean(blockDtos, currentDto.getBlockHeight() + 1);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);

    }

    @Override
    public ResponseBean<BlockDto> queryBlockByHeight(int blockHeight) {

        BlockDto blockDto = blockMapper.selectOneByHeight(blockHeight);
        if (Helper.isEmptyOrNull(blockDto)) {
            return new ResponseBean(ErrorInfo.NOT_FOUND.code(), ErrorInfo.NOT_FOUND.desc(), false);
        }
//        List<TxBasicDto> txBasicDtos = txEventLogMapper.selectTxsByBlockHeight(blockHeight);
//        if (Helper.isNotEmptyOrNull(txBasicDtos)) {
//            blockDto.setTxs(txBasicDtos);
//        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), blockDto);
    }

    @Override
    public ResponseBean queryBlockByHash(String blockHash) {

        BlockDto blockDto = blockMapper.selectOneByHash(blockHash);
        if (Helper.isEmptyOrNull(blockDto)) {
            return new ResponseBean(ErrorInfo.NOT_FOUND.code(), ErrorInfo.NOT_FOUND.desc(), false);
        }
//        int blockHeight = blockDto.getBlockHeight();

//        List<TxBasicDto> txBasicDtos = txEventLogMapper.selectTxsByBlockHeight(blockHeight);
//        if (Helper.isNotEmptyOrNull(txBasicDtos)) {
//            blockDto.setTxs(txBasicDtos);
//        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), blockDto);
    }

    @Override
    public ResponseBean queryBlockGenerateTime(int count) {

        List<Map> dataList = blockMapper.selectHeightAndTime(count + 1);
        List<Map> rsList = new ArrayList<>();
        for (int i = 0; i < dataList.size() - 1; i++) {
            int time = (Integer) dataList.get(i).get("blockTime") - (Integer) dataList.get(i + 1).get("blockTime");
            Map<String, Object> temp = new HashMap<>();
            temp.put("block_height", dataList.get(i).get("blockHeight"));
            temp.put("generate_time", time);
            rsList.add(temp);
        }
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), rsList);
    }


    @Override
    public ResponseBean<Long> queryBlockGenerateLastAvgTime(Long beforeSec) {
//        "SELECT from block where block_time > 24_Hour_before_now order by block_time as limit 1"
        Long before = Instant.now().getEpochSecond() - beforeSec;
        BlockLastAvgGenerate diffInfo = blockMapper.selectBlockBeforeTimeCount(before);
        Long blkGenAvgSec = 0L;
        if (diffInfo.getDiffHeight() != 0) {
            blkGenAvgSec = diffInfo.getDiffTime() / diffInfo.getDiffHeight();
        }
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), blkGenAvgSec);
    }


}
