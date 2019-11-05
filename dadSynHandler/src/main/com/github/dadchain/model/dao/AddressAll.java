package com.github.dadchain.model.dao;

import lombok.Builder;

import javax.persistence.*;

@Table(name = "tbl_address_all")
public class AddressAll {
    /**
     * 钱包地址
     */
    @Id
    private String address;

    /**
     * 地址第一次看到的时间, 需要对其到小时
     */
    @Column(name = "first_time")
    private Integer firstTime;

    /**
     * 获取钱包地址
     *
     * @return address - 钱包地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置钱包地址
     *
     * @param address 钱包地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取地址第一次看到的时间, 需要对其到小时
     *
     * @return first_time - 地址第一次看到的时间, 需要对其到小时
     */
    public Integer getFirstTime() {
        return firstTime;
    }

    /**
     * 设置地址第一次看到的时间, 需要对其到小时
     *
     * @param firstTime 地址第一次看到的时间, 需要对其到小时
     */
    public void setFirstTime(Integer firstTime) {
        this.firstTime = firstTime;
    }


    @Builder
    public AddressAll(String address, Integer firstTime) {
        this.address = address;
        this.firstTime = firstTime;
    }
}
