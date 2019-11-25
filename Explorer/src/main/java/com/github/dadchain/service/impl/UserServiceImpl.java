package com.github.dadchain.service.impl;

import com.github.dadchain.mapper.AddressUserMapper;
import com.github.dadchain.util.ErrorInfo;
import com.github.dadchain.model.dao.AddressUser;
import com.github.ontio.sdk.exception.SDKException;
import com.github.dadchain.service.IUserService;
import com.github.dadchain.service.ISmartCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Slf4j
@Service("UserService")
public class UserServiceImpl implements IUserService {

    public static final long FACUET_INTERAL_SEC = 60 * 60;
    public static final long FACUET_DAD_COUNT = 1000;

    @Autowired
    AddressUserMapper addressUserMapper;

    @Autowired
    ISmartCodeService iSmartCodeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String checkDoDadFacuet(String walletAddress) {
        AddressUser user = addressUserMapper.getUserByAddress(walletAddress, true);
        long nowSec = Instant.now().getEpochSecond();
        if (user == null) {
            user = new AddressUser();
            user.setAddress(walletAddress);
            user.setLastFaucet(nowSec);
            addressUserMapper.createNewUser(user);
        } else {
            long diffSec = nowSec - user.getLastFaucet();
            if (diffSec < FACUET_INTERAL_SEC) {
                throw new RuntimeException(ErrorInfo.FAUCET_WAIT.name());
            }
            addressUserMapper.updateLastFaucet(walletAddress, nowSec);
        }

        try {
            String txHash = iSmartCodeService.faucetDadToAddress(walletAddress);
            return txHash;
        } catch (SDKException e) {
            throw new RuntimeException(ErrorInfo.PARAM_ERROR.name());
        } catch (Exception e) {
            throw new RuntimeException(ErrorInfo.FAUCET_WAIT.name());
        }
    }
}
