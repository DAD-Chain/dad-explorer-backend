package com.github.dadchain.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.dadchain.util.TxAmountSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/28
 */
@Data
public class BalanceDto {

    @JsonSerialize(using = TxAmountSerializer.class)
    @ApiModelProperty("balance")
    private BigDecimal balance;

    private String assetName;

    private String assetType;

    @Builder
    public BalanceDto(BigDecimal balance, String assetName, String assetType) {
        this.balance = balance;
        this.assetName = assetName;
        this.assetType = assetType;
    }
}
