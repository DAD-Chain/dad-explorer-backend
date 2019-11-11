package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Table;

@Data
@Table(name = "tbl_block")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlockLastAvgGenerate {
    private Long diffHeight;
    private Long diffTime;

    @Builder
    public BlockLastAvgGenerate(Long diffHeight, Long diffTime) {
        this.diffHeight = diffHeight;
        this.diffTime = diffTime;
    }
}
