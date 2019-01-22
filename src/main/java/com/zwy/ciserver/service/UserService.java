package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.User;

/**
 * Created by Afauria on 2019/1/22.
 */
public interface UserService {
    int addUser(User user);

    PageInfo<User> findAllUser(int pageNum, int pageSize);
}
