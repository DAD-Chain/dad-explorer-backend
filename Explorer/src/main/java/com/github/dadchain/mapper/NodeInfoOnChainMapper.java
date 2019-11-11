package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.NodeInfoOnChain;
import com.github.dadchain.model.dto.NodeInfoOnChainDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NodeInfoOnChainMapper extends Mapper<NodeInfoOnChain> {

    List<NodeInfoOnChainDto> selectAllInfo();

    Long selectTotalStake();

    NodeInfoOnChainDto selectByPublicKey(String publicKey);

    List<NodeInfoOnChainDto> searchByName(@Param("name") String name);

    Long selectSyncNodesCount();

    Long selectCandidateNodeCount();

    Long selectConsensusNodeCount();
}
