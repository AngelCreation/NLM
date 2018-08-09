package com.conceptcore.newlifemedicines;

import android.app.Application;

import com.conceptcore.newlifemedicines.Helpers.PicassoImageLoadingService;

import ss.com.bannerslider.Slider;

/**
 * Created by SVF 15213 on 19-06-2018.
 */

public class NewLifeMedicines extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        Slider.init(new PicassoImageLoadingService(this));
    }
}
