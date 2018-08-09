package com.conceptcore.newlifemedicines.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchedProductFragment extends Fragment {

    private String productId;

    HelperMethods helperMethods = null;
    SharedPrefBean sharedPrefBean;

    private ImageView imgProduct;
    private TextView txtTitle,txtPrice,txtDiscount,btnAdd,txtQty;
    private ImageButton imgAdd,imgSub,imgDel;
    private LinearLayout llQty;

    private Call<ProductBean> callProduct;
    private Call<ProductBean> call;

    public SearchedProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_searched_product, container, false);
        imgProduct = view.findViewById(R.id.imgProduct);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtPrice = view.findViewById(R.id.txtPrice);
        txtDiscount = view.findViewById(R.id.txtDiscount);
        btnAdd = view.findViewById(R.id.btnAdd);
        txtQty = view.findViewById(R.id.txtQty);
        imgSub = view.findViewById(R.id.imgSub);
        imgDel = view.findViewById(R.id.imgDel);
        imgAdd = view.findViewById(R.id.imgAdd);
        llQty = view.findViewById(R.id.llQty);

        helperMethods = new HelperMethods(getActivity());

        sharedPrefBean = helperMethods.getLocalSharedPreferences();

        Bundle bundle = this.getArguments();
        if(bundle != null){
            productId = bundle.getString("productId");
            getProduct(productId);
        } else {
            getFragmentManager().beginTransaction().replace(R.id.frame, new CategoryFragment()).commit();
        }

        return view;
    }

    private void getProduct(String productId){
        helperMethods.showProgress();
        ProductBean productBean = new ProductBean();
        productBean.setProductId(productId);
        productBean.setUserId(sharedPrefBean.getUserId());
        callProduct = new NewLifeApiService().getNewLifeApi().getProduct(productBean);
        callProduct.enqueue(new Callback<ProductBean>() {
            @Override
            public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    ProductBean product = response.body();
                    setValuesAndListners(product);
                } else {
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setValuesAndListners(final ProductBean product){
        Picasso.with(getActivity())
                .load(Constants.HOST_NAME + product.getImagePath())
//                .resize(260, 240)
//                .centerCrop()
                .placeholder(ContextCompat.getDrawable(getActivity(), R.drawable.medicines))
//                .error(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .fit()
//                .resize(R.dimen.product_width, R.dimen.product_height)
//                .centerCrop()
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imgProduct);

        txtTitle.setText(product.getTitle());
        txtPrice.setText(product.getPrice());

        if(Integer.parseInt(product.getDiscount()) > 0) {
            txtDiscount.setText("+ " + product.getDiscount() + "% Cashback");
        } else {
            txtDiscount.setVisibility(View.GONE);
        }

        if(product.getQty() != null && Integer.parseInt(product.getQty()) > 0){
            btnAdd.setVisibility(View.GONE);
            llQty.setVisibility(View.VISIBLE);

            txtQty.setText(product.getQty());

            if(Integer.parseInt(product.getQty()) > 1){
                imgSub.setVisibility(View.VISIBLE);
                imgDel.setVisibility(View.GONE);
            } else{
                imgSub.setVisibility(View.GONE);
                imgDel.setVisibility(View.VISIBLE);
            }
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty("1");
                btnAdd.setVisibility(View.GONE);
                llQty.setVisibility(View.VISIBLE);

                imgDel.setImageResource(R.drawable.ic_action_delete);
                txtQty.setText(product.getQty());

                updateQty(product.getQty(),product.getProductId());
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty(addQuantity(product.getQty()));
                txtQty.setText(product.getQty());

                if(Integer.parseInt(product.getQty()) > 1){
                    imgSub.setVisibility(View.VISIBLE);
                    imgDel.setVisibility(View.GONE);
                } else{
                    imgSub.setVisibility(View.GONE);
                    imgDel.setVisibility(View.VISIBLE);
                }
                updateQty(product.getQty(),product.getProductId());

            }
        });

        imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty(subQuantity(product.getQty()));
                txtQty.setText(product.getQty());

                if(Integer.parseInt(product.getQty()) > 1){
                    imgSub.setVisibility(View.VISIBLE);
                    imgDel.setVisibility(View.GONE);
                } else{
                    imgSub.setVisibility(View.GONE);
                    imgDel.setVisibility(View.VISIBLE);
                }

                updateQty(product.getQty(),product.getProductId());

            }
        });

        imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product.setQty("0");
                btnAdd.setVisibility(View.VISIBLE);
                llQty.setVisibility(View.GONE);

                updateQty(product.getQty(),product.getProductId());
            }
        });


    }

    private void updateQty(String currQty,String productId){
        helperMethods.showProgress();
        ProductBean productBean = new ProductBean();
        productBean.setQty(currQty);
        productBean.setUserId(sharedPrefBean.getUserId());
        productBean.setProductId(productId);
        call = new NewLifeApiService().getNewLifeApi().updateQty(productBean);
        call.enqueue(new Callback<ProductBean>() {
            @Override
            public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                helperMethods.hideProgress();
                if(response.code() == 200){
                    ProductBean resultBean = response.body();
                    if(resultBean.getSuccess()){
                        Toast.makeText(getActivity(), resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductBean> call, Throwable t) {
                helperMethods.hideProgress();
                Toast.makeText(getActivity(), "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String addQuantity(String preQty){
        int currQty = Integer.parseInt(preQty);
        currQty += 1 ;
        return String.valueOf(currQty);
    }

    private String subQuantity(String preQty){
        int currQty = Integer.parseInt(preQty);
        currQty -= 1 ;
        return String.valueOf(currQty);
    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
