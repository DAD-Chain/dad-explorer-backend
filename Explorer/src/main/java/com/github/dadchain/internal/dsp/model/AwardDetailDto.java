package com.github.dadchain.internal.dsp.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class AwardDetailDto {

    BigDecimal accumulatedRevenue;
    BigDecimal revenueToBeCollected;
    List<UserAwardRecord> awardsList;
    private Boolean lastPage;
    private int totalPage;//一共有多少页
    private long totalCount;//一共有多少条记录
}
