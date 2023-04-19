package cn.autumnclouds.sems.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fu Qiujie
 * @since 2023/4/19
 */
@ApiModel("返回的结果类")
@Data
public class Result<T> {
    @ApiModelProperty("状态码")
    private Integer code;
    @ApiModelProperty("返回信息")
    private String msg;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("返回的其余动态数据")
    private Map<String, Object> map = new HashMap<>();

    @ApiOperation("返回成功")
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.data = data;
        result.code = 200;
        return result;
    }

    @ApiOperation("返回错误")
    public static <T> Result<T> fail(String msg) {
        return fail(-1, msg);
    }

    @ApiOperation("返回错误")
    public static <T> Result<T> fail(Integer errorCode, String errorMsg) {
        Result<T> result = new Result<>();
        result.msg = errorMsg;
        result.code = errorCode;
        return result;
    }

    @ApiOperation("返回错误")
    public static <T> Result<T> fail(ErrorCode errorCode) {
        return fail(errorCode.getCode(), errorCode.getMessage());
    }

    @ApiOperation("向返回的结果中添加动态数据")
    public Result<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}