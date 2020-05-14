package cn.net.colin.model.articleManage;

import java.io.Serializable;
import java.util.Date;

/** 
 * 文章点赞表 article_likes
 * @author sxf code generator
 * date:2020/05/14 15:29
 */
public class ArticleLikes implements Serializable {
    /** 
     * 串行版本ID
    */
    private static final long serialVersionUID = 7113484359137831666L;

    /** 
     * 主键ID
     */ 
    private Long id;

    /** 
     * 用户ID
     */ 
    private Long userId;

    /** 
     * 文章ID  默认：-1
     */ 
    private Long infoId;

    /** 
     * 创建时间
     */ 
    private Date createTime;

    /** 
     * 获取 主键ID article_likes.id
     * @return 主键ID
     */
    public Long getId() {
        return id;
    }

    /** 
     * 设置 主键ID article_likes.id
     * @param id 主键ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * 获取 用户ID article_likes.user_id
     * @return 用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /** 
     * 设置 用户ID article_likes.user_id
     * @param userId 用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /** 
     * 获取 文章ID article_likes.info_id
     * @return 文章ID
     */
    public Long getInfoId() {
        return infoId;
    }

    /** 
     * 设置 文章ID article_likes.info_id
     * @param infoId 文章ID
     */
    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    /** 
     * 获取 创建时间 article_likes.create_time
     * @return 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /** 
     * 设置 创建时间 article_likes.create_time
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        sb.append(", infoId=").append(infoId);
        sb.append(", createTime=").append(createTime);
        sb.append("]");
        return sb.toString();
    }
}