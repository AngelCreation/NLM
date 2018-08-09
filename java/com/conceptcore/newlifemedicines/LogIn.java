package com.conceptcore.newlifemedicines;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.CustomProgressBar;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.LoginBean;
import com.conceptcore.newlifemedicines.Models.UserSignUpBean;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;

import java.sql.Timestamp;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogIn extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {

    private String LOG = "LoginActivity :: ";

    private static final int RC_SIGN_IN = 9001;

    private EditText etxtEmail,etxtPassword;
    private TextView txtForgotPass;
    private Button btnLogin,btnSignUp;

    private GoogleApiClient googleApiClient;
    private SignInButton btnGoogleSignin;

    private LoginBean loginBean, resultBean;
    private UserSignUpBean resultBeanGoogle;

    private Call<LoginBean> call;
    private Call<UserSignUpBean> callGoogle;

    private GoogleSignInClient mGoogleSignInClient;

    HelperMethods helperMethods = new HelperMethods(LogIn.this);

//    private CustomProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etxtEmail = findViewById(R.id.etxtEmail);
        etxtPassword = findViewById(R.id.etxtPassword);

        txtForgotPass = findViewById(R.id.txtForgotPass);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin = findViewById(R.id.btnLogin);
        btnGoogleSignin = findViewById(R.id.btnGoogleSignin);

//        progressBar = findViewById(R.id.progress);

        btnGoogleSignin.setSize(SignInButton.SIZE_WIDE);

        btnLogin.setOnClickListener(this);
        btnGoogleSignin.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);

