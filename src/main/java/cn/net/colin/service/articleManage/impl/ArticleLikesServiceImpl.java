package cn.net.colin.service.articleManage.impl;

import cn.net.colin.mapper.articleManage.ArticleLikesMapper;
import cn.net.colin.model.articleManage.ArticleLikes;
import cn.net.colin.model.articleManage.ArticleLikesCriteria;
import cn.net.colin.service.articleManage.IArticleLikesService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikesServiceImpl implements IArticleLikesService {
    @Autowired
    private ArticleLikesMapper articleLikesMapper;

    private static final Logger logger = LoggerFactory.getLogger(ArticleLikesServiceImpl.class);

    public long countByExample(ArticleLikesCriteria example) {
        long count = this.articleLikesMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ArticleLikes selectByPrimaryKey(Long id) {
        return this.articleLikesMapper.selectByPrimaryKey(id);
    }

    public List<ArticleLikes> selectByExample(ArticleLikesCriteria example) {
        return this.articleLikesMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.articleLikesMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ArticleLikes record) {
        return this.articleLikesMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ArticleLikes record) {
        return this.articleLikesMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(ArticleLikesCriteria example) {
        return this.articleLikesMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(ArticleLikes record, ArticleLikesCriteria example) {
        return this.articleLikesMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(ArticleLikes record, ArticleLikesCriteria example) {
        return this.articleLikesMapper.updateByExample(record, example);
    }

    public int insert(ArticleLikes record) {
        return this.articleLikesMapper.insert(record);
    }

    public int insertSelective(ArticleLikes record) {
        return this.articleLikesMapper.insertSelective(record);
    }
}