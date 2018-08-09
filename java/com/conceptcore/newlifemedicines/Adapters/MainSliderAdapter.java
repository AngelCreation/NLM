package com.conceptcore.newlifemedicines.Adapters;

import com.conceptcore.newlifemedicines.Helpers.Constants;
import com.conceptcore.newlifemedicines.Models.CategoryBean;
import com.conceptcore.newlifemedicines.R;

import java.util.List;

import ss.com.bannerslider.adapters.SliderAdapter;
import ss.com.bannerslider.viewholder.ImageSlideViewHolder;

/**
 * Created by SVF 15213 on 19-06-2018.
 */

public class MainSliderAdapter extends SliderAdapter {

    private List<CategoryBean> list;

    /*public MainSliderAdapter(List<CategoryBean> list) {
        this.list = list;
    }*/

    @Override
    public int getItemCount() {
//        return list.size();
        return 3;
    }

    @Override
    public void onBindImageSlide(int position, ImageSlideViewHolder viewHolder) {
        /*for(int i = 0; i< list.size(); i++){
            if(position == i)
                viewHolder.bindImageSlide(Constants.HOST_NAME + list.get(i).getImagePath());
        }*/

        switch (position) {
            case 0:
//                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
//                viewHolder.bindImageSlide(R.drawable.banner_1);
                viewHolder.bindImageSlide(Constants.HOST_NAME + "data/media/silder/1.jpg");
                break;
            case 1:
//                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
//                viewHolder.bindImageSlide(R.drawable.banner_2);
                viewHolder.bindImageSlide(Constants.HOST_NAME + "data/media/silder/2.jpg");
                break;
            case 2:
//                viewHolder.bindImageSlide("https://assets.materialup.com/uploads/76d63bbc-54a1-450a-a462-d90056be881b/preview.png");
//                viewHolder.bindImageSlide(R.drawable.banner_3);
                viewHolder.bindImageSlide(Constants.HOST_NAME + "data/media/silder/3.jpg");
                break;
        }
    }
}
