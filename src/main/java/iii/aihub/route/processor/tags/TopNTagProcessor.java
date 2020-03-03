package iii.aihub.route.processor.tags;

import iii.aihub.entity.ResultSet;
import iii.aihub.entity.tag.Tag;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectSeekStep1;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.TAG;

@Component
public class TopNTagProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {

        ResultSet<Tag> tagListEntity = new ResultSet<>();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectSeekStep1 step = ctx.selectFrom(TAG)
                    .orderBy(TAG.UPDATED.desc());
            logger.info("select tag SQL: "+step);
            Result<TagRecord> records = step.fetch();
            List<Tag> tags = records.stream().map(Tag::new).collect(Collectors.toList());
            tagListEntity.count = tags.size();
            tagListEntity.executeDate = new Date(DateTime.now().getMillis());
            tagListEntity.data = tags;
            logger.info("result: "+tagListEntity);
        }catch (Exception e){
            throw e;
        }
        exchange.getOut().setBody(tagListEntity);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
