package com.conceptcore.newlifemedicines;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveAddress extends AppCompatActivity {

    private EditText txtFullName,txtMobile,txtPin,txtAddLine1,txtAddLine2,txtCity,txtState;
    private Button btnSave;

    private AddressBean address = null;

    private Call<AddressBean> callUpdate,callAdd;
    private AddressBean resultUpdate,resultAdd;

    private SharedPrefBean sharedPrefBean;

    HelperMethods helperMethods = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_address);

        Toolbar toolbarSaveAddress = findViewById(R.id.toolbarSaveAddress);
        setSupportActionBar(toolbarSaveAddress);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        txtFullName = findViewById(R.id.txtFullName);
        txtMobile = findViewById(R.id.txtMobile);
        txtPin = findViewById(R.id.txtPin);
        txtAddLine1 = findViewById(R.id.txtAddLine1);
        txtAddLine2 = findViewById(R.id.txtAddLine2);
        txtCity = findViewById(R.id.txtCity);
        txtState = findViewById(R.id.txtState);
        btnSave = findViewById(R.id.btnSave);

        helperMethods = new HelperMethods(SaveAddress.this);

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            address = bundle.getParcelable("address");
            setDataInFields();
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address != null){
//                    call update
                    callUpdateAddress();
                } else {
                    //call add
                    callAddAddress();
                }
            }
        });

        txtState.setFilters(new InputFilter[]{helperMethods.getInputFilter()});

        txtState.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    helperMethods.hideKeyBoard(v);
                    if(address != null){
                        callUpdateAddress();
                    } else {
                        callAddAddress();
                    }
                }
                return false;
            }
        });
    }

    private void callUpdateAddress(){
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();

        AddressBean addressBean = new AddressBean();
        addressBean.setAddressId(address.getAddressId());
        addressBean.setName(txtFullName.getText().toString());
        addressBean.setPin(txtPin.getText().toString());
        addressBean.setAdd1(txtAddLine1.getText().toString());
        addressBean.setAdd2(txtAddLine2.getText().toString());
        addressBean.setCity(txtCity.getText().toString());
        addressBean.setState(txtState.getText().toString());
        addressBean.setMobileNo(txtMobile.getText().toString());

        callUpdate = new NewLifeApiService().getNewLifeApi().updateAddress(addressBean);
        callUpdate.enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                helperMethods.hideProgress();

                if(response.code() == 200){
                    resultUpdate = response.body();
                    if(resultUpdate.getSuccess()){
                        Toast.makeText(SaveAddress.this, resultUpdate.getMessage(), Toast.LENGTH_SHORT).show();
                        openAllAddress();
                    }
                } else {
                    Toast.makeText(SaveAddress.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(SaveAddress.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void callAddAddress(){
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();

        AddressBean addressBean = new AddressBean();
        addressBean.setUserId(sharedPrefBean.getUserId());
        addressBean.setName(txtFullName.getText().toString());
        addressBean.setPin(txtPin.getText().toString());
        addressBean.setAdd1(txtAddLine1.getText().toString());
        addressBean.setAdd2(txtAddLine2.getText().toString());
        addressBean.setCity(txtCity.getText().toString());
        addressBean.setState(txtState.getText().toString());
        addressBean.setMobileNo(txtMobile.getText().toString());

        callAdd = new NewLifeApiService().getNewLifeApi().addAddress(addressBean);
        callAdd.enqueue(new Callback<AddressBean>() {
            @Override
            public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    resultAdd = response.body();
                    if(resultAdd.getSuccess()){
                        Toast.makeText(SaveAddress.this, resultAdd.getMessage(), Toast.LENGTH_SHORT).show();
                        openAllAddress();
                    }
                } else {
                    Toast.makeText(SaveAddress.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(SaveAddress.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(){
        boolean valid = true;

        String fullName = txtFullName.getText().toString();
        String pin = txtPin.getText().toString();
        String add1 = txtAddLine1.getText().toString();
        String add2 = txtAddLine2.getText().toString();
        String city = txtCity.getText().toString();
        String state = txtState.getText().toString();
        String mobile = txtMobile.getText().toString();

        if(fullName.isEmpty()){
            Toast.makeText(this, "Please enter full name", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(pin.isEmpty()){
            Toast.makeText(this, "Please enter pin", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(add1.isEmpty()){
            Toast.makeText(this, "Please enter address line 1", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(add2.isEmpty()){
            Toast.makeText(this, "Please enter address line 2", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(city.isEmpty()){
            Toast.makeText(this, "Please enter city", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(state.isEmpty()){
            Toast.makeText(this, "Please enter state", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(mobile.isEmpty()){
            Toast.makeText(this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    private void setDataInFields(){
        txtFullName.setText(address.getName());
        txtMobile.setText(address.getMobileNo());
        txtPin.setText(address.getPin());
        txtAddLine1.setText(address.getAdd1());
        txtAddLine2.setText(address.getAdd2());
        txtCity.setText(address.getCity());
        txtState.setText(address.getState());
    }

    private void openAllAddress(){
//        Intent  intent = new Intent(SaveAddress.this,Address.class);
//        startActivity(intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

}
