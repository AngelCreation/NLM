package com.conceptcore.newlifemedicines;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.PasswordBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPassword extends AppCompatActivity {

    private EditText etxtEmail;
    private Button btnForgotPass;

    private String userId;

    private Call<PasswordBean> call;

    private PasswordBean resultBean;

    HelperMethods helperMethods = new HelperMethods(ForgotPassword.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        etxtEmail = findViewById(R.id.etxtEmail);
        btnForgotPass = findViewById(R.id.btnForgotPass);

        getLocalSharedPreferences();

        btnForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callForgotPassword();
            }
        });
    }

    private void callForgotPassword(){
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();

        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setEmail(etxtEmail.getText().toString());

        call = new NewLifeApiService().getNewLifeApi().forgotPassword(passwordBean);
        call.enqueue(new Callback<PasswordBean>() {
            @Override
            public void onResponse(Call<PasswordBean> call, Response<PasswordBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200) {
                    resultBean = response.body();
                    if(resultBean.getSuccess()){
                        Toast.makeText(ForgotPassword.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ForgotPassword.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ForgotPassword.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PasswordBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(ForgotPassword.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private boolean validate(){
        boolean valid = true;

        String email = etxtEmail.getText().toString();
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            }
            valid = false;
        }

        return valid;
    }

    private void getLocalSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userId = prefs.getString("userId", "0");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
