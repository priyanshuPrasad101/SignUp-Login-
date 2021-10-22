package com.example.logintask.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.logintask.Models.WelcomeDialog;
import com.example.logintask.R;
import com.example.logintask.databinding.ActivityDashBoardBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Objects;

public class DashBoard extends AppCompatActivity {

    ActivityDashBoardBinding binding ;
    FirebaseAuth auth ;
    FirebaseDatabase database ;
    FirebaseStorage storage;
    FirebaseUser account;
    Uri selectedImage;
    TextView name;
    DatabaseReference reff;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
         account = auth.getCurrentUser();

         //for customDialog
        WelcomeDialog welcomeDialog = new WelcomeDialog(DashBoard.this);

        welcomeDialog.startDialog();
Handler handler = new Handler();
handler.postDelayed(new Runnable() {
    @Override
    public void run() {
welcomeDialog.stopDialog();
    }
},2000);



binding.button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(DashBoard.this , MainActivity.class));
        finishAffinity();
    }
});
String uid = auth.getUid();

reff = FirebaseDatabase.getInstance().getReference().child("users").child(uid);
reff.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull  DataSnapshot snapshot) {
        String name1 = snapshot.child("name").getValue().toString();
        binding.name.setText(name1);

        String photo1 = snapshot.child("profileImage").getValue().toString();
        Glide.with(DashBoard.this).load(photo1).into(binding.image);
    }

    @Override
    public void onCancelled(@NonNull  DatabaseError error) {

    }
});



    }
}