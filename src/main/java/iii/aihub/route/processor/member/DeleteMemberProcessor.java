package iii.aihub.route.processor.member;

import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jooq.DSLContext;
import org.jooq.SQL;
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

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

@Component
public class DeleteMemberProcessor implements Processor {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String memberId = InputParameterUtils.getStringParameter(data, "member_id");

        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(MEMBER)
                    .set(MEMBER.IS_DELETE, MemberIsDelete.Y)
                    .where(MEMBER.MEMBER_ID.eq(memberId));
            logger.info("delete member SQL: "+step);
            int result = step.execute();
            logger.info("delete result: "+result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
            exchange.getOut().setBody(result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
