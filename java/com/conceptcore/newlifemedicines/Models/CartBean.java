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
    "cartProducts",
    "cartAddress",
    "total"
})
public class CartBean implements Parcelable
{

    @JsonProperty("cartProducts")
    private List<CartProduct> cartProducts = new ArrayList<CartProduct>();
    @JsonProperty("cartAddress")
    private CartAddress cartAddress;
    @JsonProperty("total")
    private String total;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CartBean> CREATOR = new Creator<CartBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CartBean createFromParcel(Parcel in) {
            return new CartBean(in);
        }

        public CartBean[] newArray(int size) {
            return (new CartBean[size]);
        }

    }
    ;

    protected CartBean(Parcel in) {
        in.readList(this.cartProducts, (CartProduct.class.getClassLoader()));
        this.cartAddress = ((CartAddress) in.readValue((CartAddress.class.getClassLoader())));
        this.total = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CartBean() {
    }

    @JsonProperty("cartProducts")
    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    @JsonProperty("cartProducts")
    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    @JsonProperty("cartAddress")
    public CartAddress getCartAddress() {
        return cartAddress;
    }

    @JsonProperty("cartAddress")
    public void setCartAddress(CartAddress cartAddress) {
        this.cartAddress = cartAddress;
    }

    @JsonProperty("total")
    public String getTotal() {
        return total;
    }

    @JsonProperty("total")
    public void setTotal(String total) {
        this.total = total;
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
        dest.writeList(cartProducts);
        dest.writeValue(cartAddress);
        dest.writeValue(total);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
