package com.example.airtime;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Trial extends AppCompatActivity {

    private EditText phoneNumberEditText;
    private Button sendOTPButton;
    private Button verifyOTPButton;
    private TextView statusTextView;

    private FirebaseAuth firebaseAuth;
    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trial);

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        sendOTPButton = findViewById(R.id.sendOTPButton);
        verifyOTPButton = findViewById(R.id.verifyOTPButton);
        statusTextView = findViewById(R.id.statusTextView);

        firebaseAuth = FirebaseAuth.getInstance();

        sendOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNumber = phoneNumberEditText.getText().toString();
                sendOTP(phoneNumber);
            }
        });

        verifyOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = phoneNumberEditText.getText().toString();
                verifyOTP(otp);
            }
        });
    }

    private void sendOTP(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,
                60,
                TimeUnit.SECONDS,
                this,
                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                        // Auto-retrieval or instant verification is successful.
                        signInWithPhoneAuthCredential(phoneAuthCredential);
                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        // Verification failed.
                        statusTextView.setText("Verification failed: " + e.getMessage());
                        Log.d("tag",e.getMessage());
                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        // Code sent successfully.
                        Trial.this.verificationId = verificationId;
                        statusTextView.setText("OTP sent successfully.");
                    }
                }
        );
    }

    private void verifyOTP(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Phone number verification is successful.
                        statusTextView.setText("OTP verification successful.");
                    } else {
                        // Phone number verification failed.
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            statusTextView.setText("Invalid OTP.");
                        } else {
                            statusTextView.setText("OTP verification failed.");
                        }
                    }
                });
    }
}
