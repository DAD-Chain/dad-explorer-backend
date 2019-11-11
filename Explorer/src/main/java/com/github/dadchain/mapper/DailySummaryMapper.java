package com.github.dadchain.mapper;

import com.github.dadchain.model.dto.DailySummaryDto;
import com.github.dadchain.model.dto.NewAddressSummaryDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface DailySummaryMapper extends Mapper<DailySummaryDto> {

    List<DailySummaryDto> selectSummaryByTime(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    Map<String,BigDecimal> selectAddrAndOntIdTotal(@Param("startTime") Long startTime);

    NewAddressSummaryDto sumNewAddressSummary(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

}
