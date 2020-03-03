package iii.aihub.entity.article;

public class ArticleSolutions {

    public String articleId;

    public String solutionId;

    public ArticleSolutions() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
