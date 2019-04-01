package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.zwy.ciserver.common.BuildStatus;
import com.zwy.ciserver.common.utils.VersionUtil;
import com.zwy.ciserver.dao.ModuleBuildEntityMapper;
import com.zwy.ciserver.jenkins.JenkinsServerFactory;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ModuleEntityMapper;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.entity.ModuleBuildEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Afauria on 2019/2/25.
 */
@Service(value = "ModuleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleEntityMapper mModuleEntityMapper;
    @Autowired
    private ModuleBuildEntityMapper mModuleBuildEntityMapper;

    @Autowired
    private JenkinsServerFactory mJenkinsServerFactory;

    JenkinsServer mJenkinsServer;

    @PostConstruct
    public void init() {
        mJenkinsServer = mJenkinsServerFactory.createJenkinsServer();
    }

    @Override
    @Transactional
    public ModuleEntity addModule(ModuleEntity moduleEntity) {
        if (mModuleEntityMapper.selectModuleByName(moduleEntity.getName()) != null) {
            throw new BusinessException(-1, "添加失败；组件名已存在！");
        }
        mModuleEntityMapper.insertModule(moduleEntity);
        try {
            String jobXml = mJenkinsServerFactory.generateModuleConfig(moduleEntity);
            mJenkinsServer.createJob(mJenkinsServerFactory.getModuleFolderJob(), moduleEntity.getName(), jobXml);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "添加失败，Jenkins添加Job异常");
        }
        return moduleEntity;
    }

    @Override
    @Transactional
    public int removeModuleById(int moduleId) {
        ModuleEntity moduleEntity;
        if ((moduleEntity = mModuleEntityMapper.selectModuleById(moduleId)) == null) {
            throw new BusinessException(-1, "删除失败：组件不存在！");
        }
        mModuleEntityMapper.deleteModuleById(moduleId);
        try {
            mJenkinsServer.deleteJob(mJenkinsServerFactory.getModuleFolderJob(), moduleEntity.getName());
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "删除失败，Jenkins删除Job异常");
        }
        return moduleId;
    }

    @Override
    @Transactional
    public ModuleEntity modifyModule(ModuleEntity moduleEntity) {
        ModuleEntity oldModuleEntity;
        if ((oldModuleEntity = mModuleEntityMapper.selectModuleById(moduleEntity.getModuleId())) == null) {
            throw new BusinessException(-1, "修改失败：组件不存在！");
        }
        mModuleEntityMapper.updateModule(moduleEntity);
        String jobXml = mJenkinsServerFactory.generateModuleConfig(moduleEntity);
        try {
            mJenkinsServer.renameJob(mJenkinsServerFactory.getModuleFolderJob(), oldModuleEntity.getName(),
                    moduleEntity.getName());
            mJenkinsServer.updateJob(mJenkinsServerFactory.getModuleFolderJob(), moduleEntity.getName(), jobXml,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(-1, "修改失败，Jenkins修改Job异常");
        }
        return moduleEntity;
    }

    @Override
    public PageInfo<ModuleEntity> listModules(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了。
        PageHelper.startPage(pageNum, pageSize);
        List<ModuleEntity> modules = mModuleEntityMapper.selectModules();
        PageInfo result = new PageInfo(modules);
        return result;
    }

    @Override
    public String searchBuildVersion(String curVersion, boolean rcFlag) {
        if (rcFlag) {
            return VersionUtil.nextRCVersion(curVersion);
        } else {
            return VersionUtil.nextReleaseVersion(curVersion);
        }
    }

    @Override
    @Transactional
    public boolean buildModule(int moduleId, String version) {
        ModuleEntity moduleEntity;
        if ((moduleEntity = mModuleEntityMapper.selectModuleById(moduleId)) == null) {
            throw new BusinessException(-1, "构建失败，组件不存在");
        }
        mModuleEntityMapper.updateStatus(moduleId, BuildStatus.BUILDING);
        try {
            JobWithDetails job = mJenkinsServer.getJob(mJenkinsServerFactory.getModuleFolderJob(), moduleEntity
                    .getName());
            if (job == null) {
                throw new BusinessException(-1, "构建失败，Jenkins获取Job失败");
            }
            Map<String, String> param = new HashMap();
            param.put("MODULE_ID", moduleEntity.getName());
            param.put("MODULE_NAME", moduleEntity.getName());
            param.put("CATALOG", moduleEntity.getCatalog());
            param.put("AAR_VERSION", version);
            job.build(param);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public void handleBuildResult(ModuleBuildEntity moduleBuildEntity) {
        mModuleBuildEntityMapper.insert(moduleBuildEntity);
        if (mModuleEntityMapper.selectModuleById(moduleBuildEntity.getModuleId()) == null) {
            throw new BusinessException(-1, "组件不存在");
        }
        if (moduleBuildEntity.getBuildStatus() == BuildStatus.BUILD_SUCCESS) {
            mModuleEntityMapper.updateVersion(moduleBuildEntity.getModuleId(), moduleBuildEntity.getVersion());
        }
        mModuleEntityMapper.updateStatus(moduleBuildEntity.getModuleId(), moduleBuildEntity.getBuildStatus());
    }
}
