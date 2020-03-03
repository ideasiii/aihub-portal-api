/*
 * This file is generated by jOOQ.
 */
package jooq.generated.elastic.crawler.aihub.tables.records;


import javax.annotation.Generated;

import jooq.generated.elastic.crawler.aihub.tables.ArticleSourceRef;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record4;
import org.jooq.Row4;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * 文章資料參考來源
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class ArticleSourceRefRecord extends UpdatableRecordImpl<ArticleSourceRefRecord> implements Record4<Integer, String, String, Integer> {

    private static final long serialVersionUID = -873731508;

    /**
     * Setter for <code>aihub.article_source_ref.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>aihub.article_source_ref.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>aihub.article_source_ref.name</code>. 來源名稱
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>aihub.article_source_ref.name</code>. 來源名稱
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>aihub.article_source_ref.url</code>. 來源url
     */
    public void setUrl(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>aihub.article_source_ref.url</code>. 來源url
     */
    public String getUrl() {
        return (String) get(2);
    }

    /**
     * Setter for <code>aihub.article_source_ref.article_id</code>.
     */
    public void setArticleId(Integer value) {
        set(3, value);
    }

    /**
     * Getter for <code>aihub.article_source_ref.article_id</code>.
     */
    public Integer getArticleId() {
        return (Integer) get(3);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record4 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, String, Integer> fieldsRow() {
        return (Row4) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row4<Integer, String, String, Integer> valuesRow() {
        return (Row4) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return ArticleSourceRef.ARTICLE_SOURCE_REF.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return ArticleSourceRef.ARTICLE_SOURCE_REF.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return ArticleSourceRef.ARTICLE_SOURCE_REF.URL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field4() {
        return ArticleSourceRef.ARTICLE_SOURCE_REF.ARTICLE_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component4() {
        return getArticleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getUrl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value4() {
        return getArticleId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleSourceRefRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleSourceRefRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleSourceRefRecord value3(String value) {
        setUrl(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleSourceRefRecord value4(Integer value) {
        setArticleId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ArticleSourceRefRecord values(Integer value1, String value2, String value3, Integer value4) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached ArticleSourceRefRecord
     */
    public ArticleSourceRefRecord() {
        super(ArticleSourceRef.ARTICLE_SOURCE_REF);
    }

    /**
     * Create a detached, initialised ArticleSourceRefRecord
     */
    public ArticleSourceRefRecord(Integer id, String name, String url, Integer articleId) {
        super(ArticleSourceRef.ARTICLE_SOURCE_REF);

        set(0, id);
        set(1, name);
        set(2, url);
        set(3, articleId);
    }
}
