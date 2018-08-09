package com.conceptcore.newlifemedicines.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Adapters.CategoryAdapter;
import com.conceptcore.newlifemedicines.Adapters.MainSliderAdapter;
import com.conceptcore.newlifemedicines.Adapters.ProductAutoCompleteAdapter;
import com.conceptcore.newlifemedicines.Adapters.ProductsAdapter;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.R;
import com.conceptcore.newlifemedicines.UploadPrescription;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ss.com.bannerslider.Slider;

public class CategoryFragment extends Fragment implements CategoryAdapter.CatClickListner,ProductAutoCompleteAdapter.SugClickListner {

    private ProgressDialog progress;
    private static int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 501;

    private RecyclerView rvCats;
    private LinearLayout dataViewCats,errorViewCats;
    private Button btnUploadRequirement;

    private Call<List<CategoryBean>> call;
    List<CategoryBean> catList = new ArrayList<>();

    private CategoryAdapter categoryAdapter;

    private AutoCompleteTextView inputSearch;
    private Call<List<ProductBean>> callSuggestions;
    List<ProductBean> productsList = new ArrayList<>();
    private ProductAutoCompleteAdapter productAutoCompleteAdapter;

    private Call<List<CategoryBean>> callSliders;
    List<CategoryBean> sliderImageList = new ArrayList<>();


    private Slider slider;

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        rvCats = view.findViewById(R.id.rvCats);
        dataViewCats = view.findViewById(R.id.dataViewCats);
        errorViewCats = view.findViewById(R.id.errorViewCats);
        btnUploadRequirement = view.findViewById(R.id.btnUploadRequirement);
        inputSearch = view.findViewById(R.id.inputSearch);

        slider = view.findViewById(R.id.banner_slider);
//        sliderImageList.addAll(response.body());
        slider.setAdapter(new MainSliderAdapter());
        slider.setSelectedSlide(0);

//        getSliderImages();

        getAllCategories();

        //recycler view
        rvCats.setHasFixedSize(true);
        GridLayoutManager llm = new GridLayoutManager(getActivity(),3);
//        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
//        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvCats.setLayoutManager(llm);

        categoryAdapter = new CategoryAdapter(getActivity(),catList);
        categoryAdapter.setCatClickListner(this);
        rvCats.setAdapter(categoryAdapter);
        //recycler ended

        productAutoCompleteAdapter = new ProductAutoCompleteAdapter(getActivity(),productsList);
        inputSearch.setAdapter(productAutoCompleteAdapter);
        productAutoCompleteAdapter.setSugClickListner(this);

        btnUploadRequirement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
        return view;
    }

    /*private void getSliderImages(){
        showProgress();
        callSliders = new NewLifeApiService().getNewLifeApi().getSliderImages();
        callSliders.enqueue(new Callback<List<CategoryBean>>() {

            @Override
            public void onResponse(Call<List<CategoryBean>> call, Response<List<CategoryBean>> response) {
                if(response.code() == 200){
                    if(response.body().size() > 0){
                        sliderImageList.addAll(response.body());
                        slider.setAdapter(new MainSliderAdapter(sliderImageList));
                        slider.setSelectedSlide(0);
                    }

                    getAllCategories();
                } else {
                    hideProgress();
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryBean>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void getAllCategories(){
        showProgress();

        call = new NewLifeApiService().getNewLifeApi().getAllCats();
        call.enqueue(new Callback<List<CategoryBean>>() {
            @Override
            public void onResponse(Call<List<CategoryBean>> call, Response<List<CategoryBean>> response) {
//                hideProgress();

                if(response.code() == 200){
                    catList.clear();
                    if(response.body().size() > 0){
                        dataViewCats.setVisibility(View.VISIBLE);
                        errorViewCats.setVisibility(View.GONE);

                        catList.addAll(response.body());

                        getAllSuggestions();
                    } else {
                        hideProgress();
                        dataViewCats.setVisibility(View.GONE);
                        errorViewCats.setVisibility(View.VISIBLE);
                    }
                    categoryAdapter.notifyDataSetChanged();
                } else {
                    hideProgress();
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<CategoryBean>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllSuggestions(){
        callSuggestions = new NewLifeApiService().getNewLifeApi().getAllSuggestions();
        callSuggestions.enqueue(new Callback<List<ProductBean>>() {
            @Override
            public void onResponse(Call<List<ProductBean>> call, Response<List<ProductBean>> response) {
                hideProgress();

                if(response.code() == 200){
                    productsList.clear();
                    if (response.body().size() > 0) {
                        productsList.addAll(response.body());
                    }
                    productAutoCompleteAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ProductBean>> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

        } else {
            // Permission has already been granted
            Intent intent = new Intent(getActivity(), UploadPrescription.class);
            startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 501: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Intent intent = new Intent(getActivity(), UploadPrescription.class);
                    startActivity(intent);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }
        }
    }

    @Override
    public void OnCatClicked(View view, int position, List<CategoryBean> list) {
        CategoryBean cat = list.get(position);

        Bundle bundle = new Bundle();
        bundle.putString("catid",cat.getCategoryId());
        bundle.putBoolean("fromFilter",false);
        ProductFragment productFragment = new ProductFragment();
        productFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.frame, productFragment).commit();
    }

    private void showProgress() {
        progress = new ProgressDialog(getActivity());
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
    }

    private void hideProgress() {
        if ((progress != null) && progress.isShowing()) {
            progress.dismiss();
        }
    }

    @Override
    public void onSugClicked(View view, int position, List<ProductBean> list) {
        ProductBean product = list.get(position);
        Bundle bundle = new Bundle();
        bundle.putString("productId",product.getProductId());

        SearchedProductFragment searchedProductFragment = new SearchedProductFragment();
        searchedProductFragment.setArguments(bundle);

        getFragmentManager().beginTransaction().replace(R.id.frame, searchedProductFragment).commit();
    }
}
