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
import jooq.generated.elastic.crawler.aihub.tables.records.VisitCounterRecord;

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
public class VisitCounter extends TableImpl<VisitCounterRecord> {

    private static final long serialVersionUID = 1789372581;

    /**
     * The reference instance of <code>aihub.visit_counter</code>
     */
    public static final VisitCounter VISIT_COUNTER = new VisitCounter();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VisitCounterRecord> getRecordType() {
        return VisitCounterRecord.class;
    }

    /**
     * The column <code>aihub.visit_counter.id</code>.
     */
    public final TableField<VisitCounterRecord, Integer> ID = createField("id", org.jooq.impl.SQLDataType.INTEGER.nullable(false).identity(true), this, "");

    /**
     * The column <code>aihub.visit_counter.counter</code>.
     */
    public final TableField<VisitCounterRecord, Integer> COUNTER = createField("counter", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

    /**
     * The column <code>aihub.visit_counter.mtime</code>.
     */
    public final TableField<VisitCounterRecord, Timestamp> MTIME = createField("mtime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>aihub.visit_counter</code> table reference
     */
    public VisitCounter() {
        this(DSL.name("visit_counter"), null);
    }

    /**
     * Create an aliased <code>aihub.visit_counter</code> table reference
     */
    public VisitCounter(String alias) {
        this(DSL.name(alias), VISIT_COUNTER);
    }

    /**
     * Create an aliased <code>aihub.visit_counter</code> table reference
     */
    public VisitCounter(Name alias) {
        this(alias, VISIT_COUNTER);
    }

    private VisitCounter(Name alias, Table<VisitCounterRecord> aliased) {
        this(alias, aliased, null);
    }

    private VisitCounter(Name alias, Table<VisitCounterRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> VisitCounter(Table<O> child, ForeignKey<O, VisitCounterRecord> key) {
        super(child, key, VISIT_COUNTER);
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
        return Arrays.<Index>asList(Indexes.VISIT_COUNTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Identity<VisitCounterRecord, Integer> getIdentity() {
        return Keys.IDENTITY_VISIT_COUNTER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VisitCounterRecord> getPrimaryKey() {
        return Keys.KEY_VISIT_COUNTER_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VisitCounterRecord>> getKeys() {
        return Arrays.<UniqueKey<VisitCounterRecord>>asList(Keys.KEY_VISIT_COUNTER_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitCounter as(String alias) {
        return new VisitCounter(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitCounter as(Name alias) {
        return new VisitCounter(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VisitCounter rename(String name) {
        return new VisitCounter(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public VisitCounter rename(Name name) {
        return new VisitCounter(name, null);
    }
}
