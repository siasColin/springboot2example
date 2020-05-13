package cn.net.colin.service.articleManage.impl;

import cn.net.colin.mapper.articleManage.ArticleCommentMapper;
import cn.net.colin.model.articleManage.ArticleComment;
import cn.net.colin.model.articleManage.ArticleCommentCriteria;
import cn.net.colin.service.articleManage.IArticleCommentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleCommentServiceImpl implements IArticleCommentService {
    @Autowired
    private ArticleCommentMapper articleCommentMapper;

    private static final Logger logger = LoggerFactory.getLogger(ArticleCommentServiceImpl.class);

    public long countByExample(ArticleCommentCriteria example) {
        long count = this.articleCommentMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ArticleComment selectByPrimaryKey(Long id) {
        return this.articleCommentMapper.selectByPrimaryKey(id);
    }

    public List<ArticleComment> selectByExample(ArticleCommentCriteria example) {
        return this.articleCommentMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.articleCommentMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ArticleComment record) {
        return this.articleCommentMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ArticleComment record) {
        return this.articleCommentMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(ArticleCommentCriteria example) {
        return this.articleCommentMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(ArticleComment record, ArticleCommentCriteria example) {
        return this.articleCommentMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(ArticleComment record, ArticleCommentCriteria example) {
        return this.articleCommentMapper.updateByExample(record, example);
    }

    public int insert(ArticleComment record) {
        return this.articleCommentMapper.insert(record);
    }

    public int insertSelective(ArticleComment record) {
        return this.articleCommentMapper.insertSelective(record);
    }
}