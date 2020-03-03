package iii.aihub.route.processor.solution;

import iii.aihub.entity.solution.Solution;
import iii.aihub.entity.solution.SolutionResult;
import iii.aihub.helper.SolutionHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
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
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.SOLUTION;

@Component
public class ModifySolutionProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    SolutionHelper solutionHelper;

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String name = InputParameterUtils.getStringParameter(data, "name");
        String solutionId = InputParameterUtils.getStringParameter(data, "solution_id");
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");
        String introduction = InputParameterUtils.getStringParameter(data, "introduction");
        String contactEmail = InputParameterUtils.getStringParameter(data, "contact_email");
        String contactTel = InputParameterUtils.getStringParameter(data, "contact_tel");
        String imgUrl = InputParameterUtils.getStringParameter(data, "img_url");

        if (name == null){
            throw new Exception("廠商名稱 name is null");
        }
        if (solutionId == null){
            throw new Exception("solutionId is null");
        }
        if (introduction == null){
            throw new Exception("introduction is null");
        }
        if (contactEmail == null){
            throw new Exception("contactEmail is null");
        }
        if (contactTel == null){
            throw new Exception("contactTel is null");
        }
        if (imgUrl == null){
            throw new Exception("imgUrl is null");
        }

        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            UpdateConditionStep step = ctx.update(SOLUTION)
                    .set(SOLUTION.CONTACT_EMAIL, contactEmail)
                    .set(SOLUTION.CONTACT_TEL, contactTel)
                    .set(SOLUTION.IMG_URL, imgUrl)
                    .set(SOLUTION.INTRODUCTION, introduction)
                    .set(SOLUTION.NAME, name)
                    .set(SOLUTION.VENDER_ID, venderId)
                    .where(SOLUTION.SOLUTIONID.eq(solutionId))
                    ;
            logger.info("modify solution SQL: "+step);
            /*
            Result<SolutionRecord> records = step.returning().fetch();
            logger.info("modified solution record: "+records);
            List<Solution> list = records.stream().map(Solution::new).collect(Collectors.toList());
            SolutionResult result = new SolutionResult();
            result.totalCount = list.size();
            result.solutionList = list;
            result.executeDate = new Date(DateTime.now().getMillis());
            logger.info("solution result: "+result);
            */
            int result = step.execute();
            logger.info("modify solution result: "+result);
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
