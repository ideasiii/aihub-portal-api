package iii.aihub.route.processor.tags;

import iii.aihub.entity.tag.Tag;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.Tables;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.ARTICLE_TAG_MAP;
import static jooq.generated.elastic.crawler.aihub.Tables.TAG;

@Component
public class AddTagProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String tagName = InputParameterUtils.getStringParameter(data, "tag_name");
        String description = InputParameterUtils.getStringParameter(data, "description");

        if (tagName == null){
            throw new Exception("tag name is null");
        }

        //Integer result = 0;
        Tag tag = null;
        try (Connection conn = dataSource.getConnection()){
            tagName = StringUtils.trim(tagName);
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep existedStep = ctx.selectFrom(TAG)
                    .where(TAG.TAG_NAME.eq(tagName));
            List<Tag> existedTags = existedStep.fetch().into(Tag.class);
            if (existedTags.size() == 0) {
                InsertSetMoreStep step = ctx.insertInto(TAG)
                        .set(TAG.TAG_NAME, tagName)
                        .set(TAG.DESCRIPTION, description)
                        .set(TAG.CREATED, new Timestamp(DateTime.now().getMillis()));
                logger.info("insert tag SQL: " + step);
                Result<TagRecord> recores = step.onDuplicateKeyIgnore().returning().fetch();
                List<Tag> tagList = recores.stream().map(Tag::new).collect(Collectors.toList());
                if (tagList.size() > 0) {
                    tag = tagList.get(0);
                }
            }else{
                tag = existedTags.get(0);
            }

        }catch (Exception e){
            throw new Exception(e);
        }
        exchange.getOut().setBody(tag);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
