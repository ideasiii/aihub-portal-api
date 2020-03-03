package iii.aihub.route.processor.member;

import iii.aihub.entity.member.Member;
import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsVerified;
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

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

@Component
public class LoginCheckMemberProcessor implements Processor {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String email = InputParameterUtils.getStringParameter(data, "email");
        String password = InputParameterUtils.getStringParameter(data, "password");

        Member result = memberHelper.loginCheck(email, password);

        exchange.getOut().setBody(result);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
