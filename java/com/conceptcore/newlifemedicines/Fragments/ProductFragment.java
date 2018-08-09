package com.conceptcore.newlifemedicines.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.ProductsAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Helpers.OnFilterSelectedListner;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.Company;
import com.conceptcore.newlifemedicines.Models.Filter;
import com.conceptcore.newlifemedicines.Models.FilterBean;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductFragment extends Fragment implements OnFilterSelectedListner,Serializable {

    private RecyclerView rvProducts;
    private LinearLayout llSortBy,llFilter,errorViewProducts,dataViewProducts;
    private SwipeRefreshLayout swipeRefresh;
    private ProductsAdapter productsAdapter;
//    private EditText inputSearch;
//    Timer timer = null;

    private Call<List<ProductBean>> call;
    List<ProductBean> productsList = new ArrayList<>();

    HelperMethods helperMethods = null;
    SharedPrefBean sharedPrefBean;

    private String catId,max,min;
    private List<Filter> list = new ArrayList<>();
    private List<Company> campanyIdList = new ArrayList<>();

    public ProductFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        rvProducts = view.findViewById(R.id.rvProducts);
        llSortBy = view.findViewById(R.id.llSortBy);
        llFilter = view.findViewById(R.id.llFilter);
        dataViewProducts = view.findViewById(R.id.dataViewProducts);
        errorViewProducts = view.findViewById(R.id.errorViewProducts);
//        inputSearch = view.findViewById(R.id.inputSearch);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            if(bundle.getBoolean("fromFilter")){
                catId = bundle.getString("catId");
                max = bundle.getString("max");
                min = bundle.getString("min");
                list = bundle.getParcelableArrayList("companyList");
                getAllProductsFilterBy(max,min,list);
            } else {
                catId = bundle.getString("catid");
                getAllProductsSortBy("","");
            }

        }

        //recycler view
        rvProducts.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvProducts.setLayoutManager(llm);

        productsAdapter = new ProductsAdapter(getActivity(),productsList,sharedPrefBean.getUserId());
        rvProducts.setAdapter(productsAdapter);
        //recycler ended

        llSortBy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("dashboardref",ProductFragment.this);
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        llFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FilterFragment filterFragment = new FilterFragment();
                Bundle bundle = new Bundle();
                bundle.putString("catId",catId);
                filterFragment.setArguments(bundle);
                getChildFragmentManager().beginTransaction().replace(R.id.frame, filterFragment).addToBackStack(null).commit();
            }
        });

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllProductsSortBy("","");
            }
        });

        /*inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text

                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // do your actual work here
//                        productsAdapter.filter(inputSearch.getText().toString());

                    }
                }, 600);
            }
        });*/

        return view;
    }

    private void getAllProductsSortBy(String sortBy,String orderBy){
        helperMethods.showProgress();

        ProductBean productBean = new ProductBean();
        productBean.setCategoryId(catId);
        productBean.setSort(sortBy);
        productBean.setOrder(orderBy);
        productBean.setUserId(sharedPrefBean.getUserId());
        call = new NewLifeApiService().getNewLifeApi().getAllProducts(productBean);
        call.enqueue(new Callback<List<ProductBean>>() {
            @Override
            public void onResponse(Call<List<ProductBean>> call, Response<List<ProductBean>> response) {
                helperMethods.hideProgress();

                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }

                if(response.code() == 200){
                    productsList.clear();
                    if(response.body().size() > 0){
                        dataViewProducts.setVisibility(View.VISIBLE);
                        errorViewProducts.setVisibility(View.GONE);

                        productsList.addAll(response.body());
                    } else {
                        dataViewProducts.setVisibility(View.GONE);
                        errorViewProducts.setVisibility(View.VISIBLE);
                    }
                    productsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductBean>> call, Throwable t) {
                helperMethods.hideProgress();
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllProductsFilterBy(String max, String min, List<Filter> list){
        helperMethods.showProgress();

        FilterBean filterBean = new FilterBean();
        filterBean.setMax(max);
        filterBean.setMin(min);
        filterBean.setUserId(sharedPrefBean.getUserId());
        filterBean.setCategoryId(catId);
        for (int i = 0; i < list.size(); i++){
            if(list.get(i).getSelected()){
                Company company = new Company();
                company.setCompanyId(list.get(i).getCompayId());
                campanyIdList.add(company);
            }
        }
        filterBean.setCompany(campanyIdList);
        call = new NewLifeApiService().getNewLifeApi().getAllProductsFilterBy(filterBean);
        call.enqueue(new Callback<List<ProductBean>>() {
            @Override
            public void onResponse(Call<List<ProductBean>> call, Response<List<ProductBean>> response) {
                helperMethods.hideProgress();

                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }

                if(response.code() == 200){
                    productsList.clear();
                    if(response.body().size() > 0){
                        dataViewProducts.setVisibility(View.VISIBLE);
                        errorViewProducts.setVisibility(View.GONE);

                        productsList.addAll(response.body());
                    } else {
                        dataViewProducts.setVisibility(View.GONE);
                        errorViewProducts.setVisibility(View.VISIBLE);
                    }
                    productsAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductBean>> call, Throwable t) {
                helperMethods.hideProgress();
                if (swipeRefresh.isRefreshing()) {
                    swipeRefresh.setRefreshing(false);
                }
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnFilterSelected(View view, int position, List<String> list) {
        Toast.makeText(getActivity(), list.get(position) + " is selected", Toast.LENGTH_LONG).show();

        if(list.get(position).matches("Price -- High to Low")){
            getAllProductsSortBy("price","desc");
        } else if (list.get(position).matches("Price -- Low to High")){
            getAllProductsSortBy("price","asc");
        }

    }
}
