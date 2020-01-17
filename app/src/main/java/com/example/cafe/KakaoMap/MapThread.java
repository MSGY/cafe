package com.example.cafe.KakaoMap;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapThread extends Thread {
    final static String TAG = "ASDFQWERASDG" ;
    Context mContext;
    KakaoApi KakaoApi;
    Handler handler;


    public MapThread(Context mContext, Handler handler, String name) {
        this.mContext = mContext;
        this.handler = handler;
        this.name = name;
    }

    String name;


    @Override
    public void run() {
        super.run();
        Retrofit client =  new Retrofit.Builder().baseUrl("https://dapi.kakao.com/").addConverterFactory(GsonConverterFactory.create()).build();
        KakaoApi service = client.create(KakaoApi.class);
        Call<MapDto> call = service.getAddName(name);
        call.enqueue(new Callback<MapDto>() {
            @Override
            public void onResponse(Call<MapDto> call, Response<MapDto> response) {
                Log.d(TAG, "리스폰스 함" + response);
                if(response.isSuccessful()){
                    MapDto map= response.body();
                    Log.d(TAG, "onResponse: "+ map.getDocuments().get(0).getAddress().getAddressName());

                    Message msg = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("Address", map);
                    msg.setData(bundle);
                    handler.sendMessage(msg);
                }
            }

            @Override
            public void onFailure(Call<MapDto> call, Throwable throwable) {
                Log.d(TAG, "onFailure: 불러오기 실패");
            }
        });

    }
}
