package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.ProjectBuildEntity;
import com.zwy.ciserver.entity.ProjectEntity;
import com.zwy.ciserver.model.response.ProjectModuleResp;

import java.util.List;

/**
 * Created by Afauria on 2019/4/2.
 */
public interface ProjectService {
    PageInfo<ProjectEntity> listProjects(int pageNum, int pageSize);

    ProjectEntity addProject(ProjectEntity projectEntity);

    int removeModuleById(int projectId);

    ProjectEntity modifyProject(ProjectEntity projectEntity);

    ProjectEntity findProjectInfo(int projectId);

    List<ProjectModuleResp> findProjectModule(int projectId);

    boolean addProjectModule(int projectId, int moduleBuildId,int type);

    boolean removeProjectModule(int projectId, int moduleBuildId);

    boolean buildProject(int projectId);

    boolean integrateProject(int projectId);

    void handleBuildResult(ProjectBuildEntity projectBuildEntity);

    PageInfo<ProjectBuildEntity> findProjectBuildHistory(int projectId, int pageNum, int pageSize);

    String findProjectBuildReport(int buildId);
}
