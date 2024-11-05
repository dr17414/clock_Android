package com.example.clockapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.WindowManager; // 引入這個包
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private TextView timeText;
    private BroadcastReceiver clockReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateTime();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 讓螢幕保持常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        timeText = findViewById(R.id.timeText);

        // 啟動時鐘服務
        startService(new Intent(this, ClockService.class));

        // 註冊廣播接收器
        IntentFilter filter = new IntentFilter("UpdateClock");
        registerReceiver(clockReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 在暫停時清除保持常亮的標誌
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(clockReceiver);
    }

    private void updateTime() {
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        timeText.setText(currentTime);
    }
}
