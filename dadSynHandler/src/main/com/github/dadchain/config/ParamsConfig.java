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


package com.github.dadchain.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("ParamsConfig")
public class ParamsConfig {


    /**
     * dad blockchain restful url
     */
    @Value("${masternode.restful.url}")
    public String MASTERNODE_RESTFUL_URL;

    /**
     * the amount of the dad blockchain nodes in properties
     */
    @Value("${node.count}")
    public int NODE_COUNT;

    /**
     * the interval for waiting block generation
     */
    @Value("${block.interval}")
    public int BLOCK_INTERVAL;

    /**
     * each node fault tolerance maximum time.
     */
    @Value("${node.interruptTime.max}")
    public int NODE_INTERRUPTTIME_MAX;

    /**
     * the maximum time of each node for waiting for generating block
     */
    @Value("${node.waitForBlockTime.max}")
    public int NODE_WAITFORBLOCKTIME_MAX;


    /**
     * dad blockchain DAD asset smartcontract codehash
     */
    @Value("${dad.token.contractHash}")
    public String DAD_TOKEN_CONTRACTHASH;

    /**
     * dad blockchain DAD  smartcontract codehash
     */
    @Value("${dad.contractHash}")
    public String DAD_CONTRACTHASH;

    /**
     * dad blockchain smartcontract codehash
     */
    @Value("${dadv2.contractHash}")
    public String DADV2_CONTRACTHASH;

    /**
     * dad blockchain ONG asset smartcontract codehash
     */
    @Value("${ong.contractHash}")
    public String ONG_CONTRACTHASH;

    /**
     * dad blockchain ontId smartcontract codehash
     */
    @Value("${ontId.contractHash}")
    public String ONTID_CONTRACTHASH;

    /**
     * dad blockchain record smartcontract codehash
     */
    @Value("${claimRecord.contractHash}")
    public String CLAIMRECORD_CONTRACTHASH;

    /**
     * dad blockchainauth smartcontract codehash
     */
    @Value("${auth.contractHash}")
    public String AUTH_CONTRACTHASH;

    @Value("${pax.contractHash}")
    public String PAX_CONTRACTHASH;

    @Value("${threadPoolSize.max}")
    public int THREADPOOLSIZE_MAX;

    @Value("${threadPoolSize.core}")
    public int THREADPOOLSIZE_CORE;

    @Value("${threadPoolSize.queue}")
    public int THREADPOOLSIZE_QUEUE;

    @Value("${threadPoolSize.keepalive}")
    public int THREADPOOLSIZE_KEEPALIVE_SECOND;


    @Value("${batchInsert.blockCount}")
    public int BATCHINSERT_BLOCK_COUNT;

    @Value("${batchInsert.sqlCount}")
    public int BATCHINSERT_SQL_COUNT;

}
