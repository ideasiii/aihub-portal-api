/*
 * This file is generated by jOOQ.
 */
package jooq.generated.elastic.crawler.aihub.tables.records;


import java.sql.Timestamp;

import javax.annotation.Generated;

import jooq.generated.elastic.crawler.aihub.enums.VenderIsDelete;
import jooq.generated.elastic.crawler.aihub.tables.Vender;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record11;
import org.jooq.Row11;
import org.jooq.impl.UpdatableRecordImpl;


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
public class VenderRecord extends UpdatableRecordImpl<VenderRecord> implements Record11<Integer, String, String, String, String, String, String, String, Timestamp, Timestamp, VenderIsDelete> {

    private static final long serialVersionUID = -1082809516;

    /**
     * Setter for <code>aihub.vender.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>aihub.vender.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>aihub.vender.name</code>. 廠商名稱
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>aihub.vender.name</code>. 廠商名稱
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>aihub.vender.vender_id</code>. 統編
     */
    public void setVenderId(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>aihub.vender.vender_id</code>. 統編
     */
    public String getVenderId() {
        return (String) get(2);
    }

    /**
     * Setter for <code>aihub.vender.vender_img</code>. 廠商圖像
     */
    public void setVenderImg(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>aihub.vender.vender_img</code>. 廠商圖像
     */
    public String getVenderImg() {
        return (String) get(3);
    }

    /**
     * Setter for <code>aihub.vender.description</code>. 簡介
     */
    public void setDescription(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>aihub.vender.description</code>. 簡介
     */
    public String getDescription() {
        return (String) get(4);
    }

    /**
     * Setter for <code>aihub.vender.phone_number</code>. 聯絡電話
     */
    public void setPhoneNumber(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>aihub.vender.phone_number</code>. 聯絡電話
     */
    public String getPhoneNumber() {
        return (String) get(5);
    }

    /**
     * Setter for <code>aihub.vender.address</code>. 住址
     */
    public void setAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>aihub.vender.address</code>. 住址
     */
    public String getAddress() {
        return (String) get(6);
    }

    /**
     * Setter for <code>aihub.vender.email</code>.
     */
    public void setEmail(String value) {
        set(7, value);
    }

    /**
     * Getter for <code>aihub.vender.email</code>.
     */
    public String getEmail() {
        return (String) get(7);
    }

    /**
     * Setter for <code>aihub.vender.created</code>. 建立日期
     */
    public void setCreated(Timestamp value) {
        set(8, value);
    }

    /**
     * Getter for <code>aihub.vender.created</code>. 建立日期
     */
    public Timestamp getCreated() {
        return (Timestamp) get(8);
    }

    /**
     * Setter for <code>aihub.vender.updated</code>. 修改時間
     */
    public void setUpdated(Timestamp value) {
        set(9, value);
    }

    /**
     * Getter for <code>aihub.vender.updated</code>. 修改時間
     */
    public Timestamp getUpdated() {
        return (Timestamp) get(9);
    }

    /**
     * Setter for <code>aihub.vender.is_delete</code>.
     */
    public void setIsDelete(VenderIsDelete value) {
        set(10, value);
    }

    /**
     * Getter for <code>aihub.vender.is_delete</code>.
     */
    public VenderIsDelete getIsDelete() {
        return (VenderIsDelete) get(10);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record11 type implementation
    // -------------------------------------------------------------------------

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, String, String, String, String, String, String, String, Timestamp, Timestamp, VenderIsDelete> fieldsRow() {
        return (Row11) super.fieldsRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Row11<Integer, String, String, String, String, String, String, String, Timestamp, Timestamp, VenderIsDelete> valuesRow() {
        return (Row11) super.valuesRow();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Integer> field1() {
        return Vender.VENDER.ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field2() {
        return Vender.VENDER.NAME;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field3() {
        return Vender.VENDER.VENDER_ID;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field4() {
        return Vender.VENDER.VENDER_IMG;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field5() {
        return Vender.VENDER.DESCRIPTION;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field6() {
        return Vender.VENDER.PHONE_NUMBER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field7() {
        return Vender.VENDER.ADDRESS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<String> field8() {
        return Vender.VENDER.EMAIL;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field9() {
        return Vender.VENDER.CREATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<Timestamp> field10() {
        return Vender.VENDER.UPDATED;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Field<VenderIsDelete> field11() {
        return Vender.VENDER.IS_DELETE;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer component1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component3() {
        return getVenderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component4() {
        return getVenderImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component5() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component6() {
        return getPhoneNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component7() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String component8() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component9() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp component10() {
        return getUpdated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderIsDelete component11() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer value1() {
        return getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value2() {
        return getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value3() {
        return getVenderId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value4() {
        return getVenderImg();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value5() {
        return getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value6() {
        return getPhoneNumber();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value7() {
        return getAddress();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String value8() {
        return getEmail();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value9() {
        return getCreated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp value10() {
        return getUpdated();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderIsDelete value11() {
        return getIsDelete();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value1(Integer value) {
        setId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value2(String value) {
        setName(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value3(String value) {
        setVenderId(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value4(String value) {
        setVenderImg(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value5(String value) {
        setDescription(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value6(String value) {
        setPhoneNumber(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value7(String value) {
        setAddress(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value8(String value) {
        setEmail(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value9(Timestamp value) {
        setCreated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value10(Timestamp value) {
        setUpdated(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord value11(VenderIsDelete value) {
        setIsDelete(value);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public VenderRecord values(Integer value1, String value2, String value3, String value4, String value5, String value6, String value7, String value8, Timestamp value9, Timestamp value10, VenderIsDelete value11) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        value8(value8);
        value9(value9);
        value10(value10);
        value11(value11);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached VenderRecord
     */
    public VenderRecord() {
        super(Vender.VENDER);
    }

    /**
     * Create a detached, initialised VenderRecord
     */
    public VenderRecord(Integer id, String name, String venderId, String venderImg, String description, String phoneNumber, String address, String email, Timestamp created, Timestamp updated, VenderIsDelete isDelete) {
        super(Vender.VENDER);

        set(0, id);
        set(1, name);
        set(2, venderId);
        set(3, venderImg);
        set(4, description);
        set(5, phoneNumber);
        set(6, address);
        set(7, email);
        set(8, created);
        set(9, updated);
        set(10, isDelete);
    }
}
