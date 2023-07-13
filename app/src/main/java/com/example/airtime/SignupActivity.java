package com.example.airtime;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
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
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {

    private Button buttonSignup;
    private TextInputLayout editTextName,editTextEmail, editTextPhoneNumber, editTextPassword;
    private ImageView backbtn;
    private FirebaseAuth mAuth;
    ProgressDialog loading;
    private EditText otpEditText;
    private String verificationId;
    private boolean isOTPVerified;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextName = findViewById(R.id.editTextName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignup = findViewById(R.id.buttonSignup);
        backbtn=findViewById(R.id.back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        loading = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loading.setTitle("Signing Up");
                loading.setMessage("Please wait...");
                loading.setCanceledOnTouchOutside(false);
                String username = editTextName.getEditText().getText().toString().trim();
                String emailaddress = editTextEmail.getEditText().getText().toString().trim();
                String phoneNumber = editTextPhoneNumber.getEditText().getText().toString().trim();
                String passwords = editTextPassword.getEditText().getText().toString().trim();
                if (username.isEmpty() || username.length() < 2) {
                    editTextName.setError("invalid name");
                }
                else if (passwords.isEmpty() || passwords.length() < 6) {
                    editTextPassword.setError("6 Characters and More Required");

                }
                else if (emailaddress.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailaddress).matches()) {
                    editTextEmail.setError("wrong email address");


                }
                else if (phoneNumber.length() != 10 || phoneNumber.isEmpty()) {
                    editTextPhoneNumber.setError("invalid phone number");

                } else {
                    loading.show();
                    mAuth.createUserWithEmailAndPassword(emailaddress, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                String uuid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                User user = new User(username, emailaddress, phoneNumber, uuid);

                                FirebaseDatabase.getInstance().getReference("users").child(uuid)
                                        .setValue(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Intent intent = new Intent(getApplicationContext(), AllTasks.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        });
                            } else {
                                String message = task.getException().toString();
                                Toast.makeText(getApplicationContext(), "Registration failed!! Please try again later" + message, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    }


