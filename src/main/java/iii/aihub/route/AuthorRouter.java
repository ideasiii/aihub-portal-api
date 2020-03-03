package iii.aihub.route;

import iii.aihub.route.processor.author.AddAuthorProcessor;
import iii.aihub.route.processor.author.ListAuthorProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class AuthorRouter extends BaseRouter {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ListAuthorProcessor listAuthorProcessor;

    @Autowired
    AddAuthorProcessor addAuthorProcessor;

    public AuthorRouter() {
        super();
    }

    @Override
    public void configure() throws Exception {

        rest("/author")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/list")
                .id("list_author_endpoint")
                .description("列出作者")
                .to("direct:list_author")
            .post("/add")
                .id("add_author_endpoint")
                .description("新增作者")
                .to("direct:add_author")
                ;

        from("direct:list_author")
                .routeId("list_author")
                .process(listAuthorProcessor)
                ;

        from("direct:add_author")
                .routeId("add_author")
                .process(addAuthorProcessor)
        ;
    }
}
