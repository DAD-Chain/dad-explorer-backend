package com.github.dadchain.internal.dsp.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class GetAwardResponse {
    String txHash;
}
