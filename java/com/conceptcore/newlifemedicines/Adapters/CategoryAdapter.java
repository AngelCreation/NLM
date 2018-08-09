package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by SVF 15213 on 14-06-2018.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> implements View.OnClickListener {

    private Context context;
    private List<CategoryBean> catList;
    CatClickListner catClickListner;

    public CategoryAdapter(Context context, List<CategoryBean> catList) {
        this.context = context;
        this.catList = catList;
    }

    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_category, parent, false);
        return new CategoryAdapter.CategoryHolder(v);
    }

    @Override
    public void onBindViewHolder(CategoryAdapter.CategoryHolder holder, int position) {
        CategoryBean cat = catList.get(position);

        holder.txtEventName.setText(cat.getTitle());

        //set image with picasso
        Picasso.with(context)
                .load(Constants.HOST_NAME + cat.getImagePath())
//                .resize(260, 240)
//                .centerCrop()
                .placeholder(ContextCompat.getDrawable(context, R.drawable.medicines))
                .error(ContextCompat.getDrawable(context, R.drawable.medicines))
//                .fit()
//                .resizeDimen(R.dimen.my_events_card_width, R.dimen.my_events_card_height)
//                .centerCrop()
//                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.imgCatPic);


        holder.myCats.setOnClickListener(this);
        holder.myCats.setTag(R.string.app_name,position);
    }

    @Override
    public int getItemCount() {
        return catList.size();
    }

    public interface CatClickListner{
        void OnCatClicked(View view, int position, List<CategoryBean> list);
    }

    public void setCatClickListner(CatClickListner catClickListner) {
        this.catClickListner = catClickListner;
    }

    @Override
    public void onClick(View view) {
        LinearLayout ll = (LinearLayout) view;
        int position = (int) ll.getTag(R.string.app_name);
        catClickListner.OnCatClicked(view,position,catList);
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        ImageView imgCatPic;
        TextView txtEventName;
        LinearLayout myCats;

        public CategoryHolder(View itemView) {
            super(itemView);

            myCats = itemView.findViewById(R.id.myCats);
            imgCatPic = itemView.findViewById(R.id.imgCatPic);
            txtEventName = itemView.findViewById(R.id.txtEventName);
        }
    }
}
