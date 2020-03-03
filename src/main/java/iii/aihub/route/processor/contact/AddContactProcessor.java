package iii.aihub.route.processor.contact;

//import com.sun.javaws.jnl.RContentDesc;
import iii.aihub.entity.contact.Contact;
import iii.aihub.helper.ContactHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AddContactProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ContactHelper contactHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        //String id = InputParameterUtils.getStringParameter(data,"id");
        String name = InputParameterUtils.getStringParameter(data, "name");
        String email = InputParameterUtils.getStringParameter(data, "email");
        String category = InputParameterUtils.getStringParameter(data, "category");
        String phoneNumber = InputParameterUtils.getStringParameter(data, "phone_number");
        String note = InputParameterUtils.getStringParameter(data, "note");
        String ip = InputParameterUtils.getStringParameter(data, "ip");

        Contact contact = new Contact();
        contact.status = "new_";
        contact.phoneNumber = phoneNumber;
        contact.note = note;
        contact.name = name;
        contact.email = email;
        contact.ip = ip;
        contact.category = category;
        Boolean result = contactHelper.saveContact(contact);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(result);
    }
}
