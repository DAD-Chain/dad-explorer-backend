package com.github.dadchain.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.dadchain.mapper.*;
import com.github.dadchain.model.dto.*;
import com.github.dadchain.util.*;
import com.github.dadchain.config.ParamsConfig;
import com.github.dadchain.model.common.PageResponseBean;
import com.github.dadchain.model.common.ResponseBean;
import com.github.dadchain.model.dao.Oep4;
import com.github.dadchain.model.dao.Oep5;
import com.github.dadchain.service.IAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/28
 */
@Slf4j
@Service("AddressService")
public class AddressServiceImpl implements IAddressService {

    private static final String ADDRESS_TYPE_FROM = "fromAddress";

    private static final String ADDRESS_TYPE_TO = "toAddress";

    private final Oep4Mapper oep4Mapper;
    private final Oep8Mapper oep8Mapper;
    private final Oep5Mapper oep5Mapper;
    private final TxDetailMapper txDetailMapper;
    private final ParamsConfig paramsConfig;
    private final CommonService commonService;
    private final TxEventLogMapper txEventLogMapper;
    private final AddressTxsMapper addressTxsMapper;

    @Autowired
    public AddressServiceImpl(Oep4Mapper oep4Mapper, Oep8Mapper oep8Mapper, Oep5Mapper oep5Mapper, TxDetailMapper txDetailMapper, ParamsConfig paramsConfig, CommonService commonService,
                              TxEventLogMapper txEventLogMapper,
                              AddressTxsMapper addressTxsMapper) {
        this.oep4Mapper = oep4Mapper;
        this.oep8Mapper = oep8Mapper;
        this.oep5Mapper = oep5Mapper;
        this.txDetailMapper = txDetailMapper;
        this.paramsConfig = paramsConfig;
        this.commonService = commonService;
        this.txEventLogMapper = txEventLogMapper;
        this.addressTxsMapper = addressTxsMapper;
    }

    private DadSDKService sdk;

    private synchronized void initSDK() {
        if (sdk == null) {
            sdk = DadSDKService.getInstance(paramsConfig);
        }
    }

    private static final String BALANCESERVICE_ACTION_GETMULTIBALANCE = "getmultibalance";

    private static final String BALANCESERVICE_VERSION = "1.0.0";

    private static final Integer TIMESTAMP_20190630000000_UTC = 1561852800;


