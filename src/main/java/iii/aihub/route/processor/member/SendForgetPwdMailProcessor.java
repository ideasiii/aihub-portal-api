package iii.aihub.route.processor.member;

import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import iii.aihub.utils.MailSender;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.LinkedHashMap;

public class SendForgetPwdMailProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    MemberHelper memberHelper;

    MailSender mailSender;

    @Override
    public void process(Exchange exchange) throws Exception {
        //-- 寄出信件
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String mailTo = InputParameterUtils.getStringParameter(data, "mail_to");
        String encodeString = memberHelper.encrypt( String.valueOf(DateTime.now().getMillis()));
        String validaionURL = "https://aihub.org.tw/forget_pwd_checker?s="+encodeString;
        String mailBody = "<html>\n" +
                "\t<head></head>\n" +
                "\t<body>\n" +
                "\t\t<p>\n" +
                "\t\t\t您好：\n" +
                "\t\t</p>\n" +
                "\t\t<p>\n" +
                "\t\t\t我們收到您重設密碼的需求，請點擊下列按鈕前往 AI Hub 重設密碼\n" +
                "\t\t</p>\n" +
                "\t\t<p>\n" +
                "\t\t\t"+validaionURL+"\n" +
                "\t\t</p>\n" +
                "\t\t<p>\n" +
                "\t\t\t請注意：您有 30 分鐘來重設密碼，如果超過這個時間的話，您會需要重新點擊忘記密碼按鈕來取得連結喔！\n" +
                "\t\t</p>\n" +
                "\t</body>\n" +
                "</html>";
        mailSender = new MailSender();
        String mailTitle = "AI Hub 重設密碼通知信";
        mailSender.sendMail(mailTo, mailTitle, mailBody);
    }
}
