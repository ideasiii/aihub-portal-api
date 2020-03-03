package iii.aihub.route;

import iii.aihub.route.processor.tags.AddArticleTagProcessor;
import iii.aihub.route.processor.tags.AddTagProcessor;
import iii.aihub.route.processor.tags.ListTagProcessor;
import iii.aihub.route.processor.tags.TopNTagProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import javax.ws.rs.core.MediaType;

@Component
public class TagsRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AddArticleTagProcessor addArticleTagProcessor;

    @Autowired
    AddTagProcessor addTagProcessor;

    @Autowired
    ListTagProcessor listTagProcessor;

    @Autowired
    TopNTagProcessor topNTagProcessor;

    @Override
    public void configure() throws Exception {

        rest("/tag")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/add")
                .id("add_tag_endpoint")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .description("新增 tag")
                .to("direct:add_tag")
            .post("/add_article_tag")
                .id("add_article_tag_endpoint")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .description("新增 article tag")
                .to("direct:add_article_tag")
            .post("list")
                .id("list_tags_endpoint")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .description("列出所有tag")
                .to("direct:list_tag")
            .post("topN")
                .id("list_top_n_tags")
                .produces(MediaType.APPLICATION_JSON)
                .consumes(MediaType.APPLICATION_JSON)
                .description("列出 top N tag")
                .to("direct:list_topNtags")
        ;

        //-- 新增 article tag
        from("direct:add_article_tag")
                .routeId("add_article_tag")
                .process(addArticleTagProcessor)
                ;

        //-- 新增 tag
        from("direct:add_tag")
                .routeId("add_tag")
                .process(addTagProcessor)
                ;

        //-- 列出所有tag
        from("direct:list_tag")
                .routeId("list_tag")
                .process(listTagProcessor)
                ;

        //-- 列出 top N tag
        from("direct:list_topNtags")
                .routeId("list_topNtags")
                .process(topNTagProcessor)
                ;

    }
}
