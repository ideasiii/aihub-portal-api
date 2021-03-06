/*
 * This file is generated by jOOQ.
 */
package jooq.generated.elastic.crawler.aihub;


import javax.annotation.Generated;

import jooq.generated.elastic.crawler.aihub.tables.Article;
import jooq.generated.elastic.crawler.aihub.tables.ArticleCategory;
import jooq.generated.elastic.crawler.aihub.tables.ArticleSolutions;
import jooq.generated.elastic.crawler.aihub.tables.ArticleSourceRef;
import jooq.generated.elastic.crawler.aihub.tables.ArticleTagMap;
import jooq.generated.elastic.crawler.aihub.tables.Author;
import jooq.generated.elastic.crawler.aihub.tables.Contact;
import jooq.generated.elastic.crawler.aihub.tables.Member;
import jooq.generated.elastic.crawler.aihub.tables.Solution;
import jooq.generated.elastic.crawler.aihub.tables.Tag;
import jooq.generated.elastic.crawler.aihub.tables.Vender;
import jooq.generated.elastic.crawler.aihub.tables.VisitCounter;
import jooq.generated.elastic.crawler.aihub.tables.VisitLog;

import org.jooq.Index;
import org.jooq.OrderField;
import org.jooq.impl.Internal;


/**
 * A class modelling indexes of tables of the <code>aihub</code> schema.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Indexes {

    // -------------------------------------------------------------------------
    // INDEX definitions
    // -------------------------------------------------------------------------

    public static final Index ARTICLE_AUTHOR = Indexes0.ARTICLE_AUTHOR;
    public static final Index ARTICLE_DATA_ID = Indexes0.ARTICLE_DATA_ID;
    public static final Index ARTICLE_PRIMARY = Indexes0.ARTICLE_PRIMARY;
    public static final Index ARTICLE_TITLE = Indexes0.ARTICLE_TITLE;
    public static final Index ARTICLE_CATEGORY_PRIMARY = Indexes0.ARTICLE_CATEGORY_PRIMARY;
    public static final Index ARTICLE_SOLUTIONS_ARTICLE_ID = Indexes0.ARTICLE_SOLUTIONS_ARTICLE_ID;
    public static final Index ARTICLE_SOLUTIONS_PRIMARY = Indexes0.ARTICLE_SOLUTIONS_PRIMARY;
    public static final Index ARTICLE_SOLUTIONS_SOLUTION_ID = Indexes0.ARTICLE_SOLUTIONS_SOLUTION_ID;
    public static final Index ARTICLE_SOURCE_REF_ARTICLE_ID = Indexes0.ARTICLE_SOURCE_REF_ARTICLE_ID;
    public static final Index ARTICLE_SOURCE_REF_PRIMARY = Indexes0.ARTICLE_SOURCE_REF_PRIMARY;
    public static final Index ARTICLE_TAG_MAP_PRIMARY = Indexes0.ARTICLE_TAG_MAP_PRIMARY;
    public static final Index AUTHOR_PRIMARY = Indexes0.AUTHOR_PRIMARY;
    public static final Index CONTACT_PRIMARY = Indexes0.CONTACT_PRIMARY;
    public static final Index MEMBER_CELL_PHONE = Indexes0.MEMBER_CELL_PHONE;
    public static final Index MEMBER_EMAIL = Indexes0.MEMBER_EMAIL;
    public static final Index MEMBER_FIRST_NAME = Indexes0.MEMBER_FIRST_NAME;
    public static final Index MEMBER_LAST_NAME = Indexes0.MEMBER_LAST_NAME;
    public static final Index MEMBER_MEMBER_ID = Indexes0.MEMBER_MEMBER_ID;
    public static final Index MEMBER_PRIMARY = Indexes0.MEMBER_PRIMARY;
    public static final Index SOLUTION_PRIMARY = Indexes0.SOLUTION_PRIMARY;
    public static final Index SOLUTION_SOLUTIONID = Indexes0.SOLUTION_SOLUTIONID;
    public static final Index TAG_PRIMARY = Indexes0.TAG_PRIMARY;
    public static final Index TAG_TAG_NAME = Indexes0.TAG_TAG_NAME;
    public static final Index VENDER_PRIMARY = Indexes0.VENDER_PRIMARY;
    public static final Index VISIT_COUNTER_PRIMARY = Indexes0.VISIT_COUNTER_PRIMARY;
    public static final Index VISIT_LOG_IP = Indexes0.VISIT_LOG_IP;
    public static final Index VISIT_LOG_PRIMARY = Indexes0.VISIT_LOG_PRIMARY;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class Indexes0 {
        public static Index ARTICLE_AUTHOR = Internal.createIndex("author", Article.ARTICLE, new OrderField[] { Article.ARTICLE.AUTHOR }, false);
        public static Index ARTICLE_DATA_ID = Internal.createIndex("data_id", Article.ARTICLE, new OrderField[] { Article.ARTICLE.DATA_ID }, false);
        public static Index ARTICLE_PRIMARY = Internal.createIndex("PRIMARY", Article.ARTICLE, new OrderField[] { Article.ARTICLE.ID }, true);
        public static Index ARTICLE_TITLE = Internal.createIndex("title", Article.ARTICLE, new OrderField[] { Article.ARTICLE.TITLE }, false);
        public static Index ARTICLE_CATEGORY_PRIMARY = Internal.createIndex("PRIMARY", ArticleCategory.ARTICLE_CATEGORY, new OrderField[] { ArticleCategory.ARTICLE_CATEGORY.ID }, true);
        public static Index ARTICLE_SOLUTIONS_ARTICLE_ID = Internal.createIndex("article_id", ArticleSolutions.ARTICLE_SOLUTIONS, new OrderField[] { ArticleSolutions.ARTICLE_SOLUTIONS.ARTICLE_ID }, false);
        public static Index ARTICLE_SOLUTIONS_PRIMARY = Internal.createIndex("PRIMARY", ArticleSolutions.ARTICLE_SOLUTIONS, new OrderField[] { ArticleSolutions.ARTICLE_SOLUTIONS.ID }, true);
        public static Index ARTICLE_SOLUTIONS_SOLUTION_ID = Internal.createIndex("solution_id", ArticleSolutions.ARTICLE_SOLUTIONS, new OrderField[] { ArticleSolutions.ARTICLE_SOLUTIONS.SOLUTION_ID }, false);
        public static Index ARTICLE_SOURCE_REF_ARTICLE_ID = Internal.createIndex("article_id", ArticleSourceRef.ARTICLE_SOURCE_REF, new OrderField[] { ArticleSourceRef.ARTICLE_SOURCE_REF.ARTICLE_ID }, false);
        public static Index ARTICLE_SOURCE_REF_PRIMARY = Internal.createIndex("PRIMARY", ArticleSourceRef.ARTICLE_SOURCE_REF, new OrderField[] { ArticleSourceRef.ARTICLE_SOURCE_REF.ID }, true);
        public static Index ARTICLE_TAG_MAP_PRIMARY = Internal.createIndex("PRIMARY", ArticleTagMap.ARTICLE_TAG_MAP, new OrderField[] { ArticleTagMap.ARTICLE_TAG_MAP.ID }, true);
        public static Index AUTHOR_PRIMARY = Internal.createIndex("PRIMARY", Author.AUTHOR, new OrderField[] { Author.AUTHOR.ID }, true);
        public static Index CONTACT_PRIMARY = Internal.createIndex("PRIMARY", Contact.CONTACT, new OrderField[] { Contact.CONTACT.ID }, true);
        public static Index MEMBER_CELL_PHONE = Internal.createIndex("cell_phone", Member.MEMBER, new OrderField[] { Member.MEMBER.CELL_PHONE }, false);
        public static Index MEMBER_EMAIL = Internal.createIndex("email", Member.MEMBER, new OrderField[] { Member.MEMBER.EMAIL }, true);
        public static Index MEMBER_FIRST_NAME = Internal.createIndex("first_name", Member.MEMBER, new OrderField[] { Member.MEMBER.FIRST_NAME }, false);
        public static Index MEMBER_LAST_NAME = Internal.createIndex("last_name", Member.MEMBER, new OrderField[] { Member.MEMBER.LAST_NAME }, false);
        public static Index MEMBER_MEMBER_ID = Internal.createIndex("member_id", Member.MEMBER, new OrderField[] { Member.MEMBER.MEMBER_ID }, false);
        public static Index MEMBER_PRIMARY = Internal.createIndex("PRIMARY", Member.MEMBER, new OrderField[] { Member.MEMBER.ID }, true);
        public static Index SOLUTION_PRIMARY = Internal.createIndex("PRIMARY", Solution.SOLUTION, new OrderField[] { Solution.SOLUTION.ID }, true);
        public static Index SOLUTION_SOLUTIONID = Internal.createIndex("solutionId", Solution.SOLUTION, new OrderField[] { Solution.SOLUTION.SOLUTIONID }, false);
        public static Index TAG_PRIMARY = Internal.createIndex("PRIMARY", Tag.TAG, new OrderField[] { Tag.TAG.ID }, true);
        public static Index TAG_TAG_NAME = Internal.createIndex("tag_name", Tag.TAG, new OrderField[] { Tag.TAG.TAG_NAME }, false);
        public static Index VENDER_PRIMARY = Internal.createIndex("PRIMARY", Vender.VENDER, new OrderField[] { Vender.VENDER.ID }, true);
        public static Index VISIT_COUNTER_PRIMARY = Internal.createIndex("PRIMARY", VisitCounter.VISIT_COUNTER, new OrderField[] { VisitCounter.VISIT_COUNTER.ID }, true);
        public static Index VISIT_LOG_IP = Internal.createIndex("ip", VisitLog.VISIT_LOG, new OrderField[] { VisitLog.VISIT_LOG.IP }, false);
        public static Index VISIT_LOG_PRIMARY = Internal.createIndex("PRIMARY", VisitLog.VISIT_LOG, new OrderField[] { VisitLog.VISIT_LOG.SESSION_ID }, true);
    }
}
