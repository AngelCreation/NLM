package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.conceptcore.newlifemedicines.Models.ProductBean;
import com.conceptcore.newlifemedicines.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SVF 15213 on 24-06-2018.
 */

public class ProductAutoCompleteAdapter extends ArrayAdapter<ProductBean> implements View.OnClickListener {

    List<ProductBean> products,tempProducts,suggestions;
    private Context context;
    LinearLayout llSuggestions;
    SugClickListner sugClickListner;

    public ProductAutoCompleteAdapter(@NonNull Context context, @NonNull List<ProductBean> products) {
        super(context, android.R.layout.simple_list_item_1, products);
        this.products = products;
        this.context = context;

        this.tempProducts = products;
        this.suggestions = new ArrayList<>();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ProductBean product = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_suggestions, parent, false);
        }
        TextView txtSuggestions = convertView.findViewById(R.id.txtSuggestions);
        if(txtSuggestions != null){
            txtSuggestions.setText(product.getTitle());
        }

        llSuggestions = convertView.findViewById(R.id.llSuggestions);
        llSuggestions.setOnClickListener(this);

        llSuggestions.setTag(R.string.app_name,position);

        // Now assign alternate color for rows
        if (position % 2 == 0)
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        else
            convertView.setBackgroundColor(ContextCompat.getColor(context,R.color.suggestionBack));

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            ProductBean product = (ProductBean) resultValue;
            return product.getTitle();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
//                Log.e("temproducts size","" + tempProducts.size());
                for (ProductBean product : tempProducts) {
                    if (product.getTitle().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(product);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();

//                Log.e("filter results size","" + suggestions.size());
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<ProductBean> c = (ArrayList<ProductBean>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ProductBean prod : c) {
                    add(prod);
                    notifyDataSetChanged();
                }
            }
        }
    };

    public interface SugClickListner{
        void onSugClicked(View view, int position, List<ProductBean> list);
    }

    public void setSugClickListner(SugClickListner sugClickListner) {
        this.sugClickListner = sugClickListner;
    }

    @Override
    public void onClick(View view) {
        LinearLayout ll = (LinearLayout) view;
        int position = (int) ll.getTag(R.string.app_name);
        sugClickListner.onSugClicked(view,position,products);
    }
}
