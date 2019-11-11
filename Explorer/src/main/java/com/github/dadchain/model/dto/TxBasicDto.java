package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/5/7
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TxBasicDto {

    @ApiModelProperty("txHash")
    private String txHash;
    @ApiModelProperty("txType")
    private Integer txType;
    @ApiModelProperty("txTime")
    private Integer txTime;
    @ApiModelProperty("confirmFlag")
    private Integer confirmFlag;

}
