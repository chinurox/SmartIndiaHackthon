package com.example.gargc.smartindiahackthon;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.Activity.FirstActivity;
import com.example.gargc.smartindiahackthon.Activity.Investor.InvestActivity;
import com.example.gargc.smartindiahackthon.Activity.Startup.AddFeed;
import com.example.gargc.smartindiahackthon.Activity.Startup.CreateStartup;
import com.example.gargc.smartindiahackthon.Activity.Startup.SignUpActivity;
import com.example.gargc.smartindiahackthon.Model.Blog;
import com.example.gargc.smartindiahackthon.Model.Startup;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth mAuth;



    Toolbar mToolbar;
    ActionBar actionBar;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    private RelativeLayout noConLayout;
    private TextView tvNoNet;
    private ImageView imgNoNet;
    private Button btnNoNet;


    // For Feed
    private RecyclerView blogList;
    DatabaseReference mDatabase,mLikeDatabase;

    private boolean mProcessLike = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) {
            sendToStart();
        }

        noConLayout = (RelativeLayout) findViewById(R.id.no_network_container);
        tvNoNet = (TextView) findViewById(R.id.no_connection_tv);
        imgNoNet = (ImageView) findViewById(R.id.no_connection_img);
        btnNoNet = (Button) findViewById(R.id.retry_icon);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        actionBar = getSupportActionBar();
        actionBar.setTitle("");


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (!isNetworkAvailable()) {
            tvNoNet.setVisibility(View.VISIBLE);
            imgNoNet.setVisibility(View.VISIBLE);
            btnNoNet.setVisibility(View.VISIBLE);
            noConLayout.setVisibility(View.VISIBLE);

            noConLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tvNoNet.setVisibility(View.GONE);
                    imgNoNet.setVisibility(View.GONE);
                    btnNoNet.setVisibility(View.GONE);
                    noConLayout.setVisibility(View.GONE);
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                }
            });
        }

        blogList = (RecyclerView) findViewById(R.id.blog_list);
        blogList.setLayoutManager(new LinearLayoutManager(this));
        blogList.setHasFixedSize(true);


        mDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed");

        mLikeDatabase = FirebaseDatabase.getInstance().getReference().child("Likes");

        Menu menuNav = navigationView.getMenu();

        final MenuItem menuItem = (MenuItem) menuNav.findItem(R.id.nav_item4);
        final MenuItem menuItem1 = (MenuItem) menuNav.findItem(R.id.nav_item1);
        final MenuItem menuItem2 = (MenuItem) menuNav.findItem(R.id.nav_item2);

        View headerView = navigationView.getHeaderView(0);
        final TextView navUsername = (TextView) headerView.findViewById(R.id.userName);



        FirebaseUser mCurrentUser = mAuth.getCurrentUser();

        try {
            final String uid=mCurrentUser.getUid();
            final DatabaseReference rootRef1 = FirebaseDatabase.getInstance().getReference().child("Users").child("Startup");
            rootRef1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child(uid).exists()) {
                        DatabaseReference rootref4=rootRef1.child(uid).child("name");
                        rootref4.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String username=dataSnapshot.getValue(String.class);
                                navUsername.setText(username);

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e)
        {

        }







        try {
            final String uid = mCurrentUser.getUid();
            final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("Users").child("Investor");
            rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child(uid).exists())
                    {
                        Log.i("exist","true");
                        menuItem.setVisible(true);
                        menuItem1.setVisible(false);
                        menuItem2.setVisible(false);
                        // checking username
                        DatabaseReference rootRef3=rootRef.child(uid).child("name");
                        rootRef3.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String username=dataSnapshot.getValue(String.class);
                                navUsername.setText(username);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
        catch (Exception e)
        {

        }

    }



    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                mAuth.signOut();
                Intent intent=new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendToStart() {

        Intent startIntent=new Intent(MainActivity.this,FirstActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
           case R.id.nav_item1:
               Intent startIntent=new Intent(MainActivity.this,CreateStartup.class);
               startActivity(startIntent);
               return  true;
            case R.id.nav_item2:

                return  startupExists();
            case R.id.nav_item4:

                return checking();

            default:
                return true;


        }
    }


    private boolean startupExists() {

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = current_user.getUid();



        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("StartupUsers");
        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(uid)) {

                    Log.i("exist","true");
                    Intent startIntent1=new Intent(MainActivity.this,AddFeed.class);
                    startActivity(startIntent1);

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Create Startup First", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  true;


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Blog, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Blog, BlogViewHolder>
                (
                        Blog.class ,
                        R.layout.news ,
                        BlogViewHolder.class,
                        mDatabase
                )
        {
            @Override
            protected void populateViewHolder(final BlogViewHolder viewHolder, Blog model, int position)
            {
                final String post_key = getRef(position).getKey();

                viewHolder.setTitle(model.getTitle());
                viewHolder.setDesc(model.getDesc());
                viewHolder.setImage(getApplicationContext() , model.getImage());
                viewHolder.setUserName(model.getPostedby());

                Picasso.with(getApplicationContext()).load(model.getUserimage()).placeholder(R.drawable.user_default)
                        .into(viewHolder.userImg);

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
//                        Intent singleBlogIntent = new Intent(MainActivity.this , SingleBlogActivity.class);
//                        singleBlogIntent.putExtra("blog_id",post_key);
//                        startActivity(singleBlogIntent);
                    }
                });

                mLikeDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {

                        if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid()))
                        {
                            viewHolder.btnLike.setImageResource(R.drawable.like_blue);
                        }

                        if(dataSnapshot.child(post_key).hasChild("count"))
                        {
                            long likeVal = (long)dataSnapshot.child(post_key).child("count").getValue();
                            viewHolder.likeCount.setText(likeVal + "");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                viewHolder.btnLike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view)
                    {
                        mProcessLike = true;

                        mLikeDatabase.addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if (mProcessLike) {

                                    if (dataSnapshot.child(post_key).hasChild(mAuth.getCurrentUser().getUid())) {
                                        mLikeDatabase.child(post_key).child(mAuth.getCurrentUser().getUid()).removeValue();

                                        long val = (long) dataSnapshot.child(post_key).child("count").getValue();
                                        val--;
                                        mLikeDatabase.child(post_key).child("count").setValue(val);

                                        mProcessLike = false;

                                        viewHolder.btnLike.setImageResource(R.drawable.like_grey);
                                    } else {
                                        mLikeDatabase.child(post_key).child(mAuth.getCurrentUser().getUid()).setValue("RandomValue");

                                        //if the like exists before
                                        if(dataSnapshot.child(post_key).hasChild("count"))
                                        {
                                            long val = (long) dataSnapshot.child(post_key).child("count").getValue();
                                            val++;
                                            mLikeDatabase.child(post_key).child("count").setValue(val);

                                            long showVal = val;
                                            viewHolder.likeCount.setText(showVal+"");
                                        }
                                        else
                                        {
                                            mLikeDatabase.child(post_key).child("count").setValue(1);
                                        }

                                        mProcessLike = false;

                                        viewHolder.btnLike.setImageResource(R.drawable.like_blue);
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }
                });

            }
        };

        blogList.setAdapter(firebaseRecyclerAdapter);


    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder
    {

        View mView;
        ImageButton btnLike;
        TextView likeCount;
        CircleImageView userImg;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            userImg = (CircleImageView)mView.findViewById(R.id.blog_item_user_img);
            btnLike = (ImageButton)mView.findViewById(R.id.blog_item_btn_like);
            likeCount = (TextView)mView.findViewById(R.id.single_blog_like_count);
        }

        public void setTitle(String title)
        {
            TextView blog_title = (TextView) mView.findViewById(R.id.blog_item_title);
            blog_title.setText(title);
        }

        public void setDesc(String desc)
        {
            TextView blog_desc = (TextView) mView.findViewById(R.id.blog_item_desc);
            blog_desc.setText(desc);
        }

        public void setImage(Context ctx , String imageUrl)
        {
            ImageView blogImg = (ImageView) mView.findViewById(R.id.blog_item_img);
            Picasso.with(ctx).load(imageUrl).placeholder(R.drawable.add_image_icon).into(blogImg);
        }

        public void setUserName(String username)
        {
            TextView blog_username = (TextView) mView.findViewById(R.id.blog_item_user_name);
            blog_username.setText("Posted by " + username);
        }

    }

    private boolean checking() {

        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReferenceFromUrl("https://smartindiahackthon.firebaseio.com/Startup");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                List<Startup> list = new ArrayList<Startup>();
                for (Object obj : td.values()) {
                    if (obj instanceof Map) {
                        Map<String, Object> mapObj = (Map<String, Object>) obj;
                        Startup startup = new Startup();

                        for(Object obj1:mapObj.values())
                        {
                            if(obj1 instanceof Map)
                            {
                                Map<String, Object> mapObj1 = (Map<String, Object>) obj1;


                                    startup.setAbout((String)mapObj1.get("About"));
                                    startup.setCategory((String)mapObj1.get("Category"));
                                    startup.setCover((String)mapObj1.get("cover"));
                                    startup.setDescription((String)mapObj1.get("Description"));
                                    startup.setLogo((String)mapObj1.get("logo"));
                                    startup.setName((String)mapObj1.get("Name"));
                                    startup.setUid((String)mapObj1.get("uid"));

                            }
                        }

                        list.add(startup);
                        Log.i("size",list.size()+"");
                    }
                }
                Intent intent=new Intent(MainActivity.this, InvestActivity.class);
                intent.putExtra("mylist", (Serializable) list);
                startActivity(intent);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }


        });
        return true;
    }
}

