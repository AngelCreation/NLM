package com.conceptcore.newlifemedicines.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.CompanyFilterAdapter;
import com.conceptcore.newlifemedicines.Adapters.ProductsAdapter;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Helpers.OnFilterSelectedListner;
import com.conceptcore.newlifemedicines.Models.Filter;
import com.conceptcore.newlifemedicines.Models.FilterBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;
import com.conceptcore.newlifemedicines.SaveAddress;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.BubbleThumbRangeSeekbar;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//search filter on Recycler example: http://camposha.info/source/android-recyclerview-search-filter

public class FilterFragment extends Fragment implements CompanyFilterAdapter.FilterSelectedListner {

    CrystalRangeSeekbar rangeSeekbar;

    protected View rootView;
    private TextView tvMin,tvMax,txtApply;

    private RecyclerView rvCmpNames;
//    private RelativeLayout rvApply;
    private CompanyFilterAdapter companyFilterAdapter;
    private List<Filter> list = new ArrayList<>();

    private SharedPrefBean sharedPrefBean;
    HelperMethods helperMethods = null;

    private Call<List<Filter>> call;
    private String catId;

    private SearchView txtSearchCompany;


    public FilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        rvCmpNames = rootView.findViewById(R.id.rvCmpNames);
        txtApply = rootView.findViewById(R.id.txtApply);
        // get min and max text view
        tvMin = rootView.findViewById(R.id.textMin2);
        tvMax = rootView.findViewById(R.id.textMax2);

        txtSearchCompany = rootView.findViewById(R.id.txtSearchCompany);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        Bundle bundle = this.getArguments();

        if(bundle != null){
            catId = bundle.getString("catId");
        }

        getCompanyNames();

        rvCmpNames.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCmpNames.setLayoutManager(llm);

        companyFilterAdapter = new CompanyFilterAdapter(list,getActivity());
        rvCmpNames.setAdapter(companyFilterAdapter);
        companyFilterAdapter.setFilterSelectedListner(this);

        txtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                callFilterItems();

                ProductFragment productFragment = new ProductFragment();
                Bundle bundle = new Bundle();
                bundle.putBoolean("fromFilter",true);
                bundle.putString("catId",catId);
                bundle.putString("max",tvMax.getText().toString());
                bundle.putString("min",tvMin.getText().toString());
                bundle.putParcelableArrayList("companyList", (ArrayList<? extends Parcelable>) list);
                productFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.frame, productFragment).addToBackStack(null).commit();

            }
        });

        txtSearchCompany.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                companyFilterAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return rootView;
    }

    private void getCompanyNames(){
        helperMethods.showProgress();
        call = new NewLifeApiService().getNewLifeApi().getCompanyList();
        call.enqueue(new Callback<List<Filter>>() {
            @Override
            public void onResponse(Call<List<Filter>> call, Response<List<Filter>> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    list.clear();
                    if(response.body().size() > 0){
                        list.addAll(response.body());
                    }
                    companyFilterAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Filter>> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRangeSeekbar3();
    }

    private void setRangeSeekbar3(){

        // get seekbar from view
        rangeSeekbar = rootView.findViewById(R.id.rangeSeekbar2);

        // set listener
        rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                tvMin.setText(String.valueOf(minValue));
                tvMax.setText(String.valueOf(maxValue));
            }
        });
    }

    @Override
    public void OnFilterSelected(int position, List<Filter> list) {
        //get updated list

        this.list = list;
    }


}
