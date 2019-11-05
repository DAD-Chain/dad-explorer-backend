package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.OntidTxDetail;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Component
public interface OntidTxDetailMapper extends Mapper<OntidTxDetail> {

    void batchInsert(List<OntidTxDetail> list);

}
