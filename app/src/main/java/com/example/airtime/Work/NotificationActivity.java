package com.example.airtime.Work;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airtime.Adapters.NotificationAdapter;
import com.example.airtime.ProfileActivity;
import com.example.airtime.R;
import com.example.airtime.TaskDashBoard;
import com.example.airtime.TaskModel;
import com.example.airtime.Tasks;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    NotificationAdapter adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    Query references;
    FirebaseAuth mAuth;
    private  boolean hasData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        recyclerView = findViewById(R.id.recycler_view);
        loading = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new NotificationAdapter(this, list);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 0) {
                    navigationView.setVisibility(View.VISIBLE);
                }
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || (dy < 0 && navigationView.isShown())) {
                    navigationView.setVisibility(View.GONE);
                }
            }
        });

        loading.show();

        references = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());

        this.references.orderByChild("taskdate").addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                list.clear();
                Date currentTime = new Date();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TaskModel task = dataSnapshot.getValue(TaskModel.class);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm");

                    String taskDate = task.getStartdate();
                    Date tdate = null;
                    try {
                        tdate = sdf.parse(taskDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (tdate != null && tdate.before(currentTime)) {
                        list.add(task);
                    }
                }

                hasData = !list.isEmpty();
                loading.dismiss();
                adapter.notifyDataSetChanged();

                new Handler().postDelayed(() -> {
                    if (!hasData) {
                        loading.dismiss();
                        Toast.makeText(getApplicationContext(), "There are No Notifications", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }


            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });

        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return false;
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), TaskDashBoard.class));
                        return false;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), Tasks.class));
                        return false;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}


