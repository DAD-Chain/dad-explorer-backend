package com.github.dadchain.mapper;

import com.github.dadchain.model.dto.Oep4TxDetailDto;
import com.github.dadchain.model.dto.TxDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface Oep4TxDetailMapper extends Mapper<Oep4TxDetailDto> {

    List<TxDetailDto> selectTxsByCalledContractHash(@Param("calledContractHash") String contractHash, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    Integer selectCountByCalledContracthash(@Param("calledContractHash") String calledContractHash);

}
