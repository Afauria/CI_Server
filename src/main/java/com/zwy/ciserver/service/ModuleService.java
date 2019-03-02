package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.ModuleEntity;

/**
 * Created by Afauria on 2019/2/25.
 */
public interface ModuleService {
    ModuleEntity addModule(ModuleEntity module);

    PageInfo<ModuleEntity> listModules(int pageNum, int pageSize);

    int removeModuleById(int moduleId);

    ModuleEntity modifyModule(ModuleEntity moduleEntity);

    boolean buildModule(int moduleId);
}
