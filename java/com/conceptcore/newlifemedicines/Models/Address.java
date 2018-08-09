package com.conceptcore.newlifemedicines.Models;

import java.util.HashMap;
import java.util.Map;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "userid",
    "addressId",
    "amount",
    "status",
    "payType",
    "datetOfTme",
    "name",
    "add1",
    "add2",
    "city",
    "state",
    "pin"
})
public class Address implements Parcelable
{

    @JsonProperty("id")
    private String id;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("addressId")
    private String addressId;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("payType")
    private String payType;
    @JsonProperty("datetOfTme")
    private String datetOfTme;
    @JsonProperty("name")
    private String name;
    @JsonProperty("add1")
    private String add1;
    @JsonProperty("add2")
    private String add2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("pin")
    private String pin;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Address> CREATOR = new Creator<Address>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Address createFromParcel(Parcel in) {
            return new Address(in);
        }

        public Address[] newArray(int size) {
            return (new Address[size]);
        }

    }
    ;

    protected Address(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userid = ((String) in.readValue((String.class.getClassLoader())));
        this.addressId = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.payType = ((String) in.readValue((String.class.getClassLoader())));
        this.datetOfTme = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.add1 = ((String) in.readValue((String.class.getClassLoader())));
        this.add2 = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.pin = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Address() {
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("userid")
    public String getUserid() {
        return userid;
    }

    @JsonProperty("userid")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JsonProperty("addressId")
    public String getAddressId() {
        return addressId;
    }

    @JsonProperty("addressId")
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("payType")
    public String getPayType() {
        return payType;
    }

    @JsonProperty("payType")
    public void setPayType(String payType) {
        this.payType = payType;
    }

    @JsonProperty("datetOfTme")
    public String getDatetOfTme() {
        return datetOfTme;
    }

    @JsonProperty("datetOfTme")
    public void setDatetOfTme(String datetOfTme) {
        this.datetOfTme = datetOfTme;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("add1")
    public String getAdd1() {
        return add1;
    }

    @JsonProperty("add1")
    public void setAdd1(String add1) {
        this.add1 = add1;
    }

    @JsonProperty("add2")
    public String getAdd2() {
        return add2;
    }

    @JsonProperty("add2")
    public void setAdd2(String add2) {
        this.add2 = add2;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("pin")
    public String getPin() {
        return pin;
    }

    @JsonProperty("pin")
    public void setPin(String pin) {
        this.pin = pin;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userid);
        dest.writeValue(addressId);
        dest.writeValue(amount);
        dest.writeValue(status);
        dest.writeValue(payType);
        dest.writeValue(datetOfTme);
        dest.writeValue(name);
        dest.writeValue(add1);
        dest.writeValue(add2);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(pin);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
