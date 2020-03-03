package iii.aihub.route.processor.contact;

import iii.aihub.entity.ResultSet;
import iii.aihub.entity.contact.Contact;
import iii.aihub.helper.ContactHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ListContactProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ContactHelper contactHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        Integer id = InputParameterUtils.getIntegerParameter(data, "id");
        Integer from = InputParameterUtils.getIntegerParameter(data, "from");
        Integer size = InputParameterUtils.getIntegerParameter(data, "size");

        if (from == null) from = 0;
        if (size == null) size = 10;

        ResultSet<Contact> resultSet = new ResultSet<>();
        List<Contact> contactList = null;
        if (id == null){
            contactList = contactHelper.getContact(from, size);
        }else {
            contactList = contactHelper.getContact(id, from, size);
        }
        resultSet.count = contactList.size();
        resultSet.executeDate = new Date(DateTime.now().getMillis());
        resultSet.data = contactList;
        exchange.getOut().setBody(resultSet);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
