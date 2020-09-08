package cn.net.colin.model.sysManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/** 
 * 菜单表 sys_modullist
 * @author sxf
 * date:2020/03/04 17:48
 */
public class SysModulelist implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -6927654330368885350L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @ApiModelProperty(hidden = true)
    private Long id;

    /** 
     * 父级菜单ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    @ApiModelProperty(value = "父级菜单ID",required = false,position = 0)
    private Long pid;

    /** 
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称",required = true,position = 1)
    private String moduleName;

    /** 
     * 菜单编码
     */
    @ApiModelProperty(value = "菜单编码",required = true,position = 2)
    private String moduleCode;

    /** 
     * 菜单图标（样式）
     */
    @ApiModelProperty(value = "菜单图标（样式）",required = false,position = 3)
    private String moduleIcon;

    /** 
     * 菜单链接地址
     */
    @ApiModelProperty(value = "菜单链接地址",required = false,position = 4)
    private String moduleUrl;

    /** 
     * 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
     */
    @ApiModelProperty(value = "打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）",required = true,position = 5)
    private String moduleTarget;

    /** 
     * 菜单还是功能点，1菜单，0功能点
     */
    @ApiModelProperty(value = "菜单还是功能点，1菜单，0功能点",required = true,position = 6)
    private Integer moduleType;

    /** 
     * 菜单状态，1启用，0禁用
     */
    @ApiModelProperty(value = "菜单状态，1启用，0禁用",required = true,position = 7)
    private Integer moduleStatus;

    /** 
     * 创建人
     */
    @ApiModelProperty(hidden = true)
    private String createUser;

    /** 
     * 创建时间
     */
    @ApiModelProperty(hidden = true)
    private Date createTime;

    /**
     * 排序字段
     */
    @ApiModelProperty(value = "菜单状态，1启用，0禁用",required = false,position = 8)
    private Integer sortNum;

    /**
     * 非持久化属性 start
     */
    @ApiModelProperty(hidden = true)
    private String parentName;
    /**
     * 非持久化属性 end
     */

    /** 
     * 获取 主键ID sys_modullist.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_modullist.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 父级菜单ID sys_modullist.pid
     * @return 父级菜单ID
     */
    public Long getPid() {
        return pid;
    }

    /** 
     * 设置 父级菜单ID sys_modullist.pid
     * @param pid 父级菜单ID
     */
    public void setPid(Long pid) {
        this.pid = pid;
    }

    /** 
     * 获取 菜单名称 sys_modullist.module_name
     * @return 菜单名称
     */
    public String getModuleName() {
        return moduleName;
    }

    /** 
     * 设置 菜单名称 sys_modullist.module_name
     * @param moduleName 菜单名称
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName == null ? null : moduleName.trim();
    }

    /** 
     * 获取 菜单编码 sys_modullist.module_code
     * @return 菜单编码
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /** 
     * 设置 菜单编码 sys_modullist.module_code
     * @param moduleCode 菜单编码
     */
    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode == null ? null : moduleCode.trim();
    }

    /** 
     * 获取 菜单图标（样式） sys_modullist.module_icon
     * @return 菜单图标（样式）
     */
    public String getModuleIcon() {
        return moduleIcon;
    }

    /** 
     * 设置 菜单图标（样式） sys_modullist.module_icon
     * @param moduleIcon 菜单图标（样式）
     */
    public void setModuleIcon(String moduleIcon) {
        this.moduleIcon = moduleIcon == null ? null : moduleIcon.trim();
    }

    /** 
     * 获取 菜单链接地址 sys_modullist.module_url
     * @return 菜单链接地址
     */
    public String getModuleUrl() {
        return moduleUrl;
    }

    /** 
     * 设置 菜单链接地址 sys_modullist.module_url
     * @param moduleUrl 菜单链接地址
     */
    public void setModuleUrl(String moduleUrl) {
        this.moduleUrl = moduleUrl == null ? null : moduleUrl.trim();
    }

    /** 
     * 获取 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
            navTab：右侧iframe中打开
            _blank：新窗口打开
             sys_modullist.module_target
     * @return 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
            navTab：右侧iframe中打开
            _blank：新窗口打开
            
     */
    public String getModuleTarget() {
        return moduleTarget;
    }

    /** 
     * 设置 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
            navTab：右侧iframe中打开
            _blank：新窗口打开
             sys_modullist.module_target
     * @param moduleTarget 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
            navTab：右侧iframe中打开
            _blank：新窗口打开
            
     */
    public void setModuleTarget(String moduleTarget) {
        this.moduleTarget = moduleTarget == null ? null : moduleTarget.trim();
    }

    /** 
     * 获取 菜单还是功能点，1菜单，0功能点 sys_modullist.module_type
     * @return 菜单还是功能点，1菜单，0功能点
     */
    public Integer getModuleType() {
        return moduleType;
    }

    /** 
     * 设置 菜单还是功能点，1菜单，0功能点 sys_modullist.module_type
     * @param moduleType 菜单还是功能点，1菜单，0功能点
     */
    public void setModuleType(Integer moduleType) {
        this.moduleType = moduleType;
    }

    /** 
     * 获取 菜单状态，1启用，0禁用 sys_modullist.module_status
     * @return 菜单状态，1启用，0禁用
     */
    public Integer getModuleStatus() {
        return moduleStatus;
    }

    /** 
     * 设置 菜单状态，1启用，0禁用 sys_modullist.module_status
     * @param moduleStatus 菜单状态，1启用，0禁用
     */
    public void setModuleStatus(Integer moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    /** 
     * 获取 创建人 sys_modullist.create_user
     * @return 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_modullist.create_user
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_modullist.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_modullist.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getSortNum() {
        return sortNum;
    }

    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", pid=").append(pid);
        sb.append(", moduleName=").append(moduleName);
        sb.append(", moduleCode=").append(moduleCode);
        sb.append(", moduleIcon=").append(moduleIcon);
        sb.append(", moduleUrl=").append(moduleUrl);
        sb.append(", moduleTarget=").append(moduleTarget);
        sb.append(", moduleType=").append(moduleType);
        sb.append(", moduleStatus=").append(moduleStatus);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}