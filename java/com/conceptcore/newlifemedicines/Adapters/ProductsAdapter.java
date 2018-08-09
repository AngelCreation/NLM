package com.conceptcore.newlifemedicines.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.R;
import com.conceptcore.newlifemedicines.RootActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SVF 15213 on 04-06-2018.
 */

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ProductHolder>{

    private Context context;
    private List<ProductBean> productList;
//    private List<ProductBean> productListCopy = new ArrayList<>();
    private String userId;
    private Call<ProductBean> call;
    private ProgressDialog progress;

    public ProductsAdapter(Context context, List<ProductBean> productList,String userId) {
        this.context = context;
        this.productList = productList;
        this.userId = userId;
    }

    @Override
    public ProductsAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(final ProductsAdapter.ProductHolder holder, int position) {
        final ProductBean product = productList.get(position);
//        holder.imgProduct.setImageDrawable(context.getResources().getDrawable(R.drawable.product));
        Picasso.with(context)
                .load(Constants.HOST_NAME + product.getImagePath())
//                .resize(260, 240)
//                .centerCrop()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .error(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .fit()
//                .resize(R.dimen.product_width, R.dimen.product_height)
//                .centerCrop()
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.imgProduct);


        holder.txtTitle.setText(product.getTitle());
        holder.txtPrice.setText(product.getPrice());

        if(Integer.parseInt(product.getDiscount()) > 0) {
            holder.txtDiscount.setText("+ " + product.getDiscount() + "% Cashback");
        } else {
            holder.txtDiscount.setVisibility(View.GONE);
        }

        if(product.getQty() != null && Integer.parseInt(product.getQty()) > 0){
            holder.btnAdd.setVisibility(View.GONE);
            holder.llQty.setVisibility(View.VISIBLE);

            holder.txtQty.setText(product.getQty());

            if(Integer.parseInt(product.getQty()) > 1){
                holder.imgSub.setVisibility(View.VISIBLE);
                holder.imgDel.setVisibility(View.GONE);
            } else{
                holder.imgSub.setVisibility(View.GONE);
                holder.imgDel.setVisibility(View.VISIBLE);
            }
        }

        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty("1");
                holder.btnAdd.setVisibility(View.GONE);
                holder.llQty.setVisibility(View.VISIBLE);

                holder.imgDel.setImageResource(R.drawable.ic_action_delete);
                holder.txtQty.setText(product.getQty());

                updateQty(product.getQty(),product.getProductId());
            }
        });

        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty(addQuantity(product.getQty()));
                holder.txtQty.setText(product.getQty());

                if(Integer.parseInt(product.getQty()) > 1){
                    holder.imgSub.setVisibility(View.VISIBLE);
                    holder.imgDel.setVisibility(View.GONE);
                } else{
                    holder.imgSub.setVisibility(View.GONE);
                    holder.imgDel.setVisibility(View.VISIBLE);
                }
                updateQty(product.getQty(),product.getProductId());

            }
        });

        holder.imgSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                product.setQty(subQuantity(product.getQty()));
                holder.txtQty.setText(product.getQty());

                if(Integer.parseInt(product.getQty()) > 1){
                    holder.imgSub.setVisibility(View.VISIBLE);
                    holder.imgDel.setVisibility(View.GONE);
                } else{
                    holder.imgSub.setVisibility(View.GONE);
                    holder.imgDel.setVisibility(View.VISIBLE);
                }

                updateQty(product.getQty(),product.getProductId());

            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                product.setQty("0");
                holder.btnAdd.setVisibility(View.VISIBLE);
                holder.llQty.setVisibility(View.GONE);

                updateQty(product.getQty(),product.getProductId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtTitle,txtPrice,btnAdd,txtQty,txtDiscount;
        LinearLayout llQty;
        ImageButton imgAdd,imgSub,imgDel;

        public ProductHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDiscount = itemView.findViewById(R.id.txtDiscount);
            btnAdd = itemView.findViewById(R.id.btnAdd);

            llQty = itemView.findViewById(R.id.llQty);
            txtQty = itemView.findViewById(R.id.txtQty);
            imgSub = itemView.findViewById(R.id.imgSub);
            imgDel = itemView.findViewById(R.id.imgDel);
            imgAdd = itemView.findViewById(R.id.imgAdd);
        }
    }

    private void updateQty(String currQty,String productId){
        showProgress();
        ProductBean productBean = new ProductBean();
        productBean.setQty(currQty);
        productBean.setUserId(userId);
        productBean.setProductId(productId);
        call = new NewLifeApiService().getNewLifeApi().updateQty(productBean);
        call.enqueue(new Callback<ProductBean>() {
            @Override
            public void onResponse(Call<ProductBean> call, Response<ProductBean> response) {
                hideProgress();
                if(response.code() == 200){
                    ProductBean resultBean = response.body();
                    if(resultBean.getSuccess()){
                        Toast.makeText(context, resultBean.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProductBean> call, Throwable t) {
                hideProgress();
                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
            }
        });

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

    /*public List<ProductBean> filter(String text) {
        Log.e("Products"," list size " + productList.size());
        productListCopy.clear();
        productListCopy.addAll(productList);
        productList.clear();
        Log.e("Products"," txt " + text);
        if(text.isEmpty() || text.equals("")){
            productList.addAll(productListCopy);
        } else{
//            Log.e("Products"," inside else " + productListCopy.size());
            text = text.toLowerCase();
            for(ProductBean product: productListCopy){
//                Log.e("Products"," inside for lw text" + text);
//                Log.e("Products"," inside for title" + product.getTitle().toLowerCase());
                if(product.getTitle().toLowerCase().contains(text)){
                    productList.add(product);
                }
            }
        }
        ((RootActivity)context).runOnUiThread(
                new Runnable() {
                    public void run() {
                        if(productList.size() == 0) {
                            Toast.makeText(context, "No products found with your search", Toast.LENGTH_SHORT).show();
                        }
                        notifyDataSetChanged();
                    }
                });
        return productList;
    }*/
}
