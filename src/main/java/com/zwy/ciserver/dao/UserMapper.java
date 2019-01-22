package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.User;

import java.util.List;

public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    List<User> selectUsers();
}