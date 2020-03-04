package cn.net.colin.model.sysManage;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** 
 * 地区表 sys_area
 * @author sxf
 * date:2020/03/04 17:48
 */
public class SysArea implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -1187653594684529530L;

    /** 
     * 主键ID
     */ 
    private Long id;

    /** 
     * 地区名称
     */ 
    private String areaName;

    /** 
     * 地区编码
     */ 
    private String areaCode;

    /** 
     * 地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村)
     */ 
    private Integer areaLevel;

    /** 
     * 父地区编码
     */ 
    private String parentCode;

    /** 
     * 经度
     */ 
    private BigDecimal longitude;

    /** 
     * 纬度
     */ 
    private BigDecimal latitude;

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
     * 获取 主键ID sys_area.id
     * @return 主键ID
     */
    public final Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID sys_area.id
     * @param id 主键ID
     */
    public final void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 地区名称 sys_area.area_name
     * @return 地区名称
     */
    public final String getAreaName() {
        return areaName;
    }

    /** 
     * 设置 地区名称 sys_area.area_name
     * @param areaName 地区名称
     */
    public final void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    /** 
     * 获取 地区编码 sys_area.area_code
     * @return 地区编码
     */
    public final String getAreaCode() {
        return areaCode;
    }

    /** 
     * 设置 地区编码 sys_area.area_code
     * @param areaCode 地区编码
     */
    public final void setAreaCode(String areaCode) {
        this.areaCode = areaCode == null ? null : areaCode.trim();
    }

    /** 
     * 获取 地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村) sys_area.area_level
     * @return 地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村)
     */
    public final Integer getAreaLevel() {
        return areaLevel;
    }

    /** 
     * 设置 地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村) sys_area.area_level
     * @param areaLevel 地区等级(0 国家 1 省 2 直辖市 3 地级市 4 县 5 乡/镇 6 村)
     */
    public final void setAreaLevel(Integer areaLevel) {
        this.areaLevel = areaLevel;
    }

    /** 
     * 获取 父地区编码 sys_area.parent_code
     * @return 父地区编码
     */
    public final String getParentCode() {
        return parentCode;
    }

    /** 
     * 设置 父地区编码 sys_area.parent_code
     * @param parentCode 父地区编码
     */
    public final void setParentCode(String parentCode) {
        this.parentCode = parentCode == null ? null : parentCode.trim();
    }

    /** 
     * 获取 经度 sys_area.longitude
     * @return 经度
     */
    public final BigDecimal getLongitude() {
        return longitude;
    }

    /** 
     * 设置 经度 sys_area.longitude
     * @param longitude 经度
     */
    public final void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    /** 
     * 获取 纬度 sys_area.latitude
     * @return 纬度
     */
    public final BigDecimal getLatitude() {
        return latitude;
    }

    /** 
     * 设置 纬度 sys_area.latitude
     * @param latitude 纬度
     */
    public final void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /** 
     * 获取 创建人 sys_area.create_user
     * @return 创建人
     */
    public final String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 sys_area.create_user
     * @param createUser 创建人
     */
    public final void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 sys_area.create_time
     * @return 创建时间
     */
    public final Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 sys_area.create_time
     * @param createTime 创建时间
     */
    public final void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 排序字段 sys_area.sort_num
     * @return 排序字段
     */
    public final Integer getSortNum() {
        return sortNum;
    }

    /** 
     * 设置 排序字段 sys_area.sort_num
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
        sb.append(", areaName=").append(areaName);
        sb.append(", areaCode=").append(areaCode);
        sb.append(", areaLevel=").append(areaLevel);
        sb.append(", parentCode=").append(parentCode);
        sb.append(", longitude=").append(longitude);
        sb.append(", latitude=").append(latitude);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", sortNum=").append(sortNum);
        sb.append("]");
        return sb.toString();
    }
}