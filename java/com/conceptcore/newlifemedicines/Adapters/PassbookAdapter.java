package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.Models.PassbookBean;
import com.conceptcore.newlifemedicines.R;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by SVF 15213 on 29-06-2018.
 */

public class PassbookAdapter extends RecyclerView.Adapter<PassbookAdapter.PassbookHolder> {

    private Context context;
    private List<PassbookBean> transactionsList;

    public PassbookAdapter(Context context, List<PassbookBean> transactionsList) {
        this.context = context;
        this.transactionsList = transactionsList;
    }

    @Override
    public PassbookAdapter.PassbookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_transaction, parent, false);
        return new PassbookAdapter.PassbookHolder(v);
    }

    @Override
    public void onBindViewHolder(PassbookAdapter.PassbookHolder holder, int position) {
        PassbookBean passbookBean = transactionsList.get(position);
        holder.txtOrderNo.setText(passbookBean.getOrderId());
        if(Integer.parseInt(passbookBean.getStatus()) == 1){
            holder.txtPayRecieve.setText("Payment Done");
            holder.txtBalance.setText("- \u20B9 " +String.valueOf(Float.parseFloat(passbookBean.getBalance())));
            holder.txtBalance.setTextColor(ContextCompat.getColor(context,android.R.color.holo_red_dark));
        } else if(Integer.parseInt(passbookBean.getStatus()) == 2){
            holder.txtPayRecieve.setText("Cashback Received");
            holder.txtBalance.setText("+ \u20B9 " +  String.valueOf(Float.parseFloat(passbookBean.getBalance())));
            holder.txtBalance.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
        }
        holder.txtDateTime.setText(passbookBean.getDateTime());
    }


    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

    public class PassbookHolder extends RecyclerView.ViewHolder{
        TextView txtPayRecieve,txtBalance,txtOrderNo,txtDateTime;
        public PassbookHolder(View itemView) {
            super(itemView);
            txtPayRecieve = itemView.findViewById(R.id.txtPayRecieve);
            txtBalance = itemView.findViewById(R.id.txtBalance);
            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtDateTime = itemView.findViewById(R.id.txtDateTime);
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
