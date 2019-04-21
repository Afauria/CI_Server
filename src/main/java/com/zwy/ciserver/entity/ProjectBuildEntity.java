package com.zwy.ciserver.entity;

import java.io.Serializable;
import java.util.Date;

public class ProjectBuildEntity implements Serializable {
    private Integer projectBuildId;

    private Integer projectId;

    private String projectName;

    private Integer buildNum;

    private Integer buildStatus;
    //1:构建；2：集成
    private Integer type;

    private String message;

    private String downloadUrl;

    private Date gmtCreate;

    private static final long serialVersionUID = 1L;

    public Integer getProjectBuildId() {
        return projectBuildId;
    }

    public void setProjectBuildId(Integer projectBuildId) {
        this.projectBuildId = projectBuildId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectBuildId=").append(projectBuildId);
        sb.append(", projectId=").append(projectId);
        sb.append(", projectName=").append(projectName);
        sb.append(", buildNum=").append(buildNum);
        sb.append(", buildStatus=").append(buildStatus);
        sb.append(", message=").append(message);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public String buildMsg() {
        StringBuilder sb = new StringBuilder();
        sb.append("项目").append(projectName).append(getMessage());
        return sb.toString();
    }
}