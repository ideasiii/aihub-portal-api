package iii.aihub.route.processor.tags;

import iii.aihub.entity.article.ArticleTagMap;
import iii.aihub.helper.TagHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.Tables;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static jooq.generated.elastic.crawler.aihub.Tables.ARTICLE_TAG_MAP;

@Component
public class AddArticleTagProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    TagHelper tagHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String dataId = InputParameterUtils.getStringParameter(data, "data_id");
        String tagIds = InputParameterUtils.getStringParameter(data, "tag_ids");

        String[] tagArray = StringUtils.split(tagIds, ",");
        List<Integer> tagList = new ArrayList<>();

        for (String tagString : tagArray){
            try {
                tagList.add(Integer.parseInt(tagString));
            }catch (Exception e){
                logger.error(e.getLocalizedMessage());
            }
        }
        //List<ArticleTagMap> articleTagMapList = null;
        Integer articleTagMapSize = new Integer(0);
        try(Connection conn = dataSource.getConnection()) {
            DSLContext create = DSL.using(conn, SQLDialect.MARIADB);
            articleTagMapSize = tagHelper.addArticleTagId(dataId, tagList, create);
        }catch (Exception e){
            throw e;
        }
        exchange.getOut().setBody(articleTagMapSize);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
