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
    public int addModule(ModuleEntity module) {
        return mModuleService.addModule(module);
    }

    @GetMapping("/list")
    public Result findAllModule(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                                    int pageNum,
                                @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                                    int pageSize) {
        return ResultUtil.success(mModuleService.findAllModule(pageNum, pageSize));

    }
}
