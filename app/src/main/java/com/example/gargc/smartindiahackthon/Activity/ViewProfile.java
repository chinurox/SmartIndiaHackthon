package com.example.gargc.smartindiahackthon.Activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.gargc.smartindiahackthon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewProfile extends AppCompatActivity {

    String user;

    TextView phoneNumber,email,username,user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbar.setTitle("Profile");

        user=getIntent().getStringExtra("user");

        phoneNumber=(TextView) findViewById(R.id.tvNumber1);
        email=(TextView) findViewById(R.id.tvNumber3);
        username=(TextView) findViewById(R.id.username1);
        user1=(TextView) findViewById(R.id.user1);




        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = current_user.getUid();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child(user).child(uid);

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String username1=dataSnapshot.child("name").getValue().toString();
                String phone=dataSnapshot.child("phonenumber").getValue().toString();
                String email1=dataSnapshot.child("emailId").getValue().toString();
                String user2=dataSnapshot.child("username").getValue().toString();

                phoneNumber.setText(phone);
                email.setText(email1);
                username.setText(username1);
                user1.setText(user2);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
