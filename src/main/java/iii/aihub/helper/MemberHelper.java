package iii.aihub.helper;

import iii.aihub.entity.member.Member;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsVerified;
import jooq.generated.elastic.crawler.aihub.tables.records.MemberRecord;
import org.apache.camel.Exchange;
import org.apache.commons.codec.digest.DigestUtils;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import static jooq.generated.elastic.crawler.aihub.Tables.MEMBER;

@Component
public class MemberHelper {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DataSource dataSource;

    private static String ENCRYPT_KEY = "AiHuB";

    public static String getMemberId(String email){
        return DigestUtils.md5Hex(email+"_aihub");
    }

    public Member getMember(String memberId) throws Exception {
        Member member = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(MEMBER)
                    .where(MEMBER.MEMBER_ID.eq(memberId))
                    ;
            logger.info("get member SQL: "+step);
            Result<MemberRecord> records = step.fetch();
            List<Member> list = records.stream().map(Member::new).collect(Collectors.toList());
            if (list.size() > 0){
                member = list.get(0);
            }else {
                throw new Exception("member is empty");
            }
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return member;
    }

    public List<Member> getAllMember() throws Exception {
        List<Member> memberList = null;

        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectSeekStep1 step = ctx.selectFrom(MEMBER).orderBy(MEMBER.CREATED.desc());
            logger.info("list member SQL: "+step);
            Result<MemberRecord> records = step.fetch();
            memberList = records.stream().map(Member::new).collect(Collectors.toList());
            //memberList = step.fetch().into(Member.class);
            logger.info("members: "+memberList);

        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return memberList;
    }

    public String genMemberPassword(String password) throws Exception {
        String passwd = null;
        try {
            passwd = DigestUtils.md5Hex(password+"ai-hub");
        } catch (Exception e) {
           logger.error(e.getLocalizedMessage(), e);
           throw new Exception(e);
        }
        return passwd;
    }

    public int saveMember(Member member) throws Exception {
        if (member == null){
            throw new Exception("member data is null");
        }
        if (member.email == null){
            throw new Exception("member email is null");
        }
        Integer result = 0;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            InsertSetMoreStep step = ctx.insertInto(MEMBER)
                    .set(MEMBER.CELL_PHONE, member.cellPhone)
                    .set(MEMBER.EMAIL, member.email)
                    .set(MEMBER.FIRST_NAME, member.firstName)
                    .set(MEMBER.LAST_NAME, member.lastName)
                    .set(MEMBER.CREATED, new Timestamp(member.created.getTime()))
                    .set(MEMBER.IS_DELETE, MemberIsDelete.N)
                    .set(MEMBER.PASSWORD, this.genMemberPassword(member.password))
                    .set(MEMBER.UPDATED, new Timestamp(member.updated.getTime()))
                    .set(MEMBER.IS_VERIFIED, MemberIsVerified.valueOf(member.isVerify))
                    .set(MEMBER.MEMBER_ID, getMemberId(member.email))
                    ;
            logger.info("add member SQL: "+step);
            result = step.execute();
            logger.info("add member result: "+result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public Member loginCheck(String email, String password) throws Exception {
        if (email == null){
            throw new Exception("email is null");
        }
        if (password == null){
            throw new Exception("password is null");
        }

        Member result = null;
        try(Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MYSQL);
            SelectConditionStep step = ctx.selectFrom(MEMBER)
                    .where(MEMBER.EMAIL.eq(email))
                    .and(MEMBER.PASSWORD.eq(this.genMemberPassword(password)))
                    .and(MEMBER.IS_VERIFIED.eq(MemberIsVerified.Y))
                    .and(MEMBER.IS_DELETE.eq(MemberIsDelete.N))
                    ;
            logger.info("login check member SQL: "+step);
            Result<MemberRecord> memberRecords = step.fetch();
            List<Member> memberList = memberRecords.stream().map(Member::new).collect(Collectors.toList());
            int size = memberList.size();
            String err = "";
            if (size == 0){
                err = "login fail, email: "+email+", password: "+password;
                logger.warn(err);
                throw new Exception(err);
            }else{
                result = memberList.get(0);
            }
            logger.info("add member result: "+result);
        }catch (Exception e){
            logger.error(e.getLocalizedMessage(), e);
            throw new Exception(e);
        }
        return result;
    }

    public String encrypt(String encodeString) throws Exception {
        SecretKeySpec secretKeySpec = getSecretKeySpec(ENCRYPT_KEY);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

        return Base64.getEncoder().encodeToString(cipher.doFinal(encodeString.getBytes()));
    }

    private SecretKeySpec getSecretKeySpec(String key) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest sha = null;
        byte[] keyBytes = null;
        SecretKeySpec secretKeySpec = null;
        try {
            keyBytes = key.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            keyBytes = sha.digest(keyBytes);
            keyBytes = Arrays.copyOf(keyBytes, 16);
            secretKeySpec = new SecretKeySpec(keyBytes, "AES");
        }catch (Exception e){
            throw e;
        }
        return secretKeySpec;
    }

    public String decrypt(String decodeString) throws Exception {
        SecretKeySpec secretKeySpec = getSecretKeySpec(ENCRYPT_KEY);
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(decodeString)));
    }

    public String forgetPwdVerifyEmail(String email) throws SQLException {
        String memberId = null;
        try (Connection conn = dataSource.getConnection()){
            DSLContext ctx = DSL.using(conn, SQLDialect.MARIADB);
            List<String> memberIds = ctx.selectFrom(MEMBER)
                    .where(MEMBER.EMAIL.eq(email))
                    .fetch(MEMBER.MEMBER_ID);
            if (memberIds.size() > 0){
                memberId = memberIds.get(0);
            }
        }catch (Exception e){
            throw e;
        }
        return memberId;
    }

}
