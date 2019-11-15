package com.github.dadchain.config;
import com.github.ontio.OntSdk;
import com.github.ontio.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class DadSdkConfig {
    @Autowired
    ParamsConfig paramsConfig;

    @Bean("faucetAccount")
    public Account faucetAccount() throws Exception {
        return getFacuetDadSdk().getWalletMgr().getAccount(paramsConfig.FACUET_WALLET_ADDRESS, paramsConfig.FACUET_WALLET_PWD);
    }

    @Bean("faucetDadSdk")
    public OntSdk getFacuetDadSdk() {
        OntSdk wm = OntSdk.getInstance();
        wm.setRestful(paramsConfig.MASTERNODE_RESTFUL_URL);
        wm.openWalletFile(paramsConfig.FACUET_WALLET_FILE);
        return wm;
    }
}
