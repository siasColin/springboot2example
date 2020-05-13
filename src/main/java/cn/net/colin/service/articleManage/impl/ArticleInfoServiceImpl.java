package cn.net.colin.service.articleManage.impl;

import cn.net.colin.mapper.articleManage.ArticleInfoMapper;
import cn.net.colin.model.articleManage.ArticleInfo;
import cn.net.colin.model.articleManage.ArticleInfoCriteria;
import cn.net.colin.service.articleManage.IArticleInfoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleInfoServiceImpl implements IArticleInfoService {
    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    private static final Logger logger = LoggerFactory.getLogger(ArticleInfoServiceImpl.class);

    public long countByExample(ArticleInfoCriteria example) {
        long count = this.articleInfoMapper.countByExample(example);
        logger.debug("count: {}", count);
        return count;
    }

    public ArticleInfo selectByPrimaryKey(Long id) {
        return this.articleInfoMapper.selectByPrimaryKey(id);
    }

    public List<ArticleInfo> selectByExample(ArticleInfoCriteria example) {
        return this.articleInfoMapper.selectByExample(example);
    }
    public List<ArticleInfo> selectByExampleWithBLOBs(ArticleInfoCriteria example) {
        return this.articleInfoMapper.selectByExampleWithBLOBs(example);
    }

    public int deleteByPrimaryKey(Long id) {
        return this.articleInfoMapper.deleteByPrimaryKey(id);
    }

    public int updateByPrimaryKeySelective(ArticleInfo record) {
        return this.articleInfoMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(ArticleInfo record) {
        return this.articleInfoMapper.updateByPrimaryKey(record);
    }

    public int updateByPrimaryKeyWithBLOBs(ArticleInfo record) {
        return this.articleInfoMapper.updateByPrimaryKeyWithBLOBs(record);
    }

    public int deleteByExample(ArticleInfoCriteria example) {
        return this.articleInfoMapper.deleteByExample(example);
    }

    public int updateByExampleSelective(ArticleInfo record, ArticleInfoCriteria example) {
        return this.articleInfoMapper.updateByExampleSelective(record, example);
    }

    public int updateByExample(ArticleInfo record, ArticleInfoCriteria example) {
        return this.articleInfoMapper.updateByExample(record, example);
    }

    public int updateByExampleWithBLOBs(ArticleInfo record,ArticleInfoCriteria example) {
        return this.articleInfoMapper.updateByExampleWithBLOBs(record,example);
    }

    public int insert(ArticleInfo record) {
        return this.articleInfoMapper.insert(record);
    }

    public int insertSelective(ArticleInfo record) {
        return this.articleInfoMapper.insertSelective(record);
    }
}