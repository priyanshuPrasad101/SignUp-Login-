package com.example.logintask.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.logintask.databinding.ActivityOtpactivityBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.mukesh.OnOtpCompletionListener;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    ActivityOtpactivityBinding  binding;
    String phoneNumber;
    FirebaseAuth mAuth;
    String otpId;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        dialog = new ProgressDialog(this);
        dialog.setMessage("Sending OTP...");
        dialog.setCancelable(false);
        dialog.show();

        getSupportActionBar().hide();

        phoneNumber = getIntent().getStringExtra("mobile").toString();

        binding.phoneLbl.setText("Verify " + phoneNumber);

        initiateOTP();

        binding.otpView.setOtpCompletionListener(new OnOtpCompletionListener() {
            @Override
            public void onOtpCompleted(String otp) {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(otpId , otp);
                signInWithPhoneAuthCredential(credential);
            }
        });
    }

    private void initiateOTP()
    {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks()
                        {
                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull  PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                super.onCodeSent(s, forceResendingToken);
                                dialog.dismiss();
                                otpId = s;

                                InputMethodManager imm = (InputMethodManager)   getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                                binding.otpView.requestFocus();
                            }

                            @Override
                            public void onVerificationCompleted(@NonNull  PhoneAuthCredential phoneAuthCredential)
                            {
                                signInWithPhoneAuthCredential(phoneAuthCredential);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        })          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            startActivity(new Intent(OTPActivity.this , SetupProfileActivity.class));
                            finishAffinity();
                        } else {
                            Toast.makeText(OTPActivity.this, "Something's wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}