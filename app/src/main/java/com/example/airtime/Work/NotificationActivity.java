package com.example.airtime.Work;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.airtime.R;


public class NotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String notificationMessage = extras.getString("notificationMessage");
            if (notificationMessage != null) {
                TextView notificationMessageTextView = findViewById(R.id.notificationMessageTextView);
                notificationMessageTextView.setText(notificationMessage);
            }
        }
    }
}


