package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ProjectEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectEntityMapper {

    List<ProjectEntity> selectProjects();

    int insertProject(ProjectEntity projectEntity);

    ProjectEntity selectProjectByName(String name);

    ProjectEntity selectProjectById(int projectId);

    ProjectEntity selectProjectInfoById(int projectId);

    void deleteProjectById(int projectId);

    void updateProject(ProjectEntity projectEntity);

    boolean updateProjectModuleVersion(@Param("linkId") int linkId, @Param("moduleBuildId") int moduleBuildId);
}