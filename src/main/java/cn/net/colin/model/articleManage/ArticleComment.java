package cn.net.colin.model.articleManage;

import cn.net.colin.common.helper.LongJsonDeserializer;
import cn.net.colin.common.helper.LongJsonSerializer;
import cn.net.colin.model.sysManage.SysUser;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/** 
 * 文章评论回复表 article_comment
 * @author sxf code generator
 * date:2020/05/06 13:47
 */
public class ArticleComment implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = -8741768522266931487L;

    /** 
     * 主键ID
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long id;

    /** 
     * 评论文章的主键ID  默认：-1
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long infoId;

    /** 
     * 父ID，被引用评论的ID，默认-1：表示是一级评论
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long parentId;

    /** 
     * 评论内容
     */ 
    private String commentContent;

    /** 
     * 评论时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date commentTime;

    /** 
     * 评论人用户id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long fromUserId;

    /** 
     * 被评论人用户id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    @JsonDeserialize(using = LongJsonDeserializer.class)
    private Long toUserId;

    /** 
     * 是否已读（1 是，0 否）  默认：0
     */ 
    private Integer readStatus;

    /**
     * 文章实体类
     */
    private ArticleInfo articleInfo;

    /**
     * 评论用户实体
     */
    private SysUser fromUser;

    /**
     * 被评论用户实体
     */
    private SysUser toUser;

    /**
     * 该条评论下的子评论
     */
    private List<ArticleComment> childList;

    /** 
     * 获取 主键ID article_comment.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID article_comment.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 评论文章的主键ID article_comment.info_id
     * @return 评论文章的主键ID
     */
    public Long getInfoId() {
        return infoId;
    }

    /** 
     * 设置 评论文章的主键ID article_comment.info_id
     * @param infoId 评论文章的主键ID
     */
    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    /** 
     * 获取 父ID，被引用评论的ID，默认-1：表示是一级评论 article_comment.parent_id
     * @return 父ID，被引用评论的ID，默认-1：表示是一级评论
     */
    public Long getParentId() {
        return parentId;
    }

    /** 
     * 设置 父ID，被引用评论的ID，默认-1：表示是一级评论 article_comment.parent_id
     * @param parentId 父ID，被引用评论的ID，默认-1：表示是一级评论
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /** 
     * 获取 评论内容 article_comment.comment_content
     * @return 评论内容
     */
    public String getCommentContent() {
        return commentContent;
    }

    /** 
     * 设置 评论内容 article_comment.comment_content
     * @param commentContent 评论内容
     */
    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent == null ? null : commentContent.trim();
    }

    /** 
     * 获取 评论时间 article_comment.comment_time
     * @return 评论时间
     */
    public Date getCommentTime() {
        return commentTime;
    }

    /** 
     * 设置 评论时间 article_comment.comment_time
     * @param commentTime 评论时间
     */
    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    /** 
     * 获取 评论人用户id article_comment.from_user_id
     * @return 评论人用户id
     */
    public Long getFromUserId() {
        return fromUserId;
    }

    /** 
     * 设置 评论人用户id article_comment.from_user_id
     * @param fromUserId 评论人用户id
     */
    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    /** 
     * 获取 被评论人用户id article_comment.to_user_id
     * @return 被评论人用户id
     */
    public Long getToUserId() {
        return toUserId;
    }

    /** 
     * 设置 被评论人用户id article_comment.to_user_id
     * @param toUserId 被评论人用户id
     */
    public void setToUserId(Long toUserId) {
        this.toUserId = toUserId;
    }

    /** 
     * 获取 是否已读（1 是，0 否） article_comment.read_status
     * @return 是否已读（1 是，0 否）
     */
    public Integer getReadStatus() {
        return readStatus;
    }

    /** 
     * 设置 是否已读（1 是，0 否） article_comment.read_status
     * @param readStatus 是否已读（1 是，0 否）
     */
    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public SysUser getFromUser() {
        return fromUser;
    }

    public void setFromUser(SysUser fromUser) {
        this.fromUser = fromUser;
    }

    public SysUser getToUser() {
        return toUser;
    }

    public void setToUser(SysUser toUser) {
        this.toUser = toUser;
    }

    public List<ArticleComment> getChildList() {
        return childList;
    }

    public void setChildList(List<ArticleComment> childList) {
        this.childList = childList;
    }

    public ArticleInfo getArticleInfo() {
        return articleInfo;
    }

    public void setArticleInfo(ArticleInfo articleInfo) {
        this.articleInfo = articleInfo;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append(", id=").append(id);
        sb.append(", infoId=").append(infoId);
        sb.append(", parentId=").append(parentId);
        sb.append(", commentContent=").append(commentContent);
        sb.append(", commentTime=").append(commentTime);
        sb.append(", fromUserId=").append(fromUserId);
        sb.append(", toUserId=").append(toUserId);
        sb.append(", readStatus=").append(readStatus);
        sb.append("]");
        return sb.toString();
    }
}