package schedule.sumdu.edu.ua.schedulesumdu.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleksandr on 15-Sep-17.
 */

public class ServiceSumduApi {
    private static final String END_POINT = "http://schedule.sumdu.edu.ua";

    private static ServiceSumduApi instance;

    private ServiceSumduApi() {}

    public static synchronized ServiceSumduApi getInstance() {
        if (instance == null) {
            instance = new ServiceSumduApi();
        }

        return instance;
    }

    public SumduApi getSumduApi() {
        return (new Retrofit.Builder())
                .baseUrl(END_POINT)
                .build()
                .create(SumduApi.class);
    }

    public SumduApi getSumduApi(GsonConverterFactory converterFactory) {
        return (new Retrofit.Builder()).baseUrl(END_POINT)
                .addConverterFactory(converterFactory)
                .build()
                .create(SumduApi.class);
    }

}
