package iii.aihub.route.processor.vender;

import iii.aihub.entity.vender.Vender;
import iii.aihub.entity.vender.VenderResult;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.VenderRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
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
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.VENDER;

@Component
public class SearchVenderProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String keyword = InputParameterUtils.getStringParameter(data, "keyword");
        VenderResult result = new VenderResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(VENDER)
                    .where(VENDER.NAME.likeIgnoreCase("%"+keyword+"%"))
                    ;
            logger.info("vender search keyword SQL: "+step);
            Result<VenderRecord> records = step.fetch();
            logger.info("result records: "+records);
            List<Vender> venderList = records.stream().map(Vender::new).collect(Collectors.toList());
            result.venderList = venderList;
            result.totalCount = venderList.size();
            result.querySearch = new Date(DateTime.now().getMillis());
            logger.info("vender list: "+venderList);
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);

        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
