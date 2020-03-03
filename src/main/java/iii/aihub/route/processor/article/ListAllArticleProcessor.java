package iii.aihub.route.processor.article;

import iii.aihub.entity.article.Article;
import iii.aihub.entity.article.ArticleResult;
import iii.aihub.helper.ArticleHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ListAllArticleProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ArticleHelper articleHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        Integer from = InputParameterUtils.getIntegerParameter(data, "from");
        Integer size = InputParameterUtils.getIntegerParameter(data, "size");
        if (size == null){
            size = 10;
        }
        ArticleResult list = articleHelper.getDisplayableArtcle(from, size);
        exchange.getOut().setBody(list);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
