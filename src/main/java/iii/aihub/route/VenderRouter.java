package iii.aihub.route;

import iii.aihub.route.processor.vender.*;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class VenderRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ListVenderProcessor listVenderProcessor;

    @Autowired
    AddVenderProcessor addVenderProcessor;

    @Autowired
    SearchVenderProcessor searchVenderProcessor;

    @Autowired
    ModifyVenderProcessor modifyVenderProcessor;

    @Autowired
    DeleteVenderProcessor deleteVenderProcessor;

    @Override
    public void configure() throws Exception {

        rest("/vender")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/list")
                .id("list_vender_endpoint")
                .description("列出廠商")
                .to("direct:list_vender")
            .post("/search_keyword")
                .id("search_vender_endpoint")
                .description("以關鍵字尋找廠商")
                .to("direct:search_vender")
            .post("/add")
                .id("add_vender_endpoint")
                .description("新增廠商")
                .to("direct:add_vender")
            .post("/modify")
                .id("modify_vender_endpoint")
                .description("修改廠商資料")
                .to("direct:modify_vender")
            .post("/delete")
                .id("delete_vender_endpoint")
                .description("(軟)刪除廠商資料")
                .to("direct:delete_vender")
                ;
        //-- 列出廠商
        from("direct:list_vender")
        .routeId("list_vender")
        .process(listVenderProcessor)
                ;

        //-- 以關鍵字尋找廠商
        from("direct:search_vender")
                .routeId("search_vender")
                .process(searchVenderProcessor)
        ;

        //-- 新增廠商
        from("direct:add_vender")
                .routeId("add_vender")
                .process(addVenderProcessor)
                ;

        //-- 修改廠商資料
        from("direct:modify_vender")
                .routeId("modify_vender")
                .process(modifyVenderProcessor)
        ;

        //-- (軟)刪除廠商資料
        from("direct:delete_vender")
                .routeId("delete_vender")
                .process(deleteVenderProcessor)
        ;
    }
}
