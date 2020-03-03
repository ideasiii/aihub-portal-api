package iii.aihub.route.processor.solution;

import iii.aihub.entity.solution.Solution;
import iii.aihub.entity.solution.SolutionResult;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.SolutionIsDelete;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.SOLUTION;

@Component
public class ListAllSolutionProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        Integer from = InputParameterUtils.getIntegerParameter(data, "from");
        Integer size = InputParameterUtils.getIntegerParameter(data, "size");
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");
        logger.info("size: "+size);
        if (size == null){
            size = 10;
        }
        if (from == null){
            from = 0;
        }

        SolutionResult result = new SolutionResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            SelectConditionStep step = ctx.selectFrom(SOLUTION)
                    .where(SOLUTION.IS_DELETE.eq(SolutionIsDelete.N))
                    ;
            if (venderId != null){
                step = step.and(SOLUTION.VENDER_ID.eq(venderId));
            }
            Integer totalCount = step.fetch().size();
            Result<SolutionRecord> records;
            //-- size = -99 會在UI的下拉選單用到
            if (size != -99){
                SelectWithTiesAfterOffsetStep step1 = step.orderBy(SOLUTION.CREATED.desc()).limit(from, size);
                logger.info("list all solution from: "+from+", size: "+size+" SQL: "+step1);
                records = step1.fetch();
            }else {
                logger.info("list all solution from: "+from+", size: "+size+" SQL: "+step);
                records = step.fetch();
            }
            //Result<SolutionRecord> records = ctx.selectFrom(SOLUTION).where(SOLUTION.IS_DELETE.eq(SolutionIsDelete.N)).fetch();
            List<Solution> solutionList = records.stream().map(Solution::new).collect(Collectors.toList());
            result.executeDate = new Date(DateTime.now().getMillis());
            result.solutionList = solutionList;
            result.totalCount = totalCount;
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
            exchange.getOut().setBody(result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
