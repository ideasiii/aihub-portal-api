package iii.aihub.entity.contact;

import jooq.generated.elastic.crawler.aihub.tables.records.ContactRecord;

import java.util.Date;

public class Contact {

    public Integer id;

    public String name;

    public String email;

    /**
     * 使用者當時的ip
     */
    public String ip;

    public String category;

    public String phoneNumber;

    public String note;

    public String status;

    public Date created;

    public Contact() {
    }

    public Contact(ContactRecord r) {
        this.category = r.getCategory();
        this.created = r.getCreated();
        this.email = r.getEmail();
        this.id = r.getId();
        this.name = r.getName();
        this.note = r.getNote();
        this.phoneNumber = r.getPhoneNumber();
        this.status = r.getStatus().getLiteral();
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
