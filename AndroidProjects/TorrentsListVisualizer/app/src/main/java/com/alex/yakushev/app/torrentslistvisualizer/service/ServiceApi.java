package com.alex.yakushev.app.torrentslistvisualizer.service;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.alex.yakushev.app.torrentslistvisualizer.model.GeneralMoviesData;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleksandr on 10-Sep-17.
 */

public class ServiceApi {
    private static final String END_POINT = "https://yts.ag";

    private static ServiceApi instance;

    private ServiceApi() {}

    public static synchronized ServiceApi getInstance() {
        if (instance == null) {
            instance = new ServiceApi();
        }

        return instance;
    }

    public YtsApi getYtsApi() {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl(END_POINT)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit.create(YtsApi.class);
    }
}
