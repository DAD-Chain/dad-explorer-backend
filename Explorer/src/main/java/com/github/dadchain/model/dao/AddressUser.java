package com.github.dadchain.model.dao;

import javax.persistence.*;

@Table(name = "tbl_user")
public class AddressUser {
    @Id
    private String address;

    /**
     * 上次获取测试币的时间
     */
    @Column(name = "last_faucet")
    private Long lastFaucet;

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取上次获取测试币的时间
     *
     * @return last_faucet - 上次获取测试币的时间
     */
    public Long getLastFaucet() {
        return lastFaucet;
    }

    /**
     * 设置上次获取测试币的时间
     *
     * @param lastFaucet 上次获取测试币的时间
     */
    public void setLastFaucet(Long lastFaucet) {
        this.lastFaucet = lastFaucet;
    }
}
