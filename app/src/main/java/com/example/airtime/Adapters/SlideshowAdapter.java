package com.example.airtime.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.airtime.LoginActivity;
import com.example.airtime.MainActivity;
import com.example.airtime.MainActivity2;
import com.example.airtime.ProfileActivity;
import com.example.airtime.R;
import com.example.airtime.SlideshowActivity;
import com.example.airtime.TaskDashBoard;
import com.example.airtime.Trial;
import com.example.airtime.WelcomePage;

import org.w3c.dom.Text;

public class SlideshowAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private int[] images = {R.drawable.image, R.drawable.image1, R.drawable.image3};
    private String[] texts = {"Manage and prioritize your tasks easily","Work Together", "Tasking"};
    private String[] text = {"Increase your productivity by managing your personal and team task and do them based on the highest priority","Collaborate with others on assigned tasks and get the job done.",
            "A mobile app that helps you plan your day and improve your daily productivity"};

    public SlideshowAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView skipping=view.findViewById(R.id.skip);
        imageView.setImageResource(images[position]);
        textView.setText(texts[position]);
        textView2.setText(text[position]);
        skipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context.getApplicationContext(), TaskDashBoard.class);
                context.startActivity(intent);
            }
        });
        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}

