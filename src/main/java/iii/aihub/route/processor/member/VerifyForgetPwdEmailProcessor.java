package iii.aihub.route.processor.member;

import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import iii.aihub.utils.MailSender;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.restlet.RestletConstants;
import org.joda.time.DateTime;
import org.restlet.engine.adapter.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class VerifyForgetPwdEmailProcessor implements Processor {

    private static final String VERIFY_USER_URL = "https://aihub.org.tw/member/vfps?s=";
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberHelper memberHelper;

    @Autowired
    MailSender mailSender;

    @Override
    public void process(Exchange exchange) throws Exception {
        Map<String, Object> headers = exchange.getIn().getHeaders();
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String email = InputParameterUtils.getStringParameter(data, "email");
        //logger.info("headers: "+headers);

        String mailTitle = "[ai-hub]重設密碼認證信";
        String memberId = null;
        if( (memberId = memberHelper.forgetPwdVerifyEmail(email)) != null){
            //-- 產生 timestamp string with salt
            String encryptString = String.valueOf(DateTime.now().getMillis()) + ";" + memberId;
            String url = VERIFY_USER_URL + URLEncoder.encode( memberHelper.encrypt(encryptString), "UTF-8");
            logger.info("VERIFY_USER_URL: "+url);
            String mailContent = "<html>\n" +
                    "\t<head></head>\n" +
                    "\t<body>\n" +
                    "\t\t<p>\n" +
                    "\t\t\t您好：\n" +
                    "\t\t</p>\n" +
                    "\t\t<p>\n" +
                    "\t\t\t我們收到您重設密碼的需求，請點擊下列按鈕前往 AI Hub 重設密碼\n" +
                    "\t\t</p>\n" +
                    "\t\t<p>\n" +
                    "\t\t\t<a href='"+url+"'>[前往 AI Hub 重設密碼]</a>\n" +
                    "\t\t</p>\n" +
                    "\t\t<p>\n" +
                    "\t\t\t請注意：您有 30 分鐘來重設密碼，如果超過這個時間的話，您會需要重新點擊忘記密碼按鈕來取得連結喔！\n" +
                    "\t\t</p>\n" +
                    "\t</body>\n" +
                    "</html>";
            mailSender.sendMail(email, mailTitle, mailContent);
        }
    }
}
