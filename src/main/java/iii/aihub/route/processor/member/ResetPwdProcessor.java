package iii.aihub.route.processor.member;

import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.net.URLDecoder;
import java.sql.Connection;
import java.util.LinkedHashMap;

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

public class ResetPwdProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        //String memberId = InputParameterUtils.getStringParameter(data, "member_id");
        String encryptString = InputParameterUtils.getStringParameter(data, "e");
        String newPassword = InputParameterUtils.getStringParameter(data, "new_password");
        //String originalPassword = InputParameterUtils.getStringParameter(data,"original_password");
        logger.info("encryptString: "+encryptString);
        encryptString = URLDecoder.decode(encryptString, "UTF-8");
        logger.info("urldecode encryptString: "+encryptString);
        String decryptString = memberHelper.decrypt(encryptString);
        String[] pair = StringUtils.split(decryptString, ';');
        if (pair.length == 2) {
            String ts = pair[0];
            String memberId = pair[1];
            Long beforeLong = new Long(ts);
            DateTime dateTime = new DateTime(beforeLong);
            logger.info("date time: "+dateTime);
            Duration duration = new Duration(dateTime, DateTime.now());
            logger.info("member id: "+memberId);
            logger.info("duration: "+ duration.getStandardMinutes() +" minutes");
            if (duration.getStandardMinutes() <= 30){
                changePwd(exchange, memberId, newPassword);
            }else {
                throw new Exception("修改密碼的時間已經超過，請再重新操作一次");
            }
        }

    }

    private void changePwd(Exchange exchange, String memberId, String newPassword) throws Exception {
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(MEMBER)
                    .set(MEMBER.PASSWORD, memberHelper.genMemberPassword(newPassword))
                    .where(MEMBER.MEMBER_ID.eq(memberId))
                    ;
            logger.info("reset password SQL: "+step);
            int result = step.execute();
            logger.info("reset password result: "+result);
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            //logger.error(e.getLocalizedMessage(), e);
            throw e;
        }
    }
}
