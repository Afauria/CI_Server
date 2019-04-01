package com.zwy.ciserver.controller;

import com.zwy.ciserver.common.model.Result;
import com.zwy.ciserver.common.utils.ResultUtil;
import com.zwy.ciserver.entity.ModuleBuildEntity;
import com.zwy.ciserver.service.JenkinsService;
import com.zwy.ciserver.service.ModuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Afauria on 2019/3/28.
 */
@RestController
@RequestMapping(value = "/api/jenkins")
@CrossOrigin
public class JenkinsController {

    Logger logger = LoggerFactory.getLogger(Logger.class);
    @Autowired
    private JenkinsService mJenkinsService;
    @Autowired
    private ModuleService mModuleService;

    @PostMapping("/notify/module")
    public Result handleMsg(ModuleBuildEntity moduleBuildEntity) {
        logger.info("receive jenkins message");
        mModuleService.handleBuildResult(moduleBuildEntity);
        return ResultUtil.success("successhhhhhh");
    }
}
