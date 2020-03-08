package cn.net.colin.model.sysManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/** 
 * 系统权限表实体类
 *  例如：ADMIN_AUTH（管理员权限），INSERT_AUTH（数据添加权限）
 * @author sxf
 * date:2020/03/04 17:48
 */
public class SysOperatetype implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -4688424912538870745L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 编码
     */ 
    private String operateCode;

    /** 
     * 名称
     */ 
    private String operateName;

    /** 
     * 描述
     */ 
    private String operateDescribe;

    /** 
     * 创建人
     */ 
    private String createUser;

    /** 
     * 创建时间
     */ 
    private Date createTime;

    /** 
     * 获取 主键ID sys_operatetype.id
     * @return 主键ID
     */
    public final Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_operatetype.id
     * @param id 主键ID
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 编码 sys_operatetype.operate_code
     * @return 编码
     */
    public final String getOperateCode() {
        return operateCode;
    }

    /** 
     * 设置 编码 sys_operatetype.operate_code
     * @param operateCode 编码
     */
    public final void setOperateCode(String operateCode) {
        this.operateCode = operateCode == null ? null : operateCode.trim();
    }

    /** 
     * 获取 名称 sys_operatetype.operate_name
     * @return 名称
     */
    public final String getOperateName() {
        return operateName;
    }

    /** 
     * 设置 名称 sys_operatetype.operate_name
     * @param operateName 名称
     */
    public final void setOperateName(String operateName) {
        this.operateName = operateName == null ? null : operateName.trim();
    }

    /** 
     * 获取 描述 sys_operatetype.operate_describe
     * @return 描述
     */
    public final String getOperateDescribe() {
        return operateDescribe;
    }

    /** 
     * 设置 描述 sys_operatetype.operate_describe
     * @param operateDescribe 描述
     */
    public final void setOperateDescribe(String operateDescribe) {
        this.operateDescribe = operateDescribe == null ? null : operateDescribe.trim();
    }

    /** 
     * 获取 创建人 sys_operatetype.create_user
     * @return 创建人
     */
    public final String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_operatetype.create_user
     * @param createUser 创建人
     */
    public final void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_operatetype.create_time
     * @return 创建时间
     */
    public final Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_operatetype.create_time
     * @param createTime 创建时间
     */
    public final void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", operateCode=").append(operateCode);
        sb.append(", operateName=").append(operateName);
        sb.append(", operateDescribe=").append(operateDescribe);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}