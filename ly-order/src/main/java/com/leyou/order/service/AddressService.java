package com.leyou.order.service;


import com.leyou.auto.pojo.UserInfo;
import com.leyou.order.interceptors.UserInterceptor;
import com.leyou.order.mapper.AddressMapper;
import com.leyou.order.pojo.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    @Autowired
    private AddressMapper addressMapper;

    public List<Address> queryAddressList() {
        Address address = new Address();
        address.setUserId(getUser());
        return addressMapper.select(address);
    }

    private String getUser() {
        //获取用户信息
        UserInfo user = UserInterceptor.getUser();
        String userId = String.valueOf(user.getId());
        return userId;
    }
}
