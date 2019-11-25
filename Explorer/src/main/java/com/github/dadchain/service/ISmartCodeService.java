package com.github.dadchain.service;
import com.github.ontio.common.Address;
import com.github.dadchain.model.dto.FaucetSrcInfo;

public interface ISmartCodeService {
    Address verifySignature(String publicKey, String signature, String token);
    Boolean validateAddress(String addr);
    FaucetSrcInfo faucetSrcInfo() throws Exception;
    String faucetDadToAddress(String userAddress) throws Exception;
}
