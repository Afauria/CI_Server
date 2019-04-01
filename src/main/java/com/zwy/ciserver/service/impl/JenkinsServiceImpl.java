package com.zwy.ciserver.service.impl;

import com.zwy.ciserver.service.JenkinsService;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Afauria on 2019/3/28.
 */
@Service(value = "JenkinsService")
public class JenkinsServiceImpl implements JenkinsService {
    @Autowired
    private ModuleService mModuleService;

}
