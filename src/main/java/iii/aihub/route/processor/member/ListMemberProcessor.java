package iii.aihub.route.processor.member;

import iii.aihub.entity.member.Member;
import iii.aihub.entity.member.MemberResult;
import iii.aihub.helper.MemberHelper;
import iii.aihub.utils.InputParameterUtils;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

@Component
public class ListMemberProcessor implements Processor {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    MemberHelper memberHelper;

    @Override
    public void process(Exchange exchange) throws Exception {
        LinkedHashMap<String, Object> data = exchange.getIn().getBody(LinkedHashMap.class);
        String memberId = InputParameterUtils.getStringParameter(data, "member_id");
        List<Member> memberList = null;
        MemberResult result = new MemberResult();
        if (memberId != null){
            memberList = new ArrayList<>();
            memberList.add(memberHelper.getMember(memberId));
        }else {
            memberList = memberHelper.getAllMember();
        }
        result.memberList = memberList;
        result.queryDate = new Date(DateTime.now().getMillis());
        result.totalCount = memberList.size();
        exchange.getOut().setBody(result);
        exchange.getOut().setHeader(Exchange.HTTP_RESPONSE_CODE, 200);
    }
}
