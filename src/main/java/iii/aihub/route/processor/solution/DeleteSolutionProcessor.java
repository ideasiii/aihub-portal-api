package iii.aihub.route.processor.solution;

import iii.aihub.entity.solution.Solution;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.enums.SolutionIsDelete;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.SOLUTION;

@Component
public class DeleteSolutionProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String solutionId = InputParameterUtils.getStringParameter(data, "solution_id");
        List<Solution> results = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(SOLUTION)
                    .set(SOLUTION.IS_DELETE, SolutionIsDelete.Y)
                    .where(SOLUTION.SOLUTIONID.eq(solutionId))
                    ;
            logger.info("delete solution SQL: "+step);
            //Result<SolutionRecord> updateRecord = step.returning().fetch();
            //logger.info("delete solution record: "+updateRecord);
            //results = updateRecord.stream().map(Solution::new).collect(Collectors.toList());
            //logger.info("delete result: "+results);
            int result = step.execute();
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
            exchange.getOut().setBody(result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
