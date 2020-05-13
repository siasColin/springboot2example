package cn.net.colin.service.articleManage;

import cn.net.colin.model.articleManage.ArticleType;
import cn.net.colin.model.articleManage.ArticleTypeCriteria;

import java.util.List;

public interface IArticleTypeService {
    long countByExample(ArticleTypeCriteria example);

    ArticleType selectByPrimaryKey(Long id);

    List<ArticleType> selectByExample(ArticleTypeCriteria example);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleType record);

    int updateByPrimaryKey(ArticleType record);

    int deleteByExample(ArticleTypeCriteria example);

    int updateByExampleSelective(ArticleType record, ArticleTypeCriteria example);

    int updateByExample(ArticleType record, ArticleTypeCriteria example);

    int insert(ArticleType record);

    int insertSelective(ArticleType record);
}