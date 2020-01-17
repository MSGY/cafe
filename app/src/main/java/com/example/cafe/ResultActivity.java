package com.example.cafe;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class ResultActivity extends AppCompatActivity {
    final static String TAG = "resultActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String totalPrice= intent.getStringExtra("totalPrice");
        final String searchedAddress = intent.getStringExtra("searchedAddress");
        Log.d(TAG, "onCreate: " + totalPrice+ " " + searchedAddress);
        TextView price = findViewById(R.id.textView);
                    price.setText(totalPrice + "입니다");
        TextView addressPush = findViewById(R.id.textView2);
                    addressPush.setText("배달을 원하시는 주소는 " + searchedAddress + "입니다");

        Button pushBtn = findViewById(R.id.pushBtn);
        pushBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                setNotify(searchedAddress);
            }
        });


    }

    public void setNotify(String address){
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
                .setSmallIcon(R.drawable.ic_launcher)
                .setPriority(Notification.PRIORITY_HIGH)
                .setContentTitle("주문 완료!")
                .setContentText(address + " 로 주문하신 메뉴 배달 예정입니다");


        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }
}
