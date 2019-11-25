package com.github.dadchain.internal.dsp.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@Accessors(chain = true)
public class AdWallDetailDto {

    @Data
    @Accessors(chain = true)
    public static class ADItemInfo {
        String link;
        String title;
        List<String> src;
        long end;
        boolean tagTypeIn;
        BigDecimal progress = BigDecimal.ZERO;
    }

    ADItemInfo top;
    List<ADItemInfo> adList;
    private Boolean lastPage;
    private int totalPage;//一共有多少页
    private long totalCount;//一共有多少条记录
}
