package com.github.dadchain.model.dao;

import javax.persistence.*;

@Table(name = "tbl_tx_daily_summary")
public class TxDailySummary {
    /**
     * 统计的时间戳，对其到整天即凌晨: 00:00
     */
    @Column(name = "time_day")
    private Integer timeDay;

    /**
     * 交易个数
     */
    @Column(name = "tx_count")
    private Integer txCount;

    /**
     * 获取统计的时间戳，对其到整天即凌晨: 00:00
     *
     * @return time_day - 统计的时间戳，对其到整天即凌晨: 00:00
     */
    public Integer getTimeDay() {
        return timeDay;
    }

    /**
     * 设置统计的时间戳，对其到整天即凌晨: 00:00
     *
     * @param timeDay 统计的时间戳，对其到整天即凌晨: 00:00
     */
    public void setTimeDay(Integer timeDay) {
        this.timeDay = timeDay;
    }

    /**
     * 获取交易个数
     *
     * @return tx_count - 交易个数
     */
    public Integer getTxCount() {
        return txCount;
    }

    /**
     * 设置交易个数
     *
     * @param txCount 交易个数
     */
    public void setTxCount(Integer txCount) {
        this.txCount = txCount;
    }

}
