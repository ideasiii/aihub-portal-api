package iii.aihub.route;

import iii.aihub.route.processor.solution.*;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class SolutionRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AddSolutionProcessor addSolutionProcessor;

    @Autowired
    ListAllSolutionProcessor listAllSolutionProcessor;

    @Autowired
    ListSolutionProcessor listSolutionProcessor;

    @Autowired
    ModifySolutionProcessor modifySolutionProcessor;

    @Autowired
    DeleteSolutionProcessor deleteSolutionProcessor;

    @Override
    public void configure() throws Exception {

        rest("/solution")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/add")
                .id("add_solution_endpoint")
                .description("新增解決方案")
                .to("direct:add_solution")
            .post("/list")
                .id("list_solution_endpoint")
                .description("列出解決方案")
                .to("direct:list_solution")
            .post("/list_all")
                .id("list-all_solution_endpoint")
                .description("列出所有解決方案")
                .to("direct:list_all_solution")
            .post("/modify")
                .id("modify_solution_endpoint")
                .description("修改解決方案")
                .to("direct:modify_solution")
            .post("/delete")
                .id("delete_solution_endpoint")
                .description("刪除解決方案")
                .to("direct:delete_solution")
                ;

        //-- 新增解決方案
        from("direct:add_solution")
                .routeId("add_solution")
                .process(addSolutionProcessor)
                ;

        //-- 列出解決方案
        from("direct:list_solution")
                .routeId("list_solution")
                .process(listSolutionProcessor)
        ;

        //-- 列出所有解決方案
        from("direct:list_all_solution")
                .routeId("list_all_solution")
                .process(listAllSolutionProcessor)
        ;

        //-- 修改解決方案
        from("direct:modify_solution")
                .routeId("modify_solution")
                .process(modifySolutionProcessor)
        ;

        //-- 刪除解決方案
        from("direct:delete_solution")
                .routeId("delete_solution")
                .process(deleteSolutionProcessor)
        ;
    }
}
