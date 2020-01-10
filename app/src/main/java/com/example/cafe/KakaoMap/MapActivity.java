package com.example.cafe.KakaoMap;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.cafe.R;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity implements MapSearchFrag.AddressTransferListener{
    final static String TAG = MapActivity.class.getSimpleName();
    public MapView mapView;
    public MapPOIItem mMarker;
    public String addressName;
    public String value;
    public Callback<MapDto> mapMarkerCallback;
    @Override
    public String addressTransfer(String name) {
        addressName = name;
        Log.d(TAG, "addressTransfer: " + addressName);

        MapManager.getInstance().requestMap( mMapCallback, addressName);
        onBackPressed();
        return name;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final MapSearchFrag searchFrag = new MapSearchFrag();




        mapView = new MapView(this);
        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        ImageView map2Search = findViewById(R.id.search_Icon);

        map2Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_Layout, searchFrag);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                Log.d(TAG,"프래그먼트 생성됨");
            }
        });
    }

    public Callback<MapDto> mMapCallback = new Callback<MapDto>() {
        @Override
        public void onResponse(Call<MapDto> call, Response<MapDto> response) {
            Log.d(TAG, "리스폰스 함" + response);
            if(response.isSuccessful()){
                MapDto map= response.body();
            Log.d(TAG, "onResponse: "+ map.getDocuments().get(0).getAddress().getAddressName()
                    + map.getDocuments().get(0).getAddress().getX()
                    + map.getDocuments().get(0).getAddress().getY());
                Intent intent = new Intent();
                intent.putExtra("address", map);

//            Message msg = Message.obtain();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("Address", map);
//            msg.setData(bundle);


        }
        }

        @Override
        public void onFailure(Call<MapDto> call, Throwable t) {
            Log.d(TAG, "onFailure: 불러오기 실패");

        }
    };

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    public void createMarker(){
        MapDto maps = (MapDto) getIntent().getSerializableExtra("address");
        Log.d(TAG, "createMarker: " + maps.getDocuments().get(0).getAddress().getAddressName());

        //        mMarker = new MapPOIItem();
//        String markerName = addressTransfer(addressName);
//        MapPoint.mapPointWithGeoCoord(addName)

//        Log.d(TAG, "createMarker: "+ addressName);
//        mMarker.setItemName(markerName);
//        mMarker.setTag(0);
//        mMarker.setMapPoint();
//        mMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);

//        mapView.addPOIItem(mMarker);
//        mapView.selectPOIItem(mMarker, true);
//        mapView.setMapCenterPoint(,false);
    }
//    private void createDefaultMarker(MapView mapView) {
//        mDefaultMarker = new MapPOIItem();
//        String name = "Default Marker";
//        mDefaultMarker.setItemName(name);
//        mDefaultMarker.setTag(0);
//        mDefaultMarker.setMapPoint(DEFAULT_MARKER_POINT);
//        mDefaultMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//        mDefaultMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);
//
//        mapView.addPOIItem(mDefaultMarker);
//        mapView.selectPOIItem(mDefaultMarker, true);
//        mapView.setMapCenterPoint(DEFAULT_MARKER_POINT, false);
//    }
}
