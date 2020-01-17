package com.example.cafe.KakaoMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapManager {

    private static MapManager mInstance;


    public static MapManager getInstance() {
        if(mInstance == null){
            mInstance = new MapManager();
        }
        return mInstance;
    }

    public void requestMap(Callback<MapDto> callback , String name){
        Retrofit client =  new Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        KakaoApi service = client.create(KakaoApi.class);
        Call<MapDto> call = service.getAddName(name);

        call.enqueue(callback);


    }
}
