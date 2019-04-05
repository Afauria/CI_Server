package com.zwy.ciserver.model.response;

import java.io.Serializable;

/**
 * Created by Afauria on 2019/4/5.
 */
public class ModulesNameResp implements Serializable {
    private static final long serialVersionUID = 1L;
    private int moduleId;
    private String name;

    public int getModuleId() {
        return moduleId;
    }

    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
