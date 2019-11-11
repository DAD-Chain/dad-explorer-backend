package com.github.dadchain.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class FaucetSrcInfo {
    String address;
    BigDecimal balance;
}
