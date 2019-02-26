package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ModuleEntity;

import java.util.List;

public interface ModuleEntityMapper {
    int insert(ModuleEntity record);

    int insertSelective(ModuleEntity record);

    List<ModuleEntity> selectModules();
}