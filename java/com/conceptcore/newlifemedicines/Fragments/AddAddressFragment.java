package com.conceptcore.newlifemedicines.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;
import com.conceptcore.newlifemedicines.SaveAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class AddAddressFragment extends Fragment {

    private EditText txtFullName,txtMobile,txtPin,txtAddLine1,txtAddLine2,txtCity,txtState;
    private Button btnSave;

    private Call<AddressBean> callAdd;
    private AddressBean resultAdd;

    private SharedPrefBean sharedPrefBean;

    private ProgressDialog progress;

    public AddAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_address, container, false);

        txtFullName = view.findViewById(R.id.txtFullName);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtPin = view.findViewById(R.id.txtPin);
        txtAddLine1 = view.findViewById(R.id.txtAddLine1);
        txtAddLine2 = view.findViewById(R.id.txtAddLine2);
        txtCity = view.findViewById(R.id.txtCity);
        txtState = view.findViewById(R.id.txtState);
        btnSave = view.findViewById(R.id.btnSave);

        sharedPrefBean = getLocalSharedPreferences();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    callAddAddress();
            }
        });

        txtState.setFilters(new InputFilter[]{getInputFilter()});

        txtState.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    hideKeyBoard(v);
                    callAddAddress();
                }
                return false;
            }
        });
        return view;
    }
    private void callAddAddress(){
        if (!validate()) {
            return;
        }
        showProgress();

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
                hideProgress();
                if(response.code() == 200){
                    resultAdd = response.body();
                    if(resultAdd.getSuccess()){
                        Toast.makeText(getActivity(), resultAdd.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressBean> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), "Please enter full name", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(pin.isEmpty()){
            Toast.makeText(getActivity(), "Please enter pin", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(add1.isEmpty()){
            Toast.makeText(getActivity(), "Please enter address line 1", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(add2.isEmpty()){
            Toast.makeText(getActivity(), "Please enter address line 2", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(city.isEmpty()){
            Toast.makeText(getActivity(), "Please enter city", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(state.isEmpty()){
            Toast.makeText(getActivity(), "Please enter state", Toast.LENGTH_SHORT).show();
            valid = false;
        } else if(mobile.isEmpty()){
            Toast.makeText(getActivity(), "Please enter mobile no", Toast.LENGTH_SHORT).show();
            valid = false;
        }

        return valid;
    }

    public void showProgress() {
        progress = new ProgressDialog(getActivity());
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
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public SharedPrefBean getLocalSharedPreferences() {
        SharedPreferences prefs = getActivity().getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);

        SharedPrefBean sharedPrefBean = new SharedPrefBean();
        sharedPrefBean.setUserId(prefs.getString("userId", "0"));
        sharedPrefBean.setEmail(prefs.getString("email",""));
        sharedPrefBean.setFirstName(prefs.getString("firstName","User"));
        sharedPrefBean.setLastName(prefs.getString("lastName",""));

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
