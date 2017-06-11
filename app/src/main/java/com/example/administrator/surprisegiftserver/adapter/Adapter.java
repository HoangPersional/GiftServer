package com.example.administrator.surprisegiftserver.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 26/4/2017.
 */

public class Adapter extends FragmentPagerAdapter {
    Context mContext;
    List<Fragment> fragments;
    int pos;
    public Adapter(FragmentManager fm) {
        super(fm);
    }
    public Adapter(FragmentManager fm, Context context, List<Fragment> fragments)
    {
        super(fm);
        mContext=context;
        this.fragments=fragments;
        pos=0;
    }
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
        if(pos>getCount())
            pos=0;
    }
}
