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
    "orderId",
    "userId",
    "amount",
    "status",
    "datetOfTme"
})
public class OrderBean implements Parcelable
{

    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("status")
    private String status;
    @JsonProperty("datetOfTme")
    private String datetOfTme;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<OrderBean> CREATOR = new Creator<OrderBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderBean createFromParcel(Parcel in) {
            return new OrderBean(in);
        }

        public OrderBean[] newArray(int size) {
            return (new OrderBean[size]);
        }

    }
    ;

    protected OrderBean(Parcel in) {
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.datetOfTme = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public OrderBean() {
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
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

    @JsonProperty("datetOfTme")
    public String getDatetOfTme() {
        return datetOfTme;
    }

    @JsonProperty("datetOfTme")
    public void setDatetOfTme(String datetOfTme) {
        this.datetOfTme = datetOfTme;
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
        dest.writeValue(orderId);
        dest.writeValue(userId);
        dest.writeValue(amount);
        dest.writeValue(status);
        dest.writeValue(datetOfTme);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
