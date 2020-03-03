package iii.aihub.route.processor.author;

import iii.aihub.entity.author.Author;
import iii.aihub.helper.AuthorHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class AddAuthorProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthorHelper authorHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String authorName = InputParameterUtils.getStringParameter(data, "author_name");
        String err = "";
        if (authorName == null){
            err = "author name is null";
            logger.error(err);
            throw new Exception(err);
        }

        Author author = new Author();
        author.name = authorName;

        int result = authorHelper.saveAuthor(author);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(result);
    }
}
