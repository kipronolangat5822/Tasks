package com.example.airtime;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.airtime.Work.MainActivity;
import com.example.airtime.Work.MyReceiver;
import com.example.airtime.Work.NotificationActivity;
import com.example.airtime.Work.NotificationHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Tasks extends AppCompatActivity {
    EditText title, description;
    CardView finance, others, inquire, admin, tech;
    Button btn, st, en;
    TextView tt;
    ImageView  q, w, e, r, t;
    DatabaseReference reference, re;
    StringBuilder sb;
    String day, dateno, month;
    int n = 5;
    String id;
    FirebaseAuth mAuth;
    AlertDialog.Builder builds;
    ProgressDialog loading;
    private static final String CHANNEL_ID = "My Notification";
    private static final int NOTIFICATION_ID_1 = 1;
    private static final int NOTIFICATION_ID_2 = 2;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        String nn = mAuth.getCurrentUser().getUid();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users").child("client");
        reference.child(nn).child("id").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                id = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        q = findViewById(R.id.q1);
        w = findViewById(R.id.q2);
        e = findViewById(R.id.q3);
        title = findViewById(R.id.l_title);
        description = findViewById(R.id.l_description);
        finance = findViewById(R.id.l_finance);
        inquire = findViewById(R.id.l_inquiries);
        admin = findViewById(R.id.l_admin);
        btn = findViewById(R.id.btn_task);
        tt = findViewById(R.id.text);
        en = findViewById(R.id.l_end);
        st = findViewById(R.id.l_start);
        sb = new StringBuilder(n);
        loading=new ProgressDialog(this);



        randomString();
        st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int currentYear = c.get(Calendar.YEAR);
                int currentMonth = c.get(Calendar.MONTH);
                int currentDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Tasks.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(Tasks.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                String selectedDateTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " " + hourOfDay + ":" + minute;
                                Tasks.this.st.setText(selectedDateTime);
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                        timePickerDialog.show();
                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });

        en.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int currentYear = c.get(Calendar.YEAR);
                int currentMonth = c.get(Calendar.MONTH);
                int currentDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Tasks.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TimePickerDialog timePickerDialog = new TimePickerDialog(Tasks.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                String selectedDateTime = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year + " " + hourOfDay + ":" + minute;
                                Tasks.this.en.setText(selectedDateTime);
                            }
                        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

                        timePickerDialog.show();
                    }
                }, currentYear, currentMonth, currentDay);

                datePickerDialog.show();
            }
        });


        this.builds = new AlertDialog.Builder(this);
        this.builds.setMessage("Tasks").setTitle("Checking Dates");
        this.builds.setMessage("Start date cannot be greater").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id2) {
                dialog.dismiss();
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("alltasks");
        re = FirebaseDatabase.getInstance().getReference("tasks");
        finance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("personal");
                q.setVisibility(View.VISIBLE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.GONE);
            }
        });

        inquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("school");
                q.setVisibility(View.GONE);
                w.setVisibility(View.VISIBLE);
                e.setVisibility(View.GONE);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tt.setText("work");
                q.setVisibility(View.GONE);
                w.setVisibility(View.GONE);
                e.setVisibility(View.VISIBLE);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createTask();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
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
                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), TaskDashBoard.class));
                        return false;
                    default:
                        return false;
                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void randomString() {
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        for (int i = 0; i < n; i++) {
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());
            sb.append(AlphaNumericString
                    .charAt(index));
        }
    }

    private void createTask() throws ParseException {
        loading.setTitle("Create Task");
        loading.setMessage("Creating tasks");
        loading.setCanceledOnTouchOutside(true);
        String tasktitle = title.getText().toString().trim();
        String taskdesc = description.getText().toString().trim();
        String category = tt.getText().toString().trim();
        String start = st.getText().toString();
        String end = en.getText().toString();
        EditText nn = findViewById(R.id.s_description);
        String s_description = nn.getText().toString().trim();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm");
        String date = String.valueOf(System.currentTimeMillis());

        String taskId = sb.toString();
        Date strDate = sdf.parse(start);
        Date endDate = sdf.parse(end);

        if (endDate.before(strDate)) {
            AlertDialog alert = Tasks.this.builds.create();
            alert.setTitle("Invalid Dates");
            alert.show();
        } else if (tasktitle.equalsIgnoreCase("")) {
            title.setError("Enter Title");
        } else if (taskdesc.equalsIgnoreCase("")) {
            description.setError("Enter Description");
        } else if (start.equalsIgnoreCase("")) {
            st.setError("Enter Start Date");
        } else if (end.equalsIgnoreCase("")) {
            en.setError("Enter Due Date");
        } else if (s_description.equalsIgnoreCase("")) {
            nn.setError("Enter Description");
        } else if (taskId.equalsIgnoreCase("")) {
            Toast.makeText(this, "Invalid task id", Toast.LENGTH_SHORT).show();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                day = LocalDate.now().getDayOfWeek().toString();
                month = LocalDate.now().getMonth().toString();
                dateno = String.valueOf(LocalDate.now().getDayOfMonth());
            }
            loading.show();
            String status = "pending";
            String userid = mAuth.getCurrentUser().getUid();
            TaskModel model = new TaskModel(tasktitle, taskdesc, category, start, end, date, taskId, s_description, status, userid);
            reference.child(taskId).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> task) {
                    if (task.isSuccessful()){
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("usertask").child(mAuth.getCurrentUser().getUid()).child(taskId);
                        ref.setValue(model);
                        Toast.makeText(Tasks.this, "Task created successfully", Toast.LENGTH_SHORT).show();
                        setReminder(model.getStartdate(), model.getS_desc(), model.getTitle());

                        loading.dismiss();
                        Intent intent = new Intent(Tasks.this, TaskDashBoard.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        loading.dismiss();
                    }
                }
            });
        }

    } private void setReminder(String time,String message,String title) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy H:mm", Locale.getDefault());

        try {
            Date date = sdf.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
            intent.putExtra("title", title);
            intent.putExtra("notificationMessage", message);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}