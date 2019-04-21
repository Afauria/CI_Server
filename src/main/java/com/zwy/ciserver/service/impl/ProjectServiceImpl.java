package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.BuildWithDetails;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.zwy.ciserver.common.BuildStatus;
import com.zwy.ciserver.common.WSEvent;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ProjectBuildEntityMapper;
import com.zwy.ciserver.dao.ProjectEntityMapper;
import com.zwy.ciserver.entity.ProjectBuildEntity;
import com.zwy.ciserver.entity.ProjectEntity;
import com.zwy.ciserver.jenkins.JenkinsServerFactory;
import com.zwy.ciserver.model.response.ProjectModuleResp;
import com.zwy.ciserver.service.ProjectService;
import com.zwy.ciserver.websocket.MessageEventHandler;
import com.zwy.ciserver.websocket.MessageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Afauria on 2019/4/2.
 */
@Service(value = "ProjectService")
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectEntityMapper mProjectEntityMapper;
    @Autowired
    private ProjectBuildEntityMapper mProjectBuildEntityMapper;
    @Autowired
    private JenkinsServerFactory mJenkinsServerFactory;

    JenkinsServer mJenkinsServer;

    @PostConstruct
    public void init() {
        mJenkinsServer = mJenkinsServerFactory.createJenkinsServer();
    }

    @Override
    public PageInfo<ProjectEntity> listProjects(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectEntity> projects = mProjectEntityMapper.selectProjects();
        PageInfo result = new PageInfo(projects);
        return result;
    }

    @Override
    @Transactional
    public ProjectEntity addProject(ProjectEntity projectEntity) {
        if (mProjectEntityMapper.selectProjectByName(projectEntity.getName()) != null) {
            throw new BusinessException(-1, "添加失败；项目名已存在！");
        }
        mProjectEntityMapper.insertProject(projectEntity);
        try {
            String jobXml = mJenkinsServerFactory.generateProjectConfig(projectEntity);
            mJenkinsServer.createJob(mJenkinsServerFactory.getProjectFolderJob(), projectEntity.getName(), jobXml);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "添加失败，Jenkins添加Job异常");
        }
        return projectEntity;
    }

    @Override
    @Transactional
    public int removeModuleById(int projectId) {
        ProjectEntity projectEntity;
        if ((projectEntity = mProjectEntityMapper.selectProjectById(projectId)) == null) {
            throw new BusinessException(-1, "删除失败；项目不存在！");
        }
        mProjectEntityMapper.deleteProjectById(projectId);
        try {
            mJenkinsServer.deleteJob(mJenkinsServerFactory.getProjectFolderJob(), projectEntity.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "删除失败，Jenkins删除Job异常");
        }
        return projectId;
    }

    @Override
    @Transactional
    public ProjectEntity modifyProject(ProjectEntity projectEntity) {
        ProjectEntity oldProjectEntity;
        if ((oldProjectEntity = mProjectEntityMapper.selectProjectById(projectEntity.getProjectId())) == null) {
            throw new BusinessException(-1, "修改失败；项目不存在！");
        }
        mProjectEntityMapper.updateProject(projectEntity);
        String jobXml = mJenkinsServerFactory.generateProjectConfig(projectEntity);
        try {
            if (!oldProjectEntity.getName().equals(projectEntity.getName())) {
                mJenkinsServer.renameJob(mJenkinsServerFactory.getProjectFolderJob(), oldProjectEntity.getName(),
                        projectEntity.getName());
            }
            mJenkinsServer.updateJob(mJenkinsServerFactory.getProjectFolderJob(), projectEntity.getName(), jobXml,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "修改失败，Jenkins修改Job异常");
        }
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
    public List<ProjectModuleResp> findProjectModule(int projectId) {
        return mProjectEntityMapper.selectProjectModuleById(projectId);
    }

    @Override
    public boolean addProjectModule(int projectId, int moduleBuildId, int type) {
        Integer linkId;
        if ((linkId = mProjectEntityMapper.selectLink(projectId, moduleBuildId)) != null) {
            return mProjectEntityMapper.updateProjectModule(linkId, projectId, moduleBuildId, type);
        } else {
            return mProjectEntityMapper.addProjectModule(projectId, moduleBuildId, type);
        }
    }

    @Override
    public boolean removeProjectModule(int projectId, int moduleBuildId) {
        return mProjectEntityMapper.deleteProjectModule(projectId, moduleBuildId);
    }

    @Override
    @Transactional
    public boolean buildProject(int projectId) {
        ProjectEntity projectEntity;
        if ((projectEntity = mProjectEntityMapper.selectProjectById(projectId)) == null) {
            throw new BusinessException(-1, "构建失败，项目不存在");
        }
        if (projectEntity.getBuildStatus() == BuildStatus.BUILDING) {
            throw new BusinessException(-1, "项目正在构建");
        }
        mProjectEntityMapper.updateBuildStatus(projectId, BuildStatus.BUILDING);
        List<ProjectModuleResp> projectModules = findProjectModule(projectId);
        JSONArray jsonArray = new JSONArray();
        for (ProjectModuleResp projectModule : projectModules) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("moduleName", projectModule.getModuleName());
            jsonObject.put("version", projectModule.getVersion());
            jsonObject.put("type", projectModule.getType());
            jsonArray.add(jsonObject);
        }
        try {
            JobWithDetails job = mJenkinsServer.getJob(mJenkinsServerFactory.getProjectFolderJob(), projectEntity.getName());
            if (job == null) {
                throw new BusinessException(-1, "构建失败，Jenkins获取Job失败");
            }
            Map<String, String> param = new HashMap();
            param.put("PROJECT_ID", String.valueOf(projectId));
            param.put("PROJECT_NAME", projectEntity.getName());
            param.put("PROJECT_MODULES", jsonArray.toString());
            param.put("BRANCH",projectEntity.getBranch());
            param.put("BUILD_TYPE","1");
            job.build(param);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean integrateProject(int projectId) {
        ProjectEntity projectEntity;
        if ((projectEntity = mProjectEntityMapper.selectProjectById(projectId)) == null) {
            throw new BusinessException(-1, "集成失败，项目不存在");
        }
        if (projectEntity.getIntegrateStatus() == BuildStatus.BUILDING) {
            throw new BusinessException(-1, "项目正在集成");
        }
        mProjectEntityMapper.updateIntegrateStatus(projectId, BuildStatus.BUILDING);
        try {
            JobWithDetails job = mJenkinsServer.getJob(mJenkinsServerFactory.getProjectFolderJob(), projectEntity.getName());
            if (job == null) {
                throw new BusinessException(-1, "集成失败，Jenkins获取Job失败");
            }
            Map<String, String> param = new HashMap();
            param.put("PROJECT_ID", String.valueOf(projectId));
            param.put("PROJECT_NAME", projectEntity.getName());
            param.put("BRANCH",projectEntity.getBranch());
            param.put("BUILD_TYPE","2");
            job.build(param);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void handleBuildResult(ProjectBuildEntity projectBuildEntity) {
        mProjectBuildEntityMapper.insert(projectBuildEntity);
        if (mProjectEntityMapper.selectProjectById(projectBuildEntity.getProjectId()) == null) {
            throw new BusinessException(-1, "组件不存在");
        }
        if(projectBuildEntity.getType()==1){
            mProjectEntityMapper.updateBuildStatus(projectBuildEntity.getProjectId(), projectBuildEntity.getBuildStatus());
        }else{
            mProjectEntityMapper.updateIntegrateStatus(projectBuildEntity.getProjectId(), projectBuildEntity.getBuildStatus());
        }
        MessageInfo messageInfo = MessageInfo.success(projectBuildEntity.buildMsg());
        if (projectBuildEntity.getBuildStatus() == BuildStatus.BUILD_FAIL) {
            messageInfo = MessageInfo.error(projectBuildEntity.buildMsg());
        }
        MessageEventHandler.sendAll(WSEvent.PROJECT, messageInfo);
    }

    @Override
    public PageInfo<ProjectBuildEntity> findProjectBuildHistory(int projectId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProjectBuildEntity> projectBuilds = mProjectBuildEntityMapper.selectProjectBuilds(projectId);
        PageInfo result = new PageInfo(projectBuilds);
        return result;
    }

    @Override
    @Transactional
    public String findProjectBuildReport(int buildId) {
        ProjectBuildEntity projectBuildEntity;
        if((projectBuildEntity= mProjectBuildEntityMapper.selectProjectBuildById(buildId))==null){
            throw new BusinessException(-1,"项目构建信息不存在");
        }
        try {
            JobWithDetails job=mJenkinsServer.getJob(mJenkinsServerFactory.getProjectFolderJob(), projectBuildEntity.getProjectName());
            BuildWithDetails build = job.getBuildByNumber(projectBuildEntity.getBuildNum()).details();
            return build.getConsoleOutputText();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
