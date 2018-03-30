package com.example.gargc.smartindiahackthon.Activity.Investor.SignUp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.gargc.smartindiahackthon.Activity.Startup.SignUpActivity;
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

public class Accreditation extends AppCompatActivity {


    LinearLayout linearLayout1, linearLayout2;
    Button next, apply;

    RadioGroup radioGroup1, radioGroup2, radioGroup3;
    EditText budget, linkedin, website, bio;

    String selectedtext, selectedText2, selectedText3;
    String budget1, linkedin1, website1, bio1;

    DatabaseReference databaseReference;

    HashMap userMap;
    String password;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accreditation);

        Intent intent = getIntent();
        userMap = SignUpActivity.userMap;
        password = SignUpActivity.password;


        Log.i("password",password);


        linearLayout1 = (LinearLayout) findViewById(R.id.linearlayout1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearlayout2);

        next = (Button) findViewById(R.id.next);
        apply = (Button) findViewById(R.id.apply);



        radioGroup1 = (RadioGroup) findViewById(R.id.radiogroup1);
        radioGroup2 = (RadioGroup) findViewById(R.id.radiogroup2);
        radioGroup3 = (RadioGroup) findViewById(R.id.radiogroup3);

        budget = (EditText) findViewById(R.id.budget);
        linkedin = (EditText) findViewById(R.id.linkedin);
        website = (EditText) findViewById(R.id.website);
        bio = (EditText) findViewById(R.id.bio);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout1.setVisibility(View.GONE);
                linearLayout2.setVisibility(View.VISIBLE);

                int radioButtonID = radioGroup1.getCheckedRadioButtonId();
                RadioButton radioButton = (RadioButton) radioGroup1.findViewById(radioButtonID);
                selectedtext = (String) radioButton.getText();

                radioButtonID = radioGroup2.getCheckedRadioButtonId();
                radioButton = (RadioButton) radioGroup2.findViewById(radioButtonID);
                selectedText2 = (String) radioButton.getText();


                radioButtonID = radioGroup3.getCheckedRadioButtonId();
                radioButton = (RadioButton) radioGroup3.findViewById(radioButtonID);
                selectedText3 = (String) radioButton.getText();

            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedtext.equals("") || selectedText2.equals("") || selectedText3.equals("")) {
                    linearLayout1.setVisibility(View.VISIBLE);
                    linearLayout2.setVisibility(View.GONE);
                    Toast.makeText(Accreditation.this, "Fill all the items", Toast.LENGTH_SHORT).show();
                } else {
                    budget1 = budget.getText().toString();
                    website1 = website.getText().toString();
                    bio1 = bio.getText().toString();
                    linkedin1 = linkedin.getText().toString();

                    Log.i("checking12", selectedtext + " " + selectedText2 + " " + selectedText3 + " " + budget1 + " " + website1 + " " + bio1 + " " + linkedin1);


                    if (budget1.equals("") || website1.equals("") || bio1.equals("") || linkedin1.equals("")) {
                        Toast.makeText(Accreditation.this, "Fill all the items", Toast.LENGTH_SHORT).show();
                    } else {

                        final ProgressDialog progressDialog=new ProgressDialog(Accreditation.this);
                        progressDialog.setMessage("Creating Account");
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        FirebaseAuth mAuth = FirebaseAuth.getInstance();

                        mAuth.createUserWithEmailAndPassword((String) userMap.get("emailId"), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();
                                    String uid = current_user.getUid();
                                    Log.i("login",uid);

                                    databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Investor").child(uid);

                                    databaseReference.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Log.i("check2", "checking9");
                                            databaseReference.child("RadioGroup1").setValue(selectedtext);
                                            databaseReference.child("RadioGroup2").setValue(selectedText2);
                                            databaseReference.child("RadioGroup3").setValue(selectedText3);
                                            databaseReference.child("budget").setValue(budget1);
                                            databaseReference.child("website").setValue(website1);
                                            databaseReference.child("bio").setValue(bio1);
                                            databaseReference.child("linkedin").setValue(linkedin1);

                                            Intent intent1=new Intent(Accreditation.this, MainActivity.class);
                                            startActivity(intent1);

                                        }

                                    });

                                }
                                else
                                {
                                    Toast.makeText(Accreditation.this, "Email Id Already exists", Toast.LENGTH_SHORT).show();
                                    Intent intent1=new Intent(Accreditation.this, SignUpActivity.class);
                                    intent1.putExtra("login","Investor");
                                    startActivity(intent1);
                                }

                            }


                        });
                    }


                }
            }
        });
    }
}
