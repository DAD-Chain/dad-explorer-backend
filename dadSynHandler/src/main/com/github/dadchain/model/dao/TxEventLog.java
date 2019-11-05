package com.github.dadchain.model.dao;

import lombok.Builder;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "tbl_tx_eventlog")
public class TxEventLog {
    /**
     * 交易hash值
     */
    @Id
    @Column(name = "tx_hash")
    private String txHash;

    /**
     * 区块链交易类型，208：部署合约交易 209：调用合约交易
     */
    @Column(name = "tx_type")
    private Integer txType;

    /**
     * 交易时间戳
     */
    @Column(name = "tx_time")
    private Integer txTime;

    /**
     * 区块高度
     */
    @Column(name = "block_height")
    private Integer blockHeight;

    /**
     * 交易在区块里的索引
     */
    @Column(name = "block_index")
    private Integer blockIndex;

    /**
     * 交易手续费
     */
    private BigDecimal fee;

    /**
     * 交易落账标识  1：成功 0：失败
     */
    @Column(name = "confirm_flag")
    private Integer confirmFlag;

    /**
     * 交易的event log
     */
    @Column(name = "event_log")
    private String eventLog;

    /**
     * 该交易真正调用的合约hash
     */
    @Column(name = "called_contract_hash")
    private String calledContractHash;

    /**
     * 是否属于ontid事件交易 true:属于 false：不属于
     */
    @Column(name = "ontid_tx_flag")
    private Boolean ontidTxFlag;

    /**
     * 交易资产名
     */
    @Column(name = "asset_name")
    private String assetName;

    /**
     * 交易fromaddress
     */
    @Column(name = "from_address")
    private String fromAddress;

    /**
     * 交易toaddress
     */
    @Column(name = "to_address")
    private String toAddress;

    /**
     * 交易数量
     */
    private BigDecimal amount;

    @Column(name = "tx_index")
    private Long txIndex;

    @Builder
    public TxEventLog(String txHash, Integer txType, Integer txTime, Integer blockHeight,
                      Integer blockIndex, BigDecimal fee, Integer confirmFlag,
                      String eventLog, String calledContractHash, Boolean ontidTxFlag,
                      String fromAddress, String toAddress, String asseetName, BigDecimal amount,
                      Long txIndex) {
        this.txHash = txHash;
        this.txType = txType;
        this.txTime = txTime;
        this.blockHeight = blockHeight;
        this.blockIndex = blockIndex;
        this.fee = fee;
        this.confirmFlag = confirmFlag;
        this.eventLog = eventLog;
        this.calledContractHash = calledContractHash;
        this.ontidTxFlag = ontidTxFlag;
        this.assetName = asseetName;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.amount = amount;
        this.txIndex = txIndex;
    }
    /**
     * 获取交易hash值
     *
     * @return tx_hash - 交易hash值
     */
    public String getTxHash() {
        return txHash;
    }

    /**
     * 设置交易hash值
     *
     * @param txHash 交易hash值
     */
    public void setTxHash(String txHash) {
        this.txHash = txHash == null ? null : txHash.trim();
    }

    /**
     * 获取区块链交易类型，208：部署合约交易 209：调用合约交易
     *
     * @return tx_type - 区块链交易类型，208：部署合约交易 209：调用合约交易
     */
    public Integer getTxType() {
        return txType;
    }

    /**
     * 设置区块链交易类型，208：部署合约交易 209：调用合约交易
     *
     * @param txType 区块链交易类型，208：部署合约交易 209：调用合约交易
     */
    public void setTxType(Integer txType) {
        this.txType = txType;
    }

    /**
     * 获取交易时间戳
     *
     * @return tx_time - 交易时间戳
     */
    public Integer getTxTime() {
        return txTime;
    }

    /**
     * 设置交易时间戳
     *
     * @param txTime 交易时间戳
     */
    public void setTxTime(Integer txTime) {
        this.txTime = txTime;
    }

    /**
     * 获取区块高度
     *
     * @return block_height - 区块高度
     */
    public Integer getBlockHeight() {
        return blockHeight;
    }

