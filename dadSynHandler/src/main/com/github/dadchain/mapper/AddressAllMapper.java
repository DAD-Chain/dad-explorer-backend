package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.AddressAll;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AddressAllMapper extends Mapper<AddressAll> {
    Integer batchInsert(List<AddressAll> list);
}
