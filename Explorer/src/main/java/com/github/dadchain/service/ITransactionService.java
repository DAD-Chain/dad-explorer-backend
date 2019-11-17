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

import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dto.TxDetailDto;
import com.github.dadchain.model.dto.TxEventLogDto;

import java.util.List;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2018/2/27
 */
public interface ITransactionService {

    /**
     * query transaction by tx index optimize for page
     * @param count
     * @return
     */
    ResponseBean<List<TxEventLogDto>> queryLatestTxsByTxIndex(Long count);
    ResponseBean<List<TxEventLogDto>> queryTxsByTxIndex(Long indexMax, Long pageSize);

    /**
     * query latest transaction list
     *
     * @return
     */
    ResponseBean<List<TxEventLogDto>> queryLatestTxs(int count);

    /**
     * query transaction list by page
     *
     * @return
     */
    ResponseBean<PageResponseBean> queryTxsByPage(int pageNumber, int pageSize);

    /**
     * query latest nonontid transaction list
     *
     * @return
     */
    ResponseBean queryLatestNonontidTxs(int count);

    /**
     * query nonontid transaction list by page
     *
     * @return
     */
    ResponseBean queryNonontidTxsByPage(int pageNumber, int pageSize);

    /**
     * query transaction detail by hash
     * @param txHash
     * @return
     */
    ResponseBean<TxDetailDto> queryTxDetailByHash(String txHash);


}
