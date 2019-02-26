package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.ModuleEntity;

/**
 * Created by Afauria on 2019/2/25.
 */
public interface ModuleService {
    int addModule(ModuleEntity module);

    PageInfo<ModuleEntity> findAllModule(int pageNum, int pageSize);
}
