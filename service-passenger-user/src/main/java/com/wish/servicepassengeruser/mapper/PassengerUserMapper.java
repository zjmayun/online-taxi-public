package com.wish.servicepassengeruser.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wish.servicepassengeruser.dto.PassengerUser;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerUserMapper extends BaseMapper<PassengerUser> {
}
