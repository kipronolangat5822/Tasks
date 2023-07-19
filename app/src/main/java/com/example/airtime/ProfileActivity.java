package com.example.airtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView email,phone,id,user,name;
    FirebaseAuth mAuth;
    ProgressDialog loading;

    @Override
    protected void onStart() {
        super.onStart();
        loading.setMessage("Loading...");
        loading.show();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            DatabaseReference userRef = database.getReference("users").child(uid);
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        loading.dismiss();
                        String username = dataSnapshot.child("username").getValue(String.class);
                        String emails = dataSnapshot.child("emailaddress").getValue(String.class);
                        String phones = dataSnapshot.child("phoneNumber").getValue(String.class);
                        name.setText(username);
                        email.setText(emails);
                        phone.setText(phones);
                        user.setText("Personal");
                    }
                    else{
                        Toast.makeText(ProfileActivity.this, "Error Loading Data", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    loading.dismiss();
                    Toast.makeText(ProfileActivity.this, "Operation Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            loading.dismiss();
            Toast.makeText(this, "The user is not authenticated or the authentication state has not been resolved.", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email=findViewById(R.id.profile_emailaddress);
        phone=findViewById(R.id.profile_phonenumber);
        id=findViewById(R.id.profile);
        name=findViewById(R.id.profile_name);
        user=findViewById(R.id.profile_client);
        ImageView im=findViewById(R.id.back_arrows);
        mAuth=FirebaseAuth.getInstance();
        loading=new ProgressDialog(this);
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileActivity.this, TaskDashBoard.class);
                startActivity(intent);
                finish();
            }
        });
        Button btn=findViewById(R.id.logoutuser);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                ProfileActivity.this.startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                ProfileActivity.this.finish();
            }
        });

    }
}