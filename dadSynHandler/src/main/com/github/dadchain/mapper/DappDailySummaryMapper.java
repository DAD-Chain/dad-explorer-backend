package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.DappDailySummary;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
public interface DappDailySummaryMapper extends Mapper<DappDailySummary> {
}
