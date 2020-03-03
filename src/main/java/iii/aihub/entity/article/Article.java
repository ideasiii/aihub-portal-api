package iii.aihub.entity.article;

import com.google.gson.annotations.SerializedName;
import iii.aihub.entity.solution.Solution;
import iii.aihub.entity.tag.Tag;
import iii.aihub.utils.DateTimeUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleRecord;
import org.joda.time.DateTime;

import java.util.Date;
import java.util.List;

public class Article {

    @SerializedName("data_id")
    public String dataId;

    @SerializedName("title")
    public String title;

    @SerializedName("content")
    public String content;

    @SerializedName("short_content")
    public String shortContent;

    @SerializedName("url")
    public String url;

    @SerializedName("author")
    public String author;

    @SerializedName("author_id")
    public Integer authorId;

    @SerializedName("publish_datetime")
    public Date publishDatetime;

    @SerializedName("created")
    public Date created;

    @SerializedName("updated")
    public Date updated;

    @SerializedName("member_id")
    public String memberId;

    @SerializedName("is_delete")
    public String isDelete;

    @SerializedName("is_display")
    public String isDisplay;

    @SerializedName("img_url")
    public String imgUrl;

    @SerializedName("type")
    public String type;

    public List<Tag> tagList;

    public List<Solution> solutionList;

    public Article() {
    }

    public Article(ArticleRecord r) {
        this.author = r.getAuthor();
        this.content = r.getContent();
        this.created = new java.sql.Date(r.getCreated().getTime());
        this.dataId = r.getDataId();
        this.title = r.getTitle();
        this.updated = new java.sql.Date(r.getUpdated().getTime());
        this.type = r.getType().getLiteral();
        this.isDisplay = r.getIsDisplay().getLiteral();
        this.imgUrl = r.getImgUrl();
        this.url = r.getUrl();
        this.publishDatetime = new java.sql.Date(r.getPublishDatetime().getTime());
        this.memberId = r.getMemberId();
        this.isDelete = r.getIsDelete().getLiteral();
        this.authorId = r.getAuthorId();
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public String getIsDisplay() {
        return isDisplay;
    }

    public void setIsDisplay(String isDisplay) {
        this.isDisplay = isDisplay;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getPublishDatetime() {
        return publishDatetime;
    }

    public void setPublishDatetime(Date publishDatetime) {
        this.publishDatetime = publishDatetime;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }
}
