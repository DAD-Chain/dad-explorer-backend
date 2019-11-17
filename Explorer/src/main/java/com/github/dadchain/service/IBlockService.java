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
import com.github.dadchain.model.dto.BlockDto;
import com.github.dadchain.model.dto.CurrentDto;

import java.util.List;

public interface IBlockService {

    ResponseBean<List<BlockDto>> queryLatestBlocks(int amount);

    ResponseBean<PageResponseBean<List<CurrentDto>>> queryBlocksByPage(int pageSize, int pageNumber);

    ResponseBean<PageResponseBean<List<CurrentDto>>> queryBlocksPageByMinHeight(int minBlockNo, int pageSize);

    ResponseBean<BlockDto> queryBlockByHeight(int height);

    ResponseBean<BlockDto> queryBlockByHash(String hash);

    ResponseBean<Long> queryBlockGenerateLastAvgTime(Long beforeSec);

    ResponseBean queryBlockGenerateTime(int amount);

}
