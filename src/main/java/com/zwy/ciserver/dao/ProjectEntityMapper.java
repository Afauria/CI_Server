package com.zwy.ciserver.dao;

import com.zwy.ciserver.entity.ProjectEntity;

public interface ProjectEntityMapper {
    int insert(ProjectEntity record);

    int insertSelective(ProjectEntity record);
}