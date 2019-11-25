package com.github.dadchain.internal.dsp.model;

import com.github.dadchain.util.ErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)
public class GetAwardsListRequest {
    Integer pageSize;
    Integer pageNumber;
    AwardStatus status;
    String address;

    public void validate() {
        Assert.isTrue(StringUtils.isNotEmpty(address), ErrorInfo.PARAM_ERROR.name());
    }
}
