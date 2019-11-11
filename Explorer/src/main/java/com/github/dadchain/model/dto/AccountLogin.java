package com.github.dadchain.model.dto;

import com.github.dadchain.util.ErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)

public class AccountLogin {

    private String signMessage;
    private String publicKey;

    public void validate() {
        Assert.isTrue( StringUtils.isNotEmpty(signMessage)
                        && StringUtils.isNotEmpty(publicKey),
                ErrorInfo.PARAM_ERROR.name());
    }

}
