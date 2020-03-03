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
import jooq.generated.elastic.crawler.aihub.enums.TagIsDelete;
import jooq.generated.elastic.crawler.aihub.tables.records.TagRecord;

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
 * This class is generated by jOOQ.
 */
@Generated(
    value = {
        "http://www.jooq.org",
        "jOOQ version:3.11.7"
    },
    comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tag extends TableImpl<TagRecord> {

    private static final long serialVersionUID = 1214697391;

    /**
     * The reference instance of <code>aihub.tag</code>
     */
    public static final Tag TAG = new Tag();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<TagRecord> getRecordType() {
        return TagRecord.class;
    }

    /**
     * The column <code>aihub.tag.id</code>.
     */
    public final TableField<TagRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>aihub.tag.tag_name</code>.
     */
    public final TableField<TagRecord, String> TAG_NAME = createField("tag_name", org.jooq.impl.SQLDataType.VARCHAR(200).nullable(false), this, "");

    /**
     * The column <code>aihub.tag.description</code>.
     */
    public final TableField<TagRecord, String> DESCRIPTION = createField("description", org.jooq.impl.SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>aihub.tag.created</code>.
     */
    public final TableField<TagRecord, Timestamp> CREATED = createField("created", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>aihub.tag.updated</code>.
     */
    public final TableField<TagRecord, Timestamp> UPDATED = createField("updated", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaultValue(org.jooq.impl.DSL.field("CURRENT_TIMESTAMP", org.jooq.impl.SQLDataType.TIMESTAMP)), this, "");

    /**
     * The column <code>aihub.tag.is_delete</code>.
     */
    public final TableField<TagRecord, TagIsDelete> IS_DELETE = createField("is_delete", org.jooq.impl.SQLDataType.VARCHAR(1).nullable(false).defaultValue(org.jooq.impl.DSL.field("N", org.jooq.impl.SQLDataType.VARCHAR)).asEnumDataType(jooq.generated.elastic.crawler.aihub.enums.TagIsDelete.class), this, "");

    /**
     * Create a <code>aihub.tag</code> table reference
     */
    public Tag() {
        this(DSL.name("tag"), null);
    }

    /**
     * Create an aliased <code>aihub.tag</code> table reference
     */
    public Tag(String alias) {
        this(DSL.name(alias), TAG);
    }

    /**
     * Create an aliased <code>aihub.tag</code> table reference
     */
    public Tag(Name alias) {
        this(alias, TAG);
    }

    private Tag(Name alias, Table<TagRecord> aliased) {
        this(alias, aliased, null);
    }

    private Tag(Name alias, Table<TagRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Tag(Table<O> child, ForeignKey<O, TagRecord> key) {
        super(child, key, TAG);
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
        return Arrays.<Index>asList(Indexes.TAG_PRIMARY, Indexes.TAG_TAG_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<TagRecord, Integer> getIdentity() {
        return Keys.IDENTITY_TAG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<TagRecord> getPrimaryKey() {
        return Keys.KEY_TAG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<TagRecord>> getKeys() {
        return Arrays.<UniqueKey<TagRecord>>asList(Keys.KEY_TAG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag as(String alias) {
        return new Tag(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Tag as(Name alias) {
        return new Tag(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Tag rename(String name) {
        return new Tag(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Tag rename(Name name) {
        return new Tag(name, null);
    }
}
