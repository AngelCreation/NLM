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
    "userId",
    "addressId",
    "useWallet",
    "payType",
    "finalPay",
    "success",
    "message",
    "wallet"
})
public class CheckOutBean implements Parcelable
{

    @JsonProperty("userId")
    private String userId;
    @JsonProperty("addressId")
    private String addressId;
    @JsonProperty("useWallet")
    private Boolean useWallet;
    @JsonProperty("payType")
    private String payType;
    @JsonProperty("finalPay")
    private String finalPay;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("wallet")
    private String wallet;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CheckOutBean> CREATOR = new Creator<CheckOutBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CheckOutBean createFromParcel(Parcel in) {
            return new CheckOutBean(in);
        }

        public CheckOutBean[] newArray(int size) {
            return (new CheckOutBean[size]);
        }

    }
    ;

    protected CheckOutBean(Parcel in) {
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.addressId = ((String) in.readValue((String.class.getClassLoader())));
        this.useWallet = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.payType = ((String) in.readValue((String.class.getClassLoader())));
        this.finalPay = ((String) in.readValue((String.class.getClassLoader())));
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.wallet = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CheckOutBean() {
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("addressId")
    public String getAddressId() {
        return addressId;
    }

    @JsonProperty("addressId")
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    @JsonProperty("useWallet")
    public Boolean getUseWallet() {
        return useWallet;
    }

    @JsonProperty("useWallet")
    public void setUseWallet(Boolean useWallet) {
        this.useWallet = useWallet;
    }

    @JsonProperty("payType")
    public String getPayType() {
        return payType;
    }

    @JsonProperty("payType")
    public void setPayType(String payType) {
        this.payType = payType;
    }

    @JsonProperty("finalPay")
    public String getFinalPay() {
        return finalPay;
    }

    @JsonProperty("finalPay")
    public void setFinalPay(String finalPay) {
        this.finalPay = finalPay;
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("wallet")
    public String getWallet() {
        return wallet;
    }

    @JsonProperty("wallet")
    public void setWallet(String wallet) {
        this.wallet = wallet;
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
        dest.writeValue(userId);
        dest.writeValue(addressId);
        dest.writeValue(useWallet);
        dest.writeValue(payType);
        dest.writeValue(finalPay);
        dest.writeValue(success);
        dest.writeValue(message);
        dest.writeValue(wallet);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
