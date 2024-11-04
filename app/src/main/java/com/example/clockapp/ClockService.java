package com.example.clockapp;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ClockService extends Service {
    private Handler handler;
    private Runnable runnable;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent("UpdateClock");
                sendBroadcast(intent);
                handler.postDelayed(this, 1000); // 每秒更新一次
            }
        };
        handler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}
