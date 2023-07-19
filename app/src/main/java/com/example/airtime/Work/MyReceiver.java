package com.example.airtime.Work;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String title = "Reminder";
        String message = intent.getStringExtra("message");
        NotificationHelper.createNotification(context, title, message);
    }
}

