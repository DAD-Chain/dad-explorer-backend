package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Oep4;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Component
public interface Oep4Mapper extends Mapper<Oep4> {

    List<Oep4> selectApprovedRecords();

}
