package iii.aihub.route.processor.member;

import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

@Component
public class ModifyMemberProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String memberId = InputParameterUtils.getStringParameter(data, "member_id");
        String firstName = InputParameterUtils.getStringParameter(data, "first_name");
        String lastName = InputParameterUtils.getStringParameter(data, "last_name");
        String password = InputParameterUtils.getStringParameter(data, "password");
        String cellPhone = InputParameterUtils.getStringParameter(data, "cell_phone");
        //String email = InputParameterUtils.getStringParameter(data, "email");

        if (memberId == null){
            throw new Exception("member id is null");
        }

        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(MEMBER)
                    .set(MEMBER.CELL_PHONE, cellPhone)
                    //.set(MEMBER.EMAIL, email)
                    .set(MEMBER.FIRST_NAME, firstName)
                    .set(MEMBER.LAST_NAME, lastName)
                    .set(MEMBER.IS_DELETE, MemberIsDelete.N)
                    .set(MEMBER.PASSWORD, memberHelper.genMemberPassword(password))
                    .where(MEMBER.MEMBER_ID.eq(memberId))
                    ;
            logger.info("modify member SQL: "+step);
            int result = step.execute();
            logger.info("modify member result: "+result);
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
