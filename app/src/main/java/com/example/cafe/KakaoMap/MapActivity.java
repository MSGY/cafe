package com.example.cafe.KakaoMap;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.cafe.R;
import com.example.cafe.ResultActivity;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MapActivity extends AppCompatActivity implements MapSearchFrag.AddressTransferListener, MapView.POIItemEventListener {
    final static String TAG = MapActivity.class.getSimpleName();
    public MapView mMapView;
    public MapPOIItem mMarker;
    public String addressName;
    public String markerName;
    public static Context mapContext;
    Button map2ResultBtn;


    @Override
    public String addressTransfer(String name) {
        addressName = name;
        Log.d(TAG, "addressTransfer: " + addressName);

        MapManager.getInstance().requestMap( mMapCallback, addressName);
        return name;
    }

    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;

        public CustomCalloutBalloonAdapter() {
            mCalloutBalloon = getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((ImageView) mCalloutBalloon.findViewById(R.id.badge)).setImageResource(R.drawable.ic_launcher);
            ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(poiItem.getItemName());
            ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText("검색하신 주소입니다.");
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        final MapSearchFrag searchFrag = new MapSearchFrag();
        mapContext = this;

        Intent intent = getIntent();
        final String price = intent.getStringExtra("total");
        Log.d(TAG, "합산가격3: " + price);


        mMapView = new MapView(this);
        ViewGroup mapViewContainer = findViewById(R.id.map_view);
        mapViewContainer.addView(mMapView);
        mMapView.setPOIItemEventListener(this);
        mMapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter());

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
        map2ResultBtn = findViewById(R.id.address_confirm);
        map2ResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map2Result = new Intent(MapActivity.this, ResultActivity.class);
               if(addressName == null){
                    Toast.makeText(mapContext, "주소를 지정하셔야합니다", Toast.LENGTH_SHORT).show();
                }else{
                   map2Result.putExtra("totalPrice", price);
                   map2Result.putExtra("searchedAddress", addressTransfer(addressName));
                   Log.d(TAG, "onCreate: " + addressTransfer(addressName));
                   startActivity(map2Result);
               }

            }
        });
    }

    public Callback<MapDto> mMapCallback = new Callback<MapDto>() {
        @Override
        public void onResponse(Call<MapDto> call, Response<MapDto> response) {
            Log.d(TAG, "리스폰스 함" + response);
            if(response.isSuccessful()){
                MapDto map= response.body();

                String address = map.getDocuments().get(0).getAddress().getAddressName();
                String x = map.getDocuments().get(0).getAddress().getX();
                String y = map.getDocuments().get(0).getAddress().getY();
                double longitude = Double.valueOf(x);
                double latitude = Double.valueOf(y);

                createMarker(address, longitude, latitude, mMapView);
                Log.d(TAG, "onResponse: " + address +  longitude + latitude);

        }
        }

        @Override
        public void onFailure(Call<MapDto> call, Throwable t) {
            Log.d(TAG, "onFailure: 불러오기 실패");

        }
    };

    public void hideKeypad(EditText editText){
        InputMethodManager mInputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        mInputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }
    public void createMarker(String address, double x, double y, MapView mapView){
        Log.d(TAG, "createMarker: " + address + x + y);
        mMarker = new MapPOIItem();
        markerName = address;
        MapPoint currentMapPoint = MapPoint.mapPointWithGeoCoord(y,x);

        Log.d(TAG, "createMarker: "+ markerName + currentMapPoint);
        mMarker.setItemName(markerName);
        mMarker.setTag(0);
        mMarker.setMapPoint(currentMapPoint);
        mMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        mMarker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(mMarker);
        mapView.selectPOIItem(mMarker, true);
        mapView.setMapCenterPoint(currentMapPoint,false);



    }
    public void Click(View v){

    }
    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {

    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Toast.makeText(this, "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }
}
