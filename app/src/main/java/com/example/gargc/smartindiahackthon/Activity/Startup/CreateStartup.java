package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreateStartup extends AppCompatActivity {

    private Button btnImg ,createStartup ,btnImg2 ;
    private ImageButton imgView , imgView2;
    private DatabaseReference mDatabase,startupUsers;
    private StorageReference mStorage;
    private Uri mImageUri = null , mImageUri2 = null;

    private Spinner spinner;

    private ProgressDialog mProgress;

    TextInputLayout name,about,description;

    String uid;


    EditText editTextOfCofounder;
    Button addCofounder;
    ListView listView;
    ArrayList<String> listItems;
    ArrayAdapter<String> adapter;

    CircleImageView circleImageView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_startup);

        // Add Cofounder
        editTextOfCofounder = (EditText) findViewById(R.id.CofounderEmailId);
        addCofounder = (Button) findViewById(R.id.addItem);
        listView = (ListView) findViewById(R.id.listView);
        listItems = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, listItems);
        listView.setAdapter(adapter);
        addCofounder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if(editTextOfCofounder.getText().toString().equals(""))
                {
                    Toast.makeText(CreateStartup.this, "Cofounder email can't be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    listItems.add(editTextOfCofounder.getText().toString());
                    editTextOfCofounder.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(
                        CreateStartup.this);
                alert.setTitle("Alert!!");
                alert.setMessage("Are you sure to Delete Cofounder");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listItems.remove(i);
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();

                return true;
            }
        });


        btnImg=(Button) findViewById(R.id.main_img_btn);
        btnImg2=(Button) findViewById(R.id.main_img_btn_2);

        imgView = (ImageButton)findViewById(R.id.main_image);
        imgView2 = (ImageButton)findViewById(R.id.main_image2);

        createStartup=(Button) findViewById(R.id.createStartup);

        mProgress = new ProgressDialog(CreateStartup.this);

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
        uid = current_user.getUid();


        mDatabase = FirebaseDatabase.getInstance().getReference().child("Startup");
        startupUsers=FirebaseDatabase.getInstance().getReference().child("StartupUsers").child(uid);

        mStorage = FirebaseStorage.getInstance().getReference();

        spinner=(Spinner) findViewById(R.id.main_spinner);

        name=(TextInputLayout) findViewById(R.id.createstartupname);
        about=(TextInputLayout) findViewById(R.id.createstartupabout);
        description=(TextInputLayout) findViewById(R.id.createstartupdescription);

        List<String> categories = new ArrayList<String>();
        categories.add("Stage");
        categories.add("Ideation");
        categories.add("Proof of Concept");
        categories.add("Beta Launched");
        categories.add("Early Revenues");
        categories.add("Steady Revenues");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter2);

        circleImageView=(CircleImageView) findViewById(R.id.center);
        imageView=(ImageView) findViewById(R.id.base);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 1);

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent , 2);

            }
        });





//        btnImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//
//                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent , 1);
//
//            }
//        });
//
//        btnImg2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
//                galleryIntent.setType("image/*");
//                startActivityForResult(galleryIntent , 2);
//
//            }
//        });

        createStartup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                upload();
            }
        });







    }

    private void upload() {

        final String startupName=name.getEditText().getText().toString();
        final String startupAbout=about.getEditText().getText().toString();
        final String startupDescription=description.getEditText().getText().toString();
        final String category=spinner.getSelectedItem().toString();

        if(!TextUtils.isEmpty(startupAbout)&&!TextUtils.isEmpty(startupName)&&!TextUtils.isEmpty(startupDescription)&&!TextUtils.isEmpty(category))
        {
            mProgress.setTitle("Creating Startup....");
            mProgress.setCanceledOnTouchOutside(false);
            mProgress.show();

            StorageReference filepath = mStorage.child(mImageUri.getLastPathSegment());
            Log.i("checkd","1"+mImageUri);


            filepath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    @SuppressWarnings("VisibleForTests") final Uri imgUri = taskSnapshot.getDownloadUrl();

                    StorageReference filepath2 = mStorage.child("blog_images").child(mImageUri2.getLastPathSegment());
                    Log.i("checkd","2");

                    filepath2.putFile(mImageUri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            @SuppressWarnings("VisibleForTests") final Uri imgUri2 = taskSnapshot.getDownloadUrl();

                            Log.i("checkd","3");


                            DatabaseReference productDB = mDatabase.child(category).child(startupName);

                            final HashMap startupMap=new HashMap();
                            startupMap.put("Name",startupName);
                            startupMap.put("About",startupAbout);
                            startupMap.put("Description",startupDescription);
                            startupMap.put("Category",category);
                            startupMap.put("logo",imgUri.toString());
                            startupMap.put("cover",imgUri2.toString());
                            startupMap.put("size",listItems.size());
                            startupMap.put("uid",uid);
                            for(int i=0;i<listItems.size();i++)
                            {
                                startupMap.put("cofounder"+i,listItems.get(i));
                            }

                            productDB.setValue(startupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        HashMap hashMap=new HashMap();
                                        hashMap.put("Name",startupName);
                                        hashMap.put("Category",category);

                                        startupUsers.child(startupName).setValue(startupMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()) {
                                                    Toast.makeText(CreateStartup.this, "Startup Created", Toast.LENGTH_SHORT).show();
                                                    Log.i("checkd", "4");

                                                    mProgress.dismiss();

                                                    Intent intent=new Intent(CreateStartup.this, MainActivity.class);
                                                    startActivity(intent);
                                                }
                                                else
                                                {
                                                    Toast.makeText(CreateStartup.this, "Startup Could Not Be Created", Toast.LENGTH_SHORT).show();
                                                    mProgress.dismiss();
                                                    Log.i("checkd","5");
                                                }

                                            }
                                        });
                                    }
                                    else{
                                        Toast.makeText(CreateStartup.this, "Startup Could Not Be Created", Toast.LENGTH_SHORT).show();
                                        mProgress.dismiss();
                                        Log.i("checkd","5");

                                    }

                                }
                            });


                        }

                    });

                }
            });

        }
        else
        {
            Toast.makeText(CreateStartup.this, "please enter all the fields!", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1 && resultCode==RESULT_OK)
        {
            mImageUri = data.getData();
            circleImageView.setImageURI(mImageUri);
        }

        if(requestCode==2 && resultCode==RESULT_OK)
        {
            mImageUri2 = data.getData();
            imageView.setImageURI(mImageUri2);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth(); // ((display.getWidth()*20)/100)
            int height =display.getHeight();

            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(width,height);
            imageView.setLayoutParams(layoutParams);

        }

    }
}
