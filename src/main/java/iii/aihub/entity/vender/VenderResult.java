package iii.aihub.entity.vender;

import java.util.Date;
import java.util.List;

public class VenderResult {

    public Integer totalCount;

    public Date querySearch;

    public List<Vender> venderList;

    public VenderResult() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
