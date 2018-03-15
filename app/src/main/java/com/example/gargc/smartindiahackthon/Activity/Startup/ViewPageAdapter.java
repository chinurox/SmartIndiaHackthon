package com.example.gargc.smartindiahackthon.Activity.Startup;

/**
 * Created by gargc on 15-03-2018.
 */

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.gargc.smartindiahackthon.R;

import java.util.ArrayList;

/**
 * Created by gargc on 15-03-2018.
 */

class ViewPageAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    ArrayList<Integer> images;

    public ViewPageAdapter(Context context, ArrayList<Integer> images){
        this.context=context;
        this.images=images;
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=layoutInflater.inflate(R.layout.image_slider_layout,null);
        ImageView imageView=(ImageView) view.findViewById(R.id.imageSlider);

        imageView.setImageResource(images.get(position));

        ViewPager viewPager=(ViewPager) container;
        viewPager.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager viewPager=(ViewPager) container;
        View view=(View) object;
        viewPager.removeView(view);
    }
}

