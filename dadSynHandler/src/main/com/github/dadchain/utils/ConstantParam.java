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


package com.github.dadchain.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.ontio.OntSdk;
import com.github.dadchain.model.common.BatchBlockDto;

import java.math.BigDecimal;
import java.util.*;

public final class ConstantParam {
    /**
     * 节点restfulurl列表
     */
    public static List<String> NODE_RESTFULURLLIST = new ArrayList<>();

    /**
     * 主节点restfulurl
     */
    public static String MASTERNODE_RESTFULURL = "";

    /**
     * 主节点在列表中的序列号
     */
    public static int MASTERNODE_INDEX = 0;

    /**
     * Ontology SDK object
     */
    public static OntSdk ONT_SDKSERVICE = null;

    /**
     * the number of new ontid of batch block
     */
    public static int BATCHBLOCK_ONTID_COUNT = 0;

    /**
     * the number of transactions of batch block
     */
    public static int BATCHBLOCK_TX_COUNT = 0;

    public static int BLOCK_BOOKKEEPER_COUNT = 0;

    /**
     * the contracthash list of batch block
     */
    public static List<String> BATCHBLOCK_CONTRACTHASH_LIST = new ArrayList<>();

    /**
     * the batchblockdto of batch block
     */
    public static BatchBlockDto BATCHBLOCKDTO = new BatchBlockDto();

    /**
     * Push
     */
    public static final String CLAIMRECORD_OPE_PREFIX = "Push";

    /**
     * registerCandidate
     */
    public static final String REGISTER = "Register";

    /**
     * add action
     */
    public static final String ADD = "add";

    public static final BigDecimal ZERO = new BigDecimal("0");

    public static final BigDecimal ONE = new BigDecimal("1");

    public static final BigDecimal ONG_DECIMAL = new BigDecimal("1000000000");

    //ontid operation description separator
    public static final String ONTID_SEPARATOR = "||";

    //ontid operation description separator
    public static final String ONTID_SEPARATOR2 = "&";

    public static Map<String, JSONObject> OEP4MAP = new HashMap<>();

    public static Set<String> OEP4CONTRACTS = new HashSet<>();

    public static Map<String, JSONObject> OEP5MAP = new HashMap<>();

    public static Set<String> OEP5CONTRACTS = new HashSet<>();

    public static Map<String, JSONObject> OEP8MAP = new HashMap<>();

    public static Set<String> OEP8CONTRACTS = new HashSet<>();



    public static final String ASSET_NAME_ONT = "ONT";

    public static final String ASSET_NAME_ONG = "ONG";

    public static final String ASSET_NAME_DAD = "DAD";


    public static final String ASSET_NAME_DRAGON = "HyperDragons: ";

    public static final String IS_OEP4TX = "is_oep4tx";

    public static final String IS_OEP5TX = "is_oep5tx";

    public static final String IS_OEP8TX = "is_oep8tx";

    public static final String TXPAYLOAD_CODE_FLAG = "67";


    public static final String AUTH_CONTRACTHASH = "24a15c6aed092dfaa711c4974caf1e9d307bf4b5";


    public static final Long DAY_IN_SECONDS = 24 * 60 * 60L;

    public static final Long TX_MAX_IN_BLOCK = 100000L;


}
