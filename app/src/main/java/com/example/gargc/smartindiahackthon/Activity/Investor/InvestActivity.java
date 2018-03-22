package com.example.gargc.smartindiahackthon.Activity.Investor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.gargc.smartindiahackthon.Model.Startup;
import com.example.gargc.smartindiahackthon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InvestActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    DatabaseReference databaseReference;
    int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invest);

        Intent intent=getIntent();
        final List<Startup> list= (List<Startup>) intent.getSerializableExtra("mylist");

        mRecyclerView = (RecyclerView) findViewById(R.id.startupList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(InvestActivity.this));
        mRecyclerView.setHasFixedSize(true);


        final ArrayList<String> name=new ArrayList<>();
        final ArrayList<String> phoneNumber=new ArrayList<>();
        for (int i=0;i<list.size();i++)
        {
            databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child("Startup").child(list.get(i).getUid());
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    name.add((String) dataSnapshot.child("name").getValue());
                    phoneNumber.add((String) dataSnapshot.child("phonenumber").getValue());
                    size++;
                    if(size==list.size()) {

                        StartupAdapter startupAdapter = new StartupAdapter(list, name, phoneNumber, InvestActivity.this);
                        mRecyclerView.setAdapter(startupAdapter);
                        startupAdapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }






    }
}
