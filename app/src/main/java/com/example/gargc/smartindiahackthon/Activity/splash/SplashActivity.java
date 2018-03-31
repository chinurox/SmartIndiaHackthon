package com.example.gargc.smartindiahackthon.Activity.splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.gargc.smartindiahackthon.Activity.LoginActivity;
import com.example.gargc.smartindiahackthon.MainActivity;
import com.example.gargc.smartindiahackthon.R;

import static com.example.gargc.smartindiahackthon.utils.AppConstant.USER_PREFERENCE;

public class SplashActivity extends AppCompatActivity {

    public static  int SPLASH_SCREEN_TIMEOUT=1000;


    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

            }
        },SPLASH_SCREEN_TIMEOUT);



    }

}
