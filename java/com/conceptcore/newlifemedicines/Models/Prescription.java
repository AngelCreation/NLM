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
        "presImg",
        "presTitle",
        "presComments"
})
public class Prescription implements Parcelable
{

    @JsonProperty("presImg")
    private String presImg;
    @JsonProperty("presTitle")
    private String presTitle;
    @JsonProperty("presComments")
    private String presComments;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Parcelable.Creator<Prescription> CREATOR = new Creator<Prescription>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Prescription createFromParcel(Parcel in) {
            return new Prescription(in);
        }

        public Prescription[] newArray(int size) {
            return (new Prescription[size]);
        }

    }
            ;

    protected Prescription(Parcel in) {
        this.presImg = ((String) in.readValue((String.class.getClassLoader())));
        this.presTitle = ((String) in.readValue((String.class.getClassLoader())));
        this.presComments = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Prescription() {
    }

    @JsonProperty("presImg")
    public String getPresImg() {
        return presImg;
    }

    @JsonProperty("presImg")
    public void setPresImg(String presImg) {
        this.presImg = presImg;
    }

    @JsonProperty("presTitle")
    public String getPresTitle() {
        return presTitle;
    }

    @JsonProperty("presTitle")
    public void setPresTitle(String presTitle) {
        this.presTitle = presTitle;
    }

    @JsonProperty("presComments")
    public String getPresComments() {
        return presComments;
    }

    @JsonProperty("presComments")
    public void setPresComments(String presComments) {
        this.presComments = presComments;
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
        dest.writeValue(presImg);
        dest.writeValue(presTitle);
        dest.writeValue(presComments);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return 0;
    }

}