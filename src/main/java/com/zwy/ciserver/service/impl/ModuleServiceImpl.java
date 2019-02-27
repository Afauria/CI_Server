package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.dao.ModuleEntityMapper;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Afauria on 2019/2/25.
 */
@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleEntityMapper mModuleEntityMapper;//这里会报错，但是并不会影响

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
}
