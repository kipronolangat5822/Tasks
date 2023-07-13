package com.example.airtime;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {
    TextView email,phone,id,user,name;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        reference= FirebaseDatabase.getInstance().getReference("users").child(FirebaseAuth.getInstance().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                email.setText(snapshot.child("email").getValue(String.class));
                id.setText(snapshot.child("id").getValue(String.class));
                name.setText(snapshot.child("name").getValue(String.class));
                phone.setText(snapshot.child("phone").getValue(String.class));
                user.setText("Personal");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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