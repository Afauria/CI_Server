package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ModuleBuildEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleBuildEntityMapper {
    int insert(ModuleBuildEntity record);

    ModuleBuildEntity selectSuccessModuleBuild(@Param("moduleId") int moduleId, @Param("version") String version);
//    ModuleBuildEntity selectModuleBuildById(int linkId);
    List<ModuleBuildEntity> selectModulesBuildByModuleId(int moduleBuildId);
}