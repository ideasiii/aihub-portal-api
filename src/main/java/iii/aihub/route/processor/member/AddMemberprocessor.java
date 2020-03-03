package iii.aihub.route.processor.member;

import iii.aihub.entity.member.Member;
import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsVerified;
import org.apache.camel.Exchange;
import org.apache.camel.OutHeaders;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
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
public class AddMemberprocessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String firstName = InputParameterUtils.getStringParameter(data, "first_name");
        String lastName = InputParameterUtils.getStringParameter(data, "last_name");
        String password = InputParameterUtils.getStringParameter(data, "password");
        String cellPhone = InputParameterUtils.getStringParameter(data, "cell_phone");
        String email = InputParameterUtils.getStringParameter(data, "email");
        Date created = DateTime.now().toDate();
        Date updated = DateTime.now().toDate();
        String isDelete = "N";
        String isVerify = "N";
        String err = "";

        if (password == null){
            err = "password is null";
            logger.error(err);
            throw new Exception(err);
        }
        if (email == null){
            err = "email is null";
            logger.error(err);
            throw new Exception(err);
        }
        if (cellPhone == null){
            err = "cell_phone is null";
            logger.error(err);
            throw new Exception(err);
        }

        Member member = new Member();
        member.isVerify = isVerify;
        member.firstName = firstName;
        member.lastName = lastName;
        member.password = password;
        member.updated = updated;
        member.isDelete = isDelete;
        member.email = email;
        member.created = created;
        member.cellPhone = cellPhone;

        int result = memberHelper.saveMember(member);
        exchange.getOut().setBody(result);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
