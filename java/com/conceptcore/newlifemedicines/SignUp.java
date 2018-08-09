package com.conceptcore.newlifemedicines;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.UserSignUpBean;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText etxtEmail,etxtPassword,etxtCnfPassword,etxtMobile;
    private Button btnSignUp;
    private UserSignUpBean userSignUpBean, resultBean;

    public Pattern pattern;

    private Call<UserSignUpBean> call;

    HelperMethods helperMethods = new HelperMethods(SignUp.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);
        etxtCnfPassword = findViewById(R.id.etxtCnfPassword);
        etxtMobile = findViewById(R.id.etxtMobile);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(this);

        pattern = Pattern.compile(Constants.PASSWORD_PATTERN);

        etxtMobile.setFilters(new InputFilter[]{helperMethods.getInputFilter()});

        etxtMobile.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    helperMethods.hideKeyBoard(v);
                    signUp();
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                helperMethods.hideKeyBoard(v);
                signUp();
                break;
        }
    }

    private void signUp(){
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();

        userSignUpBean = new UserSignUpBean();
        userSignUpBean.setEmail(etxtEmail.getText().toString());
        userSignUpBean.setPassword(etxtPassword.getText().toString());
        userSignUpBean.setMobileNo(etxtMobile.getText().toString());

        call = new NewLifeApiService().getNewLifeApi().register(userSignUpBean);
        call.enqueue(new Callback<UserSignUpBean>() {
            @Override
            public void onResponse(Call<UserSignUpBean> call, Response<UserSignUpBean> response) {
                helperMethods.hideProgress();

                if(response.code() == 200){
                    resultBean = response.body();
                    if(resultBean.getSuccess()){
                        Toast.makeText(SignUp.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                        goToLogin();
                    } else {
                        Toast.makeText(SignUp.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(SignUp.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSignUpBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(SignUp.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate() {

        boolean valid = true;

        String email = etxtEmail.getText().toString();
        String password = etxtPassword.getText().toString();
        String cnfPassword = etxtCnfPassword.getText().toString();
        String mobile = etxtMobile.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Please enter valid email", Toast.LENGTH_SHORT).show();
            }
            valid = false;
        } else if (password.isEmpty() || !pattern.matcher(password).matches()) {
            if (password.isEmpty()) {
                Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            } else {
                if(password.length() < 6){
                    Toast.makeText(this, "Password must be atleast six characters long", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(this, "Enter password with atleast one upper case letter, one lower case letter and one special chearacter", Toast.LENGTH_LONG).show();
                }

            }
            valid = false;
        } else if (cnfPassword.isEmpty()) {
            Toast.makeText(this, "Please enter confirm password", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!password.equals(cnfPassword)) {
            Toast.makeText(this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (mobile.isEmpty()) {
            Toast.makeText(this, "Please enter mobile no", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void goToLogin() {
        clearAllFields();
        Intent myIntent = new Intent(this, LogIn.class);
        startActivity(myIntent);
    }

    private void clearAllFields() {
        etxtEmail.setText("");
        etxtPassword.setText("");
        etxtCnfPassword.setText("");
        etxtMobile.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
