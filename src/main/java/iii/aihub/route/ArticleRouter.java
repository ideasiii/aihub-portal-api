package iii.aihub.route;

import iii.aihub.route.processor.article.*;
import org.apache.camel.DynamicRouter;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleRouter extends BaseRouter {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AddArticleProcessor addArticleProcessor;

    @Autowired
    ModifyArticleProcessor modifyArticleProcessor;

    @Autowired
    ListAllArticleProcessor listAllArticleProcessor;

    @Autowired
    ListArticleProcessor listArticleProcessor;

    @Autowired
    DeleteArticleProcessor deleteArticleProcessor;

    @Autowired
    ArticleGetTagsProcessor articleGetTagsProcessor;

    @Autowired
    ListMoreArticleProcessor listMoreArticleProcessor;

    @Autowired
    FromTagListArticleProcessor fromTagListArticleProcessor;

    public ArticleRouter() {
        super();
    }

    @Override
    public void configure() throws Exception {


        rest("/article")
                .enableCORS(true)
                .consumes("application/json")
                .produces("application/json")
            .post("/add")
                .description("新增文章")
                .id("add_article_endpoint")
                .to("direct:add_article")
            .post("/modify")
                .description("修改文章")
                .id("modify_article_endpoint")
                .to("direct:modify_article")
            .post("/delete")
                .description("(軟)刪除文章")
                .id("delete_article_endpoint")
                .to("direct:delete_article")
            .post("/list_all")
                .description("列出所有文章")
                .id("list_all_article_endpoint")
                .to("direct:list_all_article")
            .post("/list")
                .description("列出文章")
                .id("list_article_endpoint")
                .to("direct:list_article")
            .post("/list_more")
                .description("列出多篇文章")
                .id("list_more_article_endpoint")
                .to("direct:list_more_article")
            .post("/get_tags")
                .description("文章的tags")
                .id("get_tags_endpoint")
                .to("direct:article_get_tags")
            .post("/from_tag")
                .description("從tag取得文章")
                .id("list_from_tags_endpoint")
                .to("direct:list_from_tags")
        ;

        //-- 新增文章
        from("direct:add_article")
                .routeId("add_article")
                .process(addArticleProcessor)
        ;

        //-- 修改文章
        from("direct:modify_article")
                .routeId("modify_article")
                .process(modifyArticleProcessor)
        ;

        //-- 列出所有文章
        from("direct:list_all_article")
                .routeId("list_all_article")
                .process(listAllArticleProcessor)
        ;

        //-- 列出文章
        from("direct:list_article")
                .routeId("list_article")
                .process(listArticleProcessor)
        ;

        //-- 列出多篇文章
        from("direct:list_more_article")
                .routeId("list_more_article")
                .process(listMoreArticleProcessor)
        ;

        //-- (軟)刪除文章
        from("direct:delete_article")
                .routeId("delete_article")
                .process(deleteArticleProcessor)
        ;

        //-- 文章的tags
        from("direct:article_get_tags")
                .routeId("article_get_tags")
                .process(articleGetTagsProcessor)
        ;

        //-- 從tag取得文章
        from("direct:list_from_tags")
                .routeId("list_from_tags")
                .process(fromTagListArticleProcessor)
        ;
    }
}
