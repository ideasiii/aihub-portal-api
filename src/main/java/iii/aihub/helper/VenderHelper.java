package iii.aihub.helper;

import iii.aihub.entity.vender.Vender;
import jooq.generated.elastic.crawler.aihub.Tables;
import jooq.generated.elastic.crawler.aihub.tables.Member;
import jooq.generated.elastic.crawler.aihub.tables.records.VenderRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.VENDER;

@Component
public class VenderHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    public Vender getVender(Integer venderId) throws Exception {
        Vender vender = null;
        List<Vender> list = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(VENDER)
                    .where(VENDER.ID.eq(venderId))
            ;
            logger.info("get vender SQL: "+step);
            Result<VenderRecord> records = step.fetch();
            list = records.stream().map(Vender::new).collect(Collectors.toList());
            if (list.size() > 0){
                vender = list.get(0);
            }else {
                throw new Exception("get vender result empty.");
            }
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return vender;
    }

    public String getVenderId(String venderName){
        return DigestUtils.md5Hex(venderName+"-"+System.currentTimeMillis());
    }
}
