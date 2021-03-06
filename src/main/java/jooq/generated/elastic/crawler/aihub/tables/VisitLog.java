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
import jooq.generated.elastic.crawler.aihub.tables.records.VisitLogRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class VisitLog extends TableImpl<VisitLogRecord> {

    private static final long serialVersionUID = 75539980;

    /**
     * The reference instance of <code>aihub.visit_log</code>
     */
    public static final VisitLog VISIT_LOG = new VisitLog();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<VisitLogRecord> getRecordType() {
        return VisitLogRecord.class;
    }

    /**
     * The column <code>aihub.visit_log.ip</code>.
     */
    public final TableField<VisitLogRecord, String> IP = createField("ip", org.jooq.impl.SQLDataType.VARCHAR(20).nullable(false), this, "");

    /**
     * The column <code>aihub.visit_log.session_id</code>.
     */
    public final TableField<VisitLogRecord, String> SESSION_ID = createField("session_id", org.jooq.impl.SQLDataType.VARCHAR(64).nullable(false), this, "");

    /**
     * The column <code>aihub.visit_log.ctime</code>.
     */
    public final TableField<VisitLogRecord, Timestamp> CTIME = createField("ctime", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false), this, "");

    /**
     * Create a <code>aihub.visit_log</code> table reference
     */
    public VisitLog() {
        this(DSL.name("visit_log"), null);
    }

    /**
     * Create an aliased <code>aihub.visit_log</code> table reference
     */
    public VisitLog(String alias) {
        this(DSL.name(alias), VISIT_LOG);
    }

    /**
     * Create an aliased <code>aihub.visit_log</code> table reference
     */
    public VisitLog(Name alias) {
        this(alias, VISIT_LOG);
    }

    private VisitLog(Name alias, Table<VisitLogRecord> aliased) {
        this(alias, aliased, null);
    }

    private VisitLog(Name alias, Table<VisitLogRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> VisitLog(Table<O> child, ForeignKey<O, VisitLogRecord> key) {
        super(child, key, VISIT_LOG);
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
        return Arrays.<Index>asList(Indexes.VISIT_LOG_IP, Indexes.VISIT_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UniqueKey<VisitLogRecord> getPrimaryKey() {
        return Keys.KEY_VISIT_LOG_PRIMARY;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UniqueKey<VisitLogRecord>> getKeys() {
        return Arrays.<UniqueKey<VisitLogRecord>>asList(Keys.KEY_VISIT_LOG_PRIMARY);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitLog as(String alias) {
        return new VisitLog(DSL.name(alias), this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VisitLog as(Name alias) {
        return new VisitLog(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public VisitLog rename(String name) {
        return new VisitLog(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public VisitLog rename(Name name) {
        return new VisitLog(name, null);
    }
}
