package com.example.cafe.KakaoMap;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cafe.R;

import net.daum.mf.map.api.MapView;


public class MapActivity extends AppCompatActivity  {
    final static String TAG = MapActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Handler mapHandler = new mHandler();
        Thread mapThread = new MapThread(this, mapHandler, "남부순환로 184길 13");
        mapThread.start();

        MapView mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
    }

  private class mHandler extends Handler{
      @Override
      public void handleMessage(@NonNull Message msg) {
          super.handleMessage(msg);
          MapDto map = (MapDto) msg.getData().getSerializable("Address");
          Log.d(TAG, "handleMessage" + map);

      }
  }
}
