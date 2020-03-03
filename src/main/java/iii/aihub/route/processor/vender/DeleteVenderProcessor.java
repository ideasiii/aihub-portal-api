package iii.aihub.route.processor.vender;

import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.Tables;
import jooq.generated.elastic.crawler.aihub.enums.VenderIsDelete;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;

import static jooq.generated.elastic.crawler.aihub.Tables.VENDER;

@Component
public class DeleteVenderProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");

        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(VENDER)
                    .set(VENDER.IS_DELETE, VenderIsDelete.Y)
                    .where(VENDER.VENDER_ID.eq(venderId))
                    ;
            logger.info("delete vender SQL: "+step);
            int result = step.execute();
            logger.info("delete vender result: "+result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
            exchange.getOut().setBody(result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }

    }
}
