package com.github.dadchain.internal.dsp.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Data
@Accessors(chain = true)
public class UserAwardRecord {

    public final static long AWARD_EXPIRE_AFTER_END_MIL_SEC = 24 * 60 * 60 * 1000;

    @Id
    private String txHash;
    private String userAddress;

    private String action;
    private String campaignName;
    private String campaignID;
    private String slotID;
    private Date endTime;
    private Date actionTime;
    private long revenue;
    // 奖励超时时间
    private Date expire;
    private AwardStatus collectStatus;

    @CreatedDate
    private Date createTime;

    @LastModifiedDate
    private Date lastModify;
}
