package iii.aihub.entity.solution;

import java.util.Date;
import java.util.List;

public class SolutionResult {

    public Integer totalCount;

    public Date executeDate;

    public List<Solution> solutionList;

    public SolutionResult() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
