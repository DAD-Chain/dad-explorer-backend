package com.github.dadchain.model.dto;

import com.github.dadchain.model.dao.TxDailySummary;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tbl_tx_daily_summary")
public class TxDailySummaryDto extends TxDailySummary {
//    private Integer date;
}
