package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockChainTpsDto {
    private String currentTps;
    private Integer maxTps;

    @Builder
    public BlockChainTpsDto(String currentTps, Integer maxTps) {
        this.currentTps = currentTps;
        this.maxTps = maxTps;
    }
}
