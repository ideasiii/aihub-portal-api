package iii.aihub.route.processor.member;

import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.restlet.engine.adapter.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class VerifyForgetPwdUserProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
        headers.put("member_id", null);
        logger.info("headers: "+headers);
        String s = (String)headers.get("s");
        s = URLDecoder.decode(s, "UTF-8");
        logger.info("s: "+s);
        String decryptString = memberHelper.decrypt(s);
        String[] pair = StringUtils.split(decryptString, ';');
        Integer result = new Integer(0);
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
                result = 1;
                headers.put("member_id", memberId);
            }
        }
        exchange.getOut().setBody(result);
        exchange.getOut().setHeaders(headers);
    }
}
