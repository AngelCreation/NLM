package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Models.Product;
import com.conceptcore.newlifemedicines.R;

import java.util.List;

/**
 * Created by SVF 15213 on 25-06-2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder>{

    private Context context;
    private List<Product> historyList;

    public HistoryAdapter(Context context, List<Product> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @Override
    public HistoryAdapter.HistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_history, parent, false);
        return new HistoryAdapter.HistoryHolder(v);
    }

    @Override
    public void onBindViewHolder(HistoryAdapter.HistoryHolder holder, int position) {
        Product product = historyList.get(position);
        holder.txtTitle.setText(product.getTitle());
        holder.txtQty.setText(product.getQty());
        holder.txtPrice.setText(product.getPrice());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtQty,txtPrice;
        public HistoryHolder(View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtPrice = itemView.findViewById(R.id.txtPrice);
        }
    }
}
