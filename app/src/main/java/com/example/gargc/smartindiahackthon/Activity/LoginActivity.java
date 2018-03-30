package com.example.gargc.smartindiahackthon.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    EditText username,password;

    Button mLoginButton;
    ProgressDialog mLoginProgress;

    DatabaseReference mUserDatabase;

    //ProgressBar

    ProgressBar progressBar;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent=getIntent();
        String loginType=intent.getStringExtra("login");

        //firebase
        mAuth = FirebaseAuth.getInstance();
        mUserDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(loginType);

        username=(EditText) findViewById(R.id.etUsername);
        password=(EditText) findViewById(R.id.etPassword);





        mLoginProgress=new ProgressDialog(this);
        progressBar = (ProgressBar) findViewById(R.id.progress);


        mLoginButton=(Button) findViewById(R.id.btSignIn);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email=username.getText().toString();
                String password1=password.getText().toString();

                if(!TextUtils.isEmpty(email)&&!TextUtils.isEmpty(password1))
                {
                    if (!email.matches("^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$")) {
                        Toast.makeText(LoginActivity.this, "Please enter a valid email id", Toast.LENGTH_LONG).show();
                        username.requestFocus();
                        toggleViews(true);
                    }
                    else {

                        toggleViews(false);
                        loginUser(email, password1);
                    }
                }
                else if(TextUtils.isEmpty(email))
                {
                    toggleViews(true);
                    Toast.makeText(LoginActivity.this, "Enter the email id", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    toggleViews(true);
                    Toast.makeText(LoginActivity.this, "Enter the password", Toast.LENGTH_SHORT).show();
                }


            }
        });




    }

    private void toggleViews(boolean enabled) {
        progressBar.setVisibility((enabled) ? View.GONE : View.VISIBLE);
        mLoginButton.setVisibility((enabled) ? View.VISIBLE : View.INVISIBLE);
        username.setEnabled(enabled);
        password.setEnabled(enabled);


    }

    private void loginUser(String email, String password) {

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    //successfully logged in
                    toggleViews(true);

                    Intent mainIntent=new Intent(LoginActivity.this,MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();

                }
                else
                {
                    toggleViews(true);
                    Toast.makeText(LoginActivity.this, "Cannot Sign In.. ", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.
                INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        return true;
    }

}
