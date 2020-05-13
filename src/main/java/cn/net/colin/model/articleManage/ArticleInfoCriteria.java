package cn.net.colin.model.articleManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sxf code generator
 * date:2020/05/06 13:47
 */
public class ArticleInfoCriteria {
    /** 
     * 排序字段
    */
    protected String orderByClause;

    /** 
     * 过滤重复数据
    */
    protected boolean distinct;

    /** 
     * 查询条件
    */
    protected List<Criteria> oredCriteria;

    /** 
     * 构造查询条件
     */
    public ArticleInfoCriteria() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /** 
     * 设置排序字段
     * @param orderByClause 排序字段
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /** 
     * 获取排序字段
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /** 
     * 设置过滤重复数据
     * @param distinct 是否过滤重复数据
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /** 
     * 是否过滤重复数据
     */
    public boolean isDistinct() {
        return distinct;
    }

    /** 
     * 获取当前的查询条件实例
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /** 
     * 增加或者的查询条件,用于构建或者查询
     * @param criteria 过滤条件实例
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /** 
     * 创建一个新的或者查询条件
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /** 
     * 创建一个查询条件
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /** 
     * 内部构建查询条件对象
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /** 
     * 清除查询条件
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * 文章信息表article_info
     */
    protected abstract static class BaseCriteria {
        protected List<Criterion> criteria;

        protected BaseCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNull() {
            addCriterion("user_id is null");
            return (Criteria) this;
        }

        public Criteria andUserIdIsNotNull() {
            addCriterion("user_id is not null");
            return (Criteria) this;
        }

