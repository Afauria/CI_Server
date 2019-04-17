package com.zwy.ciserver.model.response;

import java.io.Serializable;

/**
 * Created by Afauria on 2019/4/4.
 */
public class ProjectModuleResp implements Serializable {
    private static final long serialVersionUID = 1L;
    private int moduleBuildId;
    private int projectId;
    private int moduleId;
    private int type;
    private String version;
    private String moduleName;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public int getModuleBuildId() {
        return moduleBuildId;
    }

    public void setModuleBuildId(int moduleBuildId) {
        this.moduleBuildId = moduleBuildId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }
}
