package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dadchain.model.dao.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Oep5DetailDto extends Contract {

    private BigDecimal totalSupply;

    private String symbol;

    private String name;


}
