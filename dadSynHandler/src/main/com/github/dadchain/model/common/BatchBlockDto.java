package com.github.dadchain.model.common;

import com.github.dadchain.model.dao.*;
import lombok.Data;

import java.util.*;

/**
 * @author dad-explorer
 * @version 1.0
 * @date 2019/5/17
 */
@Data
public class BatchBlockDto {
    //list会放到子线程进行操作，必须初始化成线程安全的list
    private List<Block> blocks = Collections.synchronizedList(new ArrayList<Block>());

    private List<Contract> contracts = Collections.synchronizedList(new ArrayList<Contract>());

    private List<TxDetail> txDetails = Collections.synchronizedList(new ArrayList<TxDetail>());

    private List<TxDetailDaily> txDetailDailys = Collections.synchronizedList(new ArrayList<TxDetailDaily>());

    private List<TxEventLog> txEventLogs = Collections.synchronizedList(new ArrayList<TxEventLog>());

    private List<OntidTxDetail> ontidTxDetails = Collections.synchronizedList(new ArrayList<OntidTxDetail>());

    private List<Oep4TxDetail> oep4TxDetails = Collections.synchronizedList(new ArrayList<Oep4TxDetail>());

    private List<Oep5TxDetail> oep5TxDetails = Collections.synchronizedList(new ArrayList<Oep5TxDetail>());

    private List<Oep5Dragon> oep5Dragons = Collections.synchronizedList(new ArrayList<Oep5Dragon>());

    private List<Oep8TxDetail> oep8TxDetails = Collections.synchronizedList(new ArrayList<Oep8TxDetail>());

    private Map<Integer, Set<String>> blkAddresses = Collections.synchronizedMap(new HashMap<>());

    private List<AddressTxs> addressTxs = Collections.synchronizedList(new ArrayList<>());

}
