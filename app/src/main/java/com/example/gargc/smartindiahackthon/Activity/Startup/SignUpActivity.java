package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.Activity.Investor.SignUp.Accreditation;
import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private Button btCreateAccount;

    private EditText etName, etEmailId, etPhoneNumber, etUsername, etPassword;

    private String name, emailId, phoneNumber, username;
    public static String password;

    private ProgressBar progressBar;

    public static HashMap<String,String> userMap;



    DatabaseReference mDatabase;

    FirebaseAuth mAuth;

    public static String loginType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Intent intent=getIntent();
        loginType=intent.getStringExtra("login");



        Log.i("check2","checking1");

        mAuth=FirebaseAuth.getInstance();

        etName=(EditText) findViewById(R.id.etName);
        etEmailId=(EditText) findViewById(R.id.etEmailId);
        etPhoneNumber=(EditText) findViewById(R.id.etPhoneNumber);
        etUsername=(EditText) findViewById(R.id.etUsername);
        etPassword=(EditText) findViewById(R.id.etPassword);

        progressBar=(ProgressBar) findViewById(R.id.progress);

        btCreateAccount=(Button) findViewById(R.id.btCreateAccount);


        btCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("check2","checking3");


                name = etName.getText().toString().trim();
                emailId = etEmailId.getText().toString().trim().toLowerCase();
                etEmailId.setText(emailId);
                phoneNumber = etPhoneNumber.getText().toString().trim();
                username = etUsername.getText().toString().trim();
                password = etPassword.getText().toString();


                if (name.length() < 2) {
                    Toast.makeText(SignUpActivity.this, "Name must be at least 2 characters", Toast.LENGTH_LONG).show();
                    etName.requestFocus();
                    toggleViews(true);
                } else if (!name.matches("^[^<>'\"/;`%&!@#$*()+=:?.,~|{}]*$")) {
                    Toast.makeText(SignUpActivity.this, "Name cannot contain special characters", Toast.LENGTH_LONG).show();
                    etName.requestFocus();
                    toggleViews(true);
                } else if (!emailId.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid email id", Toast.LENGTH_LONG).show();
                    etEmailId.requestFocus();
                    toggleViews(true);
                } else if (!(phoneNumber.length() == 10 && (phoneNumber.startsWith("9") || phoneNumber.startsWith("8") || phoneNumber.startsWith("7")))) {
                    Toast.makeText(SignUpActivity.this, "Please enter a valid mobile number", Toast.LENGTH_SHORT).show();
                    etPhoneNumber.requestFocus();
                    toggleViews(true);
                } else if (username.length() < 3) {
                    Toast.makeText(SignUpActivity.this, "Username must be at least 3 characters long", Toast.LENGTH_LONG).show();
                    etUsername.requestFocus();
                    toggleViews(true);
                } else if (!username.matches("(?=^.{3,20}$)^[a-zA-Z][a-zA-Z0-9]*[._-]?[a-zA-Z0-9]+$")) {
                    Toast.makeText(SignUpActivity.this, "Username cannot contain special characters", Toast.LENGTH_LONG).show();
                    etUsername.requestFocus();
                    toggleViews(true);
                } else if (password.length() == 0) {
                    Toast.makeText(SignUpActivity.this, "Please enter a password", Toast.LENGTH_LONG).show();
                    etPassword.requestFocus();
                    toggleViews(true);
                } else {
                    toggleViews(false);
                    progressBar.setVisibility(View.VISIBLE);
                    btCreateAccount.setVisibility(View.GONE);
                    Log.i("check","success");
                    btCreateAccount.setEnabled(true);
                    register_user(emailId,password,name,phoneNumber,username);

                }


            }
        });


    }

    private void register_user(final String emailId, final String password, final String name, final String phoneNumber, final String username) {



        if(loginType.equals("Investor"))
        {
            userMap=new HashMap<String, String>();
            userMap.put("emailId",emailId);
            userMap.put("name",name);
            userMap.put("phonenumber",phoneNumber);
            userMap.put("username",username);
            userMap.put("devicetoken", FirebaseInstanceId.getInstance().getToken());

            Intent intent1=new Intent(SignUpActivity.this, Accreditation.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent1.putExtra("password",password);
            intent1.putExtra("hashmap",userMap);
            startActivity(intent1);
            finish();
        }
        else {

            mAuth.createUserWithEmailAndPassword(emailId, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.i("check2", "checking9");
                        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                        String uid = current_user.getUid();
                        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(loginType).child(uid);

                        HashMap<String, String> userMap = new HashMap<String, String>();
                        userMap.put("emailId", emailId);
                        userMap.put("name", name);
                        userMap.put("phonenumber", phoneNumber);
                        userMap.put("username", username);
                        userMap.put("devicetoken", FirebaseInstanceId.getInstance().getToken());

                        mDatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                toggleViews(false);

                                Log.i("login", loginType);

                                if (loginType.equals("Investor")) {
                                    Intent intent1 = new Intent(SignUpActivity.this, Accreditation.class);
                                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent1);
                                    finish();
                                } else {
                                    Intent mainIntent = new Intent(SignUpActivity.this, MainActivity.class);
                                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(mainIntent);
                                    finish();
                                }

                            }
                        });


                    } else {
                        //  Toast.makeText(SignUpActivity.this, "Unable To Create Account... ", Toast.LENGTH_SHORT).show();
                        Toast.makeText(SignUpActivity.this, "Email Id Already exists", Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(SignUpActivity.this, SignUpActivity.class);
                        intent1.putExtra("login","Startup");
                        startActivity(intent1);

                    }

                }
            });
        }


    }

    private void toggleViews(boolean enabled) {
        progressBar.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        btCreateAccount.setVisibility((enabled) ? View.VISIBLE : View.INVISIBLE);
        etName.setEnabled(enabled);
        etEmailId.setEnabled(enabled);
        etUsername.setEnabled(enabled);
        etPassword.setEnabled(enabled);
        etPhoneNumber.setEnabled(enabled);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }
}
