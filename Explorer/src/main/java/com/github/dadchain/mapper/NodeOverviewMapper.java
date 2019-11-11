package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.NodeOverview;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface NodeOverviewMapper extends Mapper<NodeOverview> {

    Long selectBlkCountToNxtRnd();

}
