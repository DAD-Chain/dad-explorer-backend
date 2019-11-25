package com.github.dadchain.internal.dsp.model;

import com.github.dadchain.util.ErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)
public class GetAwardRequest {
    String taskID;
    String address;

    public void validate() {
        Assert.isTrue(StringUtils.isNotEmpty(address) &&
                        StringUtils.isNotEmpty(taskID),
                ErrorInfo.PARAM_ERROR.name());
    }
}
