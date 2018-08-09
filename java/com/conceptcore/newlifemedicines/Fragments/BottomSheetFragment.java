package com.conceptcore.newlifemedicines.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Helpers.OnFilterSelectedListner;
import com.conceptcore.newlifemedicines.R;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetFragment extends BottomSheetDialogFragment {

    private RecyclerView rvBottomView;

    public BottomSheetFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list_dialog, container, false);
        rvBottomView = view.findViewById(R.id.rvBottomView);

        List<String> list = new ArrayList<>();
        list.add("Price -- High to Low");
        list.add("Price -- Low to High");

        Bundle bundle = getArguments();
        ProductFragment obj = (ProductFragment) bundle.getSerializable("dashboardref");


        FilterMenuAdapter filterMenuAdapter = new FilterMenuAdapter(getContext(),list);
        rvBottomView.setLayoutManager(new LinearLayoutManager(getContext()));
        rvBottomView.setAdapter(filterMenuAdapter);
        filterMenuAdapter.setOnFilterSelectedListner(obj);
        return view;
    }

    private class FilterMenuAdapter extends RecyclerView.Adapter<FilterMenuAdapter.MenuItemHolder> {

        private Context context;
        private List<String> itemList;

        private OnFilterSelectedListner onFilterSelectedListner;

        public FilterMenuAdapter(Context context, List<String> itemList) {
            this.context = context;
            this.itemList = itemList;
        }

        @Override
        public MenuItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.layout_filter_item, parent, false);
            return new MenuItemHolder(v);
        }

        @Override
        public void onBindViewHolder(final MenuItemHolder holder, final int position) {
            final String item = itemList.get(position);
            holder.txtFilterType.setText(item);

            holder.rbtnFilterType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(((RadioButton) view).isChecked()){
                        onFilterSelectedListner.OnFilterSelected(view,position,itemList);
                    }
                    dismiss();
                }
            });
        }

        public void setOnFilterSelectedListner(OnFilterSelectedListner onFilterSelectedListner) {
            this.onFilterSelectedListner = onFilterSelectedListner;
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

}
