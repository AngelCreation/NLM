package com.conceptcore.newlifemedicines;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.CheckOutBean;
import com.conceptcore.newlifemedicines.Models.LoginBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOut extends AppCompatActivity {

    private TextView balance,txtTotal;
    private RadioGroup rgroupPayType;
    private RadioButton rbtnCod,rbtnCreditCard,rbtnPaym,rbtn;
    private CheckBox ckUseWallet;
    private Button btnCheckOut;

    private String addressId,grandTotal;

    HelperMethods helperMethods = null;
    private SharedPrefBean sharedPrefBean;

    private Call<CheckOutBean> callPlaceOrder;
    private Call<LoginBean> call;

    private boolean fromProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        Toolbar toolbar = findViewById(R.id.toolbarCheckOut);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        balance = findViewById(R.id.balance);
        txtTotal = findViewById(R.id.txtTotal);
        rgroupPayType = findViewById(R.id.rgroupPayType);
        rbtnCod = findViewById(R.id.rbtnCod);
        rbtnCreditCard = findViewById(R.id.rbtnCreditCard);
        rbtnPaym = findViewById(R.id.rbtnPaym);
        ckUseWallet = findViewById(R.id.ckUseWallet);
        btnCheckOut = findViewById(R.id.btnCheckOut);

        helperMethods = new HelperMethods(CheckOut.this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            addressId = bundle.getString("addressId");
            grandTotal = bundle.getString("grandTotal");
            fromProduct = bundle.getBoolean("fromProduct");
        }

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        getWalletBalance(sharedPrefBean.getUserId());

        balance.setText(Float.toString(Float.parseFloat(sharedPrefBean.getWalletBalance())));
        txtTotal.setText(Float.toString(Float.parseFloat(grandTotal)));

        ckUseWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isselected) {
                if(isselected){
                    float gt = Float.parseFloat(grandTotal);
                    float wb = Float.parseFloat(sharedPrefBean.getWalletBalance());

                    if(gt < wb){
                        balance.setText(String.valueOf(wb - gt));
                        txtTotal.setText("0");
                    } else if(gt > wb){
                        balance.setText("0");
                        txtTotal.setText(String.valueOf(gt - wb));
                    } else if(gt == wb){
                        balance.setText("0");
                        txtTotal.setText("0");
                    }
                } else {
                    balance.setText(sharedPrefBean.getWalletBalance());
                    txtTotal.setText(grandTotal);
                }
            }
        });

        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeOrder();
            }
        });
    }

    private void placeOrder(){
        helperMethods.showProgress();
        CheckOutBean checkOutBean = new CheckOutBean();
        checkOutBean.setUserId(sharedPrefBean.getUserId());
        checkOutBean.setAddressId(addressId);
        int selectedId = rgroupPayType.getCheckedRadioButtonId();
        rbtn = findViewById(selectedId);
        if(rbtn.getText().equals("COD")){
            checkOutBean.setPayType("cod");
        } else if(rbtn.getText().equals("Credit Card")){
            checkOutBean.setPayType("cc");
        } else if(rbtn.getText().equals("Paytm")){
            checkOutBean.setPayType("paytm");
        }
        if(ckUseWallet.isChecked()){
            checkOutBean.setUseWallet(true);
        } else{
            checkOutBean.setUseWallet(false);
        }
//        checkOutBean.setFinalPay(txtTotal.getText().toString());

        callPlaceOrder = new NewLifeApiService().getNewLifeApi().checkOut(checkOutBean);
        callPlaceOrder.enqueue(new Callback<CheckOutBean>() {
            @Override
            public void onResponse(Call<CheckOutBean> call, Response<CheckOutBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    CheckOutBean bean = response.body();
                    if(bean.getSuccess()){
                        Toast.makeText(CheckOut.this, bean.getMessage(), Toast.LENGTH_SHORT).show();
                        //move to history screen for now dashboard
                        updateWalletBalance(bean.getWallet());
                        /*if(!fromProduct)
                            finish();
                        else {
                            Intent intent = new Intent(CheckOut.this,RootActivity.class);
                            startActivity(intent);
                            finish();
                        }*/
                        Intent intent = new Intent(CheckOut.this,RootActivity.class);
                        intent.putExtra("fromCheckOut",true);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(CheckOut.this,bean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(CheckOut.this,"Please check your connection" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CheckOutBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(CheckOut.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getWalletBalance(String userId){
        helperMethods.showProgress();
        LoginBean loginBean = new LoginBean();
        loginBean.setUserId(userId);
        call = new NewLifeApiService().getNewLifeApi().getWalletBalance(loginBean);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    updateWalletBalance(response.body().getWallet());
                } else {
                    helperMethods.hideProgress();
                    Toast.makeText(CheckOut.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(CheckOut.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateWalletBalance(String balance){
        SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE).edit();
        editor.putString("walletBalance", balance);
        editor.commit();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(!fromProduct)
           finish();
        else {
           Intent intent = new Intent(CheckOut.this,RootActivity.class);
           startActivity(intent);
           finish();
        }
    }
}
