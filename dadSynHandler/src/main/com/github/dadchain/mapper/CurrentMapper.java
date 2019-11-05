package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.Current;
import tk.mybatis.mapper.common.Mapper;

public interface CurrentMapper extends Mapper<Current> {
    void update(Current current);

    Integer selectBlockHeight();
    Integer selectTxCount();
}
