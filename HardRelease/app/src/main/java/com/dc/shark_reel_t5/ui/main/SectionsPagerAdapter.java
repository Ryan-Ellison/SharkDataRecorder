package com.dc.shark_reel_t5.ui.main;

import android.content.Intent;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.dc.shark_reel_t5.R;

import java.util.ArrayList;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {



    private static ArrayList<String> tab_titles = new ArrayList<String>();
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }



    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tab_titles.get(position);
    }

    @Override
    public int getCount() {
        return tab_titles.size();
    }

    public String getTitle(int position) {
        return tab_titles.get(position);
    }

    //Dynamic(runtime) methods:

    //On call: adds a hook to the end of the list in UI(DOES NOT CHANGE BACK END VARIABLES YET)
    public void addHookFrag(){
        tab_titles.add("Hook " + (tab_titles.size() + 1));
        notifyDataSetChanged();
    }

}