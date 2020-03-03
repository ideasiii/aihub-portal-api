package iii.aihub.helper;

import iii.aihub.entity.article.ArticleTagMap;
import iii.aihub.entity.tag.Tag;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleTagMapRecord;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.ARTICLE_TAG_MAP;
import static jooq.generated.elastic.crawler.aihub.Tables.TAG;

public class TagHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    public Integer addArticleTags(String dataId, List<Tag> tags, DSLContext ctx) throws Exception {

        //-- 新增tag
        List<Integer> tagIds = new ArrayList<>();
        for (Tag tag : tags){
            tagIds.add(tag.id);
        }
        return this.addArticleTagId(dataId, tagIds, ctx);
    }

    public void deleteArticleTags(String dataId, List<Tag> tags, DSLContext ctx) throws Exception {
        List<Integer> tagIds = new ArrayList<>();
        for (Tag tag : tags){
            tagIds.add(tag.id);
        }
        if (ctx == null){
            try (Connection conn = dataSource.getConnection()){
                ctx = DSL.using(conn, SQLDialect.MYSQL);
                ctx.transaction(configuration -> {
                    DSLContext tx = DSL.using(configuration);
                    //-- 刪除
                    DeleteConditionStep deleteConditionStep = tx.delete(ARTICLE_TAG_MAP)
                            .where(ARTICLE_TAG_MAP.TAG_ID.in(tagIds))
                            .and(ARTICLE_TAG_MAP.DATA_ID.eq(dataId))
                            ;
                    logger.info("delete all article tag mapping SQL: "+deleteConditionStep);
                    int delResult = deleteConditionStep.execute();
                    logger.info("delete result: "+delResult);
                });
            }catch (Exception e){
                throw e;
            }
        }
    }

    public void modifyArticleTags(String dataId, List<Tag> tags, DSLContext ctx) throws Exception {
        try {
            this.deleteArticleTags(dataId, tags, ctx);
            this.addArticleTags(dataId, tags, ctx);
        } catch (Exception e) {
            throw e;
        }
    }

    public void addArticleTagName(String dataId, List<String> tags, DSLContext ctx) throws Exception {

        //-- 新增tag

        if (ctx == null){
            try (Connection conn = dataSource.getConnection()){
                ctx = DSL.using(conn, SQLDialect.MYSQL);
            }catch (Exception e){
                throw e;
            }
        }
        ctx.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);
            for (String tag : tags){
                InsertReturningStep step = tx.insertInto(TAG)
                        .set(TAG.TAG_NAME, tag)
                        .set(TAG.CREATED, new Timestamp(DateTime.now().getMillis()))
                        .onDuplicateKeyIgnore()
                        ;
                logger.info("insert tag SQL: "+step);
                Integer tagId = step.returning(TAG.ID).fetchOne().getValue(TAG.ID);
                logger.info("tag id: "+tagId);
                //int insertResult = step.execute();
                //logger.info("insert result: "+insertResult);

                InsertSetMoreStep insStep = tx.insertInto(ARTICLE_TAG_MAP)
                        .set(ARTICLE_TAG_MAP.CREATED, new Timestamp(DateTime.now().getMillis()))
                        .set(ARTICLE_TAG_MAP.DATA_ID, dataId)
                        .set(ARTICLE_TAG_MAP.TAG_ID, tagId)
                        ;
                int insResult = insStep.onDuplicateKeyIgnore().execute();
                if (insResult != 1){
                    throw new Exception("insert result count: "+insResult);
                }
            }

        });
    }

    public Integer addArticleTagId(String dataId, List<Integer> tagIds, DSLContext ctx) throws Exception {
        AtomicReference<Integer> result = new AtomicReference<>(new Integer(0));
        //-- 新增tag
        if (ctx == null){
            try (Connection conn = dataSource.getConnection()){
                ctx = DSL.using(conn, SQLDialect.MYSQL);
            }catch (Exception e){
                throw e;
            }
        }
        List<ArticleTagMap> articleTagMapList = new ArrayList<>();
        ctx.transaction(configuration -> {
            DSLContext tx = DSL.using(configuration);
            for (Integer tagId : tagIds){
                SelectConditionStep selStep = tx.selectFrom(ARTICLE_TAG_MAP)
                        .where(ARTICLE_TAG_MAP.DATA_ID.eq(dataId))
                        .and(ARTICLE_TAG_MAP.TAG_ID.eq(tagId));

                int tagIsSize = selStep.fetch().size();
                logger.info("tag id size: "+tagIsSize);
                if (tagIsSize == 0) {
                    InsertReturningStep insStep = tx.insertInto(ARTICLE_TAG_MAP)
                            .set(ARTICLE_TAG_MAP.CREATED, new Timestamp(DateTime.now().getMillis()))
                            .set(ARTICLE_TAG_MAP.DATA_ID, dataId)
                            .set(ARTICLE_TAG_MAP.TAG_ID, tagId)
                            .onConflictDoNothing();
                    logger.info("insert tag SQL: " + insStep);
                    Result<ArticleTagMapRecord> records = insStep.returning().fetch();
                    logger.info("records: " + records);
                    List<ArticleTagMap> articleTagMaps = records.stream().map(ArticleTagMap::new).collect(Collectors.toList());
                    for (ArticleTagMap a : articleTagMaps) {
                        articleTagMapList.add(a);
                    }
                    logger.info("article tag map list: " + articleTagMapList);
                    result.set(records.size());
                }
            }
        });
        return result.get();
    }

    public List<Tag> getArticleTags(String dataId) throws Exception {
        List<Tag> tags = null;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(ARTICLE_TAG_MAP)
                    .where(ARTICLE_TAG_MAP.DATA_ID.eq(dataId))
                    ;
            logger.info("get article tags map SQL: "+step);
            List<Integer> tagIds = step.fetch(ARTICLE_TAG_MAP.TAG_ID);
            logger.info("get article tag ids: "+tagIds);
            SelectConditionStep step1 = ctx.selectFrom(TAG)
                    .where(TAG.ID.in(tagIds))
                    ;
            logger.info("get article tags SQL: "+step1);
            Result<TagRecord> records = step1.fetch();
            logger.info("get article tags: "+records);
            tags = records.stream().map(Tag::new).collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
        return tags;
    }

    public Tag getTag(Integer tagId) throws Exception {
        List<Tag> tags = null;
        Tag tag = null;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(TAG)
                    .where(TAG.ID.eq(tagId))
                    ;
            logger.info("get article tag SQL: "+step);
            Result<TagRecord> records = step.fetch();
            logger.info("get article tag: "+records);
            tags = records.stream().map(Tag::new).collect(Collectors.toList());
            if (tags.size()>0){
                tag = tags.get(0);
            }
        }catch (Exception e){
            throw e;
        }
        return tag;
    }
}
