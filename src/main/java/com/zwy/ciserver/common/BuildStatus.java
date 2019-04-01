package com.zwy.ciserver.common;

/**
 * Created by Afauria on 2019/4/2.
 */
public interface BuildStatus {
    int UNBUILD = 1;
    int BUILDING = 2;
    int BUILD_SUCCESS = 3;
    int BUILD_FAIL = 4;
}
