package com.example.cafe.KakaoMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface KakaoApi {
    @Headers({"Authorization: KakaoAK 05e3e9566030c444eedc54d020247eb3"})
    @GET("/v2/local/search/address.json")
    Call<MapDto> getAdd_name(@Query("query") String add_name);
}