//        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken("1044180222001-0qrfpgv6jpcn20refu86hhkbcs37c2dn.apps.googleusercontent.com")
//                .requestEmail()
//                .build();
//
//
//        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, signInOptions).build();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        etxtPassword.setFilters(new InputFilter[]{helperMethods.getInputFilter()});

        etxtPassword.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    helperMethods.hideKeyBoard(v);
                    signIn();
                }
                return false;
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txtForgotPass:
                helperMethods.hideKeyBoard(v);
                goToForgotPass();
                break;
            case R.id.btnSignUp:
                helperMethods.hideKeyBoard(v);
                goToSignUp();
                break;
            case R.id.btnLogin:
                helperMethods.hideKeyBoard(v);
                signIn();
                break;
            case R.id.btnGoogleSignin:
                helperMethods.hideKeyBoard(v);
                googleSignIn();
                break;
        }
    }

    private void googleSignIn() {
//        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
//        startActivityForResult(signInIntent, RC_SIGN_IN);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> result) {
        if (result != null) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount account = null;
            try {
                account = result.getResult(ApiException.class);
            } catch (ApiException e) {
                e.printStackTrace();
            }
//            Log.e("LoginFragment :: ", "handleSignInResult:Email:: " + account.getEmail());
//            Log.e("LoginFragment :: ", "handleSignInResult:getDisplayName:: " + account.getDisplayName());
//            Log.e("LoginFragment :: ", "handleSignInResult:getGivenName:: " + account.getGivenName());
//            Log.e("LoginFragment :: ", "handleSignInResult:getPhotoUrl:: " + account.getPhotoUrl());
//            Log.e("LoginFragment :: ", "handleSignInResult:getId:: " + account.getId());
//            Log.e("LoginFragment :: ", "handleSignInResult:getFamilyName:: " + account.getFamilyName());
//            Log.e("LoginFragment :: ", "handleSignInResult:getIdToken:: " + account.getIdToken());

            if(account != null) {
                if (account.getEmail() != null) {

                    UserSignUpBean userSignUpBean = new UserSignUpBean();
//                userSignUpBean.setUserPhoto(account.getPhotoUrl().toString());
//                userSignUpBean.setTimeStamp(getTimeStamp());
                    userSignUpBean.setFirstName(account.getGivenName());
                    userSignUpBean.setLastName(account.getFamilyName());
                    userSignUpBean.setEmail(account.getEmail());
                    userSignUpBean.setFcmToken(FirebaseInstanceId.getInstance().getToken());

                    signUpGoogle(userSignUpBean);
                } else {
                    Toast.makeText(LogIn.this, "Google account didn't have email", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(LogIn.this, "Can not get google account details right now\nPlease try again later", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void signUpGoogle(UserSignUpBean userSignUpBean) {

        helperMethods.showProgress();

        callGoogle = new NewLifeApiService().getNewLifeApi().userSignUpLoginGoogle(userSignUpBean);
        callGoogle.enqueue(new Callback<UserSignUpBean>() {
            @Override
            public void onResponse(Call<UserSignUpBean> call, Response<UserSignUpBean> response) {
                helperMethods.hideProgress();
//                Auth.GoogleSignInApi.signOut(googleApiClient);
                mGoogleSignInClient.signOut();
                if (response.code() == 200) {
                    resultBeanGoogle = response.body();
//                    Log.e("result","google" + resultBeanGoogle.getMessage());
                    if (resultBeanGoogle.getSuccess()) {
//                        Toast.makeText(LogIn.this, resultBeanGoogle.getMessage(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE).edit();
                        editor.putString("userId", resultBeanGoogle.getUserId());
                        editor.putString("firstName", resultBeanGoogle.getFirstName());
                        editor.putString("lastName", resultBeanGoogle.getLastName());
                        editor.putString("email", resultBeanGoogle.getEmail());
                        editor.putString("walletBalance", resultBeanGoogle.getWallet());
                        editor.commit();

                        if (getIntent().getExtras() != null) {
                            Boolean goToLinkFragment = getIntent().getExtras().getBoolean("fromLink", false);
                            if (goToLinkFragment) {
                                finish();
                            }

                        } else {
                            moveToDashBoard();
                        }

                    } else {
                        Toast.makeText(LogIn.this, resultBeanGoogle.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSignUpBean> call, Throwable t) {
//                Auth.GoogleSignInApi.signOut(googleApiClient);
                mGoogleSignInClient.signOut();
                helperMethods.hideProgress();
                Toast.makeText(LogIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signIn() {
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();
//        progressBar.setVisibility(View.VISIBLE);

        loginBean = new LoginBean();
        loginBean.setEmail(etxtEmail.getText().toString());
        loginBean.setPassword(etxtPassword.getText().toString());
        loginBean.setFcmToken(FirebaseInstanceId.getInstance().getToken());

        call = new NewLifeApiService().getNewLifeApi().login(loginBean);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                helperMethods.hideProgress();
//                progressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    resultBean = response.body();

                    if (resultBean.getSuccess()) {
//                        if (resultBean.getIsActive()) {
                            clearAllFields();
                            //save params in sharedpreferences
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE).edit();
                            editor.putString("userId",resultBean.getUserId());
                            editor.putString("firstName", resultBean.getFirstName());
                            editor.putString("lastName", resultBean.getLastName());
                            editor.putString("email", resultBean.getEmail());
                            editor.putString("walletBalance", resultBean.getWallet());
                            Log.e("balance","from login" + resultBean.getWallet());
                            editor.commit();

                            moveToDashBoard();

//                        } else {
//                            Toast.makeText(LogIn.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        Toast.makeText(LogIn.this, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                helperMethods.hideProgress();
//                progressBar.setVisibility(View.GONE);
                Toast.makeText(LogIn.this, "Please check your connection", Toast.LENGTH_SHORT).show();
//                t.printStackTrace();
            }
        });
    }

    public boolean validate() {

        boolean valid = true;

        String email = etxtEmail.getText().toString();
        String password = etxtPassword.getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(this, "Please enter email or mobile no", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (password.isEmpty()) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }

    private void moveToDashBoard() {
        Intent myIntent = new Intent(this, RootActivity.class);
        startActivity(myIntent);
        finish();
    }

    private void goToSignUp() {
        clearAllFields();
        Intent myIntent = new Intent(this, SignUp.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(myIntent);
    }

    private void goToForgotPass() {
        clearAllFields();
        Intent myIntent = new Intent(this, ForgotPassword.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(myIntent);
    }

    private void clearAllFields() {
        etxtEmail.setText("");
        etxtPassword.setText("");
    }

    /*
    private String getTimeStamp() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        try {
            return Constants.sdfDatetimeMobile.format(timestamp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        Toast.makeText(this, resultCode + "", Toast.LENGTH_SHORT).show();

        if (requestCode == RC_SIGN_IN) {
//            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Task<GoogleSignInAccount> result = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(result);
        }

        //login with fb
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(LOG, "Connection failed");
    }
}
