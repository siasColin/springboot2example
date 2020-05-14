package cn.net.colin.service.articleManage;

import cn.net.colin.model.articleManage.ArticleLikes;
import cn.net.colin.model.articleManage.ArticleLikesCriteria;

import java.util.List;

public interface IArticleLikesService {
    long countByExample(ArticleLikesCriteria example);

    ArticleLikes selectByPrimaryKey(Long id);

    List<ArticleLikes> selectByExample(ArticleLikesCriteria example);

    int deleteByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ArticleLikes record);

    int updateByPrimaryKey(ArticleLikes record);

    int deleteByExample(ArticleLikesCriteria example);

    int updateByExampleSelective(ArticleLikes record, ArticleLikesCriteria example);

    int updateByExample(ArticleLikes record, ArticleLikesCriteria example);

    int insert(ArticleLikes record);

    int insertSelective(ArticleLikes record);
}