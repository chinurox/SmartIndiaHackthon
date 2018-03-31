package com.example.gargc.smartindiahackthon.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.gargc.smartindiahackthon.Activity.Startup.Fragment.Bookmark;
import com.example.gargc.smartindiahackthon.Activity.Startup.Fragment.ProfileFragment;
import com.example.gargc.smartindiahackthon.Activity.Startup.Fragment.StartupFragment;

/**
 * Created by gargc on 30-03-2018.
 */

class MainPageAdapter extends FragmentPagerAdapter {
    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                ProfileFragment profileFragment = new ProfileFragment();
                Bundle args = new Bundle();
                args.putInt("page_position", position + 1);

                // Set the arguments on the fragment
                // that will be fetched in the
                // DemoFragment@onCreateView
                profileFragment.setArguments(args);
                return profileFragment;

            case 1:
                if(ViewProfile.user.equals("Startup")) {
                    StartupFragment startupFragment = new StartupFragment();
                    Bundle args1 = new Bundle();
                    args1.putInt("page_position", position + 1);

                    // Set the arguments on the fragment
                    // that will be fetched in the
                    // DemoFragment@onCreateView
                    startupFragment.setArguments(args1);
                    return startupFragment;
                }
                else{
                    Bookmark startupFragment = new Bookmark();
                    Bundle args1 = new Bundle();
                    args1.putInt("page_position", position + 1);

                    // Set the arguments on the fragment
                    // that will be fetched in the
                    // DemoFragment@onCreateView
                    startupFragment.setArguments(args1);
                    return startupFragment;

                }

            default : return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position) {
            case 0:
                return "Profile";

            case 1:
                if(ViewProfile.user.equals("Startup")) {
                    return "Startups";
                }else {
                    return "Favorite Startup";
                }
        }

            return super.getPageTitle(position);
    }
}
