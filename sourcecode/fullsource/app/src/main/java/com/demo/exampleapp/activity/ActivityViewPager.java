package com.demo.exampleapp.activity;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.demo.exampleapp.R;
import com.demo.exampleapp.AdapterClasses.ViewPagerAdapter;
import com.demo.exampleapp.DataHolderClasses.HolderData;
import com.demo.exampleapp.Lock.LockHolder;
import com.demo.exampleapp.Lock.PatternDialog;
import com.demo.exampleapp.Lock.SharedPrefrence;
import com.demo.exampleapp.databinding.ViewPagerActivityBinding;
import java.lang.reflect.Field;


public class ActivityViewPager extends AppCompatActivity {
    ViewPagerActivityBinding binding;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;


    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewPagerActivityBinding inflate = ViewPagerActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());



        ViewPager2 viewPager2 = (ViewPager2) findViewById(R.id.viewPager);
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int intExtra = getIntent().getIntExtra("imagePosition", 0);
        viewPager2.setAdapter(new ViewPagerAdapter(HolderData.getInstance().getImageList(), this));
        viewPager2.setCurrentItem(intExtra, false);
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(16));
        viewPager2.setPageTransformer(compositePageTransformer);
        this.binding.backhomeViewPager.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityViewPager.this.onBackPressed();
            }
        });
    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        this.isBackpressed = false;
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
        }
    }

    
    @Override 
    public void onStart() {
        super.onStart();
        boolean z = LockHolder.getInstance().getboolean();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z && this.isActivityVisible) {
            new PatternDialog(this).showDialog();
        }
        LockHolder.getInstance().setboolean(true);
        this.isActivityVisible = false;
    }

    
    @Override 
    public void onStop() {
        super.onStop();
        this.isActivityVisible = true;
    }
}
