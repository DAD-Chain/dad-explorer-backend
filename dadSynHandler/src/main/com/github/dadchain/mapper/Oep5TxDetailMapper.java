package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Oep5TxDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface Oep5TxDetailMapper extends Mapper<Oep5TxDetail> {

    void batchInsert(List<Oep5TxDetail> list);

}
