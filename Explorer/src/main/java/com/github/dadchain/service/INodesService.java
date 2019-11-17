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

import com.github.dadchain.model.dao.*;
import com.github.dadchain.model.dto.NodeInfoOnChainDto;

import java.util.List;

public interface INodesService {

    List<NodeInfoOnChainDto> getCurrentOnChainInfo();

    NodeInfoOffChain getCurrentOffChainInfo(String publicKey);

    List<NodeInfoOffChain> getCurrentOffChainInfo();

    NodeInfoOnChain getCurrentOnChainInfo(String publicKey);

    List<NodeBonus> getNodeBonusHistories();

    NodeBonus getLatestBonusByPublicKey(String publicKey);

    NodeBonus getLatestBonusByAddress(String address);

    List<NodeInfoOnChainWithBonus> getLatestBonusesWithInfos();

    List<NodeInfoOnChainWithBonus> searchNodeOnChainWithBonusByName(String name);

    List<NetNodeInfo> getActiveNetNodes();

    List<NetNodeInfo> getAllNodes();

    long getSyncNodeCount();

    long getCandidateNodeCount();

    long getConsensusNodeCount();

    long getSyncNodesCount();

    List<NodeInfoOffChain> getCurrentCandidateOffChainInfo();

    List<NodeInfoOffChain> getCurrentConsensusOffChainInfo();

    List<NodeRankChange> getNodeRankChange(boolean isDesc);

}
