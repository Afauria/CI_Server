package com.zwy.ciserver;

import com.zwy.ciserver.common.exception.BusinessException;
import com.zwy.ciserver.common.model.Result;
import com.zwy.ciserver.common.utils.ResultUtil;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static int DEFAULT_ERROR_CODE = 99999;
    private final static int SQL_ERROR_CODE = 99990;

    @ExceptionHandler(value = {NoHandlerFoundException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result noHandlerFoundException(Exception ex) {
        return ResultUtil.error(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase());
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result constraintViolationException(ConstraintViolationException ex) {
        return ResultUtil.error(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
    }

    @ExceptionHandler({RuntimeException.class, Exception.class})
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result defaultErrorHandler(Exception ex) {
        return ResultUtil.error(DEFAULT_ERROR_CODE, ex.getLocalizedMessage());
    }

    @ExceptionHandler(value = DataAccessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result sqlErrorHandler(DataAccessException ex) {
        return ResultUtil.error(SQL_ERROR_CODE, "数据库操作异常");
    }

    /**
     * 业务异常全局处理。业务异常视为成功请求，即responseStatus返回200
     *
     * @param ex 业务异常实例
     * @return
     */
    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public Result businessErrorHandler(BusinessException ex) {
        return ResultUtil.error(ex.getCode(), ex.getMsg());
    }
}
