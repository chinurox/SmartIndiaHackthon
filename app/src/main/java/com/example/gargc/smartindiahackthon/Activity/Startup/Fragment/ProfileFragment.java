package com.example.gargc.smartindiahackthon.Activity.Startup.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.gargc.smartindiahackthon.Activity.ViewProfile;
import com.example.gargc.smartindiahackthon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    String user;

    TextView phoneNumber,email,username,user1;


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);

        user= ViewProfile.user;

        phoneNumber=(TextView) view.findViewById(R.id.tvNumber1);
        email=(TextView) view.findViewById(R.id.tvNumber3);
        username=(TextView) view.findViewById(R.id.username1);
        user1=(TextView) view.findViewById(R.id.user1);


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


        return  view;
    }

}
