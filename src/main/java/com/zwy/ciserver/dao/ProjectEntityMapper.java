package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ProjectEntity;
import com.zwy.ciserver.model.response.ProjectModuleResp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectEntityMapper {

    List<ProjectEntity> selectProjects();

    int insertProject(ProjectEntity projectEntity);

    ProjectEntity selectProjectByName(String name);

    ProjectEntity selectProjectById(int projectId);

    List<ProjectModuleResp> selectProjectModuleById(int projectId);

    void deleteProjectById(int projectId);

    void updateProject(ProjectEntity projectEntity);

    boolean addProjectModule(@Param("projectId") int projectId,@Param("moduleBuildId") int moduleBuildId,@Param("type") int type);

    boolean deleteProjectModule(@Param("projectId") int projectId,@Param("moduleBuildId") int moduleBuildId);

    Integer selectLink(@Param("projectId") int projectId,@Param("moduleBuildId") int moduleBuildId);

    boolean updateProjectModule(@Param("linkId") int linkId,@Param("projectId") int projectId,@Param("moduleBuildId") int moduleBuildId,@Param("type") int type);

    void updateStatus(@Param("projectId")int projectId, @Param("buildStatus")int buildStatus);
}