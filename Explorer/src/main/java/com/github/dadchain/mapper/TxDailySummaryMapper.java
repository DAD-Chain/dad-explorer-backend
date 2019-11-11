package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.TxDailySummary;
import com.github.dadchain.model.dto.TxDailySummaryDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TxDailySummaryMapper extends Mapper<TxDailySummary> {
    List<TxDailySummaryDto> getDailTxCountSummary(@Param("begin_day") Integer beginDay, @Param("end_day") Integer endDay);
}
