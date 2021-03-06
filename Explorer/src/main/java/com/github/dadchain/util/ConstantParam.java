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


package com.github.dadchain.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2018/2/27
 */
public class ConstantParam {



    /**
     * claim
     */
    public static final String CLAIM = "claim";

    public static final String HYPERDRAGONS = "dragon";

    public static final String PUMPKIN_PREFIX = "pumpkin";


    /**
     * ONT asset
     */
    public static final String ONT = "ONT";

    public static final String ONG = "ONG";

    public static final String DAD = "DAD";

    public static final String DAD_RPC = "dad";

    public static final String UNBOUND_ONG = "unboundong";

    public static final String WAITBOUND_ONG = "waitboundong";

    public static final String OEP8_PUMPKIN_PREFIX = "pumpkin";


    public static final BigDecimal ZERO = new BigDecimal("0");


    public static final BigDecimal ONG_SECONDMAKE = new BigDecimal("5");


    public static final BigDecimal ONT_TOTAL = new BigDecimal("1000000000");

    public static final BigDecimal ONG_TOTAL = new BigDecimal("1000000000");
    public static final BigDecimal DAD_DECIMAL = new BigDecimal("1000000000");


    public static final String GO_TOTALSUPPLY_URL = "/getAssetHolder";


    public static final List<String> SPECIALADDRLIST = Arrays.asList(
            //团队锁仓地址
            "AKac3Bd6usdivrnNN8tyRcDZN94vpaoAu2",
            //共建合作伙伴锁仓地址
            "AS7MjVEicEsJ4zjEfm2LoKoYoFsmapD7rT",
            //技术社区奖励锁仓地址
            "AcdUMgeF16ScW9ts3kiD3pZkjYRMdYwtVQ",
            //生态合作地址
            "AMX6ZebrPDFELCYRMpSMbZWrhWkKbKg4y8");


    //资产类型
    public static final String ASSET_TYPE_NATIVE = "native";

    public static final String ASSET_TYPE_OEP4 = "oep4";

    public static final String ASSET_TYPE_OEP5 = "oep5";

    public static final String ASSET_TYPE_OEP8 = "oep8";

    public static final String ASSET_TYPE_ALL = "all";

    //合约审核标识
    public static final Boolean AUDIT_PASSED = true;

    public static final Boolean AUDIT_UNPASSED = false;

    //合约类型
    public static final String CONTRACT_TYPE_OEP4 = "oep4";

    public static final String CONTRACT_TYPE_OEP5 = "oep5";

    public static final String CONTRACT_TYPE_OEP8 = "oep8";

    public static final String CONTRACT_TYPE_OTHER = "other";


    /**
     * 对外访问实时性要求不高的接口中对应的mapper，属于历史数据和统计数据，基本不会修改。redis中可以设置过期时间较长，
     */
    public static final List<String> REDIS_LONGEXPIRETIME_KEYLIST = new ArrayList<String>(
            Arrays.asList(
                    "BlockMapper.selectOneByHeight",
                    "BlockMapper.selectOneByHash",
                    "TxDetailMapper.selectTransferTxDetailByHash",
                    "TxEventLogMapper.selectTxsByBlockHeight",
                    "ContractMapper.selectContractDetail",
                    "ContractDailySummaryMapper.selectDailySummaryByContractHash",
                    "DailySummaryMapper.selectSummaryByTime",
                    "DailySummaryMapper.selectAddrAndOntIdTotal",
                    "OntidTxDetailMapper.selectOneByTxHash"
            )
    );


    /**
     * 对外访问实时性要求不高的接口中对应的mapper，数据变动频率小，redis中可以设置过期时间较长
     */
    public static final List<String> REDIS_MEDIUMEXPIRETIME_KEYLIST = new ArrayList<String>(
            Arrays.asList(
                    "ContractMapper.selectApprovedContract",
                    "ContractMapper.selectApprovedContractCount",
                    "Oep4Mapper.selectOep4Tokens",
                    "Oep5Mapper.selectOep5Tokens",
                    "Oep8Mapper.selectOep8Tokens",
                    "Oep4Mapper.selectOep4TokenDetail",
                    "Oep5Mapper.selectOep5TokenDetail",
                    "Oep8Mapper.selectOep8TokenDetail",
                    "Oep8Mapper.selectAuditPassedOep8",
                    "TxEventLogMapper.queryTxCount"
            )
    );

    public static final String ADDR_DAILY_SUMMARY_ONTONG_CONTRACTHASH = "0000000000000000000000000000000000000000";

    public static final Integer REQTIME_MAX_RANGE = 40 * 24 * 60 * 60;

    public static final Integer REQTIME_MAX_RANGE_WEEK = 7 * 24 * 60 * 60;

    public static final Integer REQTIME_MAX_RANGE_100_DAYS = 100 * 24 * 60 * 60;

    public static  final  Integer DAILY_SECONDS = 24 * 60 * 60;


    public static final String BALANCESERVICE_QUERYBALANCE_URL = "/api/v1/multibalance";

    public static final String CHANNEL_ONTO = "onto";


}
