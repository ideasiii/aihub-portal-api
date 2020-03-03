package iii.aihub.entity.vender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import iii.aihub.utils.DateTimeUtils;
import jooq.generated.elastic.crawler.aihub.tables.records.VenderRecord;
import org.joda.time.DateTime;

import java.util.Date;

public class Vender {

    @JsonIgnore
    public Integer id;

    @SerializedName("name")
    public String name;

    @SerializedName("vender_id")
    public String venderId;

    @SerializedName("vender_img")
    public String venderImg;

    @SerializedName("phone_number")
    public String phoneNumber;

    @SerializedName("address")
    public String address;

    @SerializedName("email")
    public String email;

    @SerializedName("created")
    public Date created;

    @SerializedName("updated")
    public Date updated;

    @SerializedName("description")
    public String description;

    public String toString(){
        return org.apache.commons.lang3.builder.ReflectionToStringBuilder.reflectionToString(this);
    }

    public Vender() {
    }

    public Vender(VenderRecord r) {
        this.name = r.getName();
        this.address = r.getAddress();
        this.created = r.getCreated();
        this.email = r.getEmail();
        this.id = r.getId();
        this.phoneNumber = r.getPhoneNumber();
        this.updated = r.getUpdated();
        this.venderId = r.getVenderId();
        this.description = r.getDescription();
        this.venderImg = r.getVenderImg();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenderId() {
        return venderId;
    }

    public void setVenderId(String venderId) {
        this.venderId = venderId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public String getVenderImg() {
        return venderImg;
    }

    public void setVenderImg(String venderImg) {
        this.venderImg = venderImg;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
