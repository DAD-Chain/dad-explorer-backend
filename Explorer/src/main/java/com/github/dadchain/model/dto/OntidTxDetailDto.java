package com.github.dadchain.model.dto;

import com.github.dadchain.model.dao.OntidTxDetail;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/4/28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_ontid_tx_detail")
public class OntidTxDetailDto extends OntidTxDetail {

    @Builder
    public OntidTxDetailDto(String txHash, Integer txType, String ontid, Integer txTime, Integer blockHeight, String description, BigDecimal fee) {
        super(txHash, txType, ontid, txTime, blockHeight, description, fee);
    }
}
