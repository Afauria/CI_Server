package com.zwy.ciserver.controller;

import com.zwy.ciserver.common.model.Result;
import com.zwy.ciserver.common.utils.ResultUtil;
import com.zwy.ciserver.entity.ProjectEntity;
import com.zwy.ciserver.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Afauria on 2019/4/2.
 */
@RestController
@RequestMapping(value = "/api/project")
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService mProjectService;

    @GetMapping("/list")
    public Result listProjects(@RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                       int pageNum,
                               @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                       int pageSize) {
        return ResultUtil.success(mProjectService.listProjects(pageNum, pageSize));
    }

    @PostMapping("/add")
    public Result addProject(ProjectEntity projectEntity) {
        return ResultUtil.success(mProjectService.addProject(projectEntity));
    }

    @PostMapping("/remove")
    public Result removeProject(int projectId) {
        return ResultUtil.success(mProjectService.removeModuleById(projectId));
    }

    @PostMapping("/modify")
    public Result modifyProject(ProjectEntity projectEntity) {
        return ResultUtil.success(mProjectService.modifyProject(projectEntity));
    }

    @GetMapping("/info/{projectId}")
    public Result findProjectInfo(@PathVariable("projectId") int projectId) {
        return ResultUtil.success(mProjectService.findProjectInfo(projectId));
    }

    @GetMapping("/info/{projectId}/history")
    public Result findProjectBuildHistory(@PathVariable("projectId") int projectId,
                                          @RequestParam(name = "pageNum", required = false, defaultValue = "1") int pageNum,
                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") int
                                                  pageSize) {
        return ResultUtil.success(mProjectService.findProjectBuildHistory(projectId, pageNum, pageSize));
    }

    @PostMapping("/info/build")
    public Result findProjectBuildReport(int buildId) {
        return ResultUtil.success(mProjectService.findProjectBuildReport(buildId));
    }

    @PostMapping("/modules/list")
    public Result findProjectModule(int projectId) {
        return ResultUtil.success(mProjectService.findProjectModule(projectId));
    }

    @PostMapping("/modules/add")
    public Result addProjectModule(int projectId, int moduleBuildId, int type) {
        return ResultUtil.success(mProjectService.addProjectModule(projectId, moduleBuildId, type));
    }

    @PostMapping("/modules/remove")
    public Result removeProjectModule(int projectId, int moduleBuildId) {
        return ResultUtil.success(mProjectService.removeProjectModule(projectId, moduleBuildId));
    }

    @PostMapping("/build")
    public Result buildProject(int projectId) {
        return ResultUtil.success(mProjectService.buildProject(projectId));
    }
}
