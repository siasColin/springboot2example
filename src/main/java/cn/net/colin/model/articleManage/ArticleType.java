package cn.net.colin.model.articleManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/** 
 * 文章分类表 article_type
 * @author sxf code generator
 * date:2020/05/06 13:47
 */
public class ArticleType implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -6543739109218150278L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 文章类型编码
     */ 
    private String typeCode;

    /** 
     * 文章类型名
     */ 
    private String typeName;

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
     * 获取 主键ID article_type.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID article_type.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 文章类型编码 article_type.type_code
     * @return 文章类型编码
     */
    public String getTypeCode() {
        return typeCode;
    }

    /** 
     * 设置 文章类型编码 article_type.type_code
     * @param typeCode 文章类型编码
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode == null ? null : typeCode.trim();
    }

    /** 
     * 获取 文章类型名 article_type.type_name
     * @return 文章类型名
     */
    public String getTypeName() {
        return typeName;
    }

    /** 
     * 设置 文章类型名 article_type.type_name
     * @param typeName 文章类型名
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    /** 
     * 获取 创建人 article_type.create_user
     * @return 创建人
     */
    public String getCreateUser() {
        return createUser;
    }

    /** 
     * 设置 创建人 article_type.create_user
     * @param createUser 创建人
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    /** 
     * 获取 创建时间 article_type.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 article_type.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 排序字段 article_type.sort_num
     * @return 排序字段
     */
    public Integer getSortNum() {
        return sortNum;
    }

    /** 
     * 设置 排序字段 article_type.sort_num
     * @param sortNum 排序字段
     */
    public void setSortNum(Integer sortNum) {
        this.sortNum = sortNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", typeCode=").append(typeCode);
        sb.append(", typeName=").append(typeName);
        sb.append(", createUser=").append(createUser);
        sb.append(", createTime=").append(createTime);
        sb.append(", sortNum=").append(sortNum);
        sb.append("]");
        return sb.toString();
    }
}