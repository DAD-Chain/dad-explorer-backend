package com.github.dadchain.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.github.dadchain.model.dto.TransferTxDetailDto;
import com.github.dadchain.util.dadnative.DADNativeContract;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The class Eventlog parse
 */
public final class EventLogParseUtil {

    @Data
    @Accessors(chain = true)
    public static class States {
        @JSONField(name = "States")
        Object[] states;

        @JSONField(name = "ContractAddress")
        String contractAddress;
    }

    @Data
    @Accessors(chain = true)
    public static class EvtLog {
        @JSONField(name = "GasConsumed")
        private long gasConsumed;

        @JSONField(name = "TxHash")
        private String txHash;

        @JSONField(name = "State")
        private int state;

        @JSONField(name = "Notify")
        private States[] notify;
    }

    private static final String DAD_TOKEN_EVT_CONTRACT_ADDRESS = "0800000000000000000000000000000000000000";
    private static final String DAD_V2_EVT_CONTRACT_ADDRESS = "0a00000000000000000000000000000000000000";

    private static void parseNativeDADToken(Object detailNotify, String tokenField, String field) {
        if (detailNotify instanceof JSONObject) {
            JSONObject detail = (JSONObject) detailNotify;
            String token = detail.get(tokenField).toString();
            if (token.equals(DADNativeContract.PLEDGE_TOKEN_DAD)) {
                String aStr = detail.get(field).toString();
                if (StringUtils.isNotEmpty(aStr)) {
                    BigDecimal amount = new BigDecimal(aStr);
                    BigDecimal unit = BigDecimal.valueOf(1, -9);
                    detail.put(field, amount.divide(unit));
                }
            }
        }
    }

    private static void parseNativeDADEvent(JSONArray stateList) {
        String action = (String) stateList.get(0);
        switch (action) {
            case DADNativeContract.PLEDGE:
            case DADNativeContract.PLACE_AD: {
                parseNativeDADToken(stateList.get(1), "Token", "Bid");
                break;
            }
//            case DADNativeContract.PLACE_PUBLISHER_WEBWAP: {
//                break;
//            }
//            case DADNativeContract.PLACE_PUBLISHER_APP: {
//                break;
//            }
//            case DADNativeContract.CONTRIBUTE: {
//                break;
//            }
//            case DADNativeContract.CHANGE_AD_ORDER_STATUS: {
//                break;
//            }
            case DADNativeContract.DISPATCH_BONUS:
            case DADNativeContract.DISTRIBUTE_BONUS: {
                parseNativeDADToken(stateList.get(1), "Token", "Price");
                break;
            }
        }
    }

    private static void praseDadTransfer(JSONArray states) {
        if (!"transfer".equals(states.get(0).toString())) {
            return;
        }
        int amountIndx = 3;
        BigDecimal amount = new BigDecimal((states.get(amountIndx)).toString());
        states.remove(amountIndx);
        states.add(amount.divide(ConstantParam.DAD_DECIMAL));
    }

    public static void parseNativeDADToken(Object detailNotify) {
        try {
            JSONObject detail = (JSONObject) detailNotify;
            String address = detail.get("ContractAddress").toString();
            JSONArray states = (JSONArray) detail.get("States");
            switch (address) {
                case DAD_TOKEN_EVT_CONTRACT_ADDRESS: {
                    praseDadTransfer(states);
                    break;
                }
                case DAD_V2_EVT_CONTRACT_ADDRESS: {
                    parseNativeDADEvent(states);
                    break;
                }
                default: {
                    //
                }
            }
        } catch (Exception e) {
            //
        }
    }

    public static List<TransferTxDetailDto> buildTxDADTransfer(String txLog) {
        List<TransferTxDetailDto> transferTxnList = new ArrayList<>();
        if (txLog == null) {
            return transferTxnList;
        }
        try {
            EvtLog evtLog = JSON.parseObject(txLog, EvtLog.class);
            for (States stat : evtLog.notify) {
                if (stat.contractAddress.equals(DAD_TOKEN_EVT_CONTRACT_ADDRESS)) {
                    //dad transfer
                    String method = (String) stat.states[0];
                    if (!method.equals("transfer")) {
                        continue;
                    }
                    String from = (String) stat.states[1];
                    String to = (String) stat.states[2];
                    BigDecimal amount = new BigDecimal((stat.states[3]).toString());
                    amount = amount.divide(ConstantParam.DAD_DECIMAL);

                    TransferTxDetailDto transferTxDetailDto = TransferTxDetailDto.builder()
                            .amount(amount)
                            .fromAddress(from)
                            .toAddress(to)
                            .assetName("DAD")
                            .build();
                    if (amount.compareTo(BigDecimal.ZERO) > 0) {
                        transferTxnList.add(0, transferTxDetailDto);
                    } else {
                        transferTxnList.add(transferTxDetailDto);
                    }
                }
            }
        } catch (Exception e) {
            //
        }
        return transferTxnList;
    }

}
