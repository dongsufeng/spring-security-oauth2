package com.example.authorizationserver.mapper;

import com.example.authorizationserver.pojo.ClientUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ClientUserMapper {

    @Select("SELECT * FROM client_user WHERE username=#{username}")
    ClientUser selectByUsername(@Param("username") String username);
}
