package cn.net.colin.service.articleManage.impl;

import cn.net.colin.mapper.articleManage.ArticleTypeMapper;
import cn.net.colin.model.articleManage.ArticleType;
import cn.net.colin.model.articleManage.ArticleTypeCriteria;
import cn.net.colin.service.articleManage.IArticleTypeService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleTypeServiceImpl implements IArticleTypeService {
    @Autowired
    private ArticleTypeMapper articleTypeMapper;

    private static final Logger logger = LoggerFactory.getLogger(ArticleTypeServiceImpl.class);

    public long countByExample(ArticleTypeCriteria example) {
        long count = this.articleTypeMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ArticleType selectByPrimaryKey(Long id) {
        return this.articleTypeMapper.selectByPrimaryKey(id);
    }

    public List<ArticleType> selectByExample(ArticleTypeCriteria example) {
        return this.articleTypeMapper.selectByExample(example);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.articleTypeMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ArticleType record) {
        return this.articleTypeMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ArticleType record) {
        return this.articleTypeMapper.updateByPrimaryKey(record);
    }

    public int deleteByExample(ArticleTypeCriteria example) {
        return this.articleTypeMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(ArticleType record, ArticleTypeCriteria example) {
        return this.articleTypeMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(ArticleType record, ArticleTypeCriteria example) {
        return this.articleTypeMapper.updateByExample(record, example);
    }

    public int insert(ArticleType record) {
        return this.articleTypeMapper.insert(record);
    }

    public int insertSelective(ArticleType record) {
        return this.articleTypeMapper.insertSelective(record);
    }
}