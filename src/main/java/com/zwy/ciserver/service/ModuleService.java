package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.ModuleBuildEntity;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.model.response.ModulesNameResp;

import java.util.List;

/**
 * Created by Afauria on 2019/2/25.
 */
public interface ModuleService {

    PageInfo<ModuleEntity> listModules(int pageNum, int pageSize);

    ModuleEntity addModule(ModuleEntity module);

    int removeModuleById(int moduleId);

    ModuleEntity modifyModule(ModuleEntity moduleEntity);

    String searchNextVersion(String curVersion, boolean rcFlag);

    boolean buildModule(int moduleId, String version);

    void handleBuildResult(ModuleBuildEntity moduleBuildEntity);

    List<ModuleBuildEntity> searchVersions(int moduleBuildId);

    List<ModulesNameResp> searchEnableModulesName(int projectId);

    PageInfo<ModuleBuildEntity> findModuleBuildHistory(int moduleId, int pageNum, int pageSize);

    String findModuleBuildReport(int buildId);
}