    /**
     * 设置区块高度
     *
     * @param blockHeight 区块高度
     */
    public void setBlockHeight(Integer blockHeight) {
        this.blockHeight = blockHeight;
    }

    /**
     * 获取交易在区块里的索引
     *
     * @return block_index - 交易在区块里的索引
     */
    public Integer getBlockIndex() {
        return blockIndex;
    }

    /**
     * 设置交易在区块里的索引
     *
     * @param blockIndex 交易在区块里的索引
     */
    public void setBlockIndex(Integer blockIndex) {
        this.blockIndex = blockIndex;
    }

    /**
     * 获取交易手续费
     *
     * @return fee - 交易手续费
     */
    public BigDecimal getFee() {
        return fee;
    }

    /**
     * 设置交易手续费
     *
     * @param fee 交易手续费
     */
    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    /**
     * 获取交易落账标识  1：成功 0：失败
     *
     * @return confirm_flag - 交易落账标识  1：成功 0：失败
     */
    public Integer getConfirmFlag() {
        return confirmFlag;
    }

    /**
     * 设置交易落账标识  1：成功 0：失败
     *
     * @param confirmFlag 交易落账标识  1：成功 0：失败
     */
    public void setConfirmFlag(Integer confirmFlag) {
        this.confirmFlag = confirmFlag;
    }

    /**
     * 获取交易的event log
     *
     * @return event_log - 交易的event log
     */
    public String getEventLog() {
        return eventLog;
    }

    /**
     * 设置交易的event log
     *
     * @param eventLog 交易的event log
     */
    public void setEventLog(String eventLog) {
        this.eventLog = eventLog == null ? null : eventLog.trim();
    }

    /**
     * 获取该交易真正调用的合约hash
     *
     * @return called_contract_hash - 该交易真正调用的合约hash
     */
    public String getCalledContractHash() {
        return calledContractHash;
    }

    /**
     * 设置该交易真正调用的合约hash
     *
     * @param calledContractHash 该交易真正调用的合约hash
     */
    public void setCalledContractHash(String calledContractHash) {
        this.calledContractHash = calledContractHash == null ? null : calledContractHash.trim();
    }

    /**
     * 获取是否属于ontid事件交易 true:属于 false：不属于
     *
     * @return ontid_tx_flag - 是否属于ontid事件交易 true:属于 false：不属于
     */
    public Boolean getOntidTxFlag() {
        return ontidTxFlag;
    }

    /**
     * 设置是否属于ontid事件交易 true:属于 false：不属于
     *
     * @param ontidTxFlag 是否属于ontid事件交易 true:属于 false：不属于
     */
    public void setOntidTxFlag(Boolean ontidTxFlag) {
        this.ontidTxFlag = ontidTxFlag;
    }

    /**
     * 获取交易资产名
     *
     * @return asset_name - 交易资产名
     */
    public String getAssetName() {
        return assetName;
    }

    /**
     * 设置交易资产名
     *
     * @param assetName 交易资产名
     */
    public void setAssetName(String assetName) {
        this.assetName = assetName == null ? null : assetName.trim();
    }

    /**
     * 获取交易fromaddress
     *
     * @return from_address - 交易fromaddress
     */
    public String getFromAddress() {
        return fromAddress;
    }

    /**
     * 设置交易fromaddress
     *
     * @param fromAddress 交易fromaddress
     */
    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress == null ? null : fromAddress.trim();
    }

    /**
     * 获取交易toaddress
     *
     * @return to_address - 交易toaddress
     */
    public String getToAddress() {
        return toAddress;
    }

    /**
     * 设置交易toaddress
     *
     * @param toAddress 交易toaddress
     */
    public void setToAddress(String toAddress) {
        this.toAddress = toAddress == null ? null : toAddress.trim();
    }

    /**
     * 获取交易数量
     *
     * @return amount - 交易数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置交易数量
     *
     * @param amount 交易数量
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * @return tx_index
     */
    public Long getTxIndex() {
        return txIndex;
    }

    /**
     * @param txIndex
     */
    public void setTxIndex(Long txIndex) {
        this.txIndex = txIndex;
    }
}
