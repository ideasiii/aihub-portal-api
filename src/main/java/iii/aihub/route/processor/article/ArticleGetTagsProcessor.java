package iii.aihub.route.processor.article;

import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.ArticleIsDisplay;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.*;

public class ArticleGetTagsProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String articleId = InputParameterUtils.getStringParameter(data, "data_id");
        java.util.List<String> tags = null;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MARIADB);
            SelectConditionStep step = ctx.select(
                    TAG.TAG_NAME
            )
                    .from(ARTICLE_TAG_MAP)
                    .join(TAG).on(ARTICLE_TAG_MAP.TAG_ID.eq(TAG.ID))
                    .join(ARTICLE).on(ARTICLE_TAG_MAP.DATA_ID.eq(ARTICLE.DATA_ID))
                    .where(ARTICLE_TAG_MAP.DATA_ID.eq(articleId))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.Y))
                    ;
            logger.info("get article tags SQL:"+step);
            //Result<Record6<String, String, Timestamp, String, String, String>> record6s = step.fetch();
            Result<Record1<String>> record1s = step.fetch();
            tags = record1s.stream().map(
                    d -> {
                        String tag = d.value1();
                        return tag;
                    }
            ).collect(Collectors.toList());

        }catch (Exception e){
            throw e;
        }
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        exchange.getOut().setBody(tags);
    }
}
