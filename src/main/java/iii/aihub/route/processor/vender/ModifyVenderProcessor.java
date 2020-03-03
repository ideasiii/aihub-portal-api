package iii.aihub.route.processor.vender;

import iii.aihub.entity.vender.Vender;
import iii.aihub.utils.InputParameterUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.VenderRecord;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
import org.jooq.UpdateConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.VENDER;

@Component
public class ModifyVenderProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String venderId = InputParameterUtils.getStringParameter(data, "vender_id");
        String venderName = InputParameterUtils.getStringParameter(data, "vender_name");
        String venderImgUrl = InputParameterUtils.getStringParameter(data, "vender_img_url");
        String description = InputParameterUtils.getStringParameter(data, "description");
        String phoneNumber = InputParameterUtils.getStringParameter(data, "phone_number");
        String address = InputParameterUtils.getStringParameter(data, "address");
        String email = InputParameterUtils.getStringParameter(data, "email");

        String err = null;
        if (venderName == null){
            err = "vendor name is null";
            logger.error(err);
            throw new Exception(err);
        }
        if (venderId == null){
            err = "vendor id is null";
            logger.error(err);
            throw new Exception(err);
        }

        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            UpdateConditionStep step = ctx.update(VENDER)
                    .set(VENDER.NAME, venderName)
                    .set(VENDER.ADDRESS, address)
                    .set(VENDER.DESCRIPTION, description)
                    .set(VENDER.EMAIL, email)
                    .set(VENDER.PHONE_NUMBER, phoneNumber)
                    .set(VENDER.VENDER_IMG, venderImgUrl)
                    .where(VENDER.VENDER_ID.eq(venderId))
                    ;
            logger.info("update vender SQL: "+step);
            int result = step.execute();
            logger.info("update result: "+result);
            //List<VenderRecord> records = step.returning().fetch();
            //List<Vender> list = records.stream().map(Vender::new).collect(Collectors.toList());
            exchange.getOut().setBody(result);
            exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage());
            throw new Exception(e);
        }

    }
}
