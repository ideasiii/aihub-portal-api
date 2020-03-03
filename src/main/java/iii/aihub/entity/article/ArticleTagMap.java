package iii.aihub.entity.article;

import jooq.generated.elastic.crawler.aihub.tables.records.ArticleTagMapRecord;

import java.util.Date;

public class ArticleTagMap {

    public Integer id;

    public String dataId;

    public String tagId;

    public Date created;

    public Date updated;

    public ArticleTagMap() {
    }

    public ArticleTagMap(ArticleTagMapRecord r) {
        this.dataId = r.getDataId();
        this.tagId = r.getDataId();
        this.id = r.getId();
        this.created = r.getCreated();
        this.updated = r.getUpdated();
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
