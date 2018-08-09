package com.conceptcore.newlifemedicines.API;

import com.conceptcore.newlifemedicines.Helpers.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by SVF 15213 on 02-06-2018.
 */

public class NewLifeApiService {
    NewLifeApi newLifeApi;

    public NewLifeApiService() {
        OkHttpClient client;

        client = new OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
//                .addNetworkInterceptor(new StethoInterceptor()) //stetho
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.SERVICE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client)
                .build();
        newLifeApi = retrofit.create(NewLifeApi.class);
    }

    public NewLifeApi getNewLifeApi() {
        return newLifeApi;
    }
}
