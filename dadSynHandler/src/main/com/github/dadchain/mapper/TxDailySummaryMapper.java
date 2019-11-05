package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.TxDailySummary;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface TxDailySummaryMapper extends Mapper<TxDailySummary> {
    void IncDailyTxCount(@Param("day") Long day, @Param("count") Integer count);
}
