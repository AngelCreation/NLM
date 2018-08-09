package com.conceptcore.newlifemedicines;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.LoginBean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splash extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 2000;

    private Call<LoginBean> call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);

        if(checkConnection(Splash.this)){
            if(prefs.contains("userId")){
                LoginBean loginBean = new LoginBean();
                loginBean.setUserId(prefs.getString("userId", "0"));
                call = new NewLifeApiService().getNewLifeApi().getWalletBalance(loginBean);
                call.enqueue(new Callback<LoginBean>() {
                    @Override
                    public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                        if(response.code() == 200){
                            SharedPreferences.Editor editor = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE).edit();
                            editor.putString("walletBalance", response.body().getWallet());
                            editor.commit();
                        }
                        moveToRoot();
                    }

                    @Override
                    public void onFailure(Call<LoginBean> call, Throwable t) {
                        moveToRoot();
                    }
                });
            } else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       moveToRoot();
                    }
                }, SPLASH_TIME_OUT);
            }

        } else {
            Toast.makeText(this, "Please check your connection", Toast.LENGTH_SHORT).show();
        }


    }

    private void moveToRoot(){
        Intent i = new Intent(Splash.this, RootActivity.class);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        startActivity(i);
        finish();
    }

    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
//            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
