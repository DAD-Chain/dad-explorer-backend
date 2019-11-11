package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dadchain.model.dao.Contract;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Oep8DetailDto extends Contract {

    private Object totalSupply;

    private Object symbol;

    private Object tokenName;

    private Object tokenId;

}
