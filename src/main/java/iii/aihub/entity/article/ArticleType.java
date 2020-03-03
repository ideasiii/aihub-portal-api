package iii.aihub.entity.article;

public enum ArticleType {

    ARTICLE,
    MARKET;

    public String getArticleType(){
        String type = null;
        switch (this.ordinal()){
            case 0:
                type = "article";
                break;
            case 1:
                type = "market";
                break;
        }
        return type;
    }

    public String getArticleTypeDescription(){
        String type = null;
        switch (this.ordinal()){
            case 0:
                type = "文章";
                break;
            case 1:
                type = "行銷";
                break;
        }
        return type;
    }
}
