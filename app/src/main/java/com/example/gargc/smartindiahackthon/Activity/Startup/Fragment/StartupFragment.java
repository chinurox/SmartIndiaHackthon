package com.example.gargc.smartindiahackthon.Activity.Startup.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.Activity.Investor.InvestActivity;
import com.example.gargc.smartindiahackthon.Activity.Startup.AddFeed;
import com.example.gargc.smartindiahackthon.Activity.Startup.ViewProfileAdapter;
import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.Model.Startup;
import com.example.gargc.smartindiahackthon.Model.StartupFragmentModel;
import com.example.gargc.smartindiahackthon.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StartupFragment extends Fragment {


    ImageView imageViewOfStartup;
    TextView startupName,startupAbout,startupdescription;
    RecyclerView startupRecyclerView,startupFragment;



    public StartupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_startup, container, false);

        //id fetch
//        startupName=(TextView) view.findViewById(R.id.profilestartupname);
//        startupAbout=(TextView) view.findViewById(R.id.profilestartupabout);
//        startupdescription=(TextView) view.findViewById(R.id.profilestartupdescription);
//        startupRecyclerView=(RecyclerView) view.findViewById(R.id.startuprecyclerview);
        startupFragment=(RecyclerView) view.findViewById(R.id.startupfragment);
        startupFragment.setLayoutManager(new LinearLayoutManager(getContext()));
        startupFragment.setHasFixedSize(true);


        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = current_user.getUid();

        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("StartupUsers");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)) {

                    Log.i("exist","true");
                    DatabaseReference rootRef1=rootRef.child(uid);
                    rootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                            List<StartupFragmentModel> list = new ArrayList<StartupFragmentModel>();
                            for (Object obj : td.values()) {
                                if (obj instanceof Map) {
                                    Map<String, Object> mapObj1 = (Map<String, Object>) obj;
                                    StartupFragmentModel startup = new StartupFragmentModel();


                                    if (mapObj1 instanceof Map) {

                                        ArrayList<String> coFounderEmail = new ArrayList<String>();


                                        startup.setAbout((String) mapObj1.get("About"));
                                        Log.i("ABOUT", (String) mapObj1.get("About"));
                                        startup.setCategory((String) mapObj1.get("Category"));
                                        startup.setCover((String) mapObj1.get("cover"));
                                        startup.setDescription((String) mapObj1.get("Description"));
                                        startup.setLogo((String) mapObj1.get("logo"));
                                        startup.setName((String) mapObj1.get("Name"));
                                        startup.setUid((String) mapObj1.get("uid"));
                                        startup.setSize( String.valueOf(mapObj1.get("size")));
                                        String size = String.valueOf(mapObj1.get("size"));
                                        Log.i("size1", size);
                                        for (int i = 0; i < Integer.parseInt(size); i++) {
                                            coFounderEmail.add((String) mapObj1.get("cofounder" + i));
                                            //Log.i("cofounder",(String) mapObj1.get("cofounder"+i));
                                        }
                                        startup.setCoFounderEmail(coFounderEmail);



                                    }


                                    list.add(startup);
                                    Log.i("list", list.toString());
                                    Log.i("size", list.size() + "");

                                }
                            }
                            ViewProfileAdapter viewProfileAdapter = new ViewProfileAdapter(getContext(), list);
                            startupFragment.setAdapter(viewProfileAdapter);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
                else
                {
                    Toast.makeText(getContext(), "You don't have any startup", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;

    }

}
