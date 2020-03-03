package iii.aihub.route.processor.author;

import iii.aihub.entity.author.Author;
import iii.aihub.helper.AuthorHelper;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class ListAuthorProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthorHelper authorHelper;

    @Override
    public void process(Exchange exchange) throws Exception {

        List<Author> authorList = authorHelper.getAllAuthor();
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(authorList);
    }
}
