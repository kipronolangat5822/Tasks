package com.example.airtime;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout editTextEmail, editTextPassword;
    private Button buttonLogin;
    private ImageView img;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ProgressDialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        mAuth = FirebaseAuth.getInstance();
        img = findViewById(R.id.backT);
        loading = new ProgressDialog(this);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();

            }
        });
        if (mAuth.getCurrentUser()!=null){
            String emm=mAuth.getCurrentUser().getEmail();

            Intent intent=new Intent(LoginActivity.this, TaskDashBoard.class);
            startActivity(intent);
            finish();


        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getEditText().getText().toString().trim();
        String password = editTextPassword.getEditText().getText().toString().trim();

        loading.setTitle("Signing in");
        loading.setMessage("Please wait...");
        loading.setCanceledOnTouchOutside(false);
        if (password.isEmpty() || password.length() < 6) {
            editTextPassword.setError("6 Characters and More Required");

        } else if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("invalid Email");

        } else {
            loading.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        loading.dismiss();
                        Intent intent = new Intent(LoginActivity.this, TaskDashBoard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String message = task.getException().toString();
                        loading.dismiss();
                        Toast.makeText(
                                        getApplicationContext(),
                                        "Login failed!!"
                                                + " Please try again later" + message,
                                        Toast.LENGTH_LONG)
                                .show();
                    }
                }
            });
        }


    }


        }

