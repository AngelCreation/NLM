package com.conceptcore.newlifemedicines.Models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "categoryId",
    "min",
    "max",
    "company"
})
public class FilterBean implements Parcelable
{

    @JsonProperty("min")
    private String min;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("max")
    private String max;
    @JsonProperty("company")
    private List<Company> company = new ArrayList<Company>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<FilterBean> CREATOR = new Creator<FilterBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public FilterBean createFromParcel(Parcel in) {
            return new FilterBean(in);
        }

        public FilterBean[] newArray(int size) {
            return (new FilterBean[size]);
        }

    }
    ;

    protected FilterBean(Parcel in) {
        this.min = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.max = ((String) in.readValue((String.class.getClassLoader())));
        in.readList(this.company, (Filter.class.getClassLoader()));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public FilterBean() {
    }

    @JsonProperty("min")
    public String getMin() {
        return min;
    }

    @JsonProperty("min")
    public void setMin(String min) {
        this.min = min;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonProperty("categoryId")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("max")
    public String getMax() {
        return max;
    }

    @JsonProperty("max")
    public void setMax(String max) {
        this.max = max;
    }

    @JsonProperty("company")
    public List<Company> getCompany() {
        return company;
    }

    @JsonProperty("company")
    public void setCompany(List<Company> company) {
        this.company = company;
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
        dest.writeValue(min);
        dest.writeValue(userId);
        dest.writeValue(categoryId);
        dest.writeValue(max);
        dest.writeList(company);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
