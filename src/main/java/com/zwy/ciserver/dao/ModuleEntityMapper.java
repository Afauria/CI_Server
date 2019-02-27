package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ModuleEntity;

import java.util.List;

public interface ModuleEntityMapper {
    int insertModule(ModuleEntity record);

    List<ModuleEntity> selectModules();

    ModuleEntity selectModuleByName(String name);

    ModuleEntity selectModuleById(int moduleId);

    int deleteModuleById(int id);

    void updateModule(ModuleEntity moduleEntity);
}