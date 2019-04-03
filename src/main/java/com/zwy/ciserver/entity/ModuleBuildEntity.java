package com.zwy.ciserver.entity;

import java.io.Serializable;
import java.util.Date;

public class ModuleBuildEntity implements Serializable {
    private Integer moduleBuildId;

    private Integer moduleId;

    private String moduleName;

    private Integer buildNum;

    private Integer buildStatus;

    private String version;

    private String message;

    private Date gmtCreate;

    private static final long serialVersionUID = 1L;

    public Integer getModuleBuildId() {
        return moduleBuildId;
    }

    public void setModuleBuildId(Integer moduleBuildId) {
        this.moduleBuildId = moduleBuildId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    public Integer getBuildNum() {
        return buildNum;
    }

    public void setBuildNum(Integer buildNum) {
        this.buildNum = buildNum;
    }

    public Integer getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(Integer buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", moduleBuildId=").append(moduleBuildId);
        sb.append(", moduleId=").append(moduleId);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", buildNum=").append(buildNum);
        sb.append(", buildStatus=").append(buildStatus);
        sb.append(", version=").append(version);
        sb.append(", message=").append(message);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String buildMsg(){
        StringBuilder sb = new StringBuilder();
        sb.append("组件").append(moduleName).append("构建成功");
        return sb.toString();
    }
}