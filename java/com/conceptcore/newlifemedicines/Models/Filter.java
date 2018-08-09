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
    "name",
    "selected",
    "compayId"
})
public class Filter implements Parcelable
{

    @JsonProperty("name")
    private String name;
    @JsonProperty("compayId")
    private String compayId;
    @JsonProperty("selected")
    private boolean selected;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<Filter> CREATOR = new Creator<Filter>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Filter createFromParcel(Parcel in) {
            return new Filter(in);
        }

        public Filter[] newArray(int size) {
            return (new Filter[size]);
        }

    }
    ;

    protected Filter(Parcel in) {
        this.name = ((String) in.readValue((String.class.getClassLoader())));
        this.compayId = ((String) in.readValue((String.class.getClassLoader())));
        this.selected = ((boolean) in.readValue((Boolean.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public Filter() {
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("compayId")
    public String getCompayId() {
        return compayId;
    }

    @JsonProperty("compayId")
    public void setCompayId(String compayId) {
        this.compayId = compayId;
    }

    @JsonProperty("selected")
    public boolean getSelected() {
        return selected;
    }

    @JsonProperty("selected")
    public void setSelected(boolean selected) {
        this.selected = selected;
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
        dest.writeValue(name);
        dest.writeValue(compayId);
        dest.writeValue(selected);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
