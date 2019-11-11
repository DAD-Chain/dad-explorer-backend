package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.AddressTxs;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface AddressTxsMapper extends Mapper<AddressTxs> {
    List<Long> selectAddressTxIndexsByPage(String address, Long startIndex, Long pageSize, int txType);
    Long selectAddressTxCount(String address, int txType);
}
