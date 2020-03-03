package iii.aihub.entity.author;

import java.util.Date;

public class Author {

    public Integer id;

    public String name;

    public Date created;

    public Date updated;

    public Author() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
