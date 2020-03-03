package iii.aihub.route.processor.article;

import iii.aihub.helper.ArticleHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;

@Component
public class DeleteArticleProcessor implements Processor {

    @Autowired
    ArticleHelper articleHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String dataId = InputParameterUtils.getStringParameter(data, "data_id");
        int result = articleHelper.deleteArticle(dataId);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(result);
    }
}
