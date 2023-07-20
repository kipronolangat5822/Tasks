package com.example.airtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.airtime.Work.MainActivity;
import com.example.airtime.Work.NotificationActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TaskDashBoard extends AppCompatActivity {
    RecyclerView recyclerView;
    UserDashboardAdapter adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    Query references;
    FirebaseAuth mAuth;
    TextView hello,card1,card2,card3,textView;
    int pendingTasksCount = 0;
    int completeTasksCount = 0;
    int overdueTasksCount = 0;
    private  boolean hasData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_dash_board);
        recyclerView = findViewById(R.id.recycler_view);
        loading = new ProgressDialog(this);
        textView=findViewById(R.id.text_right);
        mAuth = FirebaseAuth.getInstance();
        hello=findViewById(R.id.text_name);
        card1=findViewById(R.id.text_card1_number);
        card2=findViewById(R.id.text_card2_number);
        card3=findViewById(R.id.text_card3_number);
        String uuid=mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(uuid);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    User user = snapshot.getValue(User.class);
                    hello.setText("Hello "+user.getUsername());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
textView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),AllTasks.class);
        startActivity(intent);


    }
});

         final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new UserDashboardAdapter(this, list);
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
                pendingTasksCount = 0;
                completeTasksCount = 0;
                overdueTasksCount = 0;

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    TaskModel task = dataSnapshot.getValue(TaskModel.class);
                    list.add(task);
                    if (task.getStatus().equalsIgnoreCase("pending")) {
                        pendingTasksCount++;
                    } else if (task.getStatus().equalsIgnoreCase("completed")) {
                        completeTasksCount++;
                    } else if (task.getStatus().equalsIgnoreCase("overdue")) {
                        overdueTasksCount++;
                    }
                    hasData = true;
                    loading.dismiss();
                }

                adapter.notifyDataSetChanged();
                card2.setText(String.valueOf(pendingTasksCount));
                card1.setText(String.valueOf(completeTasksCount));
                card3.setText(String.valueOf(overdueTasksCount));
                new Handler().postDelayed(() -> {
                    if (!hasData) {
                        loading.dismiss();
                        Toast.makeText(TaskDashBoard.this, "There are No Tasks", Toast.LENGTH_SHORT).show();
                    }
                }, 5000);
            }

            public void onCancelled(DatabaseError error) {
                loading.dismiss();
                Toast.makeText(TaskDashBoard.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        ((BottomNavigationView) findViewById(R.id.navigation)).setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_profile:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        return false;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), NotificationActivity.class));
                        return false;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), Tasks.class));
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}
