package com.eguo.lullabyes.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Владимир on 21.05.2018.
 */

public class ContentAdapter  extends FragmentPagerAdapter {
    private final List<Fragment> fragmentListView =new ArrayList<>();
    public ContentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentListView.get(position);
    }

    @Override
    public int getCount() {
        return fragmentListView.size();
    }
    public  void  addFragment(Fragment fragment){
        fragmentListView.add(fragment);
    }

}
