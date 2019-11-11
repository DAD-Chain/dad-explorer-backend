package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Oep8;
import com.github.dadchain.model.dto.Oep8DetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Repository
public interface Oep8Mapper extends Mapper<Oep8> {

    // self-defined SQL

    List<Map<String, String>> selectAuditPassedOep8(@Param("symbol") String symbol);

    List<Oep8DetailDto> selectOep8Tokens();

    Oep8DetailDto selectOep8TokenDetail(@Param("contractHash") String contractHash);


}
