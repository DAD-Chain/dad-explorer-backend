package com.github.dadchain.internal.dsp.model;

import com.github.dadchain.util.ErrorInfo;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.Assert;

@Data
@Accessors(chain = true)
public class GetADWallRequest {

    ADWallType type;
    int pageSize;
    int pageNumber;

    public void validate() {
        Assert.isTrue(type != null &&
                        pageSize > 0 &&
                        pageNumber > 0,
                ErrorInfo.PARAM_ERROR.name());
    }
}
