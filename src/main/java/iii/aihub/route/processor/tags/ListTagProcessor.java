package iii.aihub.route.processor.tags;

import iii.aihub.entity.ResultSet;
import iii.aihub.entity.tag.Tag;
import iii.aihub.helper.ArticleHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.ArrayUtils;
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
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.TAG;

@Component
public class ListTagProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    ArticleHelper articleHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String tagIds = InputParameterUtils.getStringParameter(data, "tag_ids");
        List<Integer> tagIntList = new ArrayList<>();
        if (tagIds != null) {
            String[] tagSplit = StringUtils.split(tagIds,",");
            for (String s : tagSplit) {
                tagIntList.add(Integer.parseInt(s.trim()));
            }
        }

        ResultSet<Tag> tagListEntity = new ResultSet<>();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(TAG).where("1=1");

            if (tagIds != null) {
                step = step.and(TAG.ID.in(tagIntList));
            }

            SelectSeekStep1 step1 =step.orderBy(TAG.UPDATED.desc());
            logger.info("select tag SQL: "+step1);
            Result<TagRecord> records = step1.fetch();
            List<Tag> tags = records.stream().map(Tag::new).collect(Collectors.toList());
            for (Tag t : tags){
                t.articleDataIds = articleHelper.getArticleDataId(Arrays.asList(t.id));
            }
            tagListEntity.count = tags.size();
            tagListEntity.executeDate = new Date(DateTime.now().getMillis());
            tagListEntity.data = tags;
            logger.info("result: "+tagListEntity);
        }catch (Exception e){
            throw new Exception(e);
        }
        exchange.getOut().setBody(tagListEntity);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
