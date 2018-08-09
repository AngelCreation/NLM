package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.R;
import com.google.android.gms.tasks.TaskExecutors;

import java.util.List;

/**
 * Created by SVF 15213 on 05-06-2018.
 */

public class FilterMenuAdapter extends RecyclerView.Adapter<FilterMenuAdapter.MenuItemHolder> {

    private Context context;
    private List<String> itemList;

    public FilterMenuAdapter(Context context, List<String> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public FilterMenuAdapter.MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_filter_item, parent, false);
        return new MenuItemHolder(v);
    }

    @Override
    public void onBindViewHolder(final FilterMenuAdapter.MenuItemHolder holder, int position) {
        final String item = itemList.get(position);
        holder.txtFilterType.setText(item);

        holder.rbtnFilterType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((RadioButton) view).isChecked()){
                    Toast.makeText(context, item + " is selected", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MenuItemHolder extends RecyclerView.ViewHolder {

        TextView txtFilterType;
        RadioButton rbtnFilterType;

        public MenuItemHolder(View itemView) {
            super(itemView);

            txtFilterType = itemView.findViewById(R.id.txtFilterType);
            rbtnFilterType = itemView.findViewById(R.id.rbtnFilterType);
        }
    }
}
