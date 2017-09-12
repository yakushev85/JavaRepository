package com.alex.yakushev.app.torrentslistvisualizer;

import android.app.Application;

import com.alex.yakushev.app.torrentslistvisualizer.service.ServiceApi;

/**
 * Created by Oleksandr on 11-Sep-17.
 */

public class YtsServiceApplication extends Application {
    private ServiceApi serviceApi;

    @Override
    public void onCreate() {
        super.onCreate();

        serviceApi = ServiceApi.getInstance();
    }

    public ServiceApi getServiceApi() {
        return serviceApi;
    }
}
