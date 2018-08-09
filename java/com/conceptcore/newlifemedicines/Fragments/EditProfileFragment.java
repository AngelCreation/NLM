package com.conceptcore.newlifemedicines.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Address;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.PasswordBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.Models.UserSignUpBean;
import com.conceptcore.newlifemedicines.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileFragment extends Fragment {

    private EditText etxtFirstName,etxtLastName,etxtEmail,etxtMobile;

    private Button btnEditProfile;

    private Call<UserSignUpBean> call,callEditProfile;

    private UserSignUpBean resultBean;

    HelperMethods helperMethods = null;

    private SharedPrefBean sharedPrefBean;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        etxtFirstName = view.findViewById(R.id.etxtFirstName);
        etxtLastName = view.findViewById(R.id.etxtLastName);
        etxtEmail = view.findViewById(R.id.etxtEmail);
        etxtMobile = view.findViewById(R.id.etxtMobile);
        btnEditProfile = view.findViewById(R.id.btnEditProfile);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        callGetUserInfo();

        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

        return view;
    }

    private void callGetUserInfo(){
        helperMethods.showProgress();
        UserSignUpBean userSignUpBean = new UserSignUpBean();
        userSignUpBean.setUserId(sharedPrefBean.getUserId());
        call = new NewLifeApiService().getNewLifeApi().getUserInfo(userSignUpBean);
        call.enqueue(new Callback<UserSignUpBean>() {
            @Override
            public void onResponse(Call<UserSignUpBean> call, Response<UserSignUpBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200) {
                    resultBean = response.body();
                    etxtFirstName.setText(resultBean.getFirstName());
                    etxtLastName.setText(resultBean.getLastName());
                    etxtEmail.setText(resultBean.getEmail());
                    etxtMobile.setText(resultBean.getMobileNo());
                } else{
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSignUpBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void editProfile(){
        helperMethods.showProgress();
        UserSignUpBean userSignUpBean = new UserSignUpBean();
        userSignUpBean.setUserId(sharedPrefBean.getUserId());
        userSignUpBean.setFirstName(etxtFirstName.getText().toString());
        userSignUpBean.setLastName(etxtLastName.getText().toString());
//        userSignUpBean.setEmail(etxtEmail.getText().toString());
//        userSignUpBean.setMobileNo(etxtMobile.getText().toString());
        callEditProfile = new NewLifeApiService().getNewLifeApi().editProfile(userSignUpBean);
        callEditProfile.enqueue(new Callback<UserSignUpBean>() {
            @Override
            public void onResponse(Call<UserSignUpBean> call, Response<UserSignUpBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200) {
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserSignUpBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
