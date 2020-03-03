package iii.aihub.route.processor.solution;

import iii.aihub.entity.solution.Solution;
import iii.aihub.helper.SolutionHelper;
import iii.aihub.helper.VenderHelper;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.Tables;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Date;
import java.util.LinkedHashMap;

import static jooq.generated.elastic.crawler.aihub.Tables.SOLUTION;

@Component
public class AddSolutionProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    VenderHelper venderHelper;

    @Autowired
    SolutionHelper solutionHelper;

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String name = InputParameterUtils.getStringParameter(data, "name");
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");
        String introduction = InputParameterUtils.getStringParameter(data, "introduction");
        String contactEmail = InputParameterUtils.getStringParameter(data, "contact_email");
        String contactTel = InputParameterUtils.getStringParameter(data, "contact_tel");
        String imgUrl = InputParameterUtils.getStringParameter(data, "img_url");
        Date created = DateTime.now().toDate();
        Date updated = DateTime.now().toDate();
        String isDelete = "N";

        Solution solution = new Solution();
        solution.solutionId = solutionHelper.getSolutionId(name);
        solution.venderName = null;
        solution.introduction = introduction;
        solution.contactTel = contactTel;
        solution.contactEmail = contactEmail;
        solution.venderId = venderId;
        solution.updated = updated;
        solution.name = name;
        solution.imgUrl = imgUrl;
        solution.created = created;
        solution.isDelete = "N";

        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SolutionRecord record = ctx.newRecord(SOLUTION, solution);
            logger.info("new solution record: "+record );
            int result = ctx.executeInsert(record);
            if (result == 0){
                throw new Exception("insert new solution fail (result = "+result+").");
            }
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
    }
}
