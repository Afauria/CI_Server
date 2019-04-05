package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.model.response.ModulesNameResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleEntityMapper {
    int insertModule(ModuleEntity moduleEntity);

    List<ModuleEntity> selectModules();

    ModuleEntity selectModuleByName(String name);

    ModuleEntity selectModuleById(int moduleId);

    int deleteModuleById(int id);

    void updateModule(ModuleEntity moduleEntity);

    void updateStatus(@Param("moduleId")int moduleId, @Param("buildStatus")int buildStatus);

    void updateVersion(@Param("moduleId")int moduleId,@Param("curVersion")String curVersion);

    List<ModulesNameResp> selectEnableModulesName(int projectId);
}