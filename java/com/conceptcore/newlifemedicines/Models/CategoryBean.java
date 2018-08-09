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
    "categoryId",
    "title",
    "imagePath"
})
public class CategoryBean implements Parcelable
{

    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("imagePath")
    private String imagePath;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CategoryBean> CREATOR = new Creator<CategoryBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CategoryBean createFromParcel(Parcel in) {
            return new CategoryBean(in);
        }

        public CategoryBean[] newArray(int size) {
            return (new CategoryBean[size]);
        }

    }
    ;

    protected CategoryBean(Parcel in) {
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePath = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CategoryBean() {
    }

    @JsonProperty("categoryId")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonProperty("imagePath")
    public String getImagePath() {
        return imagePath;
    }

    @JsonProperty("imagePath")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
        dest.writeValue(categoryId);
        dest.writeValue(title);
        dest.writeValue(imagePath);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
