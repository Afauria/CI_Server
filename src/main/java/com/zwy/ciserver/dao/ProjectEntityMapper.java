package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ProjectEntity;

import java.util.List;

public interface ProjectEntityMapper {

    List<ProjectEntity> selectProjects();

    int insertProject(ProjectEntity projectEntity);

    ProjectEntity selectProjectByName(String name);

    ProjectEntity selectProjectById(int projectId);

    void deleteProjectById(int projectId);

    void updateProject(ProjectEntity projectEntity);
}