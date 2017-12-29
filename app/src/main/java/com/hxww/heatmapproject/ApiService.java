package com.hxww.heatmapproject;

import retrofit2.http.GET;
import rx.Observable;

/**
 * @author 作者：杨荣华
 * @date 创建时间：2017/11/20 18:03
 * @description 描述：
 */

public interface ApiService {
    String BASE_URL = "http://47.94.45.196:8222/api/";

    @GET("Voice/GetListVoice")
    Observable<LocationModel> query();

    @GET("Voice/GetListCode")
    Observable<DateModel> queryTime();
}
