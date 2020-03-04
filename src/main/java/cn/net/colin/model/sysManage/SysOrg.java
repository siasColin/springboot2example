package cn.net.colin.model.sysManage;

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
     * 获取 主键ID sys_org.id
     * @return 主键ID
     */
    public final Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_org.id
     * @param id 主键ID
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 关联地区表 sys_org.area_code
     * @return 关联地区表
     */
    public final String getAreaCode() {
        return areaCode;
    }

    /** 
     * 设置 关联地区表 sys_org.area_code
     * @param areaCode 关联地区表
     */
    public final void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /** 
     * 获取 机构名称 sys_org.org_name
     * @return 机构名称
     */
    public final String getOrgName() {
        return orgName;
    }

    /** 
     * 设置 机构名称 sys_org.org_name
     * @param orgName 机构名称
     */
    public final void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    /** 
     * 获取 机构编码 sys_org.org_code
     * @return 机构编码
     */
    public final String getOrgCode() {
        return orgCode;
    }

    /** 
     * 设置 机构编码 sys_org.org_code
     * @param orgCode 机构编码
     */
    public final void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    /** 
     * 获取 父级机构编码 sys_org.parent_code
     * @return 父级机构编码
     */
    public final String getParentCode() {
        return parentCode;
    }

    /** 
     * 设置 父级机构编码 sys_org.parent_code
     * @param parentCode 父级机构编码
     */
    public final void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /** 
     * 获取 关联行业表（备用） sys_org.industryid
     * @return 关联行业表（备用）
     */
    public final Long getIndustryid() {
        return industryid;
    }

    /** 
     * 设置 关联行业表（备用） sys_org.industryid
     * @param industryid 关联行业表（备用）
     */
    public final void setIndustryid(Long industryid) {
        this.industryid = industryid;
    }

    /** 
     * 获取 机构地址 sys_org.org_address
     * @return 机构地址
     */
    public final String getOrgAddress() {
        return orgAddress;
    }

    /** 
     * 设置 机构地址 sys_org.org_address
     * @param orgAddress 机构地址
     */
    public final void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress == null ? null : orgAddress.trim();
    }

    /** 
     * 获取 机构logo sys_org.org_logo
     * @return 机构logo
     */
    public final String getOrgLogo() {
        return orgLogo;
    }

    /** 
     * 设置 机构logo sys_org.org_logo
     * @param orgLogo 机构logo
     */
    public final void setOrgLogo(String orgLogo) {
        this.orgLogo = orgLogo == null ? null : orgLogo.trim();
    }

    /** 
     * 获取 创建人 sys_org.create_user
     * @return 创建人
     */
    public final String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_org.create_user
     * @param createUser 创建人
     */
    public final void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_org.create_time
     * @return 创建时间
     */
    public final Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_org.create_time
     * @param createTime 创建时间
     */
    public final void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 排序字段 sys_org.sort_num
     * @return 排序字段
     */
    public final Integer getSortNum() {
        return sortNum;
    }

    /** 
     * 设置 排序字段 sys_org.sort_num
     * @param sortNum 排序字段
     */
    public final void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public final String toString() {
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