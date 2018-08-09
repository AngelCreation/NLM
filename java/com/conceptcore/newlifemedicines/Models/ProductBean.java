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
    "productId",
    "userId",
    "title",
    "price",
    "imagePath",
    "qty",
    "categoryId",
    "sort",
    "order",
    "delDate",
    "success",
    "message",
    "discount",
    "cartId"
})
public class ProductBean implements Parcelable
{

    @JsonProperty("productId")
    private String productId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("price")
    private String price;
    @JsonProperty("imagePath")
    private String imagePath;
    @JsonProperty("qty")
    private String qty;
    @JsonProperty("categoryId")
    private String categoryId;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("order")
    private String order;
    @JsonProperty("delDate")
    private String delDate;
    @JsonProperty("success")
    private Boolean success;
    @JsonProperty("message")
    private String message;
    @JsonProperty("discount")
    private String discount;
    @JsonProperty("cartId")
    private String cartId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    public final static Creator<ProductBean> CREATOR = new Creator<ProductBean>() {


        @SuppressWarnings({
            "unchecked"
        })
        public ProductBean createFromParcel(Parcel in) {
            return new ProductBean(in);
        }

        public ProductBean[] newArray(int size) {
            return (new ProductBean[size]);
        }

    }
    ;

    protected ProductBean(Parcel in) {
        this.productId = ((String) in.readValue((String.class.getClassLoader())));
        this.userId = ((String) in.readValue((String.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((String) in.readValue((String.class.getClassLoader())));
        this.imagePath = ((String) in.readValue((String.class.getClassLoader())));
        this.qty = ((String) in.readValue((String.class.getClassLoader())));
        this.categoryId = ((String) in.readValue((String.class.getClassLoader())));
        this.sort = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((String) in.readValue((String.class.getClassLoader())));
        this.delDate = ((String) in.readValue((String.class.getClassLoader())));
        this.success = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.message = ((String) in.readValue((String.class.getClassLoader())));
        this.discount = ((String) in.readValue((String.class.getClassLoader())));
        this.cartId = ((String) in.readValue((String.class.getClassLoader())));
        this.additionalProperties = ((Map<String, Object> ) in.readValue((Map.class.getClassLoader())));
    }

    public ProductBean() {
    }

    @JsonProperty("productId")
    public String getProductId() {
        return productId;
    }

    @JsonProperty("productId")
    public void setProductId(String productId) {
        this.productId = productId;
    }

    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("userId")
    public void setUserId(String userId) {
        this.userId = userId;
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

    @JsonProperty("imagePath")
    public String getImagePath() {
        return imagePath;
    }

    @JsonProperty("imagePath")
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @JsonProperty("qty")
    public String getQty() {
        return qty;
    }

    @JsonProperty("qty")
    public void setQty(String qty) {
        this.qty = qty;
    }

    @JsonProperty("categoryId")
    public String getCategoryId() {
        return categoryId;
    }

    @JsonProperty("categoryId")
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @JsonProperty("sort")
    public String getSort() {
        return sort;
    }

    @JsonProperty("sort")
    public void setSort(String sort) {
        this.sort = sort;
    }

    @JsonProperty("order")
    public String getOrder() {
        return order;
    }

    @JsonProperty("order")
    public void setOrder(String order) {
        this.order = order;
    }

    @JsonProperty("delDate")
    public String getDelDate() {
        return delDate;
    }

    @JsonProperty("delDate")
    public void setDelDate(String delDate) {
        this.delDate = delDate;
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

    @JsonProperty("discount")
    public String getDiscount() {
        return discount;
    }

    @JsonProperty("discount")
    public void setDiscount(String discount) {
        this.discount = discount;
    }

    @JsonProperty("cartId")
    public String getCartId() {
        return cartId;
    }

    @JsonProperty("cartId")
    public void setCartId(String cartId) {
        this.cartId = cartId;
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
        dest.writeValue(productId);
        dest.writeValue(userId);
        dest.writeValue(title);
        dest.writeValue(price);
        dest.writeValue(imagePath);
        dest.writeValue(qty);
        dest.writeValue(categoryId);
        dest.writeValue(sort);
        dest.writeValue(order);
        dest.writeValue(delDate);
        dest.writeValue(success);
        dest.writeValue(message);
        dest.writeValue(discount);
        dest.writeValue(cartId);
        dest.writeValue(additionalProperties);
    }

    public int describeContents() {
        return  0;
    }

}
