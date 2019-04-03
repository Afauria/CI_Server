package com.zwy.ciserver.entity;

import com.zwy.ciserver.model.request.ProjectModuleReq;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ProjectEntity implements Serializable {
    private Integer projectId;

    private String name;

    private String repo;

    private String branch;

    private String curVersion;

    private Integer buildStatus;

    private Integer integrateStatus;

    private String descr;

    private Date gmtCreate;

    private Date gmtUpdate;

    private List<ProjectModuleReq> modules;

    private static final long serialVersionUID = 1L;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    public String getCurVersion() {
        return curVersion;
    }

    public void setCurVersion(String curVersion) {
        this.curVersion = curVersion == null ? null : curVersion.trim();
    }

    public Integer getBuildStatus() {
        return buildStatus;
    }

    public void setBuildStatus(Integer buildStatus) {
        this.buildStatus = buildStatus;
    }

    public Integer getIntegrateStatus() {
        return integrateStatus;
    }

    public void setIntegrateStatus(Integer integrateStatus) {
        this.integrateStatus = integrateStatus;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr == null ? null : descr.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtUpdate() {
        return gmtUpdate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public List<ProjectModuleReq> getModules() {
        return modules;
    }

    public void setModules(List<ProjectModuleReq> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", projectId=").append(projectId);
        sb.append(", name=").append(name);
        sb.append(", repo=").append(repo);
        sb.append(", branch=").append(branch);
        sb.append(", curVersion=").append(curVersion);
        sb.append(", buildStatus=").append(buildStatus);
        sb.append(", integrateStatus=").append(integrateStatus);
        sb.append(", descr=").append(descr);
        sb.append(", gmtCreate=").append(gmtCreate);
        sb.append(", gmtUpdate=").append(gmtUpdate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}