package iii.aihub.helper;

import iii.aihub.entity.article.Article;
import iii.aihub.entity.article.ArticleResult;
import iii.aihub.entity.article.ArticleSolutions;
import iii.aihub.entity.author.Author;
import iii.aihub.entity.solution.Solution;
import jooq.generated.elastic.crawler.aihub.enums.ArticleIsDelete;
import jooq.generated.elastic.crawler.aihub.enums.ArticleIsDisplay;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleRecord;
import jooq.generated.elastic.crawler.aihub.tables.records.ArticleSolutionsRecord;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.DateTime;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.*;

@Component
public class ArticleHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    @Autowired
    SolutionHelper solutionHelper;

    @Autowired
    AuthorHelper authorHelper;

    @Autowired
    TagHelper tagHelper;

    public String generateArticleId(String url){
        return DigestUtils.md5Hex(url+ DateTime.now());
    }

    public ArticleResult getDisplayableArtcle(Integer from, Integer size) throws Exception {
        return getArtcle("Y", from, size, false);
    }
    public ArticleResult getNotDisplayArtcle(Integer from, Integer size) throws Exception {
        return getArtcle("N", from, size, false);
    }

    public ArticleResult getDisplayablePublishedArtcle(Integer from, Integer size) throws Exception {
        return getArtcle("Y", from, size, true);
    }


    /**
     * 取得所有文章
     * @param isDisplay
     * @return
     * @throws Exception
     */
    public ArticleResult getArtcle(String isDisplay, Integer from, Integer size, Boolean published) throws Exception {
        List<Article> articleList = null;
        ArticleResult result = new ArticleResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(ARTICLE)
                    .where(ARTICLE.IS_DELETE.eq(ArticleIsDelete.N))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.valueOf(isDisplay)))
                    ;

            if (published){
                step = step.and(ARTICLE.PUBLISH_DATETIME.le(new Timestamp(DateTime.now().getMillis())));
            }
            SelectSeekStep1 step1 = step.orderBy(ARTICLE.CREATED.desc());
            Integer count = step1.fetch().size();
            if (size == null){
                size = 10;
                logger.warn("size setting to 10");
            }
            Result<ArticleRecord> records = null;
            if (from != null){
                SelectWithTiesAfterOffsetStep step2 = step1.limit(from, size);
                records = step2.fetch();
            }else {
                SelectLimitPercentStep step2 = step1.limit(size);
                records = step2.fetch();
            }
            logger.info("get article SQL: "+step);
            articleList = records.stream().map(Article::new).collect(Collectors.toList());
            enrichArticle(articleList);
            result.articleList = articleList;
            result.totalCount = count;
            result.queryDate = new Date(DateTime.now().getMillis());
        }catch (Exception e){
            //logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public List<Article> getDisplayableArticles(List<String> dataIds) throws Exception {
        return getArticles(dataIds, "Y");
    }

    public List<Article> getNotDisplayArticles(List<String> dataIds) throws Exception {
        return getArticles(dataIds, "N");
    }

    /**
     * 取得文章
     * @param dataId
     * @param isDisplay
     * @return
     * @throws Exception
     */
    public Article getArticle(String dataId, String isDisplay) throws Exception {
        List<Article> articleList = null;
        Article article = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(ARTICLE)
                    .where(ARTICLE.IS_DELETE.eq(ArticleIsDelete.N))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.Y))
                    .and(ARTICLE.DATA_ID.eq(dataId))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.valueOf(isDisplay)))
                    ;
            logger.info("get article SQL: "+step);
            //articleList = step.fetch().into(Article.class);
            Result<ArticleRecord> records = step.fetch();
            articleList = records.stream().map(Article::new).collect(Collectors.toList());
            enrichArticle(articleList);
            if (articleList.size() > 0){
                article = articleList.get(0);
            }
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return article;
    }

    public List<Article> getArticles(List<String> dataIds, String isDisplay) throws Exception {
        List<Article> articleList = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(ARTICLE)
                    .where(ARTICLE.IS_DELETE.eq(ArticleIsDelete.N))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.Y))
                    .and(ARTICLE.DATA_ID.in(dataIds))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.valueOf(isDisplay)))
                    ;
            logger.info("get articles SQL: "+step);
            //articleList = step.fetch().into(Article.class);
            Result<ArticleRecord> records = step.fetch();
            articleList = records.stream().map(Article::new).collect(Collectors.toList());
            enrichArticle(articleList);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return articleList;
    }

    public void enrichArticle(List<Article> articleList) throws Exception {
        Author author;
        for (Article article1 : articleList) {
            article1.solutionList = solutionHelper.getSolutionInArticle(article1.dataId);
            article1.tagList = tagHelper.getArticleTags(article1.dataId);
            article1.shortContent = _getShortContent(article1.content);
            author = authorHelper.getAuthor(article1.authorId);
            if (author != null) {
                article1.author = author.name;
            }
        }
    }

    private String _getShortContent(String content){
        return _getShortContent(content, 150);
    }

    private String _getShortContent(String content, int size){
        if (content == null){
            return "";
        }
        String parsedContent = _htmlParser(content);
        int len = parsedContent == null ? 0:parsedContent.length() > 150 ? 150:parsedContent.length();
        String shortContent = parsedContent.substring(0, len);
        //logger.info("short content: "+shortContent);
        return shortContent;
    }

    private String _htmlParser(String content){
        String parsedContent = "";
        Document doc = Jsoup.parse(content);
        Elements imgElements = doc.select("img");
        for (Element imge : doc.select("img")){
            imge.remove();
        }
        parsedContent = doc.text();
        //logger.info("parsed content: "+parsedContent);
        return parsedContent;
    }

    /**
     * 取得文章
     * @param memberId
     * @return
     * @throws Exception
     */
    public ArticleResult getArticleByMemberId(String memberId) throws Exception {
        List<Article> articleList = null;
        ArticleResult result = new ArticleResult();
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(ARTICLE)
                    .where(ARTICLE.IS_DELETE.eq(ArticleIsDelete.N))
                    .and(ARTICLE.IS_DISPLAY.eq(ArticleIsDisplay.Y))
                    .and(ARTICLE.MEMBER_ID.eq(memberId))
                    ;
            logger.info("get article with vender id SQL: "+step);
            Result<ArticleRecord> records = step.fetch();
            articleList = records.stream().map(Article::new).collect(Collectors.toList());
            result.articleList = articleList;
            result.totalCount = articleList.size();
            result.queryDate = new Date(DateTime.now().getMillis());
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }


    public int saveArticle(Article article) throws Exception {
        //List<Article> articleList = null;
        AtomicReference<Integer> result = new AtomicReference<>(new Integer(0));
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);

            ctx.transaction(configuration -> {
                DSLContext tx = DSL.using(configuration);
                ArticleRecord ar =  tx.newRecord(ARTICLE, article);
                logger.info("save article: "+ar);
                int res = tx.executeInsert(ar);
                //-- add solutions
                if (res > 0){
                    ArticleSolutionsRecord record = null;
                    ArticleSolutions as = null;
                    for (Solution solution : article.solutionList){
                        InsertReturningStep returningStep = tx.insertInto(ARTICLE_SOLUTIONS)
                                .set(ARTICLE_SOLUTIONS.ARTICLE_ID, article.dataId)
                                .set(ARTICLE_SOLUTIONS.SOLUTION_ID, solution.solutionId)
                                .onDuplicateKeyIgnore()
                                ;
                        logger.info("insert new article solutions SQL: "+returningStep);
                        result.set(returningStep.returning().fetch().size());
                        logger.info("insert new article solutions result: "+result);
                    }
                }

                //-- add tags
                /*
                List<ArticleTagMap> articleTagMapList = new ArrayList<>();
                if (!article.tagList.isEmpty()) {
                    articleTagMapList = tagHelper.addArticleTags(article.dataId, article.tagList, tx);
                }
                 */
                if (result.get() > 0) {
                    tagHelper.addArticleTags(article.dataId, article.tagList, tx);
                }
            });

        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result.get();
    }

    public int modifyArticle(Article article) throws Exception {
        List<Article> articleList = null;
        Integer result = 1;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            logger.info("article: "+article);
            ArticleRecord ar =  ctx.newRecord(ARTICLE, article);
            logger.info("update article: "+ar);

            ctx.transaction(configuration -> {
                DSLContext tx = DSL.using(configuration);
                int res = tx.update(ARTICLE).set(ar).where(ARTICLE.DATA_ID.eq(article.dataId)).execute();
                if (res > 0){
                    //-- 先清空 article_solutions
                    List<Solution> solutionList = null;
                    for (Solution solution : article.solutionList){
                        int deleteResult = tx.deleteFrom(ARTICLE_SOLUTIONS)
                                .where(ARTICLE_SOLUTIONS.ARTICLE_ID.eq(article.dataId))
                                .and(ARTICLE_SOLUTIONS.SOLUTION_ID.eq(solution.solutionId))
                                .execute();
                        logger.info("delete article_solutions result: "+deleteResult);
                    }

                    //-- 再寫入新的資料
                    for (Solution solution : article.solutionList) {
                        InsertReturningStep returningStep = tx.insertInto(ARTICLE_SOLUTIONS)
                                .set(ARTICLE_SOLUTIONS.ARTICLE_ID, article.dataId)
                                .set(ARTICLE_SOLUTIONS.SOLUTION_ID, solution.solutionId)
                                 .onDuplicateKeyIgnore()
                                ;
                        logger.info("insert new article solutions SQL: "+returningStep);
                        int insRes = returningStep.returning().fetch().size();
                        logger.info("insert new article solutions result: "+insRes);
                    }
                    //-- 處理標籤
                    tagHelper.modifyArticleTags(article.dataId, article.tagList, tx);
                }
            });
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public int deleteArticle(String dataId) throws Exception {
        Integer result = 0;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            result = ctx.update(ARTICLE)
                    .set(ARTICLE.IS_DELETE, ArticleIsDelete.Y)
                    .where(ARTICLE.DATA_ID.eq(dataId)).execute();
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public List<String> getArticleDataId(List<Integer> tags) throws Exception {
        List<String> tagList = new ArrayList<>();
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MARIADB);
            SelectConditionStep step = ctx.selectDistinct(
                    ARTICLE.DATA_ID
            )
                    .from(ARTICLE_TAG_MAP)
                    .join(ARTICLE).on(ARTICLE.DATA_ID.eq(ARTICLE_TAG_MAP.DATA_ID))
                    .where(ARTICLE_TAG_MAP.TAG_ID.in(tags))
                    ;
            logger.info("get article data id from tag id SQL: "+step);
            Result<Record1<String>> record1s = step.fetch();
            tagList = record1s.stream().map(d -> {
                return d.value1();
            }).collect(Collectors.toList());
        }catch (Exception e){
            throw e;
        }
        return tagList;
    }

    public List<Article> getArticleFromTags(List<Integer> tags, Integer from, Integer size) throws Exception {
        List<Article> articleList = new ArrayList<>();
        Result<ArticleRecord> records = null;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MARIADB);
            SelectConditionStep step = ctx.selectFrom(ARTICLE_TAG_MAP)
                    .where(ARTICLE_TAG_MAP.TAG_ID.in(tags))
                    ;
            List<String> dataIds = step.fetch(ARTICLE_TAG_MAP.DATA_ID);
            if (dataIds.size() > 0) {
                if (size == null)size = 10;
                step = ctx.selectFrom(ARTICLE)
                        .where(ARTICLE.DATA_ID.in(dataIds))
                        ;
                if (from != null){
                    SelectWithTiesAfterOffsetStep selectWithTiesAfterOffsetStep = step.limit(from, size);
                    records = selectWithTiesAfterOffsetStep.fetch();
                }else {
                    SelectLimitPercentStep selectLimitPercentStep = step.limit(size);
                    records = selectLimitPercentStep.fetch();
                }
                articleList = records.stream().map(Article::new).collect(Collectors.toList());
            }
        }catch (Exception e){
            throw e;
        }
        return articleList;
    }
}
