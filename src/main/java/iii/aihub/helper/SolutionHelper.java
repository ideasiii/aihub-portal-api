package iii.aihub.helper;

import iii.aihub.entity.solution.Solution;
import jooq.generated.elastic.crawler.aihub.Tables;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleSolutionsRecord;
import jooq.generated.elastic.crawler.aihub.tables.records.SolutionRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.DSLContext;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.SelectConditionStep;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.*;

@Component
public class SolutionHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    public String getSolutionId(String name){
        return DigestUtils.md5Hex(name +"-"+ System.currentTimeMillis());
    }

    public List<Solution> getSolutionInArticle(String articleId) throws Exception {
        List<Solution> solutionList = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.select(SOLUTION.fields()).
                from(ARTICLE_SOLUTIONS)
                    .join(ARTICLE).on(ARTICLE.DATA_ID.eq(ARTICLE_SOLUTIONS.ARTICLE_ID))
                    .join(SOLUTION).on(SOLUTION.SOLUTIONID.eq(ARTICLE_SOLUTIONS.SOLUTION_ID))
                    .where(ARTICLE_SOLUTIONS.ARTICLE_ID.eq(articleId))
                    ;
            logger.info("get solutions in article ["+articleId+"] SQL: "+step);
            Result<SolutionRecord> records = step.fetch().into(SOLUTION);
            logger.info("records: "+records);
            //solutionList = records.stream().map(Solution::new).collect(Collectors.toList());
            Solution solution = null;
            for (SolutionRecord record : records){
                solution = new Solution();
                solution.solutionId = record.getSolutionid();
                solution.isDelete = record.getIsDelete().getLiteral();
                solution.created = record.getCreated();
                solution.imgUrl = record.getImgUrl();
                solution.name = record.getName();
                solution.updated = record.getUpdated();
                solution.venderId = record.getVenderId();
                solution.contactEmail = record.getContactEmail();
                solution.contactTel = record.getContactTel();
                solution.introduction = record.getIntroduction();
                solution.venderName = "";
                solution.id = record.getId();
                solutionList.add(solution);
            }
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return solutionList;
    }
}
