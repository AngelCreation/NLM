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
    "productId",
    "orderId",
    "qty",
    "title",
    "price"
})
public class Product implements Parcelable
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
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private String price;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Product> CREATOR = new Creator<Product>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        public Product[] newArray(int size) {
            return (new Product[size]);
        }

    }
    ;

    protected Product(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
        this.userid = ((String) in.readValue((String.class.getClassLoader())));
        this.addressId = ((String) in.readValue((String.class.getClassLoader())));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.status = ((String) in.readValue((String.class.getClassLoader())));
        this.payType = ((String) in.readValue((String.class.getClassLoader())));
        this.datetOfTme = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Product() {
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

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @JsonProperty("qty")
    public String getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(String qty) {
        this.qty = qty;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
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
        dest.writeValue(productId);
        dest.writeValue(orderId);
        dest.writeValue(qty);
        dest.writeValue(title);
        dest.writeValue(price);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
