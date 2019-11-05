package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Contract;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface ContractMapper extends Mapper<Contract> {

    void batchInsert(List<Contract> list);

}
