package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.DailySummary;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
@Component
public interface DailySummaryMapper extends Mapper<DailySummary> {
}
