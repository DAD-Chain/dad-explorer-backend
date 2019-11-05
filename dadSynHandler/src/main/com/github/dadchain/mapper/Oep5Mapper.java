package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Oep5;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Component
public interface Oep5Mapper extends Mapper<Oep5> {

    List<Oep5> selectApprovedRecords();

}
