package cn.net.colin.common.exception;


import cn.net.colin.common.exception.entity.ResultCode;

/**
 * 自定义业务异常
 */
public class BusinessRuntimeException extends RuntimeException {
    
    /**
     * 结果码
     */
    private String code;

    /**
     * 结果码描述
     */
    private String msg;

    /**
     * 结果码枚举
     */
    private ResultCode resultCode;


    public BusinessRuntimeException(ResultCode resultCode) {
        super(resultCode.getMsg());
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.resultCode = resultCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(ResultCode resultCode) {
        this.resultCode = resultCode;
    }
}
