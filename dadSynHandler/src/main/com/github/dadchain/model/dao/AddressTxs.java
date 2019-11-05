package com.github.dadchain.model.dao;

import lombok.Builder;

import javax.persistence.*;

@Table(name = "tbl_address_txs")
public class AddressTxs {
    /**
     * 交易关联的地址：from to
     */
    @Id
    private String address;

    /**
     * 交易排序
     */
    @Id
    @Column(name = "tx_index")
    private Long txIndex;

    @Column(name = "tx_type")
    private Integer txType;

    @Builder
    public AddressTxs(String address, Long txIndex, int txType) {
        this.address = address;
        this.txIndex = txIndex;
        this.txType = txType;
    }

    /**
     * 获取交易关联的地址：from to
     *
     * @return address - 交易关联的地址：from to
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置交易关联的地址：from to
     *
     * @param address 交易关联的地址：from to
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取交易排序
     *
     * @return tx_index - 交易排序
     */
    public Long getTxIndex() {
        return txIndex;
    }

    /**
     * 设置交易排序
     *
     * @param txIndex 交易排序
     */
    public void setTxIndex(Long txIndex) {
        this.txIndex = txIndex;
    }

    /**
     * @return tx_type
     */
    public Integer getTxType() {
        return txType;
    }

    /**
     * @param txType
     */
    public void setTxType(Integer txType) {
        this.txType = txType;
    }
}
