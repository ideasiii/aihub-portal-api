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
public class FromTagListArticleProcessor implements Processor {

    @Autowired
    ArticleHelper articleHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        Integer tag_idx = InputParameterUtils.getIntegerParameter(data, "tag_id");
        Integer from = InputParameterUtils.getIntegerParameter(data, "from");
        Integer size = InputParameterUtils.getIntegerParameter(data, "size");
        ArticleResult articleResult = new ArticleResult();

        if (from == null){
            from = 0;
        }

        if (size == null){
            size = 10;
        }

        List<Article> articleList = articleHelper.getArticleFromTags(Arrays.asList(tag_idx), from, size);
        articleResult.queryDate = new Date(DateTime.now().getMillis());
        articleResult.totalCount = articleList.size();
        articleResult.articleList = articleList;
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(articleResult);
    }
}
