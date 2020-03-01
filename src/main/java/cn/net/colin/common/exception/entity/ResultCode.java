package cn.net.colin.common.exception.entity;

/**
 * Created by sxf on 2019-5-15.
 * 结果码枚举类
 */
public enum ResultCode {

    SUCCESS("0", "操作成功!"),
    FAILED("-1","操作失败"),
    STATUS_CODE_400("400","请求无效"),
    STATUS_CODE_403("403","禁止访问"),
    STATUS_CODE_404("404","请求的网页不存在"),
    STATUS_CODE_405("405","资源被禁止"),
    STATUS_CODE_500("500","内部服务器错误,请联系管理员"),
    UNKNOWN_ERROR("999","未知异常");

    /**
     * 结果码
     */
    private String code;

    /**
     * 结果码描述
     */
    private String msg;


    ResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}