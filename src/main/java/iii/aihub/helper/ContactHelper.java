package iii.aihub.helper;

import iii.aihub.entity.contact.Contact;
import jooq.generated.elastic.crawler.aihub.enums.ContactStatus;
import jooq.generated.elastic.crawler.aihub.tables.records.ContactRecord;
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
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.CONTACT;

@Component
public class ContactHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    public List<Contact> getContact(Integer from , Integer size) throws Exception {
        return getContact(null, from, size);
    }

    public List<Contact> getContact(Integer id, Integer from , Integer size) throws Exception {
        List<Contact> contactList = null;

        if (from == null){
            from = 0;
        }
        if (size == null){
            size = 10;
        }
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(CONTACT).where("1=1");
            if (id != null){
                step = step.and(CONTACT.ID.eq(id));
            }
            SelectWithTiesAfterOffsetStep step1 = step.orderBy(CONTACT.CREATED.desc()).limit(from, size);
            logger.info("get contact SQL: "+step1);
            Result<ContactRecord> records = step1.fetch();
            contactList = records.stream().map(Contact::new).collect(Collectors.toList());
        }catch (Exception e){
            throw new Exception(e);
        }

        return contactList;
    }

    public Boolean saveContact(Contact contact) throws Exception {
        if (contact == null){
            throw new Exception("contact is null");
        }
        Boolean result = Boolean.FALSE;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            InsertSetMoreStep step = ctx.insertInto(CONTACT)
                    .set(CONTACT.CATEGORY, contact.category)
                    .set(CONTACT.CREATED, new Timestamp(DateTime.now().getMillis()))
                    .set(CONTACT.EMAIL, contact.email)
                    .set(CONTACT.IP, contact.ip)
                    .set(CONTACT.NAME, contact.name)
                    .set(CONTACT.NOTE, contact.note)
                    .set(CONTACT.PHONE_NUMBER, contact.phoneNumber)
                    .set(CONTACT.STATUS, ContactStatus.valueOf(contact.status))
                    ;
            logger.info("save contact SQL: "+step);
            int rs = step.execute();
            logger.info("Save contact result: "+result);
            if (rs > 0){
                result = Boolean.TRUE;
            }
        }catch (Exception e){
            throw new Exception(e);
        }
        return  result;
    }

}
