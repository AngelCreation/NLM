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
    "addressId",
    "name",
    "pin",
    "add1",
    "add2",
    "city",
    "state",
    "mobileNo"
})
public class CartAddress implements Parcelable
{

    @JsonProperty("addressId")
    private String addressId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("pin")
    private String pin;
    @JsonProperty("add1")
    private String add1;
    @JsonProperty("add2")
    private String add2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CartAddress> CREATOR = new Creator<CartAddress>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CartAddress createFromParcel(Parcel in) {
            return new CartAddress(in);
        }

        public CartAddress[] newArray(int size) {
            return (new CartAddress[size]);
        }

    }
    ;

    protected CartAddress(Parcel in) {
        this.addressId = ((String) in.readValue((String.class.getClassLoader())));
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.pin = ((String) in.readValue((String.class.getClassLoader())));
        this.add1 = ((String) in.readValue((String.class.getClassLoader())));
        this.add2 = ((String) in.readValue((String.class.getClassLoader())));
        this.city = ((String) in.readValue((String.class.getClassLoader())));
        this.state = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileNo = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CartAddress() {
    }

    @JsonProperty("addressId")
    public String getAddressId() {
        return addressId;
    }

    @JsonProperty("addressId")
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("pin")
    public String getPin() {
        return pin;
    }

    @JsonProperty("pin")
    public void setPin(String pin) {
        this.pin = pin;
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

    @JsonProperty("mobileNo")
    public String getMobileNo() {
        return mobileNo;
    }

    @JsonProperty("mobileNo")
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
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
        dest.writeValue(addressId);
        dest.writeValue(name);
        dest.writeValue(pin);
        dest.writeValue(add1);
        dest.writeValue(add2);
        dest.writeValue(city);
        dest.writeValue(state);
        dest.writeValue(mobileNo);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
