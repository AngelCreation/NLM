package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filterable;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Helpers.CompanyFilter;
import com.conceptcore.newlifemedicines.Models.Filter;
import com.conceptcore.newlifemedicines.Models.FilterBean;
import com.conceptcore.newlifemedicines.R;

import java.util.List;

/**
 * Created by SVF 15213 on 29-06-2018.
 */

public class CompanyFilterAdapter extends RecyclerView.Adapter<CompanyFilterAdapter.CompanyHolder> implements Filterable {

    public List<Filter> list;
    private Context context;
    FilterSelectedListner filterSelectedListner;
    CompanyFilter filter;

    public CompanyFilterAdapter(List<Filter> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public CompanyFilterAdapter.CompanyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_filter_company_names, parent, false);
        return new CompanyFilterAdapter.CompanyHolder(v);
    }

    @Override
    public void onBindViewHolder(final CompanyFilterAdapter.CompanyHolder holder, final int position) {
        final Filter bean = list.get(position);

        holder.txtCompanyName.setText(bean.getName());
//        holder.ckBox.setChecked(bean.getSelected());

        holder.ckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    holder.ckBox.setChecked(true);
                    bean.setSelected(true);
                } else {
                    holder.ckBox.setChecked(false);
                    bean.setSelected(false);
                }

                filterSelectedListner.OnFilterSelected(position,list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public android.widget.Filter getFilter() {
        if(filter==null)
        {
            filter=new CompanyFilter(this,list);
        }

        return filter;
    }

    public interface FilterSelectedListner{
        void OnFilterSelected(int position, List<Filter> list);
    }

    public void setFilterSelectedListner(FilterSelectedListner filterSelectedListner) {
        this.filterSelectedListner = filterSelectedListner;
    }

    public class CompanyHolder extends RecyclerView.ViewHolder {
        TextView txtCompanyName;
        CheckBox ckBox;

        public CompanyHolder(View itemView) {
            super(itemView);
            txtCompanyName = itemView.findViewById(R.id.txtCompanyName);
            ckBox = itemView.findViewById(R.id.ckBox);
        }
    }
}
