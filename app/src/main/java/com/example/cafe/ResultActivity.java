package com.example.cafe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.cafe.KakaoMap.MapActivity;
import com.example.cafe.util.KeyboardUtil;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
    final static String TAG = "resultActivity";
    TextView price;
    TextView address_info;
    EditText detail_address;
    Button push_Btn;
    String detailAddress;
    int sum;
    String formattedStringPrice;
    DecimalFormat mFormatter = new DecimalFormat("###,###");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        

        Intent intent = getIntent();
        String totalPrice= intent.getStringExtra("totalPrice");
        final String searchedAddress = intent.getStringExtra("searchedAddress");
        Log.d(TAG, "onCreate: " + totalPrice+ " " + searchedAddress);
        sum = Integer.parseInt(totalPrice);
        formattedStringPrice = mFormatter.format(sum);

        price = findViewById(R.id.order_price);
        price.setText("총 주문금액은 " + formattedStringPrice+ "원 입니다");

        address_info = findViewById(R.id.address_info);
        address_info.setText(searchedAddress);

        detail_address = findViewById(R.id.detail_address);
        detail_address.setImeOptions(EditorInfo.IME_ACTION_DONE);
        detail_address.setInputType(InputType.TYPE_CLASS_TEXT);

        detail_address.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if( actionId == EditorInfo.IME_ACTION_DONE ){
                    KeyboardUtil.hideKeypad(ResultActivity.this, detail_address);
                    if(detailAddress !=  null){
                        detailAddress = detail_address.getText().toString();

                        return true;
                    }else{
                        detail_address.setText("");
                        detailAddress = detail_address.getText().toString();
                        Log.d(TAG, "onEditorAction: 다름" + actionId + EditorInfo.IME_ACTION_DONE);
                        return  true;
                    }
                } else{
                    return false;
                }
            }
        });

        push_Btn = findViewById(R.id.pushBtn);
        push_Btn.setText(formattedStringPrice + "원 결제하기");
        push_Btn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                setNotify(searchedAddress, detailAddress);
            }
        });


    }

    public void setNotify(String address, String detailAdd){
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "my_channel_id_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);

        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.title_photo)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("주문 완료!")
                .setContentText(address + " " + detailAdd + "(으)로 주문하신 메뉴 배달 예정입니다");


        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
