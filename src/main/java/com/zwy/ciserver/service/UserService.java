package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.UserEntity;

/**
 * Created by Afauria on 2019/1/22.
 */
public interface UserService {
    int addUser(UserEntity user);

    PageInfo<UserEntity> findAllUser(int pageNum, int pageSize);
}
