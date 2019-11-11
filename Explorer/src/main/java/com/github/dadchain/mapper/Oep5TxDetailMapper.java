package com.github.dadchain.mapper;

import com.github.dadchain.model.dto.Oep5TxDetailDto;
import com.github.dadchain.model.dto.TxDetailDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface Oep5TxDetailMapper extends Mapper<Oep5TxDetailDto> {

    List<TxDetailDto> selectTxsByCalledContractHash(@Param("calledContractHash") String contractHash, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    Integer selectCountByCalledContracthash(@Param("calledContractHash") String calledContractHash);

    List<Oep5TxDetailDto> selectTxs4Dragon(@Param("calledContractHash") String contractHash, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}
