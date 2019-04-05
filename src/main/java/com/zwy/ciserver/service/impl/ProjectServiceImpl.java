package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ModuleBuildEntityMapper;
import com.zwy.ciserver.dao.ProjectEntityMapper;
import com.zwy.ciserver.entity.ProjectEntity;
import com.zwy.ciserver.model.response.ProjectModuleResp;
import com.zwy.ciserver.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Afauria on 2019/4/2.
 */
@Service(value = "ProjectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectEntityMapper mProjectEntityMapper;
    @Autowired
    private ModuleBuildEntityMapper mModuleBuildEntityMapper;

    @Override
    public PageInfo<ProjectEntity> listProjects(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectEntity> projects = mProjectEntityMapper.selectProjects();
        PageInfo result = new PageInfo(projects);
        return result;
    }

    @Override
    public ProjectEntity addProject(ProjectEntity projectEntity) {
        if (mProjectEntityMapper.selectProjectByName(projectEntity.getName()) != null) {
            throw new BusinessException(-1, "添加失败；项目名已存在！");
        }
        mProjectEntityMapper.insertProject(projectEntity);
        return projectEntity;
    }

    @Override
    public int removeModuleById(int projectId) {
        if (mProjectEntityMapper.selectProjectById(projectId) == null) {
            throw new BusinessException(-1, "删除失败；项目不存在！");
        }
        mProjectEntityMapper.deleteProjectById(projectId);
        return projectId;
    }

    @Override
    public ProjectEntity modifyProject(ProjectEntity projectEntity) {
        if (mProjectEntityMapper.selectProjectById(projectEntity.getProjectId()) == null) {
            throw new BusinessException(-1, "修改失败；项目不存在！");
        }
        mProjectEntityMapper.updateProject(projectEntity);
        return projectEntity;
    }

    @Override
    public ProjectEntity findProjectInfo(int projectId) {
        ProjectEntity projectEntity;
        if ((projectEntity = mProjectEntityMapper.selectProjectById(projectId)) == null) {
            throw new BusinessException(-1, "项目不存在！");
        }
        return projectEntity;
    }

    @Override
    public List<ProjectModuleResp> findProjectModule(int projectId){
        return mProjectEntityMapper.selectProjectModuleById(projectId);
    }

    @Override
    public boolean addProjectModule(int projectId, int moduleBuildId) {
        Integer linkId;
        if((linkId=mProjectEntityMapper.selectLink(projectId,moduleBuildId))!=null){
            return mProjectEntityMapper.updateProjectModule(linkId, projectId,moduleBuildId);
        }else{
            return mProjectEntityMapper.addProjectModule(projectId,moduleBuildId);
        }
    }
    @Override
    public boolean removeProjectModule(int projectId, int moduleBuildId) {
        return mProjectEntityMapper.deleteProjectModule(projectId,moduleBuildId);
    }
}
