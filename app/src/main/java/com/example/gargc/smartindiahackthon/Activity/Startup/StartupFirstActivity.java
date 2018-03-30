package com.example.gargc.smartindiahackthon.Activity.Startup;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gargc.smartindiahackthon.Activity.LoginActivity;
import com.example.gargc.smartindiahackthon.R;

import java.util.ArrayList;



public class StartupFirstActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout sliderDotspanel;


    int dotscount;
    ImageView[] dots;

    Button signIn;
    TextView signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup_first);

        sliderDotspanel=(LinearLayout) findViewById(R.id.SliderDots);
        viewPager=(ViewPager) findViewById(R.id.viewpager);

        ArrayList<Integer> arrayList=new ArrayList<>();
        arrayList.add(R.drawable.connect);
        arrayList.add(R.drawable.discover);



        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getApplicationContext(),arrayList);

        viewPager.setAdapter(viewPageAdapter);
        dotscount=viewPageAdapter.getCount();
        dots=new ImageView[dotscount];

        for(int i=0;i<dotscount;i++)
        {
            dots[i]=new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dots));

            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8,0,8,0);

            sliderDotspanel.addView(dots[i],params);

        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<dotscount;i++)
                {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.nonactive_dots));

                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.active_dots));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        signIn=(Button) findViewById(R.id.btSignIn);
        signUp=(TextView) findViewById(R.id.tvSignUp);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StartupFirstActivity.this, LoginActivity.class);
                intent.putExtra("login","startup");
                startActivity(intent);

            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(StartupFirstActivity.this, SignUpActivity.class);
                intent.putExtra("login","Startup");
                startActivity(intent);


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
