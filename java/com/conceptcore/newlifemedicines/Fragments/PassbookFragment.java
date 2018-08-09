package com.conceptcore.newlifemedicines.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.PassbookAdapter;
import com.conceptcore.newlifemedicines.Adapters.ProductsAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.PassbookBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassbookFragment extends Fragment {

    private LinearLayout dataViewPassbook,errorViewPassbook;
    private RecyclerView rvPassbook;
    private PassbookAdapter passbookAdapter;
    private SwipeRefreshLayout swipeRefreshPassbook;

    private SharedPrefBean sharedPrefBean;
    HelperMethods helperMethods = null;

    private Call<List<PassbookBean>> call;
    List<PassbookBean> transactionsList = new ArrayList<>();

    public PassbookFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_passbook, container, false);
        dataViewPassbook = view.findViewById(R.id.dataViewPassbook);
        errorViewPassbook = view.findViewById(R.id.errorViewPassbook);
        rvPassbook = view.findViewById(R.id.rvPassbook);
        swipeRefreshPassbook = view.findViewById(R.id.swipeRefreshPassbook);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        getPassbookDetails();

        rvPassbook.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvPassbook.setLayoutManager(llm);

        passbookAdapter = new PassbookAdapter(getActivity(),transactionsList);
        rvPassbook.setAdapter(passbookAdapter);

        swipeRefreshPassbook.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPassbookDetails();
            }
        });

        return view;
    }

    private void getPassbookDetails(){
        helperMethods.showProgress();
        PassbookBean passbookBean = new PassbookBean();
        passbookBean.setUserId(sharedPrefBean.getUserId());
        call = new NewLifeApiService().getNewLifeApi().getPassbookDetails(passbookBean);
        call.enqueue(new Callback<List<PassbookBean>>() {
            @Override
            public void onResponse(Call<List<PassbookBean>> call, Response<List<PassbookBean>> response) {
                helperMethods.hideProgress();
                if (swipeRefreshPassbook.isRefreshing()) {
                    swipeRefreshPassbook.setRefreshing(false);
                }

                if(response.code() == 200){
                    transactionsList.clear();
                    if(response.body().size() > 0){
                        Log.e("passbook list size","" + response.body().size());
                        dataViewPassbook.setVisibility(View.VISIBLE);
                        errorViewPassbook.setVisibility(View.GONE);

                        transactionsList.addAll(response.body());
                    } else {
                        dataViewPassbook.setVisibility(View.GONE);
                        errorViewPassbook.setVisibility(View.VISIBLE);
                    }
                    passbookAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PassbookBean>> call, Throwable t) {
                helperMethods.hideProgress();
                if (swipeRefreshPassbook.isRefreshing()) {
                    swipeRefreshPassbook.setRefreshing(false);
                }
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
