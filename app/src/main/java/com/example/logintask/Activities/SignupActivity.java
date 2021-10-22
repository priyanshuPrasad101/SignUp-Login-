package com.example.logintask.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.example.logintask.databinding.ActivityMainBinding;
import com.example.logintask.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    FirebaseAuth mAuth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = binding.username.getText().toString().trim();
                String password = binding.password.getText().toString().trim();
                String confirmPass = binding.confirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email) ){
                    Toast.makeText(SignupActivity.this, "Please provide Email", Toast.LENGTH_SHORT).show();
               return;
                }

                if(TextUtils.isEmpty(password) ){
                    Toast.makeText(SignupActivity.this, "Please fill password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPass) ){
                    Toast.makeText(SignupActivity.this, "Please fill confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(password.length()<6)
                {
                    Toast.makeText(SignupActivity.this, "Password must be of at least 6 digit/char", Toast.LENGTH_SHORT).show();
                }


                if(password.equals(confirmPass))
                {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information


                                        startActivity(new Intent(SignupActivity.this , SetupProfileActivity.class));
                                        finishAffinity();

                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                }
            }
        });

    }
}