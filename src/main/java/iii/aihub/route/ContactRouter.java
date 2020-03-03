package iii.aihub.route;

import iii.aihub.route.processor.contact.AddContactProcessor;
import iii.aihub.route.processor.contact.ListContactProcessor;
import org.apache.camel.spring.SpringRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;

@Component
public class ContactRouter extends SpringRouteBuilder {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AddContactProcessor addContactProcessor;

    @Autowired
    ListContactProcessor listContactProcessor;

    @Override
    public void configure() throws Exception {

        rest("/contact")
                .enableCORS(true)
                .consumes(MediaType.APPLICATION_JSON)
                .produces(MediaType.APPLICATION_JSON)
            .post("/list")
                .id("list_contact_endpoint")
                .description("列出聯絡我們")
                .to("direct:list_contact")
            .post("/add")
                .id("add_contact_endpoint")
                .description("新增聯絡我們")
                .to("direct:add_contact")
             ;

        //-- 列出聯絡我們
        from("direct:list_contact")
                .routeId("list_contact")
                .process(listContactProcessor)
                ;

        //-- 新增聯絡我們
        from("direct:add_contact")
                .routeId("add_contact")
                .process(addContactProcessor)
        ;
    }
}
