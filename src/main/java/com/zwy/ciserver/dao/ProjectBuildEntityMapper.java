package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ProjectBuildEntity;

import java.util.List;

public interface ProjectBuildEntityMapper {
    int insert(ProjectBuildEntity record);

    List<ProjectBuildEntity> selectProjectBuilds(int projectId);

    ProjectBuildEntity selectProjectBuildById(int projectBuildId);
}