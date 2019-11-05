package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Block;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BlockMapper extends Mapper<Block> {
    void batchInsert(List<Block> list);
}
