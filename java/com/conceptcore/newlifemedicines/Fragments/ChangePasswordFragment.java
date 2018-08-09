package com.conceptcore.newlifemedicines.Fragments;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.PasswordBean;
import com.conceptcore.newlifemedicines.R;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordFragment extends Fragment {

    private EditText etxtCurrPass, etxtNewPass, etxtCnfNewPass;
    private Button btnChangePass;

    private String userId;

    private Call<PasswordBean> call;

    private PasswordBean resultBean;

    public Pattern pattern;

    private ProgressDialog progress;

    HelperMethods helperMethods = null;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        etxtCurrPass = view.findViewById(R.id.etxtCurrPass);
        etxtNewPass = view.findViewById(R.id.etxtNewPass);
        etxtCnfNewPass = view.findViewById(R.id.etxtCnfNewPass);
        btnChangePass = view.findViewById(R.id.btnChangePass);

        helperMethods = new HelperMethods(getActivity());

        pattern = Pattern.compile(Constants.PASSWORD_PATTERN);

        getLocalSharedPreferences();

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callChangePassword();
            }
        });

        etxtCnfNewPass.setFilters(new InputFilter[]{helperMethods.getInputFilter()});

        etxtCnfNewPass.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    helperMethods.hideKeyBoard(v);
                    callChangePassword();
                }
                return false;
            }
        });

        return view;
    }

    private void callChangePassword() {
        if (!validate()) {
            return;
        }
        helperMethods.showProgress();

        PasswordBean passwordBean = new PasswordBean();
        passwordBean.setUserId(userId);
        passwordBean.setOldPassword(etxtCurrPass.getText().toString());
        passwordBean.setNewPassword(etxtNewPass.getText().toString());

        call = new NewLifeApiService().getNewLifeApi().changePassword(passwordBean);
        call.enqueue(new Callback<PasswordBean>() {
            @Override
            public void onResponse(Call<PasswordBean> call, Response<PasswordBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200) {
                    resultBean = response.body();
                    if(resultBean.getSuccess()){
                        Toast.makeText(getActivity(), resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                        clearAllFields();
                        openDashBoard();
                    } else {
                        Toast.makeText(getActivity(), resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PasswordBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validate(){
        boolean valid = true;

        String currPass = etxtCurrPass.getText().toString();
        String newPass = etxtNewPass.getText().toString();
        String cnfNewPass = etxtCnfNewPass.getText().toString();

        if (currPass.isEmpty() || !pattern.matcher(currPass).matches()) {
            if (currPass.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter current password", Toast.LENGTH_SHORT).show();
            } else {
                if(currPass.length() < 6){
                    Toast.makeText(getActivity(), "Password must be atleast six characters long", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "Enter password with atleast one upper case letter, one lower case letter and one special chearacter", Toast.LENGTH_LONG).show();
                }
            }
            valid = false;
        } else if (newPass.isEmpty() || !pattern.matcher(newPass).matches()) {
            if (newPass.isEmpty()) {
                Toast.makeText(getActivity(), "Please enter new password", Toast.LENGTH_SHORT).show();
            } else {
                if(newPass.length() < 6){
                    Toast.makeText(getActivity(), "New password must be atleast six characters long", Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "Enter new password with atleast one upper case letter, one lower case letter and one special chearacter", Toast.LENGTH_LONG).show();
                }
            }
            valid = false;
        } else if (cnfNewPass.isEmpty()) {
            Toast.makeText(getActivity(), "Please enter confirm password", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if (!newPass.equals(cnfNewPass)) {
            Toast.makeText(getActivity(), "New password and confirm new password doesn't match", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;

    }

    private void openDashBoard() {
        getFragmentManager().beginTransaction().replace(R.id.frame, new ProductFragment()).commit();
    }

    private void clearAllFields(){
        etxtCurrPass.setText("");
        etxtNewPass.setText("");
        etxtCnfNewPass.setText("");
    }

    private void getLocalSharedPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userId = prefs.getString("userId", "0");
    }



}
