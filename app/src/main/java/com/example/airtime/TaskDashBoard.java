package com.example.airtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class TaskDashBoard extends AppCompatActivity {
    RecyclerView recyclerView;
    UserDashboardAdapter adapter;
    ArrayList<TaskModel> list;
    ProgressDialog loading;
    Query references;
    FirebaseAuth mAuth;
    TextView hello,complete,progress,overdue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_dash_board);
        recyclerView = findViewById(R.id.recycler_view);
        loading = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        hello=findViewById(R.id.text_name);
        complete=findViewById(R.id.text_card1_number);
        progress=findViewById(R.id.text_card2_number);
        overdue=findViewById(R.id.text_card3_number);
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


        //  final BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigation);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        adapter = new UserDashboardAdapter(this, list);
        recyclerView.setAdapter(adapter);
      /*  recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });*/
        this.loading.show();
        references = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid());
        this.references.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    loading.dismiss();
                    list.add((TaskModel) dataSnapshot.getValue(TaskModel.class));
                }
                adapter.notifyDataSetChanged();
              /*  if (UserDashboard.this.list.size() == 0) {
                    Toast.makeText(UserDashboard.this, "No tasks", Toast.LENGTH_SHORT).show();
                    loading.dismiss();
                }
                int total = 0;
                for (int i = 0; i < UserDashboard.this.list.size(); i++) {
                    total++;
                }*/
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
                        finish();
                        return false;
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), AllTasks.class));
                        finish();
                        return false;
                    case R.id.settings:
                        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        finish();
                        return false;
                    case R.id.task:
                        startActivity(new Intent(getApplicationContext(), Tasks.class));
                        finish();
                        return false;
                    default:
                        return false;
                }
            }
        });
    }
}
