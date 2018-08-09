package com.conceptcore.newlifemedicines.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.conceptcore.newlifemedicines.R;

import java.io.File;
import java.util.List;

/**
 * Created by SVF 15213 on 22-06-2018.
 */

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private Context context;
    private List<String> list;

    public PhotoAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.layout_chosen_photo, parent, false);
        return new PhotoAdapter.PhotoHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.PhotoHolder holder, final int position) {
        String uri = list.get(position);
//        Log.e("photoadapter","" + Uri.fromFile(new File(uri)));
        holder.imgPrescription.setImageURI(Uri.fromFile(new File(uri)));
        holder.imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PhotoHolder extends RecyclerView.ViewHolder {
        ImageView imgPrescription,imgClose;
        public PhotoHolder(View itemView) {
            super(itemView);

            imgPrescription = itemView.findViewById(R.id.imgPrescription);
            imgClose = itemView.findViewById(R.id.imgClose);
        }
    }
}
