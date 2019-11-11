package com.github.dadchain.mapper;

import com.alibaba.fastjson.JSONArray;
import com.github.dadchain.model.dto.TxBasicDto;
import com.github.dadchain.model.dto.TxEventLogDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TxEventLogMapper extends Mapper<TxEventLogDto> {

    List<TxEventLogDto> selectTxsByIndexList(@Param(("indexLists")) List<Long> indexLists);

    List<TxEventLogDto> selectLastTxsByTxIndex(@Param("pageSize") Long pageSize);

    List<TxEventLogDto> selectTxsByTxIndex(@Param("startIndex") Long startIndex, @Param("pageSize") Long pageSize);

    List<TxEventLogDto> selectTxsByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    List<TxEventLogDto> selectNonontidTxsByPage(@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    Integer queryTxCount(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

    List<TxEventLogDto> selectTxsByCalledContractHash(@Param("calledContractHash") String contractHash, @Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

    Integer selectCountByCalledContracthash(@Param("calledContractHash") String calledContractHash);

    List<TxBasicDto> selectTxsByBlockHeight(@Param("blockHeight") Integer blockHeight);


    BigDecimal queryTxFeeByParam(@Param("contractList") JSONArray contractList, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    Integer queryTxCountByParam(@Param("contractList") JSONArray contractList, @Param("startTime") Long startTime, @Param("endTime") Long endTime);

    String selectTxEvtLogByHash(@Param("txHash") String txHash);
}
