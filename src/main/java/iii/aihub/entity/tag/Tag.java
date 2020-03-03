package iii.aihub.entity.tag;

import io.swagger.models.auth.In;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

public class Tag {

    public Integer id;

    public String tagName;

    public Date created;

    public Date updated;

    public String description;

    public String isDelete;

    public List<String> articleDataIds;

    public Tag() {
    }

    public Tag(TagRecord r) {
        this.created = r.getCreated();
        this.description = r.getDescription();
        this.id = r.getId();
        this.isDelete = r.getIsDelete().getLiteral();
        this.tagName = r.getTagName();
        this.updated = r.getUpdated();
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }

}
