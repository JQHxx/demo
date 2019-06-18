package com.wenran.wenran.main.login.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Crowhine on 2019/2/13
 *
 * @author Crowhine
 */
public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    public MyFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPagerAdapter(FragmentManager fm, Context context, List<Fragment> fragmentList) {
        super(fm);
        this.context = context;
        this.fragmentList = fragmentList;
    }

    private Context context;
    private List<Fragment> fragmentList;

    @Override
    public Fragment getItem(int i) {
        if (fragmentList != null && fragmentList.size() != 0) {
            return fragmentList.get(i);
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragmentList != null ? fragmentList.size() : 0;
    }


}
