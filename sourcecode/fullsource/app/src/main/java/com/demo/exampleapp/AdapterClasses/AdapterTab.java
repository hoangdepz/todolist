package com.demo.exampleapp.AdapterClasses;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import java.util.ArrayList;
import java.util.List;


public class AdapterTab extends FragmentStateAdapter {
    private final List<Integer> fragmentIconList;
    private final List<Fragment> fragmentList;

    public AdapterTab(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this.fragmentList = new ArrayList();
        this.fragmentIconList = new ArrayList();
    }

    public void addFragment(Fragment fragment, int i) {
        this.fragmentList.add(fragment);
        this.fragmentIconList.add(Integer.valueOf(i));
    }

    @Override 
    public Fragment createFragment(int i) {
        return this.fragmentList.get(i);
    }

    @Override 
    public int getItemCount() {
        return this.fragmentList.size();
    }

    public int getIcon(int i) {
        return this.fragmentIconList.get(i).intValue();
    }
}