        public Criteria andUserIdEqualTo(Long value) {
            addCriterion("user_id =", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotEqualTo(Long value) {
            addCriterion("user_id <>", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThan(Long value) {
            addCriterion("user_id >", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdGreaterThanOrEqualTo(Long value) {
            addCriterion("user_id >=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThan(Long value) {
            addCriterion("user_id <", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdLessThanOrEqualTo(Long value) {
            addCriterion("user_id <=", value, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdIn(List<Long> values) {
            addCriterion("user_id in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotIn(List<Long> values) {
            addCriterion("user_id not in", values, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdBetween(Long value1, Long value2) {
            addCriterion("user_id between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andUserIdNotBetween(Long value1, Long value2) {
            addCriterion("user_id not between", value1, value2, "userId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Long value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Long value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Long value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Long value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Long value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Long value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Long> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Long> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Long value1, Long value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Long value1, Long value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIsNull() {
            addCriterion("info_title is null");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIsNotNull() {
            addCriterion("info_title is not null");
            return (Criteria) this;
        }

        public Criteria andInfoTitleEqualTo(String value) {
            addCriterion("info_title =", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotEqualTo(String value) {
            addCriterion("info_title <>", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleGreaterThan(String value) {
            addCriterion("info_title >", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleGreaterThanOrEqualTo(String value) {
            addCriterion("info_title >=", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLessThan(String value) {
            addCriterion("info_title <", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLessThanOrEqualTo(String value) {
            addCriterion("info_title <=", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleLike(String value) {
            addCriterion("info_title like", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotLike(String value) {
            addCriterion("info_title not like", value, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleIn(List<String> values) {
            addCriterion("info_title in", values, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotIn(List<String> values) {
            addCriterion("info_title not in", values, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleBetween(String value1, String value2) {
            addCriterion("info_title between", value1, value2, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoTitleNotBetween(String value1, String value2) {
            addCriterion("info_title not between", value1, value2, "infoTitle");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractIsNull() {
            addCriterion("info_abstract is null");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractIsNotNull() {
            addCriterion("info_abstract is not null");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractEqualTo(String value) {
            addCriterion("info_abstract =", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractNotEqualTo(String value) {
            addCriterion("info_abstract <>", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractGreaterThan(String value) {
            addCriterion("info_abstract >", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractGreaterThanOrEqualTo(String value) {
            addCriterion("info_abstract >=", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractLessThan(String value) {
            addCriterion("info_abstract <", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractLessThanOrEqualTo(String value) {
            addCriterion("info_abstract <=", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractLike(String value) {
            addCriterion("info_abstract like", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractNotLike(String value) {
            addCriterion("info_abstract not like", value, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractIn(List<String> values) {
            addCriterion("info_abstract in", values, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractNotIn(List<String> values) {
            addCriterion("info_abstract not in", values, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractBetween(String value1, String value2) {
            addCriterion("info_abstract between", value1, value2, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoAbstractNotBetween(String value1, String value2) {
            addCriterion("info_abstract not between", value1, value2, "infoAbstract");
            return (Criteria) this;
        }

        public Criteria andInfoTagsIsNull() {
            addCriterion("info_tags is null");
            return (Criteria) this;
        }

        public Criteria andInfoTagsIsNotNull() {
            addCriterion("info_tags is not null");
            return (Criteria) this;
        }

        public Criteria andInfoTagsEqualTo(String value) {
            addCriterion("info_tags =", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsNotEqualTo(String value) {
            addCriterion("info_tags <>", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsGreaterThan(String value) {
            addCriterion("info_tags >", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsGreaterThanOrEqualTo(String value) {
            addCriterion("info_tags >=", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsLessThan(String value) {
            addCriterion("info_tags <", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsLessThanOrEqualTo(String value) {
            addCriterion("info_tags <=", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsLike(String value) {
            addCriterion("info_tags like", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsNotLike(String value) {
            addCriterion("info_tags not like", value, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsIn(List<String> values) {
            addCriterion("info_tags in", values, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsNotIn(List<String> values) {
            addCriterion("info_tags not in", values, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsBetween(String value1, String value2) {
            addCriterion("info_tags between", value1, value2, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoTagsNotBetween(String value1, String value2) {
            addCriterion("info_tags not between", value1, value2, "infoTags");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsIsNull() {
            addCriterion("info_attachments is null");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsIsNotNull() {
            addCriterion("info_attachments is not null");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsEqualTo(String value) {
            addCriterion("info_attachments =", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsNotEqualTo(String value) {
            addCriterion("info_attachments <>", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsGreaterThan(String value) {
            addCriterion("info_attachments >", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsGreaterThanOrEqualTo(String value) {
            addCriterion("info_attachments >=", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsLessThan(String value) {
            addCriterion("info_attachments <", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsLessThanOrEqualTo(String value) {
            addCriterion("info_attachments <=", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsLike(String value) {
            addCriterion("info_attachments like", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsNotLike(String value) {
            addCriterion("info_attachments not like", value, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsIn(List<String> values) {
            addCriterion("info_attachments in", values, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsNotIn(List<String> values) {
            addCriterion("info_attachments not in", values, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsBetween(String value1, String value2) {
            addCriterion("info_attachments between", value1, value2, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAttachmentsNotBetween(String value1, String value2) {
            addCriterion("info_attachments not between", value1, value2, "infoAttachments");
            return (Criteria) this;
        }

        public Criteria andInfoAmountIsNull() {
            addCriterion("info_amount is null");
            return (Criteria) this;
        }

        public Criteria andInfoAmountIsNotNull() {
            addCriterion("info_amount is not null");
            return (Criteria) this;
        }

        public Criteria andInfoAmountEqualTo(Integer value) {
            addCriterion("info_amount =", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountNotEqualTo(Integer value) {
            addCriterion("info_amount <>", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountGreaterThan(Integer value) {
            addCriterion("info_amount >", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountGreaterThanOrEqualTo(Integer value) {
            addCriterion("info_amount >=", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountLessThan(Integer value) {
            addCriterion("info_amount <", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountLessThanOrEqualTo(Integer value) {
            addCriterion("info_amount <=", value, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountIn(List<Integer> values) {
            addCriterion("info_amount in", values, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountNotIn(List<Integer> values) {
            addCriterion("info_amount not in", values, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountBetween(Integer value1, Integer value2) {
            addCriterion("info_amount between", value1, value2, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoAmountNotBetween(Integer value1, Integer value2) {
            addCriterion("info_amount not between", value1, value2, "infoAmount");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgIsNull() {
            addCriterion("info_coverimg is null");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgIsNotNull() {
            addCriterion("info_coverimg is not null");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgEqualTo(String value) {
            addCriterion("info_coverimg =", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgNotEqualTo(String value) {
            addCriterion("info_coverimg <>", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgGreaterThan(String value) {
            addCriterion("info_coverimg >", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgGreaterThanOrEqualTo(String value) {
            addCriterion("info_coverimg >=", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgLessThan(String value) {
            addCriterion("info_coverimg <", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgLessThanOrEqualTo(String value) {
            addCriterion("info_coverimg <=", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgLike(String value) {
            addCriterion("info_coverimg like", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgNotLike(String value) {
            addCriterion("info_coverimg not like", value, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgIn(List<String> values) {
            addCriterion("info_coverimg in", values, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgNotIn(List<String> values) {
            addCriterion("info_coverimg not in", values, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgBetween(String value1, String value2) {
            addCriterion("info_coverimg between", value1, value2, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoCoverimgNotBetween(String value1, String value2) {
            addCriterion("info_coverimg not between", value1, value2, "infoCoverimg");
            return (Criteria) this;
        }

        public Criteria andInfoOpenIsNull() {
            addCriterion("info_open is null");
            return (Criteria) this;
        }

        public Criteria andInfoOpenIsNotNull() {
            addCriterion("info_open is not null");
            return (Criteria) this;
        }

        public Criteria andInfoOpenEqualTo(Integer value) {
            addCriterion("info_open =", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenNotEqualTo(Integer value) {
            addCriterion("info_open <>", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenGreaterThan(Integer value) {
            addCriterion("info_open >", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenGreaterThanOrEqualTo(Integer value) {
            addCriterion("info_open >=", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenLessThan(Integer value) {
            addCriterion("info_open <", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenLessThanOrEqualTo(Integer value) {
            addCriterion("info_open <=", value, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenIn(List<Integer> values) {
            addCriterion("info_open in", values, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenNotIn(List<Integer> values) {
            addCriterion("info_open not in", values, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenBetween(Integer value1, Integer value2) {
            addCriterion("info_open between", value1, value2, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoOpenNotBetween(Integer value1, Integer value2) {
            addCriterion("info_open not between", value1, value2, "infoOpen");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentIsNull() {
            addCriterion("info_iscomment is null");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentIsNotNull() {
            addCriterion("info_iscomment is not null");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentEqualTo(Integer value) {
            addCriterion("info_iscomment =", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentNotEqualTo(Integer value) {
            addCriterion("info_iscomment <>", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentGreaterThan(Integer value) {
            addCriterion("info_iscomment >", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentGreaterThanOrEqualTo(Integer value) {
            addCriterion("info_iscomment >=", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentLessThan(Integer value) {
            addCriterion("info_iscomment <", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentLessThanOrEqualTo(Integer value) {
            addCriterion("info_iscomment <=", value, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentIn(List<Integer> values) {
            addCriterion("info_iscomment in", values, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentNotIn(List<Integer> values) {
            addCriterion("info_iscomment not in", values, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentBetween(Integer value1, Integer value2) {
            addCriterion("info_iscomment between", value1, value2, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andInfoIscommentNotBetween(Integer value1, Integer value2) {
            addCriterion("info_iscomment not between", value1, value2, "infoIscomment");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }
    }

    /**
     * 文章信息表article_info的映射实体
     */
    public static class Criteria extends BaseCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * 文章信息表article_info的动态SQL对象.
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}