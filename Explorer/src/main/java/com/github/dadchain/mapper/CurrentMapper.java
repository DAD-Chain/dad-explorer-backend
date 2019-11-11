package com.github.dadchain.mapper;

import com.github.dadchain.model.dto.CurrentDto;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface CurrentMapper extends Mapper<CurrentDto> {

    // self-defined SQL

    CurrentDto selectSummaryInfo();

}
