package com.zwy.ciserver.common.utils;

import com.zwy.ciserver.common.model.Result;

import javax.validation.constraints.NotNull;

/**
 * Created by Afauria on 2019/1/22.
 */
public class ResultUtil {
    public static <T> Result success(T data) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMsg("ok");
        result.setSuccess(true);
        result.setData(data);

        return result;
    }

    public static Result error(int code, @NotNull String msg) {

        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);

        return result;
    }

    public static Result businessSuccess(int code, @NotNull String msg) {

        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(true);

        return result;
    }
}
