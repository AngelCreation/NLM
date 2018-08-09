package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.OrderBean;
import com.conceptcore.newlifemedicines.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SVF 15213 on 25-06-2018.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> implements View.OnClickListener {

    private Context context;
    private List<OrderBean> orderList;
    OrderClickListner orderClickListner;

    public OrderAdapter(Context context, List<OrderBean> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public OrderAdapter.OrderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_order, parent, false);
        return new OrderAdapter.OrderHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderAdapter.OrderHolder holder, int position) {
        OrderBean orderBean = orderList.get(position);
        holder.txtOrderNo.setText("Order No " + orderBean.getOrderId());
        holder.txtDateTime.setText(changeDateFormat(orderBean.getDatetOfTme()));
        holder.txtPrice.setText(orderBean.getAmount());

        holder.llOrder.setOnClickListener(this);
        holder.llOrder.setTag(R.string.app_name,position);

        int status = Integer.parseInt(orderBean.getStatus());
        switch (status){
            case 1:
                holder.txtStatus.setText("Pending");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_dark));
                break;
            case 2:
                holder.txtStatus.setText("Picked Up");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,android.R.color.holo_orange_dark));

                break;
            case 3:
                holder.txtStatus.setText("On the Way");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,android.R.color.holo_orange_dark));
                break;
            case 4:
                holder.txtStatus.setText("Delivered");
                holder.txtStatus.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public interface OrderClickListner{
        void OnOrderClicked(View view, int position, List<OrderBean> list);
    }

    public void setOrderClickListner(OrderClickListner orderClickListner) {
        this.orderClickListner = orderClickListner;
    }

    @Override
    public void onClick(View view) {
        LinearLayout ll = (LinearLayout) view;
        int position = (int) ll.getTag(R.string.app_name);
        orderClickListner.OnOrderClicked(view,position,orderList);
    }

    public class OrderHolder extends RecyclerView.ViewHolder {

        TextView txtOrderNo,txtDateTime,txtPrice,txtStatus;
        LinearLayout llOrder;

        public OrderHolder(View itemView) {
            super(itemView);

            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            llOrder = itemView.findViewById(R.id.llOrder);
            txtStatus = itemView.findViewById(R.id.txtStatus);

        }
    }

    private String changeDateFormat(String str){
//        String[] dateArr = str.split("\\s+");
//        String strDate = dateArr[0];
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            Date date = Constants.sdfDatetimeMobile.parse(str);
            return Constants.dateFormatOrder.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}

