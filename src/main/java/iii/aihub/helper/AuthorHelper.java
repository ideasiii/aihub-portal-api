package iii.aihub.helper;

import iii.aihub.entity.author.Author;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import static jooq.generated.elastic.crawler.aihub.Tables.AUTHOR;

@Component
public class AuthorHelper {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    public List<Author> getAllAuthor() throws Exception {
        List<Author> authorList = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            authorList = ctx.selectFrom(AUTHOR).orderBy(AUTHOR.UPDATED.desc()).fetch().into(Author.class);
            logger.info("get all author: "+authorList);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return authorList;
    }

    public Author getAuthor(Integer id) throws Exception {
        Author author = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            List<Author> authorList = ctx.selectFrom(AUTHOR)
                    .where(AUTHOR.ID.eq(id))
                    .orderBy(AUTHOR.UPDATED.desc()).fetch().into(Author.class);
            logger.info("get all author: "+authorList);
            if (authorList.size() > 0){
                author = authorList.get(0);
            }
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return author;
    }

    public Integer saveAuthor(Author author) throws Exception {
        if (author == null){
            throw new Exception("author is null.");
        }
        Integer result = 0;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            InsertSetMoreStep step = ctx.insertInto(AUTHOR)
                    .set(AUTHOR.NAME, author.name)
                    .set(AUTHOR.CREATED, new Timestamp( DateTime.now().getMillis() ))
                    .set(AUTHOR.UPDATED, new Timestamp( DateTime.now().getMillis() ))
                    ;
            logger.info("save author SQL: "+step);
            result = step.execute();
            logger.info("save result: "+result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }
}
