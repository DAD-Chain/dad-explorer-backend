package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.AddressAll;
import com.github.dadchain.model.dto.NewAddressSummaryDto;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface AddressAllMapper extends Mapper<AddressAll> {
    NewAddressSummaryDto selectAllAddressRangeCount(@Param("begin_day") Integer beginDay, @Param("end_day") Integer endDay);
    Integer selectAllAddressCount();
}
