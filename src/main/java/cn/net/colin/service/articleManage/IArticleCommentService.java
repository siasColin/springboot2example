package cn.net.colin.service.articleManage;

import cn.net.colin.model.articleManage.ArticleComment;
import cn.net.colin.model.articleManage.ArticleCommentCriteria;

import java.util.List;

public interface IArticleCommentService {
    long countByExample(ArticleCommentCriteria example);

    ArticleComment selectByPrimaryKey(Long id);

    List<ArticleComment> selectByExample(ArticleCommentCriteria example);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleComment record);

    int updateByPrimaryKey(ArticleComment record);

    int deleteByExample(ArticleCommentCriteria example);

    int updateByExampleSelective(ArticleComment record, ArticleCommentCriteria example);

    int updateByExample(ArticleComment record, ArticleCommentCriteria example);

    int insert(ArticleComment record);

    int insertSelective(ArticleComment record);
}