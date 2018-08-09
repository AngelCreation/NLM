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
    "cartId",
    "productId",
    "userid",
    "qty",
    "delDate",
    "prescriptionId",
    "flag",
    "title",
    "imagePath",
    "price",
    "categoryId",
    "discount",
    "description",
    "prescriptionImage"
})
public class CartProduct implements Parcelable
{

    @JsonProperty("cartId")
    private String cartId;
    @JsonProperty("productId")
    private String productId;
    @JsonProperty("userid")
    private String userid;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("delDate")
    private String delDate;
    @JsonProperty("prescriptionId")
    private String prescriptionId;
    @JsonProperty("flag")
    private String flag;
    @JsonProperty("title")
    private String title;
    @JsonProperty("imagePath")
    private String imagePath;
    @JsonProperty("price")
    private String price;
    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("discount")
    private String discount;
    @JsonProperty("description")
    private String description;
    @JsonProperty("prescriptionImage")
    private String prescriptionImage;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<CartProduct> CREATOR = new Creator<CartProduct>() {


        @SuppressWarnings({
            "unchecked"
        })
        public CartProduct createFromParcel(Parcel in) {
            return new CartProduct(in);
        }

        public CartProduct[] newArray(int size) {
            return (new CartProduct[size]);
        }

    }
    ;

    protected CartProduct(Parcel in) {
        this.cartId = ((String) in.readValue((String.class.getClassLoader())));
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.userid = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.delDate = ((String) in.readValue((String.class.getClassLoader())));
        this.prescriptionId = ((String) in.readValue((String.class.getClassLoader())));
        this.flag = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePath = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.discount = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.prescriptionImage = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public CartProduct() {
    }

    @JsonProperty("cartId")
    public String getCartId() {
        return cartId;
    }

    @JsonProperty("cartId")
    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("userid")
    public String getUserid() {
        return userid;
    }

    @JsonProperty("userid")
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @JsonProperty("qty")
    public String getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(String qty) {
        this.qty = qty;
    }

    @JsonProperty("delDate")
    public String getDelDate() {
        return delDate;
    }

    @JsonProperty("delDate")
    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    @JsonProperty("prescriptionId")
    public String getPrescriptionId() {
        return prescriptionId;
    }

    @JsonProperty("prescriptionId")
    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    @JsonProperty("flag")
    public String getFlag() {
        return flag;
    }

    @JsonProperty("flag")
    public void setFlag(String flag) {
        this.flag = flag;
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

    @JsonProperty("price")
    public String getPrice() {
        return price;
    }

    @JsonProperty("price")
    public void setPrice(String price) {
        this.price = price;
    }

    @JsonProperty("categoryId")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("discount")
    public String getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("prescriptionImage")
    public String getPrescriptionImage() {
        return prescriptionImage;
    }

    @JsonProperty("prescriptionImage")
    public void setPrescriptionImage(String prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
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
        dest.writeValue(cartId);
        dest.writeValue(productId);
        dest.writeValue(userid);
        dest.writeValue(qty);
        dest.writeValue(delDate);
        dest.writeValue(prescriptionId);
        dest.writeValue(flag);
        dest.writeValue(title);
        dest.writeValue(imagePath);
        dest.writeValue(price);
        dest.writeValue(categoryId);
        dest.writeValue(discount);
        dest.writeValue(description);
        dest.writeValue(prescriptionImage);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
