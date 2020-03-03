package iii.aihub.entity.solution;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;

import java.util.Date;

public class Solution {

    public Integer id;

    public String solutionId;

    public String name;

    public String venderId;

    public String venderName;

    public String introduction;

    public String contactEmail;

    public String contactTel;

    public String imgUrl;

    public Date created;

    public Date updated;

    public String isDelete;

    public Solution() {
    }

    public Solution(SolutionRecord r) {
        this.id = r.getId();
        this.solutionId = r.getSolutionid();
        this.created = r.getCreated();
        this.imgUrl = r.getImgUrl();
        this.name = r.getName();
        this.updated = r.getUpdated();
        this.venderId = r.getVenderId();
        this.contactEmail = r.getContactEmail();
        this.contactTel = r.getContactTel();
        this.introduction = r.getIntroduction();
        //this.venderName =
        this.isDelete = r.getIsDelete().getLiteral();

    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
