/*
 * This file is generated by jOOQ.
 */
package jooq.generated.elastic.crawler.aihub.tables;


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import jooq.generated.elastic.crawler.aihub.Aihub;
import jooq.generated.elastic.crawler.aihub.Indexes;
import jooq.generated.elastic.crawler.aihub.Keys;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete;
import jooq.generated.elastic.crawler.aihub.enums.MemberIsVerified;
import jooq.generated.elastic.crawler.aihub.tables.records.MemberRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.Index;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;


/**
 * 會員列表
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Member extends TableImpl<MemberRecord> {

    private static final long serialVersionUID = 1110824014;

    /**
     * The reference instance of <code>aihub.member</code>
     */
    public static final Member MEMBER = new Member();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MemberRecord> getRecordType() {
        return MemberRecord.class;
    }

    /**
     * The column <code>aihub.member.id</code>.
     */
    public final TableField<MemberRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>aihub.member.member_id</code>.
     */
    public final TableField<MemberRecord, String> MEMBER_ID = createField("member_id", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>aihub.member.first_name</code>. 姓
     */
    public final TableField<MemberRecord, String> FIRST_NAME = createField("first_name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "姓");

    /**
     * The column <code>aihub.member.last_name</code>. 名
     */
    public final TableField<MemberRecord, String> LAST_NAME = createField("last_name", org.jooq.impl.SQLDataType.VARCHAR(50), this, "名");

    /**
     * The column <code>aihub.member.password</code>. 密碼
     */
    public final TableField<MemberRecord, String> PASSWORD = createField("password", org.jooq.impl.SQLDataType.VARCHAR(50), this, "密碼");

    /**
     * The column <code>aihub.member.cell_phone</code>. 手機號碼
     */
    public final TableField<MemberRecord, String> CELL_PHONE = createField("cell_phone", org.jooq.impl.SQLDataType.VARCHAR(50), this, "手機號碼");

    /**
     * The column <code>aihub.member.email</code>. email
     */
    public final TableField<MemberRecord, String> EMAIL = createField("email", org.jooq.impl.SQLDataType.VARCHAR(50).nullable(false), this, "email");

    /**
     * The column <code>aihub.member.created</code>.
     */
    public final TableField<MemberRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>aihub.member.updated</code>.
     */
    public final TableField<MemberRecord, Timestamp> UPDATED = createField("updated", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>aihub.member.is_delete</code>.
     */
    public final TableField<MemberRecord, MemberIsDelete> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1).defaultValue(org.jooq.impl.DSL.field("N", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(jooq.generated.elastic.crawler.aihub.enums.MemberIsDelete.class), this, "");

    /**
     * The column <code>aihub.member.is_verified</code>. 開通
     */
    public final TableField<MemberRecord, MemberIsVerified> IS_VERIFIED = createField("is_verified", org.jooq.impl.SQLDataType.VARCHAR(1).defaultValue(org.jooq.impl.DSL.field("N", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(jooq.generated.elastic.crawler.aihub.enums.MemberIsVerified.class), this, "開通");

    /**
     * Create a <code>aihub.member</code> table reference
     */
    public Member() {
        this(DSL.name("member"), null);
    }

    /**
     * Create an aliased <code>aihub.member</code> table reference
     */
    public Member(String alias) {
        this(DSL.name(alias), MEMBER);
    }

    /**
     * Create an aliased <code>aihub.member</code> table reference
     */
    public Member(Name alias) {
        this(alias, MEMBER);
    }

    private Member(Name alias, Table<MemberRecord> aliased) {
        this(alias, aliased, null);
    }

    private Member(Name alias, Table<MemberRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment("會員列表"));
    }

    public <O extends Record> Member(Table<O> child, ForeignKey<O, MemberRecord> key) {
        super(child, key, MEMBER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Schema getSchema() {
        return Aihub.AIHUB;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.MEMBER_CELL_PHONE, Indexes.MEMBER_EMAIL, Indexes.MEMBER_FIRST_NAME, Indexes.MEMBER_LAST_NAME, Indexes.MEMBER_MEMBER_ID, Indexes.MEMBER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<MemberRecord, Integer> getIdentity() {
        return Keys.IDENTITY_MEMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<MemberRecord> getPrimaryKey() {
        return Keys.KEY_MEMBER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<MemberRecord>> getKeys() {
        return Arrays.<UniqueKey<MemberRecord>>asList(Keys.KEY_MEMBER_PRIMARY, Keys.KEY_MEMBER_EMAIL);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member as(String alias) {
        return new Member(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Member as(Name alias) {
        return new Member(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Member rename(String name) {
        return new Member(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Member rename(Name name) {
        return new Member(name, null);
    }
}
