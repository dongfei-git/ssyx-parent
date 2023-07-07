package tech.dongfei.ssyx.common.result;

import lombok.Data;

@Data
public class Result<T> {

    //状态码
    private Integer code;
    //信息
    private String message;
    //数据
    private T data;

    //构造私有化，无法new
    private Result(){}

    //用于设置数据的方法
    public static<T> Result<T> build(T data, Integer code, String message) {
        //创建Result对象
        Result<T> result = new Result<>();
        //判断返回结果中是否需要data数据
        if (data != null) {
            result.setData(data);
        }
        //设置其他值
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    //成功的方法
    public static<T> Result<T> ok(T data) {
        return build(data, ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage());
    }

    //失败的方法
    public static<T> Result<T> fail(T data) {
        return build(data, ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage());
    }
}
