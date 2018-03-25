package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFeed extends AppCompatActivity {

    private ImageButton btnAddImage;
    private Button btnSubmit;
    private EditText edt_title , edt_desc , edt_content;
    private Uri mImageUri = null;
    private StorageReference mStorage;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase,nameDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;

    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feed);

        btnAddImage = (ImageButton)findViewById(R.id.btn_add_image);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        edt_desc = (EditText)findViewById(R.id.post_description);
        edt_title = (EditText)findViewById(R.id.post_title);
        edt_content = (EditText)findViewById(R.id.post_content);

        mStorage = FirebaseStorage.getInstance().getReference();


        mAuth = FirebaseAuth.getInstance();

        mCurrentUser = mAuth.getCurrentUser();


        final ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_single_choice);

        DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("StartupUsers").child(mCurrentUser.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()) {
                    //Loop 1 to go through all the child nodes of users

                    String username1 = uniqueKeySnapshot.getKey();
                    Log.i("username",username1+"");
                    arrayAdapter.add(username1);


                }

                //AlertDialog
                AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(AddFeed.this);


                alertDialogBuilder.setTitle("Choose Your Startup");
                alertDialogBuilder.setCancelable(false);

                alertDialogBuilder.
                        setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(AddFeed.this, "Select one of the startup", Toast.LENGTH_SHORT).show();
                                Intent intent2=new Intent(AddFeed.this,AddFeed.class);
                                startActivity(intent2);
                                finish();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();

                    }
                }).setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AddFeed.this, arrayAdapter.getItem(i), Toast.LENGTH_SHORT).show();
                        username=arrayAdapter.getItem(i);
                    }
                });

                AlertDialog alertDialog=alertDialogBuilder.create();
                alertDialog.show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








        mProgress = new ProgressDialog(this);



        //mDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed").child(mCurrentUser.getUid());

        mDatabase = FirebaseDatabase.getInstance().getReference().child("NewsFeed");






        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 1);

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startPosting();
            }
        });

    }

    private void startPosting()
    {
        final String title = edt_title.getText().toString().trim();
        final String desc = edt_desc.getText().toString().trim();
        final String content = edt_content.getText().toString().trim();

        if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(desc) && mImageUri!=null &&!TextUtils.isEmpty(content))
        {
            mProgress.setTitle("Posting Feed");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            StorageReference filepath = mStorage.child("blog_feed").child(mImageUri.getLastPathSegment());
            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {

                    final Uri downloadUrl = taskSnapshot.getDownloadUrl();

                    final DatabaseReference newPost = mDatabase.child(title);

                    newPost.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot)
                        {
                            newPost.child("title").setValue(title);
                            newPost.child("desc").setValue(desc);
                            newPost.child("image").setValue(downloadUrl.toString());
                            newPost.child("uid").setValue(mCurrentUser.getUid());
                            newPost.child("content").setValue(content);
                            newPost.child("username").setValue(dataSnapshot.child("name").getValue());
                            newPost.child("userimage").setValue(dataSnapshot.child("image").getValue());
                            newPost.child("postedby").setValue(username);

                            Toast.makeText(AddFeed.this, "Feed posted", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(AddFeed.this , MainActivity.class));

                        }



                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    mProgress.dismiss();

                }
            });
        }

        else
        {
            Toast.makeText(this, "Enter all the details", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK)
        {
            mImageUri = data.getData();
            btnAddImage.setImageURI(mImageUri);
        }

    }

}