package cn.net.colin.common.exception.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;

/**
 * Created by sxf on 2019-5-15.
 * 统一的结果返回类
 */
@JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL)
public class ResultInfo implements Serializable{
    private static final long serialVersionUID = -5996425918881028431L;
    /**
     * 结果码
     */
    private String returnCode;

    /**
     * 结果码描述
     */
    private String returnMessage;

    /**
     * 返回记录条数
     */
    private Long total;

    /**
     * 返回结果数据
     */
    private Object data;


    public ResultInfo() {

    }

    public ResultInfo(ResultCode resultCode) {
        this.returnCode = resultCode.getCode();
        this.returnMessage = resultCode.getMsg();
    }

    public ResultInfo(ResultCode resultCode, Object data){
        this.returnCode = resultCode.getCode();
        this.returnMessage = resultCode.getMsg();
        this.data = data;
    }

    public ResultInfo(ResultCode resultCode, Object data, Long total){
        this.returnCode = resultCode.getCode();
        this.returnMessage = resultCode.getMsg();
        this.data = data;
        this.total = total;
    }


    /**
     * 生成一个ApiResult对象, 并返回
     *
     * @param resultCode
     * @return
     */
    public static ResultInfo of(ResultCode resultCode) {
        return new ResultInfo(resultCode);
    }

    /**
     * 生成一个带返回结果的ApiResult对象
     * @param resultCode
     * @param data
     * @return
     */
    public static ResultInfo ofData(ResultCode resultCode, Object data){
        return new ResultInfo(resultCode, data);
    }
    /**
     * 生成一个带返回结果和返回结果条数的ApiResult对象
     * @param resultCode
     * @param data
     * @return
     */
    public static ResultInfo ofDataAndTotal(ResultCode resultCode, Object data, long total){
        return new ResultInfo(resultCode, data,total);
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

}