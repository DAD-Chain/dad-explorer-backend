package com.github.dadchain.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.dadchain.model.dao.TxDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransferTxDto extends TxDetail {

    List<TransferTxDetailDto> transfers;

    String evtLog;

}
