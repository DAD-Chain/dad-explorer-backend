package com.github.dadchain.model.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/26
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResponseBean<T> {

    private Integer total;

    private List<T> records;

    public PageResponseBean(List records, Integer total) {
        this.total = total == null ? 0 : total;
        this.records = records;
    }

    public PageResponseBean() {
    }
}
