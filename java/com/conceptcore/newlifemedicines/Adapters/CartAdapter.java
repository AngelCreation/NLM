package com.conceptcore.newlifemedicines.Adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import com.conceptcore.newlifemedicines.Models.CartProduct;
import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.R;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SVF 15213 on 04-06-2018.
 */

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ProductHolder>{

    private Context context;
    private List<CartProduct> productList;
    private String userId;
    private Call<ProductBean> call;
    private ProgressDialog progress;
    CartRefreshListner cartRefreshListner;

    public CartAdapter(Context context, List<CartProduct> productList,String userId) {
        this.context = context;
        this.productList = productList;
        this.userId = userId;
    }

    @Override
    public CartAdapter.ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_cart, parent, false);
        return new ProductHolder(v);
    }

    @Override
    public void onBindViewHolder(final CartAdapter.ProductHolder holder, final int position) {
        final CartProduct product = productList.get(position);

        if(Integer.parseInt(product.getFlag()) == 1){
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

            holder.txtPrice.setText(product.getPrice());
        } else if(Integer.parseInt(product.getFlag()) == 2){
            Picasso.with(context)
                    .load(Constants.HOST_NAME + product.getPrescriptionImage())
//                .resize(260, 240)
//                .centerCrop()
                    .placeholder(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .error(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .fit()
//                .resize(R.dimen.product_width, R.dimen.product_height)
//                .centerCrop()
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                    .into(holder.imgProduct);

            try {
                if(Integer.parseInt(product.getPrice()) > 0){
                    holder.txtPrice.setText(product.getPrice());
                } else {
                    holder.llPrice.setVisibility(View.GONE);
                }
            } catch (Exception e){
                holder.llPrice.setVisibility(View.GONE);
                e.printStackTrace();
            }
        }


        holder.txtTitle.setText(product.getTitle());
        holder.txtDelDate.setText(changeDateFormat(product.getDelDate()));

        if(Integer.parseInt(product.getQty()) > 0){
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

//        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                product.setQty("1");
//                holder.btnAdd.setVisibility(View.GONE);
//                holder.llQty.setVisibility(View.VISIBLE);
//
//                holder.imgDel.setImageResource(R.drawable.ic_action_delete);
//                holder.txtQty.setText(product.getQty());
//            }
//        });

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
                updateQty(product.getQty(),product.getProductId(),product.getCartId());
                cartRefreshListner.OnCartRefresh(view,position,productList);
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
                updateQty(product.getQty(),product.getProductId(),product.getCartId());
                cartRefreshListner.OnCartRefresh(view,position,productList);
            }
        });

        holder.imgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //remove item after showing alert
                showAlert(product,view,position);

            }
        });

    }

    public interface CartRefreshListner{
        void OnCartRefresh(View view, int position, List<CartProduct> list);
    }

    public void setCartRefreshListner(CartRefreshListner cartRefreshListner) {
        this.cartRefreshListner = cartRefreshListner;
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        ImageView imgProduct;
        TextView txtTitle,txtPrice,txtQty,txtDelDate;
        LinearLayout llQty,llPrice;
        ImageButton imgAdd,imgSub,imgDel;

        public ProductHolder(View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtDelDate = itemView.findViewById(R.id.txtDelDate);

            llQty = itemView.findViewById(R.id.llQty);
            llPrice = itemView.findViewById(R.id.llPrice);
            txtQty = itemView.findViewById(R.id.txtQty);
            imgSub = itemView.findViewById(R.id.imgSub);
            imgDel = itemView.findViewById(R.id.imgDel);
            imgAdd = itemView.findViewById(R.id.imgAdd);
        }
    }

    private void updateQty(String currQty,String productId,String cartId){
        showProgress();
        ProductBean productBean = new ProductBean();
        productBean.setQty(currQty);
        productBean.setUserId(userId);
        productBean.setProductId(productId);
        productBean.setCartId(cartId);
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

    private String changeDateFormat(String str){
        String[] dateArr = str.split("\\s+");
        String strDate = dateArr[0];
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date date = format.parse(strDate);
            return Constants.dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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

    private void showAlert(final CartProduct product, final View view, final int position){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(context);
        }
        builder.setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        updateQty("0",product.getProductId(),product.getCartId());
                        cartRefreshListner.OnCartRefresh(view,position,productList);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.dismiss();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}
