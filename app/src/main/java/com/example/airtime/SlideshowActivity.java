package com.example.airtime;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.airtime.Adapters.SlideshowAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class SlideshowActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private SlideshowAdapter slideshowAdapter;
    private Button nextButton;
    private int currentPosition = 0;
    private TextView skipping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);
        viewPager = findViewById(R.id.viewPager);
        slideshowAdapter = new SlideshowAdapter(this);
        viewPager.setAdapter(slideshowAdapter);
        nextButton = findViewById(R.id.nextButton);
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser()!=null){
            String emm=mAuth.getCurrentUser().getEmail();

            Intent intent=new Intent(SlideshowActivity.this, TaskDashBoard.class);
            startActivity(intent);
            finish();


        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int nextPosition = currentPosition + 1;
                if (nextPosition < slideshowAdapter.getCount()) {
                    viewPager.setCurrentItem(nextPosition, true);
                    currentPosition = nextPosition;
                } else {
                    Intent intent = new Intent(SlideshowActivity.this, WelcomePage.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Not needed for this implementation
            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}



