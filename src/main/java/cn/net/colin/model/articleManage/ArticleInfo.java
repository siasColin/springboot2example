package cn.net.colin.model.articleManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import cn.net.colin.model.sysManage.SysUser;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;

/** 
 * 文章信息表 article_info
 * @author sxf code generator
 * date:2020/05/06 13:47
 */
public class ArticleInfo implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -2126770783595742547L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 发布用户ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long userId;

    /** 
     * 分类id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long typeId;

    /** 
     * 标题
     */ 
    private String infoTitle;

    /** 
     * 摘要
     */ 
    private String infoAbstract;

    /** 
     * 标签（多个逗号分隔）
     */ 
    private String infoTags;

    /** 
     * 附件
     */ 
    private String infoAttachments;

    /**
     * 附件名
     */
    private String infoFilename;

    /** 
     * 阅读量  默认：0
     */ 
    private Integer infoAmount;

    /** 
     * 封面图片
     */ 
    private String infoCoverimg;

    /** 
     * 是否公开 0仅自己 1公开  默认：1
     */ 
    private Integer infoOpen;

    /** 
     * 是否开启评论 0关闭 1开启  默认：1
     */ 
    private Integer infoIscomment;

    /** 
     * 创建时间
     */ 
    private Date createTime;

    /** 
     * 最后一次编辑时间
     */ 
    private Date updateTime;

    /** 
     * 内容
     */ 
    private String infoContent;

    /**
     * 文章分类实体
     */
    private ArticleType articleType;

    /**
     * 文章发布者实体
     */
    private SysUser publishUser;

    /** 
     * 获取 主键ID article_info.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID article_info.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 发布用户ID article_info.user_id
     * @return 发布用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /** 
     * 设置 发布用户ID article_info.user_id
     * @param userId 发布用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 
     * 获取 分类id article_info.type_id
     * @return 分类id
     */
    public Long getTypeId() {
        return typeId;
    }

    /** 
     * 设置 分类id article_info.type_id
     * @param typeId 分类id
     */
    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    /** 
     * 获取 标题 article_info.info_title
     * @return 标题
     */
    public String getInfoTitle() {
        return infoTitle;
    }

    /** 
     * 设置 标题 article_info.info_title
     * @param infoTitle 标题
     */
    public void setInfoTitle(String infoTitle) {
        this.infoTitle = infoTitle == null ? null : infoTitle.trim();
    }

    /** 
     * 获取 摘要 article_info.info_abstract
     * @return 摘要
     */
    public String getInfoAbstract() {
        return infoAbstract;
    }

    /** 
     * 设置 摘要 article_info.info_abstract
     * @param infoAbstract 摘要
     */
    public void setInfoAbstract(String infoAbstract) {
        this.infoAbstract = infoAbstract == null ? null : infoAbstract.trim();
    }

    /** 
     * 获取 标签（多个逗号分隔） article_info.info_tags
     * @return 标签（多个逗号分隔）
     */
    public String getInfoTags() {
        return infoTags;
    }

    /** 
     * 设置 标签（多个逗号分隔） article_info.info_tags
     * @param infoTags 标签（多个逗号分隔）
     */
    public void setInfoTags(String infoTags) {
        this.infoTags = infoTags == null ? null : infoTags.trim();
    }

    /** 
     * 获取 附件 article_info.info_attachments
     * @return 附件
     */
    public String getInfoAttachments() {
        return infoAttachments;
    }

    /** 
     * 设置 附件 article_info.info_attachments
     * @param infoAttachments 附件
     */
    public void setInfoAttachments(String infoAttachments) {
        this.infoAttachments = infoAttachments == null ? null : infoAttachments.trim();
    }

    public String getInfoFilename() {
        return infoFilename;
    }

    public void setInfoFilename(String infoFilename) {
        this.infoFilename = infoFilename;
    }

    /**
     * 获取 阅读量 article_info.info_amount
     * @return 阅读量
     */
    public Integer getInfoAmount() {
        return infoAmount;
    }

    /** 
     * 设置 阅读量 article_info.info_amount
     * @param infoAmount 阅读量
     */
    public void setInfoAmount(Integer infoAmount) {
        this.infoAmount = infoAmount;
    }

    /** 
     * 获取 封面图片 article_info.info_coverimg
     * @return 封面图片
     */
    public String getInfoCoverimg() {
        return infoCoverimg;
    }

    /** 
     * 设置 封面图片 article_info.info_coverimg
     * @param infoCoverimg 封面图片
     */
    public void setInfoCoverimg(String infoCoverimg) {
        this.infoCoverimg = infoCoverimg == null ? null : infoCoverimg.trim();
    }

    /** 
     * 获取 是否公开 0仅自己 1公开 article_info.info_open
     * @return 是否公开 0仅自己 1公开
     */
    public Integer getInfoOpen() {
        return infoOpen;
    }

    /** 
     * 设置 是否公开 0仅自己 1公开 article_info.info_open
     * @param infoOpen 是否公开 0仅自己 1公开
     */
    public void setInfoOpen(Integer infoOpen) {
        this.infoOpen = infoOpen;
    }

    /** 
     * 获取 是否开启评论 0关闭 1开启 article_info.info_iscomment
     * @return 是否开启评论 0关闭 1开启
     */
    public Integer getInfoIscomment() {
        return infoIscomment;
    }

    /** 
     * 设置 是否开启评论 0关闭 1开启 article_info.info_iscomment
     * @param infoIscomment 是否开启评论 0关闭 1开启
     */
    public void setInfoIscomment(Integer infoIscomment) {
        this.infoIscomment = infoIscomment;
    }

    /** 
     * 获取 创建时间 article_info.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 article_info.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 
     * 获取 最后一次编辑时间 article_info.update_time
     * @return 最后一次编辑时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /** 
     * 设置 最后一次编辑时间 article_info.update_time
     * @param updateTime 最后一次编辑时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** 
     * 获取 内容 article_info.info_content
     * @return 内容
     */
    public String getInfoContent() {
        return infoContent;
    }

    /** 
     * 设置 内容 article_info.info_content
     * @param infoContent 内容
     */
    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent == null ? null : infoContent.trim();
    }

    public ArticleType getArticleType() {
        return articleType;
    }

    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }

    public SysUser getPublishUser() {
        return publishUser;
    }

    public void setPublishUser(SysUser publishUser) {
        this.publishUser = publishUser;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", typeId=").append(typeId);
        sb.append(", infoTitle=").append(infoTitle);
        sb.append(", infoAbstract=").append(infoAbstract);
        sb.append(", infoTags=").append(infoTags);
        sb.append(", infoAttachments=").append(infoAttachments);
        sb.append(", infoAmount=").append(infoAmount);
        sb.append(", infoCoverimg=").append(infoCoverimg);
        sb.append(", infoOpen=").append(infoOpen);
        sb.append(", infoIscomment=").append(infoIscomment);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", infoContent=").append(infoContent);
        sb.append("]");
        return sb.toString();
    }
}