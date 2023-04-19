package cn.autumnclouds.sems.exception;

import cn.autumnclouds.sems.common.ErrorCode;
import cn.autumnclouds.sems.common.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static cn.autumnclouds.sems.common.ErrorCode.PARAMS_ERROR;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public Result<?> validExceptionHandler(BindException exception) {
        return Result.fail(PARAMS_ERROR.getCode(), exception.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler(BusinessException.class)
    public Result<?> businessExceptionHandler(BusinessException exception) {
        return Result.fail(exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public Result<?> runtimeExceptionHandler(RuntimeException exception) {
        return Result.fail(ErrorCode.SYSTEM_ERROR);
    }
}
