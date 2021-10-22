package com.example.logintask.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.logintask.databinding.ActivityPhoneNumberBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.hbb20.CountryCodePicker;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding binding ;
    CountryCodePicker ccp ;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ccp.registerCarrierNumberEditText(binding.phoneBox);

        auth = FirebaseAuth.getInstance();

//        if(auth.getCurrentUser() != null) {
//            Intent intent = new Intent(PhoneNumberActivity.this, MainActivity.class);
//            startActivity(intent);
//            finish();
//        }

        binding.phoneBox.requestFocus();


        getSupportActionBar().hide();



        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PhoneNumberActivity.this , OTPActivity.class);
                intent.putExtra("mobile" , binding.ccp.getFullNumberWithPlus().replace(" ", ""));
                startActivity(intent);
            }
        });

    }
}