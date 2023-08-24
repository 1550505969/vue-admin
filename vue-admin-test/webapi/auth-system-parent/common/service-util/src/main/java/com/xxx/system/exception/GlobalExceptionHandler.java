package com.xxx.system.exception;

import com.xxx.common.result.Result;
import com.xxx.common.result.ResultCodeEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 精确匹配异常优先
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常处理
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        return Result.fail().message(e.getMessage());
    }

    // 特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    public Result error(ArithmeticException e){
        return Result.fail().message("执行特定异常处理");
    }

    // 自定义异常处理
    @ExceptionHandler(MyException.class)
    public Result error(MyException e){
        return Result.fail().message(e.getMessage()).code(e.getCode());
    }

    @ExceptionHandler()
    public Result error(AccessDeniedException e){
        return Result.fail().code(ResultCodeEnum.PERMISSION.getCode()).message(ResultCodeEnum.PERMISSION.getMessage());
    }
}
