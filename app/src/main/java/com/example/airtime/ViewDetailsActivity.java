package com.example.airtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class ViewDetailsActivity extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn;
    private RadioGroup radioGroup;
    String statuss = "pending";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_activities);
        btn = findViewById(R.id.pp_buttonsp);
        name = findViewById(R.id.pp_namesp);
        urgency = findViewById(R.id.pp_urgencysp);
        start = findViewById(R.id.pp_datesp);
        end = findViewById(R.id.pp_e_datesp);
        status = findViewById(R.id.pp_statussp);
        dep = findViewById(R.id.pp_departmentsp);
        to = findViewById(R.id.pp_descsp);
        to.setMovementMethod(new ScrollingMovementMethod());


        String nn = getIntent().getStringExtra("name");
        String uu = "critical";
        String ss = getIntent().getStringExtra("start");
        String ee = getIntent().getStringExtra("end");
        String stu = getIntent().getStringExtra("id");
        String de = getIntent().getStringExtra("dep");
        String descc = getIntent().getStringExtra("desc");
        String taskId = getIntent().getStringExtra("taskid");

        name.setText(nn);
        urgency.setText(uu);
        start.setText(ss);
        end.setText(ee);
        status.setText(stu);
        dep.setText(de);
        to.setText(descc);
        radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                statuss = radioButton.getText().toString();
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                DatabaseReference allTasksReference = FirebaseDatabase.getInstance().getReference("alltasks");
                allTasksReference.child(taskId).child("status").setValue(statuss);
                DatabaseReference userTaskReference = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid()).child(taskId);
                userTaskReference.child("status").setValue(statuss);
                Toast.makeText(ViewDetailsActivity.this, "Task is "+statuss, Toast.LENGTH_SHORT).show(); }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewDetailsActivity.this, TaskDashBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }
}