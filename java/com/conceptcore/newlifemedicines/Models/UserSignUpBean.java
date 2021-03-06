package com.conceptcore.newlifemedicines.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "success",
        "isActive",
        "userId",
        "firstName",
        "lastName",
        "email",
        "password",
        "mobileNo",
        "timeStamp",
        "userPhoto",
        "message",
        "fcmToken",
        "notiStatus",
        "wallet"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserSignUpBean implements Parcelable {

    public final static Creator<UserSignUpBean> CREATOR = new Creator<UserSignUpBean>() {


        @SuppressWarnings({
                "unchecked"
        })
        public UserSignUpBean createFromParcel(Parcel in) {
            return new UserSignUpBean(in);
        }

        public UserSignUpBean[] newArray(int size) {
            return (new UserSignUpBean[size]);
        }

    };
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("isActive")
    private Boolean isActive;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;
    @JsonProperty("mobileNo")
    private String mobileNo;
    @JsonProperty("timeStamp")
    private String timeStamp;
    @JsonProperty("userPhoto")
    private String userPhoto;
    @JsonProperty("message")
    private String message;
    @JsonProperty("fcmToken")
    private String fcmToken;
    @JsonProperty("notiStatus")
    private Integer notiStatus;
    @JsonProperty("wallet")
    private String wallet;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    protected UserSignUpBean(Parcel in) {
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.isActive = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.userId = ((String) in.readValue((Integer.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.password = ((String) in.readValue((String.class.getClassLoader())));
        this.mobileNo = ((String) in.readValue((String.class.getClassLoader())));
        this.timeStamp = ((String) in.readValue((String.class.getClassLoader())));
        this.userPhoto = ((String) in.readValue((String.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.fcmToken = ((String) in.readValue((String.class.getClassLoader())));
        this.notiStatus = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.wallet = ((String) in.readValue((Integer.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object>) in.readValue((Map.class.getClassLoader())));
    }

    public UserSignUpBean() {
    }

    @JsonProperty("success")
    public Boolean getSuccess() {
        return success;
    }

    @JsonProperty("success")
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @JsonProperty("isActive")
    public Boolean getIsActive() {
        return isActive;
    }

    @JsonProperty("isActive")
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("firstName")
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("firstName")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonProperty("lastName")
    public String getLastName() {
        return lastName;
    }

    @JsonProperty("lastName")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("password")
    public String getPassword() {
        return password;
    }

    @JsonProperty("password")
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonProperty("mobileNo")
    public String getMobileNo() {
        return mobileNo;
    }

    @JsonProperty("mobileNo")
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    @JsonProperty("timeStamp")
    public String getTimeStamp() {
        return timeStamp;
    }

    @JsonProperty("timeStamp")
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @JsonProperty("userPhoto")
    public String getUserPhoto() {
        return userPhoto;
    }

    @JsonProperty("userPhoto")
    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    @JsonProperty("message")
    public String getMessage() {
        return message;
    }

    @JsonProperty("message")
    public void setMessage(String message) {
        this.message = message;
    }

    @JsonProperty("fcmToken")
    public String getFcmToken() {
        return fcmToken;
    }

    @JsonProperty("fcmToken")
    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @JsonProperty("notiStatus")
    public Integer getNotiStatus() {
        return notiStatus;
    }

    @JsonProperty("notiStatus")
    public void setNotiStatus(Integer notiStatus) {
        this.notiStatus = notiStatus;
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
        dest.writeValue(success);
        dest.writeValue(isActive);
        dest.writeValue(userId);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(email);
        dest.writeValue(password);
        dest.writeValue(mobileNo);
        dest.writeValue(timeStamp);
        dest.writeValue(userPhoto);
        dest.writeValue(message);
        dest.writeValue(fcmToken);
        dest.writeValue(notiStatus);
        dest.writeValue(wallet);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}
