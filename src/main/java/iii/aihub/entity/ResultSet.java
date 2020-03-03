package iii.aihub.entity;

import java.util.Date;
import java.util.List;

public class ResultSet<T> {

    public Integer count;

    public Date executeDate;

    public List<T> data;

    public ResultSet() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
