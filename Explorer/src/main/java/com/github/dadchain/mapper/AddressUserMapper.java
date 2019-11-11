package com.github.dadchain.mapper;

import com.github.dadchain.model.dao.AddressUser;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface AddressUserMapper extends Mapper<AddressUser> {
    AddressUser getUserByAddress(String address,boolean lock);

    int createNewUser(AddressUser user);

    int updateLastFaucet(String address, long lastFaucet);
}
