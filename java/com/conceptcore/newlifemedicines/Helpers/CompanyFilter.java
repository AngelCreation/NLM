package com.conceptcore.newlifemedicines.Helpers;

import android.util.Log;
import android.widget.Filter;

import com.conceptcore.newlifemedicines.Adapters.CompanyFilterAdapter;

import java.util.ArrayList;
import java.util.List;

//search filter on Recycler example: http://camposha.info/source/android-recyclerview-search-filter
//https://gist.github.com/DeepakRattan/26521c404ffd7071d0a4

public class CompanyFilter extends Filter {

    CompanyFilterAdapter companyFilterAdapter;
    private List<com.conceptcore.newlifemedicines.Models.Filter> companyList;

    public CompanyFilter(CompanyFilterAdapter companyFilterAdapter, List<com.conceptcore.newlifemedicines.Models.Filter> companyList) {
        this.companyFilterAdapter = companyFilterAdapter;
        this.companyList = companyList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {
        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length() > 0){
            charSequence = charSequence.toString().toUpperCase();

            ArrayList<com.conceptcore.newlifemedicines.Models.Filter> filteredCompanies = new ArrayList<>();

            for (int i = 0; i < companyList.size(); i++){
                if(companyList.get(i).getName().toUpperCase().contains(charSequence)){
                    filteredCompanies.add(companyList.get(i));
                }
            }
            Log.e("company list size ","non - filtered :; " + companyList.size());
            Log.e("company list size ","filtered :; " + filteredCompanies.size());
            results.count=filteredCompanies.size();
            results.values=filteredCompanies;
        } else {
            results.count=companyList.size();
            results.values=companyList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        companyFilterAdapter.list = (List<com.conceptcore.newlifemedicines.Models.Filter>) filterResults.values;
        companyFilterAdapter.notifyDataSetChanged();
    }
}
