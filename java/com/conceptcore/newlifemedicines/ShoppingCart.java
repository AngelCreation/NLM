package com.conceptcore.newlifemedicines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.CartAdapter;
import com.conceptcore.newlifemedicines.Adapters.ProductsAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.CartAddress;
import com.conceptcore.newlifemedicines.Models.CartBean;
import com.conceptcore.newlifemedicines.Models.CartProduct;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShoppingCart extends AppCompatActivity implements CartAdapter.CartRefreshListner {

    private RecyclerView rvCart;
    private TextView txtSelectAddress,txtName,txtAdd1,txtAdd2,txtCityStatePin,txtMobile,txtChangeAddress,txtTotal;
    private LinearLayout llAddress,llTotal,errorViewCart,dataViewCart;

    private SharedPrefBean sharedPrefBean;
    private CartAdapter cartAdapter;

    private Call<CartBean> call;
    private List<CartProduct> cartProductsList = new ArrayList<>();

    private CartBean cartBean;
    private String addressId;

    private static boolean fromProduct;


    HelperMethods helperMethods = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtSelectAddress = findViewById(R.id.txtSelectAddress);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if(getIntent().getExtras() != null){
            fromProduct = getIntent().getExtras().getBoolean("fromProduct");
        }

        rvCart = findViewById(R.id.rvCart);

        llAddress = findViewById(R.id.llAddress);
        errorViewCart = findViewById(R.id.errorViewCart);
        dataViewCart = findViewById(R.id.dataViewCart);
        llTotal = findViewById(R.id.llTotal);

        txtName = findViewById(R.id.txtName);
        txtAdd1 = findViewById(R.id.txtAdd1);
        txtAdd2 = findViewById(R.id.txtAdd2);
        txtCityStatePin = findViewById(R.id.txtCityStatePin);
        txtMobile = findViewById(R.id.txtMobile);
        txtChangeAddress = findViewById(R.id.txtChangeAddress);

        txtTotal = findViewById(R.id.txtTotal);

        helperMethods = new HelperMethods(ShoppingCart.this);
        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        getCartDetails();

        //recycler view
        rvCart.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(ShoppingCart.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCart.setLayoutManager(llm);

        cartAdapter = new CartAdapter(ShoppingCart.this,cartProductsList,sharedPrefBean.getUserId());
        rvCart.setAdapter(cartAdapter);
        cartAdapter.setCartRefreshListner(this);
        //recycler ended

        txtSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtSelectAddress.getText().equals("Select Address")){
                    openSelectAddress();
                } else if(txtSelectAddress.getText().equals("Proceed To Checkout")){
//                    placeOrder();
                    Intent intent = new Intent(ShoppingCart.this,CheckOut.class);
                    intent.putExtra("addressId",addressId);
                    intent.putExtra("grandTotal",cartBean.getTotal());
                    intent.putExtra("fromProduct",fromProduct);
                    startActivity(intent);
//                    finish();
                }
            }
        });

        txtChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSelectAddress();
            }
        });
    }

    private void openSelectAddress(){
        Intent myIntent = new Intent(ShoppingCart.this, Address.class);
        startActivityForResult(myIntent,1);
    }

    private void getCartDetails(){
        helperMethods.showProgress();

        ProductBean productBean = new ProductBean();
        productBean.setUserId(sharedPrefBean.getUserId());
        call = new NewLifeApiService().getNewLifeApi().getCartDetails(productBean);
        call.enqueue(new Callback<CartBean>() {
            @Override
            public void onResponse(Call<CartBean> call, Response<CartBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    cartProductsList.clear();
                    cartBean = response.body();

                    if(cartBean.getCartAddress() != null){
                        CartAddress address = cartBean.getCartAddress();
                        txtName.setText(address.getName());
                        txtAdd1.setText(address.getAdd1());
                        txtAdd2.setText(address.getAdd2());
                        txtCityStatePin.setText(address.getCity() + " " + address.getState() + "-" + address.getPin());
                        txtMobile.setText(address.getMobileNo());

                        addressId = address.getAddressId();

                        llAddress.setVisibility(View.VISIBLE);
                        txtSelectAddress.setText("Proceed To Checkout");
                    } else {
                        txtSelectAddress.setText("Select Address");
                    }
//                    Log.e("no of products: ","cart:: " + cartBean.getCartProducts().size());
                    if(cartBean.getCartProducts().size() > 0){
                            dataViewCart.setVisibility(View.VISIBLE);
                            errorViewCart.setVisibility(View.GONE);

                            cartProductsList.addAll(cartBean.getCartProducts());

                            txtTotal.setText(cartBean.getTotal());

                    } else {
                        dataViewCart.setVisibility(View.GONE);
                        errorViewCart.setVisibility(View.VISIBLE);
                        llTotal.setVisibility(View.GONE);
                        txtSelectAddress.setVisibility(View.GONE);
                    }

                    cartAdapter.notifyDataSetChanged();

                } else{
                    Toast.makeText(ShoppingCart.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<CartBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(ShoppingCart.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                AddressBean address = data.getParcelableExtra("address");
                Log.e("Shopping Cart:: ","selected address id" + address.getAddressId());

                txtName.setText(address.getName());
                txtAdd1.setText(address.getAdd1());
                txtAdd2.setText(address.getAdd2());
                txtCityStatePin.setText(address.getCity() + " " + address.getState() + "-" + address.getPin());
                txtMobile.setText(address.getMobileNo());

                addressId = address.getAddressId();

                llAddress.setVisibility(View.VISIBLE);
                txtSelectAddress.setText("Proceed To Checkout");
            }
        }
    }

    @Override
    public void OnCartRefresh(View view, int position, List<CartProduct> list) {
        getCartDetails();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(fromProduct){
            Intent intent = new Intent(ShoppingCart.this,RootActivity.class);
            startActivity(intent);
            this.finish();
        } else {
            this.finish();
        }

    }
}