    @Override
    public ResponseBean<List<BalanceDto>> queryAddressBalance(String address, String tokenType) {

        List<BalanceDto> balanceList = new ArrayList<>();

        if (paramsConfig.QUERYBALANCE_MODE == 1) {
            switch (tokenType.toLowerCase()) {
                case ConstantParam.ASSET_TYPE_OEP4:
                    balanceList = getOep4Balance(address, "");
                    break;
                case ConstantParam.ASSET_TYPE_OEP5:
                    balanceList = getOep5Balance(address, "", "");
                    break;
                case ConstantParam.ASSET_TYPE_OEP8:
                    balanceList = getOep8Balance(address, "");
                    break;
                case ConstantParam.ASSET_TYPE_NATIVE:
                    balanceList = getNativeBalance(address);
                    break;
                case ConstantParam.ASSET_TYPE_ALL:
                    balanceList = getAllAssetBalance(address);
                    break;
                default:
                    break;
            }
        } else if (paramsConfig.QUERYBALANCE_MODE == 0) {
            switch (tokenType.toLowerCase()) {
                case ConstantParam.ASSET_TYPE_OEP4:
                    balanceList = getOep4BalanceOld(address, "");
                    break;
                case ConstantParam.ASSET_TYPE_OEP5:
                    balanceList = getOep5BalanceOld(address, "", "");
                    break;
                case ConstantParam.ASSET_TYPE_OEP8:
                    balanceList = getOep8BalanceOld(address, "");
                    break;
                case ConstantParam.ASSET_TYPE_NATIVE:
                    balanceList = getNativeBalance(address);
                    break;
                case ConstantParam.ASSET_TYPE_ALL:
                    balanceList = getAllAssetBalanceOld(address);
                    break;
                default:
                    break;
            }
        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), balanceList);
    }


    @Override
    public ResponseBean queryAddressBalanceByAssetName4Onto(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();

        if (ConstantParam.ONT.equals(assetName)) {

            initSDK();
            Map<String, Object> balanceMap = sdk.getNativeAssetBalance(address);
            //ONT
            BalanceDto balanceDto = BalanceDto.builder()
                    .assetName(ConstantParam.ONT)
                    .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                    .balance(new BigDecimal((String) balanceMap.get(ConstantParam.ONT)))
                    .build();
            balanceList.add(balanceDto);

        } else if (ConstantParam.ONG.equals(assetName)) {

            initSDK();
            Map<String, Object> balanceMap = sdk.getNativeAssetBalance(address);
            //ONG
            BalanceDto balanceDto1 = BalanceDto.builder()
                    .assetName(ConstantParam.ONG)
                    .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                    .balance((new BigDecimal((String) balanceMap.get(ConstantParam.ONG)).divide(ConstantParam.ONG_TOTAL)))
                    .build();
            balanceList.add(balanceDto1);

            //waiting bound ONG
            String waitBoundOng = calculateWaitingBoundOng(address, (String) balanceMap.get(ConstantParam.ONT));
            BalanceDto balanceDto2 = BalanceDto.builder()
                    .assetName(ConstantParam.WAITBOUND_ONG)
                    .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                    .balance((new BigDecimal(waitBoundOng)))
                    .build();
            balanceList.add(balanceDto2);

            //Claimable ONG
            String unBoundOng = sdk.getUnBoundOng(address);
            if (Helper.isEmptyOrNull(unBoundOng)) {
                unBoundOng = "0";
            }
            BalanceDto balanceDto3 = BalanceDto.builder()
                    .assetName(ConstantParam.UNBOUND_ONG)
                    .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                    .balance((new BigDecimal(unBoundOng)))
                    .build();
            balanceList.add(balanceDto3);
        }
        if (paramsConfig.QUERYBALANCE_MODE == 1) {
            //return oep4+oep5 token balance
            balanceList.addAll(getOep4Balance(address, ""));
            balanceList.addAll(getOep5Balance(address, "", ConstantParam.CHANNEL_ONTO));
            if (assetName.startsWith(ConstantParam.PUMPKIN_PREFIX)) {
                balanceList.addAll(getOep8Balance4Onto(address, ""));
            }
        } else if (paramsConfig.QUERYBALANCE_MODE == 0) {
            //return oep4+oep5 token balance
            balanceList.addAll(getOep4BalanceOld(address, ""));
            balanceList.addAll(getOep5BalanceOld(address, "", ConstantParam.CHANNEL_ONTO));
            if (assetName.startsWith(ConstantParam.PUMPKIN_PREFIX)) {
                balanceList.addAll(getOep8Balance4OntoOld(address, ""));
            }
        }

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), balanceList);
    }

    /**
     * 获取原生资产余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getNativeBalance(String address) {

        List<BalanceDto> balanceList = new ArrayList<>();

        initSDK();
        Map<String, Object> balanceMap = sdk.getNativeAssetBalance(address);
        //ONG
        BalanceDto balanceDto1 = BalanceDto.builder()
                .assetName(ConstantParam.ONG)
                .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                .balance((new BigDecimal((String) balanceMap.get(ConstantParam.ONG)).divide(ConstantParam.ONG_TOTAL)))
                .build();
        balanceList.add(balanceDto1);

        //waiting bound ONG
        String waitBoundOng = calculateWaitingBoundOng(address, (String) balanceMap.get(ConstantParam.ONT));
        BalanceDto balanceDto2 = BalanceDto.builder()
                .assetName(ConstantParam.WAITBOUND_ONG)
                .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                .balance((new BigDecimal(waitBoundOng)))
                .build();
        balanceList.add(balanceDto2);

        //Claimable ONG
        String unBoundOng = sdk.getUnBoundOng(address);
        if (Helper.isEmptyOrNull(unBoundOng)) {
            unBoundOng = "0";
        }
        BalanceDto balanceDto3 = BalanceDto.builder()
                .assetName(ConstantParam.UNBOUND_ONG)
                .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                .balance((new BigDecimal(unBoundOng)))
                .build();
        balanceList.add(balanceDto3);

        //ONT
        BalanceDto balanceDto4 = BalanceDto.builder()
                .assetName(ConstantParam.ONT)
                .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                .balance(new BigDecimal((String) balanceMap.get(ConstantParam.ONT)))
                .build();
        balanceList.add(balanceDto4);

        return balanceList;
    }

    /**
     * get all type asset balance
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getAllAssetBalance(String address) {

        List<BalanceDto> balanceDtos = new ArrayList<>();
        balanceDtos.addAll(getNativeBalance(address));
        balanceDtos.addAll(getOep4Balance(address, ""));
        balanceDtos.addAll(getOep5Balance(address, "", ""));
        balanceDtos.addAll(getOep8Balance(address, ""));
        return balanceDtos;
    }


    /**
     * get all type asset balance
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getAllAssetBalanceOld(String address) {

        List<BalanceDto> balanceDtos = new ArrayList<>();
        balanceDtos.addAll(getNativeBalance(address));
        balanceDtos.addAll(getOep4BalanceOld(address, ""));
        balanceDtos.addAll(getOep5BalanceOld(address, "", ""));
        balanceDtos.addAll(getOep8BalanceOld(address, ""));
        return balanceDtos;
    }


    /**
     * 获取OEP4余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep4BalanceOld(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        //审核过的OEP4余额
        Oep4 oep4Temp = new Oep4();
        oep4Temp.setAuditFlag(ConstantParam.AUDIT_PASSED);
        if (Helper.isNotEmptyOrNull(assetName)) {
            oep4Temp.setSymbol(assetName);
        }
        List<Oep4> oep4s = oep4Mapper.select(oep4Temp);
        for (Oep4 oep4 :
                oep4s) {
            String contractHash = oep4.getContractHash();
            BigDecimal balance = new BigDecimal(sdk.getOep4AssetBalance(address, contractHash)).divide(new BigDecimal(Math.pow(10, oep4.getDecimals())));
            if (balance.compareTo(ConstantParam.ZERO) == 0) {
                continue;
            }
            BalanceDto balanceDto = BalanceDto.builder()
                    .assetName(oep4.getSymbol())
                    .assetType(ConstantParam.ASSET_TYPE_OEP4)
                    .balance(balance)
                    .build();
            balanceList.add(balanceDto);
        }
        return balanceList;
    }


    /**
     * 获取OEP4余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep4Balance2(String address, Oep4 oep4) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        String contractHash = oep4.getContractHash();
        BigDecimal balance = new BigDecimal(sdk.getOep4AssetBalance(address, contractHash)).divide(new BigDecimal(Math.pow(10, oep4.getDecimals())));
        BalanceDto balanceDto = BalanceDto.builder()
                .assetName(oep4.getSymbol())
                .assetType(ConstantParam.ASSET_TYPE_OEP4)
                .balance(balance)
                .build();
        balanceList.add(balanceDto);
        return balanceList;
    }


    /**
     * 获取OEP5余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep5BalanceOld(String address, String assetName, String channel) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        //审核过的OEP5余额
        Oep5 oep5Temp = new Oep5();
        oep5Temp.setAuditFlag(ConstantParam.AUDIT_PASSED);
        if (Helper.isNotEmptyOrNull(assetName)) {
            oep5Temp.setSymbol(assetName);
        }
        List<Oep5> oep5s = oep5Mapper.select(oep5Temp);
        for (Oep5 oep5 :
                oep5s) {
            String contractHash = oep5.getContractHash();
            BigDecimal balance = new BigDecimal(sdk.getOep5AssetBalance(address, contractHash));
            if (balance.compareTo(ConstantParam.ZERO) == 0) {
                continue;
            }
            if (ConstantParam.CHANNEL_ONTO.equals(channel)) {
                //ONTO return name
                BalanceDto balanceDto = BalanceDto.builder()
                        .assetName(oep5.getName())
                        .assetType(ConstantParam.ASSET_TYPE_OEP5)
                        .balance(balance)
                        .build();
                balanceList.add(balanceDto);
            } else {
                //other return symbol
                BalanceDto balanceDto = BalanceDto.builder()
                        .assetName(oep5.getSymbol())
                        .assetType(ConstantParam.ASSET_TYPE_OEP5)
                        .balance(balance)
                        .build();
                balanceList.add(balanceDto);
            }
        }
        return balanceList;
    }


    /**
     * 获取OEP5余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep5Balance2(String address, Oep5 oep5) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();

        String contractHash = oep5.getContractHash();
        BigDecimal balance = new BigDecimal(sdk.getOep5AssetBalance(address, contractHash));
        BalanceDto balanceDto = BalanceDto.builder()
                .assetName(oep5.getSymbol())
                .assetType(ConstantParam.ASSET_TYPE_OEP5)
                .balance(balance)
                .build();
        balanceList.add(balanceDto);
        return balanceList;
    }


    /**
     * 获取OEP8余额
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep8BalanceOld(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        //审核过的OEP8余额
        if (Helper.isNotEmptyOrNull(assetName)) {
            assetName = assetName + "%";
        }
        List<Map<String, String>> oep8s = oep8Mapper.selectAuditPassedOep8(assetName);
        for (Map<String, String> map :
                oep8s) {
            String contractHash = map.get("contractHash");
            String symbol = map.get("symbol");

            JSONArray balanceArray = sdk.getOpe8AssetBalance(address, contractHash);
            String[] symbolArray = symbol.split(",");
            for (int i = 0; i < symbolArray.length; i++) {
                if (Integer.parseInt((String) balanceArray.get(i)) == 0) {
                    continue;
                }
                BalanceDto balanceDto = BalanceDto.builder()
                        .assetName(symbolArray[i])
                        .assetType(ConstantParam.ASSET_TYPE_OEP8)
                        .balance(new BigDecimal((String) balanceArray.get(i)))
                        .build();
                balanceList.add(balanceDto);
            }
        }
        return balanceList;
    }


    /**
     * get oep4 balance
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep4Balance(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        //query audit passed oep4 token
        Oep4 oep4Temp = new Oep4();
        oep4Temp.setAuditFlag(ConstantParam.AUDIT_PASSED);
        if (Helper.isNotEmptyOrNull(assetName)) {
            oep4Temp.setSymbol(assetName);
        }
        List<Oep4> oep4s = oep4Mapper.select(oep4Temp);
        oep4s.forEach(item -> stringBuilder.append(item.getContractHash()).append(","));
        String contractAddrsStr = stringBuilder.toString();
        if (Helper.isEmptyOrNull(contractAddrsStr)) {
            return balanceList;
        }

        QueryBatchBalanceDto queryBatchBalanceDto = QueryBatchBalanceDto.builder()
                .action(BALANCESERVICE_ACTION_GETMULTIBALANCE)
                .version(BALANCESERVICE_VERSION)
                .base58Addrs(address)
                .contractAddrs(contractAddrsStr.substring(0, contractAddrsStr.length() - 1))
                .build();

        String responseStr = commonService.httpPostRequest(paramsConfig.BALANCESERVICE_HOST + ConstantParam.BALANCESERVICE_QUERYBALANCE_URL,
                JacksonUtil.beanToJSonStr(queryBatchBalanceDto), null);
        if (Helper.isNotEmptyOrNull(responseStr)) {
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            JSONArray oepBalanceArray = ((JSONObject) jsonObject.getJSONArray("Result").get(0)).getJSONArray("OepBalance");

            Map<String, String> map = new HashMap<>();
            oepBalanceArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                map.put(obj.getString("contract_address"), obj.getString("balance"));
            });

            oep4s.forEach(item -> {
                        String contractHash = item.getContractHash();
                        BigDecimal balance = new BigDecimal(map.get(contractHash));
                        //only return balance != 0 token
                        if (balance.compareTo(ConstantParam.ZERO) != 0) {
                            BalanceDto balanceDto = BalanceDto.builder()
                                    .assetName(item.getSymbol())
                                    .assetType(ConstantParam.ASSET_TYPE_OEP4)
                                    .balance(balance)
                                    .build();
                            balanceList.add(balanceDto);
                        }
                    }
            );
        }
        return balanceList;
    }


    /**
     * get oep5 balance
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep5Balance(String address, String assetName, String channel) {

        List<BalanceDto> balanceList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        //query audit passed oep5 token
        Oep5 oep5Temp = new Oep5();
        oep5Temp.setAuditFlag(ConstantParam.AUDIT_PASSED);
        if (Helper.isNotEmptyOrNull(assetName)) {
            oep5Temp.setSymbol(assetName);
        }
        List<Oep5> oep5s = oep5Mapper.select(oep5Temp);
        oep5s.forEach(item -> stringBuilder.append(item.getContractHash()).append(","));
        String contractAddrsStr = stringBuilder.toString();
        if (Helper.isEmptyOrNull(contractAddrsStr)) {
            return balanceList;
        }

        QueryBatchBalanceDto queryBatchBalanceDto = QueryBatchBalanceDto.builder()
                .action(BALANCESERVICE_ACTION_GETMULTIBALANCE)
                .version(BALANCESERVICE_VERSION)
                .base58Addrs(address)
                .contractAddrs(contractAddrsStr.substring(0, contractAddrsStr.length() - 1))
                .build();

        String responseStr = commonService.httpPostRequest(paramsConfig.BALANCESERVICE_HOST + ConstantParam.BALANCESERVICE_QUERYBALANCE_URL,
                JacksonUtil.beanToJSonStr(queryBatchBalanceDto), null);
        if (Helper.isNotEmptyOrNull(responseStr)) {
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            JSONArray oepBalanceArray = ((JSONObject) jsonObject.getJSONArray("Result").get(0)).getJSONArray("OepBalance");

            Map<String, String> map = new HashMap<>();
            oepBalanceArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                map.put(obj.getString("contract_address"), obj.getString("balance"));
            });

            oep5s.forEach(item -> {
                        String contractHash = item.getContractHash();
                        BigDecimal balance = new BigDecimal(map.get(contractHash));
                        if (balance.compareTo(ConstantParam.ZERO) != 0) {
                            //ONTO返回name
                            if (ConstantParam.CHANNEL_ONTO.equals(channel)) {
                                BalanceDto balanceDto = BalanceDto.builder()
                                        .assetName(item.getName())
                                        .assetType(ConstantParam.ASSET_TYPE_OEP5)
                                        .balance(balance)
                                        .build();
                                balanceList.add(balanceDto);
                            } else {
                                //其他渠道返回symbol
                                //TODO 后续统一
                                BalanceDto balanceDto = BalanceDto.builder()
                                        .assetName(item.getSymbol())
                                        .assetType(ConstantParam.ASSET_TYPE_OEP5)
                                        .balance(balance)
                                        .build();
                                balanceList.add(balanceDto);
                            }
                        }
                    }
            );
        }
        return balanceList;
    }

    /**
     * get oep8 token
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep8Balance(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        //query audit passed oep8 token
        if (Helper.isNotEmptyOrNull(assetName)) {
            assetName = assetName + "%";
        }
        List<Map<String, String>> oep8s = oep8Mapper.selectAuditPassedOep8(assetName);
        oep8s.forEach(item -> {
            String tokenIdStr = item.get("tokenId");
            String str = tokenIdStr.replace(",", "&");
            stringBuilder.append(item.get("contractHash")).append(":").append(str).append(",");
        });
        String contractAddrsStr = stringBuilder.toString();
        if (Helper.isEmptyOrNull(contractAddrsStr)) {
            return balanceList;
        }

        QueryBatchBalanceDto queryBatchBalanceDto = QueryBatchBalanceDto.builder()
                .action(BALANCESERVICE_ACTION_GETMULTIBALANCE)
                .version(BALANCESERVICE_VERSION)
                .base58Addrs(address)
                .contractAddrs(contractAddrsStr.substring(0, contractAddrsStr.length() - 1))
                .build();

        String responseStr = commonService.httpPostRequest(paramsConfig.BALANCESERVICE_HOST + ConstantParam.BALANCESERVICE_QUERYBALANCE_URL,
                JacksonUtil.beanToJSonStr(queryBatchBalanceDto), null);
        if (Helper.isNotEmptyOrNull(responseStr)) {
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            JSONArray oepBalanceArray = ((JSONObject) jsonObject.getJSONArray("Result").get(0)).getJSONArray("OepBalance");

            Map<String, String> map = new HashMap<>();
            oepBalanceArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                map.put(obj.getString("contract_address"), obj.getString("balance"));
            });

            oep8s.forEach(item -> {
                        String contractHash = item.get("contractHash");
                        String symbolStr = item.get("symbol");
                        String[] symbolArray = symbolStr.split(",");
                        String balanceStr = map.get(contractHash);
                        String[] balanceArray = balanceStr.split(",");
                        for (int i = 0; i < symbolArray.length; i++) {
                            BalanceDto balanceDto = BalanceDto.builder()
                                    .assetName(symbolArray[i])
                                    .assetType(ConstantParam.ASSET_TYPE_OEP8)
                                    .balance(new BigDecimal((String) balanceArray[i]))
                                    .build();
                            balanceList.add(balanceDto);
                        }
                    }
            );
        }

        return balanceList;
    }


    /**
     * get oep8 token
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep8Balance4Onto(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (Helper.isNotEmptyOrNull(assetName)) {
            assetName = assetName + "%";
        }
        //query audit passed oep8 token
        List<Map<String, String>> oep8s = oep8Mapper.selectAuditPassedOep8(assetName);
        oep8s.forEach(item -> {
            String tokenIdStr = item.get("tokenId");
            String str = tokenIdStr.replace(",", "&");
            stringBuilder.append(item.get("contractHash")).append(":").append(str).append(",");
        });
        String contractAddrsStr = stringBuilder.toString();
        if (Helper.isEmptyOrNull(contractAddrsStr)) {
            return balanceList;
        }

        QueryBatchBalanceDto queryBatchBalanceDto = QueryBatchBalanceDto.builder()
                .action(BALANCESERVICE_ACTION_GETMULTIBALANCE)
                .version(BALANCESERVICE_VERSION)
                .base58Addrs(address)
                .contractAddrs(contractAddrsStr.substring(0, contractAddrsStr.length() - 1))
                .build();

        String responseStr = commonService.httpPostRequest(paramsConfig.BALANCESERVICE_HOST + ConstantParam.BALANCESERVICE_QUERYBALANCE_URL,
                JacksonUtil.beanToJSonStr(queryBatchBalanceDto), null);
        if (Helper.isNotEmptyOrNull(responseStr)) {
            JSONObject jsonObject = JSONObject.parseObject(responseStr);
            JSONArray oepBalanceArray = ((JSONObject) jsonObject.getJSONArray("Result").get(0)).getJSONArray("OepBalance");

            Map<String, String> map = new HashMap<>();
            oepBalanceArray.forEach(item -> {
                JSONObject obj = (JSONObject) item;
                map.put(obj.getString("contract_address"), obj.getString("balance"));
            });

            oep8s.forEach(item -> {
                        String contractHash = item.get("contractHash");
                        String symbolStr = item.get("symbol");
                        String[] symbolArray = symbolStr.split(",");
                        String balanceStr = map.get(contractHash);
                        String[] balanceArray = balanceStr.split(",");
                        int total = 0;
                        for (int i = 0; i < symbolArray.length; i++) {
                            BalanceDto balanceDto = BalanceDto.builder()
                                    .assetName(symbolArray[i])
                                    .assetType(ConstantParam.ASSET_TYPE_OEP8)
                                    .balance(new BigDecimal((String) balanceArray[i]))
                                    .build();
                            balanceList.add(balanceDto);
                            total += Integer.parseInt(balanceArray[i]);
                        }
                        BalanceDto balanceDto = BalanceDto.builder()
                                .assetName("totalpumpkin")
                                .assetType(ConstantParam.ASSET_TYPE_OEP8)
                                .balance(new BigDecimal(total))
                                .build();
                        balanceList.add(balanceDto);
                    }
            );
        }

        return balanceList;
    }


    /**
     * get oep8 token
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep8Balance4OntoOld(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        //审核过的OEP8余额
        if (Helper.isNotEmptyOrNull(assetName)) {
            assetName = assetName + "%";
        }
        List<Map<String, String>> oep8s = oep8Mapper.selectAuditPassedOep8(assetName);
        for (Map<String, String> map :
                oep8s) {
            String contractHash = map.get("contractHash");
            String symbol = map.get("symbol");
            String[] symbolArray = symbol.split(",");
            int total = 0;

            JSONArray balanceArray = sdk.getOpe8AssetBalance(address, contractHash);
            for (int i = 0; i < symbolArray.length; i++) {
                BalanceDto balanceDto = BalanceDto.builder()
                        .assetName(symbolArray[i])
                        .assetType(ConstantParam.ASSET_TYPE_OEP8)
                        .balance(new BigDecimal((String) balanceArray.get(i)))
                        .build();
                balanceList.add(balanceDto);
                total = total + Integer.valueOf((String) balanceArray.get(i));
            }
            BalanceDto balanceDto = BalanceDto.builder()
                    .assetName("totalpumpkin")
                    .assetType(ConstantParam.ASSET_TYPE_OEP8)
                    .balance(new BigDecimal(total))
                    .build();
            balanceList.add(balanceDto);
        }
        return balanceList;
    }


    /**
     * get oep8 token
     *
     * @param address
     * @return
     */
    private List<BalanceDto> getOep8Balance2(String address, String inputSymbol) {

        List<BalanceDto> balanceList = new ArrayList<>();
        initSDK();
        int i = Integer.valueOf(inputSymbol.substring(inputSymbol.length() - 1, inputSymbol.length()));
        List<Map<String, String>> oep8s = oep8Mapper.selectAuditPassedOep8(inputSymbol);

        String contractHash = oep8s.get(0).get("contractHash");
        String symbol = oep8s.get(0).get("symbol");

        JSONArray balanceArray = sdk.getOpe8AssetBalance(address, contractHash);

        BalanceDto balanceDto = BalanceDto.builder()
                .assetName(symbol)
                .assetType(ConstantParam.ASSET_TYPE_OEP8)
                .balance(new BigDecimal((String) balanceArray.get(i - 1)))
                .build();
        balanceList.add(balanceDto);
        return balanceList;
    }

    /**
     * 计算待提取的
     *
     * @param address
     * @param asset
     * @return
     */
    private String calculateWaitingBoundOng(String address, String asset) {

        Integer latestOntTransferTxTime = null;
        //mysql 4.0.14+ bug
        try {
            latestOntTransferTxTime = txDetailMapper.selectLatestOntTransferTxTime(address);
        } catch (Exception e) {
            log.error("{} error...", Helper.currentMethod(), e);
        }

        if (Helper.isEmptyOrNull(latestOntTransferTxTime)) {
            return "0";
        }
        long now = System.currentTimeMillis() / 1000L;
        log.info("calculateWaitingBoundOng latestOntTransferTxTime:{},now:{}", latestOntTransferTxTime, now);
        BigDecimal totalOng = new BigDecimal("0");
        //before 20190630000000 UTC
        if (latestOntTransferTxTime < TIMESTAMP_20190630000000_UTC) {
            BigDecimal ong01 = new BigDecimal(TIMESTAMP_20190630000000_UTC).subtract(new BigDecimal(latestOntTransferTxTime)).multiply(new BigDecimal(5));
            BigDecimal ong02 = new BigDecimal(now).subtract(new BigDecimal(TIMESTAMP_20190630000000_UTC)).multiply(paramsConfig.ONG_SECOND_GENERATE);
            totalOng = ong01.add(ong02);
        } else {
            totalOng = new BigDecimal(now).subtract(new BigDecimal(latestOntTransferTxTime)).multiply(paramsConfig.ONG_SECOND_GENERATE);
        }
        BigDecimal ong = totalOng.multiply(new BigDecimal(asset)).divide(ConstantParam.ONT_TOTAL);

        return ong.toPlainString();
    }


    /**
     * 获取分页后的转账交易列表
     *
     * @param pageNumber
     * @param pageSize
     * @param formattedTransferTxDtos
     * @return
     */
    private List<TransferTxDto> getTransferTxDtosByPage(int pageNumber, int pageSize, List<TransferTxDto> formattedTransferTxDtos) {

        int start = (pageNumber - 1) * pageSize <= 0 ? 0 : (pageNumber - 1) * pageSize;
        int end = (pageSize + start) > formattedTransferTxDtos.size() ? formattedTransferTxDtos.size() : (pageSize + start);

        return formattedTransferTxDtos.subList(start, end);
    }

    @Override
    public ResponseBean queryTransferTxsByTime(String address, String assetName, Long beginTime, Long endTime) {

        List<TransferTxDto> transferTxDtos = txDetailMapper.selectTransferTxsByTime(address, assetName, beginTime, endTime);

        List<TransferTxDto> formattedTransferTxDtos = formatTransferTxDtos(transferTxDtos);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), formattedTransferTxDtos);
    }

    @Override
    public ResponseBean queryTransferTxsByTime4Onto(String address, String assetName, Long beginTime, Long endTime, String addressType) {

        List<TransferTxDto> transferTxDtos = new ArrayList<>();

        if (Helper.isEmptyOrNull(addressType)) {
            //云斗龙资产使用like查询, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTime4Onto(address, assetName, beginTime, endTime);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTime4Onto(address, assetName, beginTime, endTime);
            }
        } else if (ADDRESS_TYPE_FROM.equals(addressType)) {
            //query transfer txs by fromaddress
            //dragon asset use 'like' query, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTimeInFromAddr4Onto(address, assetName, beginTime, endTime);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTimeInFromAddr4Onto(address, assetName, beginTime, endTime);
            }
        } else if (ADDRESS_TYPE_TO.equals(addressType)) {
            //query transfer txs by toaddress
            //dragon asset use 'like' query, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTimeInToAddr4Onto(address, assetName, beginTime, endTime);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTimeInToAddr4Onto(address, assetName, beginTime, endTime);
            }
        }
        List<TransferTxDto> formattedTransferTxDtos = formatTransferTxDtos(transferTxDtos);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), formattedTransferTxDtos);
    }

    @Override
    public ResponseBean queryTransferTxsByTimeAndPage4Onto(String address, String assetName, Long endTime, Integer pageSize, String addressType) {

        List<TransferTxDto> transferTxDtos = new ArrayList<>();

        if (Helper.isEmptyOrNull(addressType)) {
            //dragon asset use 'like' query, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTimeAndPage4Onto(address, assetName, endTime, pageSize);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTimeAndPage4Onto(address, assetName, endTime, pageSize);
            }
        } else if (ADDRESS_TYPE_FROM.equals(addressType)) {
            //query transfer txs by fromaddress
            //dragon asset use 'like' query, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTimeAndPageInFromAddr4Onto(address, assetName, endTime, pageSize);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTimeAndPageInFromAddr4Onto(address, assetName, endTime, pageSize);
            }
        } else if (ADDRESS_TYPE_TO.equals(addressType)) {
            //query transfer txs by toaddress
            //dragon asset use 'like' query, for ONTO
            if (ConstantParam.HYPERDRAGONS.equals(assetName)) {
                assetName = assetName + "%";
                transferTxDtos = txDetailMapper.selectDragonTransferTxsByTimeAndPageInToAddr4Onto(address, assetName, endTime, pageSize);
            } else {
                transferTxDtos = txDetailMapper.selectTransferTxsByTimeAndPageInToAddr4Onto(address, assetName, endTime, pageSize);
            }
        }
        List<TransferTxDto> formattedTransferTxDtos = formatTransferTxDtos(transferTxDtos);

        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), formattedTransferTxDtos);
    }

    /**
     * 格式化转账交易列表
     *
     * @return
     */
    private List<TransferTxDto> formatTransferTxDtos(List<TransferTxDto> transferTxDtos) {

        List<TransferTxDto> formattedTransferTxs = new ArrayList<>();

//        String previousTxHash = "";
//        int previousTxIndex = 0;
        for (int i = 0; i < transferTxDtos.size(); i++) {
            TransferTxDto transferTxDto = transferTxDtos.get(i);
            //ONG精度格式化
            String assetName = transferTxDto.getAssetName();
            BigDecimal amount = transferTxDto.getAmount();
            if (ConstantParam.DAD.equals(assetName)) {
                amount = amount.divide(ConstantParam.DAD_DECIMAL);
            }

            String txHash = transferTxDto.getTxHash();
            log.info("txHash:{}", txHash);

            TransferTxDetailDto transferTxDetailDto = TransferTxDetailDto.builder()
                    .amount(amount)
                    .fromAddress(transferTxDto.getFromAddress())
                    .toAddress(transferTxDto.getToAddress())
                    .assetName(transferTxDto.getAssetName())
                    .build();
            List<TransferTxDetailDto> transferTxnList = new ArrayList<>();
            transferTxnList.add(transferTxDetailDto);

            transferTxDto.setTransfers(transferTxnList);
            transferTxDto.setFromAddress(null);
            transferTxDto.setToAddress(null);
            transferTxDto.setAmount(null);
            transferTxDto.setAssetName(null);
            transferTxDto.setTxIndex(null);

            formattedTransferTxs.add(transferTxDto);
        }

        return formattedTransferTxs;
    }
    //////////////////////////////////////////

    @Override
    public ResponseBean<List<BalanceDto>> queryAddressBalanceByAssetName(String address, String assetName) {

        List<BalanceDto> balanceList = new ArrayList<>();

        if (ConstantParam.DAD_RPC.equals(assetName)) {

            initSDK();
            Map<String, Object> balanceMap = sdk.getNativeAssetBalance(address);
            //ONT
            BalanceDto balanceDto4 = BalanceDto.builder()
                    .assetName(ConstantParam.DAD)
                    .assetType(ConstantParam.ASSET_TYPE_NATIVE)
                    .balance(new BigDecimal((String) balanceMap.get(ConstantParam.DAD_RPC)).divide(ConstantParam.DAD_DECIMAL))
                    .build();
            balanceList.add(balanceDto4);
        }
        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), balanceList);
    }

    private List<TransferTxDto> formatTransferTxDtosFromEvtTx(List<TxEventLogDto> evtLogTxs) {

        List<TransferTxDto> formattedTransferTxs = new ArrayList<>();

        for (int i = 0; i < evtLogTxs.size(); i++) {
            TxEventLogDto evtTx = evtLogTxs.get(i);
            TransferTxDto transferTxDto = new TransferTxDto();

            List<TransferTxDetailDto> transferTxnList = EventLogParseUtil.buildTxDADTransfer(evtTx.getEventLog());
            transferTxDto.setTransfers(transferTxnList);
            transferTxDto.setTxHash(evtTx.getTxHash());
            transferTxDto.setTxTime(evtTx.getTxTime());
            transferTxDto.setTxType(evtTx.getTxType());
            transferTxDto.setBlockHeight(evtTx.getBlockHeight());
            transferTxDto.setConfirmFlag(evtTx.getConfirmFlag());
            transferTxDto.setBlockIndex(evtTx.getBlockIndex());
            transferTxDto.setEvtLog(evtTx.getEventLog());

            transferTxDto.setFromAddress(null);
            transferTxDto.setToAddress(null);
            transferTxDto.setAmount(null);
            transferTxDto.setAssetName(null);
            transferTxDto.setTxIndex(null);

            formattedTransferTxs.add(transferTxDto);
        }

        return formattedTransferTxs;
    }

    @Override
    public ResponseBean<PageResponseBean<List<TransferTxDto>>> queryTransferTxsByPage(String address, Long pageNumber, Long pageSize) {

        Long totalCount = addressTxsMapper.selectAddressTxCount(address, 1);
        List<Long> indexs = addressTxsMapper.selectAddressTxIndexsByPage(address, (pageNumber - 1) * pageSize, pageSize, 1);
        List<TransferTxDto> formattedTransferTxDtos;
        if (indexs.size() > 0) {
            List<TxEventLogDto> txLogs = txEventLogMapper.selectTxsByIndexList(indexs);
            formattedTransferTxDtos = formatTransferTxDtosFromEvtTx(txLogs);
        } else {
            formattedTransferTxDtos = new ArrayList();
        }

        PageResponseBean<List<TransferTxDto>> pageResponseBean = new PageResponseBean(formattedTransferTxDtos, totalCount.intValue());


        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);
    }

    @Override
    public ResponseBean<PageResponseBean<List<TransferTxDto>>> queryTransferTxsByPageV2(String address, Long pageNumber, Long pageSize) {
        Long totalCount = addressTxsMapper.selectAddressTxCount(address, 0);
        List<Long> indexs = addressTxsMapper.selectAddressTxIndexsByPage(address, (pageNumber - 1) * pageSize, pageSize, 0);
        List<TransferTxDto> formattedTransferTxDtos;
        if (indexs.size() > 0) {
            List<TxEventLogDto> txLogs = txEventLogMapper.selectTxsByIndexList(indexs);
            formattedTransferTxDtos = formatTransferTxDtosFromEvtTx(txLogs);
        } else {
            formattedTransferTxDtos = new ArrayList();
        }

        PageResponseBean<List<TransferTxDto>> pageResponseBean = new PageResponseBean(formattedTransferTxDtos, totalCount.intValue());


        return new ResponseBean(ErrorInfo.SUCCESS.code(), ErrorInfo.SUCCESS.desc(), pageResponseBean);

    }


}
