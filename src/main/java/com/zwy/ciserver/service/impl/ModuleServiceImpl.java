package com.zwy.ciserver.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zwy.ciserver.dao.ModuleEntityMapper;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Afauria on 2019/2/25.
 */
@Service(value = "moduleService")
public class ModuleServiceImpl implements ModuleService {
    @Autowired
    private ModuleEntityMapper mModuleEntityMapper;//这里会报错，但是并不会影响

    @Override
    public int addModule(ModuleEntity moduleEntity) {
        return mModuleEntityMapper.insert(moduleEntity);
    }

    @Override
    public PageInfo<ModuleEntity> findAllModule(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<ModuleEntity> modules = mModuleEntityMapper.selectModules();
        PageInfo result = new PageInfo(modules);
        return result;
    }
}
