package iii.aihub.entity.article;

import java.util.Date;
import java.util.List;

public class ArticleResult {

    public Integer totalCount;

    public Date queryDate;

    public List<Article> articleList;

    public ArticleResult() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
