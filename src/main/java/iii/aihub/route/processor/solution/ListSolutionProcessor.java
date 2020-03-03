package iii.aihub.route.processor.solution;

import iii.aihub.entity.solution.Solution;
import iii.aihub.entity.solution.SolutionResult;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.SolutionIsDelete;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.SOLUTION;

public class ListSolutionProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String solutionId = InputParameterUtils.getStringParameter(data, "solution_id");

        if (solutionId == null){
            logger.error("solution id is null");
        }

        SolutionResult result = new SolutionResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(SOLUTION)
                    .where(SOLUTION.IS_DELETE.eq(SolutionIsDelete.N))
                    .and(SOLUTION.SOLUTIONID.eq(solutionId))
                    ;
            logger.info("list solution SQL: "+step);
            Result<SolutionRecord> records = step.fetch();
            logger.info("list solution record: "+records);
            List<Solution> solutionList = records.stream().map(Solution::new).collect(Collectors.toList());
            result.executeDate = new Date(DateTime.now().getMillis());
            result.solutionList = solutionList;
            result.totalCount = solutionList.size();
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE,200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
