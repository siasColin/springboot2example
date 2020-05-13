package cn.net.colin.service.articleManage;

import cn.net.colin.model.articleManage.ArticleInfo;
import cn.net.colin.model.articleManage.ArticleInfoCriteria;

import java.util.List;

public interface IArticleInfoService {
    long countByExample(ArticleInfoCriteria example);

    ArticleInfo selectByPrimaryKey(Long id);

    List<ArticleInfo> selectByExample(ArticleInfoCriteria example);

    List<ArticleInfo> selectByExampleWithBLOBs(ArticleInfoCriteria example);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleInfo record);

    int updateByPrimaryKey(ArticleInfo record);

    int updateByPrimaryKeyWithBLOBs(ArticleInfo record);

    int deleteByExample(ArticleInfoCriteria example);

    int updateByExampleSelective(ArticleInfo record, ArticleInfoCriteria example);

    int updateByExample(ArticleInfo record, ArticleInfoCriteria example);

    int updateByExampleWithBLOBs(ArticleInfo record, ArticleInfoCriteria example);

    int insert(ArticleInfo record);

    int insertSelective(ArticleInfo record);
}