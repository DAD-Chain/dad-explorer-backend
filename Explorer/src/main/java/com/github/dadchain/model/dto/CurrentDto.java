package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dadchain.model.dao.Current;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_current")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentDto extends Current {

    private Integer nodeCount;

    private Integer addressCount;
}
