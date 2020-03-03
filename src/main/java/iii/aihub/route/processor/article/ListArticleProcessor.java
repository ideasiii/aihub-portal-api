package iii.aihub.route.processor.article;

import iii.aihub.entity.article.Article;
import iii.aihub.entity.article.ArticleResult;
import iii.aihub.helper.ArticleHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@Component
public class ListArticleProcessor implements Processor {

    @Autowired
    ArticleHelper articleHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String dataId = InputParameterUtils.getStringParameter(data, "data_id");
        Article article = null;
        List<Article> articleList = articleHelper.getDisplayableArticles(Arrays.asList(dataId));
        if (articleList.size() > 0){
            article = articleList.get(0);
        }
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(article);
    }
}
