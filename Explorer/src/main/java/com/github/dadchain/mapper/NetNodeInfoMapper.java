package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.NetNodeInfo;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NetNodeInfoMapper extends Mapper<NetNodeInfo> {

    List<NetNodeInfo> selectAllNodes();

    List<NetNodeInfo> selectAllActiveNodes();

    Long selectSyncNodeCount();
}
