package schedule.sumdu.edu.ua.schedulesumdu;

import android.app.Application;

import schedule.sumdu.edu.ua.schedulesumdu.service.ServiceSumduApi;

/**
 * Created by Oleksandr on 15-Sep-17.
 */

public class ScheduleSumduApp extends Application {
    private ServiceSumduApi mServiceSumduApi;

    @Override
    public void onCreate() {
        super.onCreate();
        mServiceSumduApi = ServiceSumduApi.getInstance();
    }

    public ServiceSumduApi getServiceSumduApi() {
        return mServiceSumduApi;
    }
}