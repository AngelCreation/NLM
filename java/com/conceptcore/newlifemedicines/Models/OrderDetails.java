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
    "address",
    "product",
    "amount",
    "orderId",
    "pres"
})
public class OrderDetails implements Parcelable
{

    @JsonProperty("address")
    private Address address;
    @JsonProperty("product")
    private List<Product> product = new ArrayList<Product>();
    @JsonProperty("amount")
    private String amount;
    @JsonProperty("orderId")
    private String orderId;
    @JsonProperty("pres")
    private Prescription prescription;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<OrderDetails> CREATOR = new Creator<OrderDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public OrderDetails createFromParcel(Parcel in) {
            return new OrderDetails(in);
        }

        public OrderDetails[] newArray(int size) {
            return (new OrderDetails[size]);
        }

    }
    ;

    protected OrderDetails(Parcel in) {
        this.address = ((Address) in.readValue((Address.class.getClassLoader())));
        this.prescription = ((Prescription) in.readValue((Prescription.class.getClassLoader())));
        in.readList(this.product, (Product.class.getClassLoader()));
        this.amount = ((String) in.readValue((String.class.getClassLoader())));
        this.orderId = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public OrderDetails() {
    }

    @JsonProperty("address")
    public Address getAddress() {
        return address;
    }

    @JsonProperty("address")
    public void setAddress(Address address) {
        this.address = address;
    }

    @JsonProperty("pres")
    public Prescription getPrescription() {
        return prescription;
    }

    @JsonProperty("pres")
    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    @JsonProperty("product")
    public List<Product> getProduct() {
        return product;
    }

    @JsonProperty("product")
    public void setProduct(List<Product> product) {
        this.product = product;
    }

    @JsonProperty("amount")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("orderId")
    public String getOrderId() {
        return orderId;
    }

    @JsonProperty("orderId")
    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
        dest.writeValue(address);
        dest.writeValue(prescription);
        dest.writeList(product);
        dest.writeValue(amount);
        dest.writeValue(orderId);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
