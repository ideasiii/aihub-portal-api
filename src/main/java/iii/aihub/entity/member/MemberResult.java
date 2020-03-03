package iii.aihub.entity.member;

import iii.aihub.entity.article.Article;

import java.util.Date;
import java.util.List;

public class MemberResult {

    public Integer totalCount;

    public Date queryDate;

    public List<Member> memberList;

    public MemberResult() {
    }

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }
}
