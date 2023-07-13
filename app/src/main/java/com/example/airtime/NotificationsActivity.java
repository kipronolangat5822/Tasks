package com.example.airtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Intent intent = getIntent();
        if (intent != null) {
            String title = intent.getStringExtra("title");
            String content = intent.getStringExtra("content");
            TextView titleTextView = findViewById(R.id.notificationTitleTextView);
            TextView contentTextView = findViewById(R.id.notificationContentTextView);

            titleTextView.setText(title);
            contentTextView.setText(content);
        }
    }
}
