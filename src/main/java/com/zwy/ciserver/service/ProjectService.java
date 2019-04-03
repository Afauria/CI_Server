package com.zwy.ciserver.service;

import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.entity.ProjectEntity;

/**
 * Created by Afauria on 2019/4/2.
 */
public interface ProjectService {
    PageInfo<ProjectEntity> listProjects(int pageNum, int pageSize);

    ProjectEntity addProject(ProjectEntity projectEntity);

    int removeModuleById(int projectId);

    ProjectEntity modifyProject(ProjectEntity projectEntity);

    ProjectEntity findProjectInfo(int projectId);

    boolean modifyModuleVersion(int linkId, int moduleBuildId);
}
