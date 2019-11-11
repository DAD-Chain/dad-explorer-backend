package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dadchain.model.dao.AddressUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_user")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccountInfo extends AddressUser {
}
