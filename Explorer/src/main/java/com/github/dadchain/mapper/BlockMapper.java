package com.github.dadchain.mapper;

import com.github.dadchain.model.dto.BlockDto;
import com.github.dadchain.model.dto.BlockLastAvgGenerate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface BlockMapper extends Mapper<BlockDto> {


    List<BlockDto> selectBlocksPageByMinBlockNo(@Param("minBlockNo") int startIndex, @Param("pageSize") int pageSize);

    List<BlockDto> selectBlocksByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    List<Map> selectHeightAndTime(@Param("count") int count);

    BlockLastAvgGenerate selectBlockBeforeTimeCount(@Param("before") Long before);

    BlockDto selectOneByHeight(@Param("blockHeight") int blockHeight);

    BlockDto selectOneByHash(@Param("blockHash") String hash);

}
