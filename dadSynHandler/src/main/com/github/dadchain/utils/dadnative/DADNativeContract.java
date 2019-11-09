package com.github.dadchain.utils.dadnative;

import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.ErrorCode;
import com.github.ontio.common.Helper;
import com.github.ontio.core.transaction.Transaction;
import com.github.ontio.io.BinaryReader;
import com.github.ontio.network.exception.ConnectorException;
import com.github.ontio.sdk.exception.SDKException;
import com.github.ontio.smartcontract.nativevm.abi.NativeBuildParams;
import com.github.ontio.smartcontract.nativevm.abi.Struct;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DADNativeContract {
    private OntSdk sdk;
    public static final String contractAddress = "000000000000000000000000000000000000000A";
    // function name
    public static final String SET_GLOBAL_PARAM_NAME = "setGlobalParam";
    public static final String SET_ADMIN = "setAdmin";
    public static final String PLEDGE = "pledge";
    public static final String PLEDGE_BALANCE = "pledgeBalance";
    public static final String REDEEM_PLEDGE = "redeemPledge";
    public static final String PLACE_AD = "placeAd";
    public static final String CONTRIBUTE = "contribute";
    public static final String DISPATCH_BONUS = "dispatchBonus";
    public static final String REDEEM_ORDER = "redeemOrder";
    public static final String CHANGE_AD_ORDER_STATUS = "changeADOrderStatus";
    public static final String PLACE_PUBLISHER_WEBWAP      = "placePublisherWebWap";
    public static final String PLACE_PUBLISHER_APP         = "placePublisherApp";
    public static final String CHANGE_PUBLISHER_APP_STATUS = "changePublisherAppStatus";

    // key prefix
    public static final String GLOBAL_PARAM = "globalParam";
    public static final String ORDER = "Order";
    public static final String BALANCE = "Balance";
    public static final String PLEDGE_KEY = "Pledge";

    public static final String PLEDGE_TOKEN_DAD = "DAD";

    public DADNativeContract(OntSdk sdk) {
        this.sdk = sdk;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    /**
     * @param account
     * @param userBonusRate
     * @param publisherBonusRate
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String setGlobalParam(Account account, long userBonusRate,
                                 long publisherBonusRate, Account payerAcct,
                                 long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(userBonusRate, publisherBonusRate));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                SET_GLOBAL_PARAM_NAME, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param admin
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String setAdmin(Account account, Account admin, Account payerAcct,
                           long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(admin.getAddressU160()));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                SET_ADMIN, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param adver
     * @param token
     * @param bid
     * @param orderID
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String pledge(Account account,
                         Address adver,
                         String token,
                         long bid,
                         String orderID,
                         Account payerAcct,
                         long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(adver, token, bid, orderID));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                PLEDGE, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param adver
     * @param adType
     * @param token
     * @param bid
     * @param begin
     * @param expire
     * @param orderID
     * @param payerAcct
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String placeAd(Account account,
                          Address adver,
                          String adType,
                          String token,
                          long bid,
                          long begin,
                          long expire,
                          String orderID,
                          String campaignName,
                          String campaignLink,
                          List<String> contries,
                          List<String> slots,
                          List<String> creative,
                          Account payerAcct,
                          long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(adver, adType, token,
                bid, begin, expire, orderID,
                campaignName,campaignLink,
                contries,slots,creative));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                PLACE_AD, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param orderID
     * @param statusID
     * @param publisherID
     * @param action
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String contribute(Account account,
                             String orderID,
                             Address statusID,
                             String publisherID,
                             String action,
                             Account payerAcct,
                             long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(orderID, statusID, publisherID, action));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                CONTRIBUTE, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param orderID
     * @param status
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String changeAdOrderStatus(Account account,
                                      String orderID,
                                      long status,
                                      Account payerAcct,
                                      long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(orderID, status));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                CHANGE_AD_ORDER_STATUS, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param orderID
     * @param adType
     * @param price
     * @param token
     * @param quantity
     * @param publisherID
     * @param userAddresses
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String dispatchBonus(Account account,
                                String orderID,
                                String adType,
                                long price,
                                String token,
                                long quantity,
                                Address publisherID,
                                List<Address> userAddresses,
                                Account payerAcct,
                                long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(orderID, adType, price, token
                , quantity, publisherID, userAddresses));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                DISPATCH_BONUS, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    /**
     * @param account
     * @param orderID
     * @param toAddress
     * @param gaslimit
     * @param gasprice
     * @return
     * @throws Exception
     */
    public String redeemOrderPlaced(Account account,
                                    String orderID,
                                    Address toAddress,
                                    Account payerAcct,
                                    long gaslimit, long gasprice) throws Exception {
        List list = new ArrayList();
        list.add(new Struct().add(orderID, toAddress));
        byte[] args = NativeBuildParams.createCodeParamsScript(list);
        Transaction tx = sdk.vm().buildNativeParams(new Address(Helper.hexToBytes(contractAddress)),
                REDEEM_ORDER, args, payerAcct.getAddressU160().toBase58(), gaslimit, gasprice);
        sdk.signTx(tx, new Account[][]{{account}});
        if (!account.equals(payerAcct)) {
            sdk.addSign(tx, payerAcct);
        }
        boolean b = sdk.getConnect().sendRawTransaction(tx.toHexString());
        if (b) {
            return tx.hash().toString();
        }
        return null;
    }

    public DadGlobalParam getGlobalParams() throws ConnectorException, IOException, SDKException {
        String global = sdk.getConnect().getStorage(Helper.reverse(contractAddress),
                Helper.toHexString(GLOBAL_PARAM.getBytes()));
        if (global == null || global.equals("")) {
            throw new SDKException(ErrorCode.OtherError("view is null"));
        }
        DadGlobalParam globalParam = new DadGlobalParam();
        ByteArrayInputStream bais = new ByteArrayInputStream(Helper.hexToBytes(global));
        BinaryReader br = new BinaryReader(bais);
        globalParam.deserialize(br);
        return globalParam;
    }

    public PlacedOrder getPlacedOrder(String orderID) throws ConnectorException, IOException, SDKException {
        String key = Helper.toHexString(ORDER.getBytes()) + Helper.toHexString(orderID.getBytes());
        String view = sdk.getConnect().getStorage(Helper.reverse(contractAddress), key);
        if (view == null || view.equals("")) {
            throw new SDKException(ErrorCode.OtherError("order is null"));
        }
        PlacedOrder placedOrder = new PlacedOrder();
        ByteArrayInputStream bais = new ByteArrayInputStream(Helper.hexToBytes(view));
        BinaryReader br = new BinaryReader(bais);
        placedOrder.deserialize(br);
        return placedOrder;
    }
}
