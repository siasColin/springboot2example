package cn.net.colin.model.sysManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/** 
 * 角色表 sys_role
 * @author sxf
 * date:2020/03/04 17:48
 */
public class SysRole implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = 1227279465189616737L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 角色编码
     */ 
    private String roleCode;

    /** 
     * 角色名称
     */ 
    private String roleName;

    /** 
     * 角色属性(0 共享，1 私有)
     */ 
    private Integer roleAttr;

    /** 
     * 机构编码（role_attr为1时该字段不能为空）
     */ 
    private String orgCode;

    /** 
     * 关联地区表地区编码
     */ 
    private String areaCode;

    /** 
     * 状态，1启用，0禁用
     */ 
    private Integer roleStatus;

    /** 
     * 创建人
     */ 
    private String createUser;

    /** 
     * 创建时间
     */ 
    private Date createTime;

    /** 
     * 排序字段
     */ 
    private Integer sortNum;

    /**
     * 非持久化属性 start
     */
    private  String orgName;
    private  String areaName;
    /**
     * 非持久化属性 end
     */

    /** 
     * 获取 主键ID sys_role.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_role.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 角色编码 sys_role.role_code
     * @return 角色编码
     */
    public String getRoleCode() {
        return roleCode;
    }

    /** 
     * 设置 角色编码 sys_role.role_code
     * @param roleCode 角色编码
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode == null ? null : roleCode.trim();
    }

    /** 
     * 获取 角色名称 sys_role.role_name
     * @return 角色名称
     */
    public String getRoleName() {
        return roleName;
    }

    /** 
     * 设置 角色名称 sys_role.role_name
     * @param roleName 角色名称
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    /** 
     * 获取 角色属性(0 共享，1 私有) sys_role.role_attr
     * @return 角色属性(0 共享，1 私有)
     */
    public Integer getRoleAttr() {
        return roleAttr;
    }

    /** 
     * 设置 角色属性(0 共享，1 私有) sys_role.role_attr
     * @param roleAttr 角色属性(0 共享，1 私有)
     */
    public void setRoleAttr(Integer roleAttr) {
        this.roleAttr = roleAttr;
    }

    /** 
     * 获取 机构编码（role_attr为1时该字段不能为空） sys_role.org_code
     * @return 机构编码（role_attr为1时该字段不能为空）
     */
    public String getOrgCode() {
        return orgCode;
    }

    /** 
     * 设置 机构编码（role_attr为1时该字段不能为空） sys_role.org_code
     * @param orgCode 机构编码（role_attr为1时该字段不能为空）
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /** 
     * 获取 关联地区表地区编码 sys_role.area_code
     * @return 关联地区表地区编码
     */
    public String getAreaCode() {
        return areaCode;
    }

    /** 
     * 设置 关联地区表地区编码 sys_role.area_code
     * @param areaCode 关联地区表地区编码
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /** 
     * 获取 状态，1启用，0禁用 sys_role.role_status
     * @return 状态，1启用，0禁用
     */
    public Integer getRoleStatus() {
        return roleStatus;
    }

    /** 
     * 设置 状态，1启用，0禁用 sys_role.role_status
     * @param roleStatus 状态，1启用，0禁用
     */
    public void setRoleStatus(Integer roleStatus) {
        this.roleStatus = roleStatus;
    }

    /** 
     * 获取 创建人 sys_role.create_user
     * @return 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_role.create_user
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_role.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_role.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 排序字段 sys_role.sort_num
     * @return 排序字段
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /** 
     * 设置 排序字段 sys_role.sort_num
     * @param sortNum 排序字段
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", roleCode=").append(roleCode);
        sb.append(", roleName=").append(roleName);
        sb.append(", roleAttr=").append(roleAttr);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", roleStatus=").append(roleStatus);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", sortNum=").append(sortNum);
        sb.append("]");
        return sb.toString();
    }
}