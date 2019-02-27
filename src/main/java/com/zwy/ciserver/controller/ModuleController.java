package com.zwy.ciserver.controller;

import com.zwy.ciserver.common.model.Result;
import com.zwy.ciserver.common.utils.ResultUtil;
import com.zwy.ciserver.entity.ModuleEntity;
import com.zwy.ciserver.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Afauria on 2019/2/25.
 */
@RestController//相当于@Controller+@ResponseBody
@RequestMapping(value = "/api/module")
@CrossOrigin
public class ModuleController {
    @Autowired
    private ModuleService mModuleService;

    @PostMapping("/add")
    public Result addModule(@RequestBody ModuleEntity module) {
        return ResultUtil.success(mModuleService.addModule(module));
    }

    @PostMapping("/remove")
    public Result removeModule(int moduleId) {
        return ResultUtil.success(mModuleService.removeModuleById(moduleId));
    }

    @PostMapping("/modify")
    public Result modifyModule(ModuleEntity moduleEntity) {
        return ResultUtil.success(mModuleService.modifyModule(moduleEntity));
    }

    @GetMapping("/list")
    public Result listModules(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                      int pageNum,
                              @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                      int pageSize) {
        return ResultUtil.success(mModuleService.listModules(pageNum, pageSize));

    }
}
