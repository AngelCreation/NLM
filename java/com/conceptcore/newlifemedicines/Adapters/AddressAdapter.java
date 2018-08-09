package com.conceptcore.newlifemedicines.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.API.NewLifeApiService;
import com.conceptcore.newlifemedicines.Address;
import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Helpers.HelperMethods;
import com.conceptcore.newlifemedicines.Models.AddressBean;
import com.conceptcore.newlifemedicines.Models.SharedPrefBean;
import com.conceptcore.newlifemedicines.R;
import com.conceptcore.newlifemedicines.SaveAddress;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SVF 15213 on 09-06-2018.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressHolder> {

    private Context context;
    private List<AddressBean> addressList;
    private String userId;
    Call<AddressBean> call;

    private ProgressDialog progress;

    public AddressAdapter(Context context, List<AddressBean> addressList, String userId) {
        this.context = context;
        this.addressList = addressList;
        this.userId = userId;
    }

    @Override
    public AddressAdapter.AddressHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_address, parent, false);
        return new AddressAdapter.AddressHolder(v);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.AddressHolder holder, int position) {
        final AddressBean address = addressList.get(position);
        holder.txtName.setText(address.getName());
        holder.txtAdd1.setText(address.getAdd1());
        holder.txtAdd2.setText(address.getAdd2());
        holder.txtCityStatePin.setText(address.getCity() + " " + address.getState() + "-" + address.getPin());
        holder.txtMobile.setText(address.getMobileNo());

        holder.txtEdit.setTag(R.string.app_name,position);

        if(address.getStatus().equals("1")){
            holder.btnSelect.setTextColor(context.getResources().getColor(R.color.white));
            holder.btnSelect.setBackgroundResource(R.drawable.add_btn_back);
        } else{
            holder.btnSelect.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.btnSelect.setBackgroundResource(R.drawable.add_qty_back);
        }


        holder.btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call to save address
                showProgress();
                AddressBean addressBean = new AddressBean();
                addressBean.setAddressId(address.getAddressId());
                addressBean.setUserId(userId);
//                Log.e("data:: ","" + address.getAddressId() + userId);
                call = new NewLifeApiService().getNewLifeApi().selectAddress(addressBean);
                call.enqueue(new Callback<AddressBean>() {
                    @Override
                    public void onResponse(Call<AddressBean> call, Response<AddressBean> response) {
                        hideProgress();
                        if (response.code() == 200){
                            if(response.body().getSuccess()){
                                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.putExtra("address", address);
                                ((Activity)context).setResult(RESULT_OK, intent);
                                ((Activity)context).finish();
                            } else {
                                Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddressBean> call, Throwable t) {
                        hideProgress();
                        Toast.makeText(context, "Please check your connection", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        holder.txtEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //pass address obj and open fragment
                int position = (int) view.getTag(R.string.app_name);
                Intent intent = new Intent(context, SaveAddress.class);
                intent.putExtra("address",addressList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class AddressHolder extends RecyclerView.ViewHolder {

        TextView txtName,txtAdd1,txtAdd2,txtCityStatePin,txtMobile,txtEdit;
        Button btnSelect;

        public AddressHolder(View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtAdd1 = itemView.findViewById(R.id.txtAdd1);
            txtAdd2 = itemView.findViewById(R.id.txtAdd2);
            txtCityStatePin = itemView.findViewById(R.id.txtCityStatePin);
            txtMobile = itemView.findViewById(R.id.txtMobile);
            txtEdit = itemView.findViewById(R.id.txtEdit);
            btnSelect = itemView.findViewById(R.id.btnSelect);
        }
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


}
