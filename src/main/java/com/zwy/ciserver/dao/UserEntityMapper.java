package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.UserEntity;

import java.util.List;

public interface UserEntityMapper {
    int insert(UserEntity record);

    int insertSelective(UserEntity record);

    List<UserEntity> selectUsers();
}