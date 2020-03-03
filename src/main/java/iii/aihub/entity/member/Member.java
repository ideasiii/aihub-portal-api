package iii.aihub.entity.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iii.aihub.utils.DateTimeUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.MemberRecord;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Date;

public class Member {

    public String firstName;

    public String lastName;

    public Integer id;

    public String memberId;

    public String password;

    public String cellPhone;

    public String email;

    public Date created;

    public Date updated;

    public String isDelete;

    public String isVerify;

    public Member() {
    }

    public Member(MemberRecord r) {
        this.cellPhone = r.getCellPhone();
        this.created = new java.sql.Date(r.getCreated().getTime());
        this.email = r.getEmail();
        this.firstName = r.getFirstName();
        this.id = r.getId();
        this.isDelete = r.getIsDelete().getLiteral();
        this.lastName = r.getLastName();
        this.updated = new java.sql.Date(r.getUpdated().getTime());
        this.password = r.getPassword();
        this.isVerify = r.getIsVerified().getLiteral();
        this.memberId = r.getMemberId();
    }


    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }

}
