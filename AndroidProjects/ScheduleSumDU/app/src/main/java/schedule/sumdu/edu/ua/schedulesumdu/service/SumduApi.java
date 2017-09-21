package schedule.sumdu.edu.ua.schedulesumdu.service;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import schedule.sumdu.edu.ua.schedulesumdu.model.ScheduleItem;

/**
 * Created by Oleksandr on 15-Sep-17.
 */

public interface SumduApi {
    @GET("/index/json/?method=getTeachers")
    Call<ResponseBody> getTeachers();

    @GET("/index/json?method=getGroups")
    Call<ResponseBody> getGroups();

    @GET("/index/json/?method=getAuditoriums")
    Call<ResponseBody> getAuditoriums();

    @GET("/index/json")
    Call<List<ScheduleItem>> getScheduleItems(
            @Query("id_grp") String idGroup,
            @Query("id_fio") String idTeacher,
            @Query("id_aud") String idAuditorium,
            @Query("date_beg") String beginDate,
            @Query("date_end") String endDate);
}
