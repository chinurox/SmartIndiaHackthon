package com.example.gargc.smartindiahackthon.Activity;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.gargc.smartindiahackthon.Activity.Investor.InvestorFirstActivity;
import com.example.gargc.smartindiahackthon.Activity.Startup.StartupFirstActivity;
import com.example.gargc.smartindiahackthon.R;

public class FirstActivity extends AppCompatActivity {

    ImageView startup,investor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        startup=(ImageView) findViewById(R.id.startup);
        investor=(ImageView) findViewById(R.id.investor);

        investor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this, InvestorFirstActivity.class);
                startActivity(intent);


            }
        });

        startup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(FirstActivity.this, StartupFirstActivity.class);
                startActivity(intent);
            }
        });


    }
}
