package com.github.dadchain.service.impl;

import com.github.dadchain.service.ISmartCodeService;
import com.github.dadchain.service.impl.UserServiceImpl;
import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.util.dadnative.DadToken;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import com.github.ontio.common.Address;
import com.github.ontio.common.Helper;
import com.github.dadchain.model.dto.FaucetSrcInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 调用合约的方法和签名方法
 */
@Slf4j
@Service
public class SmartCodeServiceImp implements ISmartCodeService {

    @Autowired
    @Qualifier("faucetAccount")
    Account faucetAccount;

    @Autowired
    @Qualifier("faucetDadSdk")
    OntSdk faucetSDK;

    @Override
    public Address verifySignature(String publicKey, String signature, String token) {
        try {
            Account account = new Account(false, Helper.hexToBytes(publicKey));
            boolean verified = account.verifySignature(token.getBytes(), Helper.hexToBytes(signature));
            if (!verified) {
                throw new RuntimeException(ErrorInfo.TOKEN_VERIFIED_ERROR.name());
            }
            return Address.addressFromPubKey(account.serializePublicKey());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(ErrorInfo.TOKEN_VERIFIED_ERROR.name());
        }
    }

    @Override
    public Boolean validateAddress(String addr) {
        Boolean valid = false;
        try {
            Address.decodeBase58(addr);
            valid = true;
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    @Override
    public String faucetDadToAddress(String userAddress) throws Exception {
        DadToken dadToken = new DadToken(faucetSDK);
        Long decimal = dadToken.queryDecimals();
        BigDecimal dadUnit = BigDecimal.TEN.pow(decimal.intValue());
        BigDecimal amount = BigDecimal.valueOf(UserServiceImpl.FACUET_DAD_COUNT).multiply(dadUnit);
        String address = faucetAccount.getAddressU160().toBase58();

        long curBalance = dadToken.queryBalanceOf(address);
        if (curBalance < amount.longValue()) {
            throw new RuntimeException(ErrorInfo.FAUCET_WAIT.name());
        }
        return dadToken.sendTransfer(faucetAccount, userAddress, amount.longValue(), faucetAccount, 20000, 0);
    }

    public FaucetSrcInfo faucetSrcInfo() throws Exception {
        FaucetSrcInfo info = new FaucetSrcInfo();
        DadToken dadToken = new DadToken(faucetSDK);
        String address = faucetAccount.getAddressU160().toBase58();
        info.setAddress(address);
        info.setBalance(BigDecimal.valueOf(dadToken.queryBalanceOf(address)));
        return info;
    }
}
