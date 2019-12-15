package com.example.cafe.KakaoMap;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapManager {


    public void requestMap(Context mContext,Callback<MapDto> callback , String name){
        Retrofit client =  new Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        KakaoApi service = client.create(KakaoApi.class);
        Call<MapDto> call = service.getAdd_name(name);

        call.enqueue(callback);
//        call.enqueue(new Callback<MapDto>() {
//            @Override
//            public void onResponse(Call<MapDto> call, Response<MapDto> response) {
//                Log.d(TAG, "리스폰스 함" + response);
//                if(response.isSuccessful()){
//                    MapDto map= response.body();
//                    Log.d(TAG, "onResponse: "+ map.getDocuments().get(0).getAddress().getAddress_name());
//
//                    Message msg = Message.obtain();
//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Address", map);
//                    msg.setData(bundle);
//                    .sendMessage(msg);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MapDto> call, Throwable throwable) {
//                Log.d(TAG, "onFailure: 불러오기 실패");
//            }
//        });


    }
}
