package com.conceptcore.newlifemedicines;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.AddressAdapter;
import com.conceptcore.newlifemedicines.Adapters.CartAdapter;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Address extends AppCompatActivity {

    private TextView txtAddNew;
    private RecyclerView rvAddress;
    private SwipeRefreshLayout swipeRefresh;
    private LinearLayout dataViewAddress,errorViewAddress;
    private ProgressDialog progress;

    private String userId;

    private Call<List<AddressBean>> call;

    private AddressAdapter addressAdapter;
    List<AddressBean> listAddresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // back button pressed
                finish();
            }
        });

        txtAddNew = findViewById(R.id.txtAddNew);
        rvAddress = findViewById(R.id.rvAddress);
        swipeRefresh = findViewById(R.id.swipeRefresh);
        dataViewAddress = findViewById(R.id.dataViewAddress);
        errorViewAddress = findViewById(R.id.errorViewAddress);
//        List<AddressBean> list = createAddressList();

        getLocalSharedPreferences();

        //recycler view
        rvAddress.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(Address.this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvAddress.setLayoutManager(llm);

        addressAdapter = new AddressAdapter(Address.this,listAddresses,userId);
        rvAddress.setAdapter(addressAdapter);
        //recycler ended

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllAddresses();
            }
        });

        txtAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Address.this, SaveAddress.class);
                startActivity(intent);
            }
        });

        getAllAddresses();

    }

    private void getAllAddresses(){
        showProgress();

        final AddressBean addressBean = new AddressBean();
        addressBean.setUserId(userId);

        call = new NewLifeApiService().getNewLifeApi().getAllAddresses(addressBean);
        call.enqueue(new Callback<List<AddressBean>>() {
            @Override
            public void onResponse(Call<List<AddressBean>> call, Response<List<AddressBean>> response) {
                hideProgress();

                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }

                if(response.code() == 200) {
                    listAddresses.clear();

                    if(response.body().size() > 0){
                        dataViewAddress.setVisibility(View.VISIBLE);
                        errorViewAddress.setVisibility(View.GONE);

                        listAddresses.addAll(response.body());
                    } else {
                        dataViewAddress.setVisibility(View.GONE);
                        errorViewAddress.setVisibility(View.VISIBLE);
                    }
                    addressAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(Address.this, "Please check your connection", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<AddressBean>> call, Throwable t) {
                hideProgress();
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
                Toast.makeText(Address.this, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showProgress() {
        progress = new ProgressDialog(Address.this);
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
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void getLocalSharedPreferences() {
        SharedPreferences prefs = getSharedPreferences(Constants.USER_DETAILS, MODE_PRIVATE);
        userId = prefs.getString("userId", "0");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
