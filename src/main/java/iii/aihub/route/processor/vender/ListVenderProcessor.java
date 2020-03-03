package iii.aihub.route.processor.vender;

import iii.aihub.entity.vender.Vender;
import iii.aihub.entity.vender.VenderResult;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.Tables;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

import static jooq.generated.elastic.crawler.aihub.Tables.VENDER;

@Component
public class ListVenderProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");
        VenderResult result = new VenderResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            List<Vender> venderList = null;
            if (venderId == null){
                venderList = ctx.selectFrom(VENDER).orderBy(VENDER.CREATED.desc()).fetch().into(Vender.class);
            }else {
                venderList = ctx.selectFrom(VENDER).where(VENDER.VENDER_ID.eq(venderId)).fetch().into(Vender.class);
            }

            logger.info("vender list: "+venderList);
            result.querySearch = new Date(DateTime.now().getMillis());
            result.totalCount = venderList.size();
            result.venderList = venderList;
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }

    }
}
