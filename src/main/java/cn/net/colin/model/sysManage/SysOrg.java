package cn.net.colin.model.sysManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/** 
 * 机构表 sys_org
 * @author sxf
 * date:2020/03/04 17:48
 */
public class SysOrg implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -6038055450124236203L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 关联地区表
     */ 
    private String areaCode;

    /** 
     * 机构名称
     */ 
    private String orgName;

    /** 
     * 机构编码
     */ 
    private String orgCode;

    /** 
     * 父级机构编码
     */ 
    private String parentCode;

    /** 
     * 关联行业表（备用）
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long industryid;

    /** 
     * 机构地址
     */ 
    private String orgAddress;

    /** 
     * 机构logo
     */ 
    private String orgLogo;

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
     * 存储所属地区信息
     */
    private SysArea sysArea;


    /**
     * 非持久化属性 start
     */
    private String parentName;
    private String areaName;
    /**
     * 非持久化属性 end
     */

    /** 
     * 获取 主键ID sys_org.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_org.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 关联地区表 sys_org.area_code
     * @return 关联地区表
     */
    public String getAreaCode() {
        return areaCode;
    }

    /** 
     * 设置 关联地区表 sys_org.area_code
     * @param areaCode 关联地区表
     */
    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /** 
     * 获取 机构名称 sys_org.org_name
     * @return 机构名称
     */
    public String getOrgName() {
        return orgName;
    }

    /** 
     * 设置 机构名称 sys_org.org_name
     * @param orgName 机构名称
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /** 
     * 获取 机构编码 sys_org.org_code
     * @return 机构编码
     */
    public String getOrgCode() {
        return orgCode;
    }

    /** 
     * 设置 机构编码 sys_org.org_code
     * @param orgCode 机构编码
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /** 
     * 获取 父级机构编码 sys_org.parent_code
     * @return 父级机构编码
     */
    public String getParentCode() {
        return parentCode;
    }

    /** 
     * 设置 父级机构编码 sys_org.parent_code
     * @param parentCode 父级机构编码
     */
    public void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /** 
     * 获取 关联行业表（备用） sys_org.industryid
     * @return 关联行业表（备用）
     */
    public Long getIndustryid() {
        return industryid;
    }

    /** 
     * 设置 关联行业表（备用） sys_org.industryid
     * @param industryid 关联行业表（备用）
     */
    public void setIndustryid(Long industryid) {
        this.industryid = industryid;
    }

    /** 
     * 获取 机构地址 sys_org.org_address
     * @return 机构地址
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    /** 
     * 设置 机构地址 sys_org.org_address
     * @param orgAddress 机构地址
     */
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    /** 
     * 获取 机构logo sys_org.org_logo
     * @return 机构logo
     */
    public String getOrgLogo() {
        return orgLogo;
    }

    /** 
     * 设置 机构logo sys_org.org_logo
     * @param orgLogo 机构logo
     */
    public void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo == null ? null : orgLogo.trim();
    }

    /** 
     * 获取 创建人 sys_org.create_user
     * @return 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_org.create_user
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_org.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_org.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 排序字段 sys_org.sort_num
     * @return 排序字段
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /** 
     * 设置 排序字段 sys_org.sort_num
     * @param sortNum 排序字段
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    public SysArea getSysArea() {
        return sysArea;
    }

    public void setSysArea(SysArea sysArea) {
        this.sysArea = sysArea;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
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
        sb.append(", areaCode=").append(areaCode);
        sb.append(", orgName=").append(orgName);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", industryid=").append(industryid);
        sb.append(", orgAddress=").append(orgAddress);
        sb.append(", orgLogo=").append(orgLogo);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", sortNum=").append(sortNum);
        sb.append("]");
        return sb.toString();
    }
}