package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.TxEventLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TxEventLogMapper extends Mapper<TxEventLog> {
    void batchInsert(List<TxEventLog> list);
}
