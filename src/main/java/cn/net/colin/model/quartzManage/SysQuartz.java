package cn.net.colin.model.quartzManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/** 
 * @author sxf code generator
 * date:2020/04/12 15:54
 */
public class SysQuartz implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -5359510887702196647L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 任务名称
     */ 
    private String quartzname;

    /** 
     * 执行时间（Cron表达式）
     */ 
    private String cron;

    /** 
     * 任务类名
     */ 
    private String clazzname;

    /** 
     * 状态（1 启用，0禁用）  默认：0
     */ 
    private Integer running;

    /**
     * 上次任务开始执行时间
     */ 
    private String exp1;

    /**
     * 上次任务执行完成时间
     */ 
    private String exp2;

    /**
     * quartz定时任务的服务标识码，启动多个服务时用于区分一个任务属于哪个服务
     */ 
    private String exp3;

    /**
     * 当前服务的标识码，前端用于和exp3进行比较，判断是否允许修改和删除
     */ 
    private String exp4;

    /** 
     */ 
    private String exp5;

    /** 
     * 任务参数
     */ 
    private String params;

    /** 
     * 获取 主键ID sys_quartz.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_quartz.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 任务名称 sys_quartz.quartzname
     * @return 任务名称
     */
    public String getQuartzname() {
        return quartzname;
    }

    /** 
     * 设置 任务名称 sys_quartz.quartzname
     * @param quartzname 任务名称
     */
    public void setQuartzname(String quartzname) {
        this.quartzname = quartzname == null ? null : quartzname.trim();
    }

    /** 
     * 获取 执行时间（Cron表达式） sys_quartz.cron
     * @return 执行时间（Cron表达式）
     */
    public String getCron() {
        return cron;
    }

    /** 
     * 设置 执行时间（Cron表达式） sys_quartz.cron
     * @param cron 执行时间（Cron表达式）
     */
    public void setCron(String cron) {
        this.cron = cron == null ? null : cron.trim();
    }

    /** 
     * 获取 任务类名 sys_quartz.clazzname
     * @return 任务类名
     */
    public String getClazzname() {
        return clazzname;
    }

    /** 
     * 设置 任务类名 sys_quartz.clazzname
     * @param clazzname 任务类名
     */
    public void setClazzname(String clazzname) {
        this.clazzname = clazzname == null ? null : clazzname.trim();
    }

    /** 
     * 获取 状态（1 启用，0禁用） sys_quartz.running
     * @return 状态（1 启用，0禁用）
     */
    public Integer getRunning() {
        return running;
    }

    /** 
     * 设置 状态（1 启用，0禁用） sys_quartz.running
     * @param running 状态（1 启用，0禁用）
     */
    public void setRunning(Integer running) {
        this.running = running;
    }

    /** 
     * 获取 sys_quartz.exp1
     * @return sys_quartz.exp1
     */
    public String getExp1() {
        return exp1;
    }

    /** 
     * 设置 sys_quartz.exp1
     * @param exp1 sys_quartz.exp1
     */
    public void setExp1(String exp1) {
        this.exp1 = exp1 == null ? null : exp1.trim();
    }

    /** 
     * 获取 sys_quartz.exp2
     * @return sys_quartz.exp2
     */
    public String getExp2() {
        return exp2;
    }

    /** 
     * 设置 sys_quartz.exp2
     * @param exp2 sys_quartz.exp2
     */
    public void setExp2(String exp2) {
        this.exp2 = exp2 == null ? null : exp2.trim();
    }

    /** 
     * 获取 sys_quartz.exp3
     * @return sys_quartz.exp3
     */
    public String getExp3() {
        return exp3;
    }

    /** 
     * 设置 sys_quartz.exp3
     * @param exp3 sys_quartz.exp3
     */
    public void setExp3(String exp3) {
        this.exp3 = exp3 == null ? null : exp3.trim();
    }

    /** 
     * 获取 sys_quartz.exp4
     * @return sys_quartz.exp4
     */
    public String getExp4() {
        return exp4;
    }

    /** 
     * 设置 sys_quartz.exp4
     * @param exp4 sys_quartz.exp4
     */
    public void setExp4(String exp4) {
        this.exp4 = exp4 == null ? null : exp4.trim();
    }

    /** 
     * 获取 sys_quartz.exp5
     * @return sys_quartz.exp5
     */
    public String getExp5() {
        return exp5;
    }

    /** 
     * 设置 sys_quartz.exp5
     * @param exp5 sys_quartz.exp5
     */
    public void setExp5(String exp5) {
        this.exp5 = exp5 == null ? null : exp5.trim();
    }

    /** 
     * 获取 任务参数 sys_quartz.params
     * @return 任务参数
     */
    public String getParams() {
        return params;
    }

    /** 
     * 设置 任务参数 sys_quartz.params
     * @param params 任务参数
     */
    public void setParams(String params) {
        this.params = params == null ? null : params.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", quartzname=").append(quartzname);
        sb.append(", cron=").append(cron);
        sb.append(", clazzname=").append(clazzname);
        sb.append(", running=").append(running);
        sb.append(", exp1=").append(exp1);
        sb.append(", exp2=").append(exp2);
        sb.append(", exp3=").append(exp3);
        sb.append(", exp4=").append(exp4);
        sb.append(", exp5=").append(exp5);
        sb.append(", params=").append(params);
        sb.append("]");
        return sb.toString();
    }
}