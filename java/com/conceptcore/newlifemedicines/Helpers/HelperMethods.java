package com.conceptcore.newlifemedicines.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SVF 15213 on 09-06-2018.
 */

public class HelperMethods {

    private Context context;
    private ProgressDialog progress;

    public HelperMethods(Context context) {
        this.context = context;
    }

    public void showProgress() {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
    }

    public void hideProgress() {
        if ((progress != null) && progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void hideKeyBoard(View view) {
        //close keyboard
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public SharedPrefBean getLocalSharedPreferences() {
        SharedPreferences prefs = context.getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);

        SharedPrefBean sharedPrefBean = new SharedPrefBean();
        sharedPrefBean.setUserId(prefs.getString("userId", "0"));
        sharedPrefBean.setEmail(prefs.getString("email",""));
        sharedPrefBean.setFirstName(prefs.getString("firstName","User"));
        sharedPrefBean.setLastName(prefs.getString("lastName",""));
        sharedPrefBean.setWalletBalance(prefs.getString("walletBalance","0"));
        return sharedPrefBean;
    }



    public InputFilter getInputFilter() {
        InputFilter filter = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                for (int i = start; i < end; i++) {
                    if (Character.isWhitespace(source.charAt(i))) {
                        return "";
                    }
                }
                return null;
            }
        };
        return filter;
    }
}
