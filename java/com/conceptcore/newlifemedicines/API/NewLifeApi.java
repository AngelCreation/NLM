package com.conceptcore.newlifemedicines.API;

import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.CartBean;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.CheckOutBean;
import com.conceptcore.newlifemedicines.Models.Filter;
import com.conceptcore.newlifemedicines.Models.FilterBean;
import com.conceptcore.newlifemedicines.Models.LoginBean;
import com.conceptcore.newlifemedicines.Models.OrderBean;
import com.conceptcore.newlifemedicines.Models.OrderDetails;
import com.conceptcore.newlifemedicines.Models.PassbookBean;
import com.conceptcore.newlifemedicines.Models.PasswordBean;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.Models.UserSignUpBean;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by SVF 15213 on 02-06-2018.
 */

public interface NewLifeApi {
    @POST("user/login")
    Call<LoginBean> login(@Body LoginBean loginbean);

    @POST("user/balance")
    Call<LoginBean> getWalletBalance(@Body LoginBean loginbean);

    @POST("user/register")
    Call<UserSignUpBean> register(@Body UserSignUpBean userSignUpBean);

    @POST("user/googlelogin")
    Call<UserSignUpBean> userSignUpLoginGoogle(@Body UserSignUpBean userSignUpBean);

    @POST("user/forgotpassword")
    Call<PasswordBean> forgotPassword(@Body PasswordBean passwordBean);

    @POST("user/details")
    Call<UserSignUpBean> getUserInfo(@Body UserSignUpBean userSignUpBean);

    @POST("user/update")
    Call<UserSignUpBean> editProfile(@Body UserSignUpBean userSignUpBean);

    @POST("user/changepassword")
    Call<PasswordBean> changePassword(@Body PasswordBean passwordBean);

    @GET("category")
    Call<List<CategoryBean>> getAllCats();

    @GET("products/home")
    Call<List<ProductBean>> getAllSuggestions();

    @POST("products/singal")
    Call<ProductBean> getProduct(@Body ProductBean productBean);

    @POST("products")
    Call<List<ProductBean>> getAllProducts(@Body ProductBean productBean);

    @POST("products")
    Call<List<ProductBean>> getAllProductsFilterBy(@Body FilterBean filterBean);

    @POST("cart/add")
    Call<ProductBean> updateQty(@Body ProductBean productBean);

    @POST("cart")
    Call<CartBean> getCartDetails(@Body ProductBean productBean);

    @POST("user/order")
    Call<CheckOutBean> checkOut(@Body CheckOutBean checkOutBean);

    @POST("address")
    Call<List<AddressBean>> getAllAddresses(@Body AddressBean addressBean);

    @POST("address/add")
    Call<AddressBean> addAddress(@Body AddressBean addressBean);

    @POST("address/update")
    Call<AddressBean> updateAddress(@Body AddressBean addressBean);

    @POST("address/select")
    Call<AddressBean> selectAddress(@Body AddressBean addressBean);

    @POST("prescription")
    Call<AddressBean> uploadPrescription(@Body RequestBody file);

    @POST("orders")
    Call<List<OrderBean>> getMyOrders(@Body OrderBean orderBean);

    @POST("user/wallet")
    Call<List<PassbookBean>> getPassbookDetails(@Body PassbookBean passbookBean);

    @POST("orders/view")
    Call<OrderDetails> getOrderDetails(@Body OrderBean orderBean);

    @GET("company")
    Call<List<Filter>> getCompanyList();

    @GET("silder")
    Call<List<CategoryBean>> getSliderImages();
}
