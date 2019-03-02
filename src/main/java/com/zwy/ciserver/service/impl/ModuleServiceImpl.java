package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Job;
import com.zwy.ciserver.JenkinsServerFactory;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ModuleEntityMapper;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Afauria on 2019/2/25.
 */
@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleEntityMapper mModuleEntityMapper;//这里会报错，但是并不会影响
    JenkinsServer mJenkinsServer;
    @Autowired
    JenkinsServerFactory mJenkinsServerFactory;
    @Override
    @Transactional
    public ModuleEntity addModule(ModuleEntity moduleEntity) {
        if (mModuleEntityMapper.selectModuleByName(moduleEntity.getName()) != null) {
            throw new BusinessException(-1, "添加失败；组件名已存在！");
        }
        mModuleEntityMapper.insertModule(moduleEntity);
        return moduleEntity;
    }

    @Override
    @Transactional
    public int removeModuleById(int moduleId) {
        if (mModuleEntityMapper.selectModuleById(moduleId) == null) {
            throw new BusinessException(-1, "删除失败：组件不存在！");
        }
        mModuleEntityMapper.deleteModuleById(moduleId);
        return moduleId;
    }

    @Override
    @Transactional
    public ModuleEntity modifyModule(ModuleEntity moduleEntity) {
        if(mModuleEntityMapper.selectModuleById(moduleEntity.getModuleId())==null){
            throw new BusinessException(-1,"修改失败：组件不存在！");
        }
        mModuleEntityMapper.updateModule(moduleEntity);
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
    public boolean buildModule(int moduleId){
        mJenkinsServer = mJenkinsServerFactory.createJenkinsServer();
        try {
            Map<String,Job> jobs = mJenkinsServer.getJobs();
            System.out.println(mJenkinsServer.getJobXml("CI_Module"));
            for (Map.Entry<String,Job> o : jobs.entrySet()) {
                System.out.println(o.getValue().getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
