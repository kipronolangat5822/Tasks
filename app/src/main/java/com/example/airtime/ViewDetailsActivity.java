package com.example.airtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewDetailsActivity extends AppCompatActivity {
    TextView name, urgency, start, end, dep, to, status;
    Button btn;

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
        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);

    /*    if (selectedRadioButton != null) {
            String statuss = "";
            String selectedText = selectedRadioButton.getText().toString();
            if (selectedText.equals("Option 1")) {
                statuss = "Completed";
            } else if (selectedText.equals("Option 2")) {
                statuss = "Progress";
            } else if (selectedText.equals("Option 3")) {
                statuss = "Pending";
            }

            status.setText(statuss);
        }*//*
        FirebaseAuth mAuth=FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("alltasks");
        reference.child(taskId).child("status").setValue(status.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid()).child(taskId);
                    ref.child("status").setValue(status.getText().toString());
                    Intent intent = new Intent(ViewDetailsActivity.this, TaskDashBoard.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ViewDetailsActivity.this, "Failed" +task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });*/
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   String date = "13-07-2023";
                String time = "03:02";
                ReminderUtils.setReminder(getApplicationContext(), date, time);*/
                Intent intent=new Intent(ViewDetailsActivity.this, TaskDashBoard.class);
                startActivity(intent);
                finish();
            }
        });


    }
}