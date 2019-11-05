package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.TxDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface TxDetailMapper extends Mapper<TxDetail> {

    void batchInsert(List<TxDetail> list);
}
