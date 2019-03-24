package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.base.Optional;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.FolderJob;
import com.offbytwo.jenkins.model.Job;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.zwy.ciserver.jenkins.JenkinsServerFactory;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ModuleEntityMapper;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Afauria on 2019/2/25.
 */
@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleEntityMapper mModuleEntityMapper;

    @Autowired
    JenkinsServerFactory mJenkinsServerFactory;

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
            throw new BusinessException(-1,"添加失败，Jenkins添加Job异常");
        }
        return moduleEntity;
    }

    @Override
    @Transactional
    public int removeModuleById(int moduleId) {
        if (mModuleEntityMapper.selectModuleById(moduleId) == null) {
            throw new BusinessException(-1, "删除失败：组件不存在！");
        }
        ModuleEntity moduleEntity = mModuleEntityMapper.selectModuleById(moduleId);
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
        if (mModuleEntityMapper.selectModuleById(moduleEntity.getModuleId()) == null) {
            throw new BusinessException(-1, "修改失败：组件不存在！");
        }
        mModuleEntityMapper.updateModule(moduleEntity);
        String jobXml = mJenkinsServerFactory.generateModuleConfig(moduleEntity);
        try {
            mJenkinsServer.updateJob(moduleEntity.getName(),jobXml);
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
    public boolean buildModule(int moduleId) {
        return false;
    }
}
