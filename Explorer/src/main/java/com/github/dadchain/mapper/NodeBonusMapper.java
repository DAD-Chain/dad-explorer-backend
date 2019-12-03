package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.NodeBonus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface NodeBonusMapper extends Mapper<NodeBonus> {
    Integer selectNodeCount();

    List<NodeBonus> selectLatestNodeBonusList(@Param("nodeCount") Integer nodeCount);

    NodeBonus selectLatestNodeBonusByPublicKey(@Param("publicKey") String publicKey);

    NodeBonus selectLatestNodeBonusByAddress(@Param("address") String address);

    List<NodeBonus> searchByName(@Param("name") String name);

}
