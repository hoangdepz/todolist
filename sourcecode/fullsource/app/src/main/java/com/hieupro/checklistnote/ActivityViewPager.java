package com.hieupro.checklistnote;

import android.content.Intent;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.hieupro.checklistnote.R;
import com.hieupro.checklistnote.AdapterClasses.ViewPagerAdapter;
import com.hieupro.checklistnote.DataHolderClasses.HolderData;
import com.hieupro.checklistnote.Lock.LockHolder;
import com.hieupro.checklistnote.Lock.PatternDialog;
import com.hieupro.checklistnote.Lock.SharedPrefrence;
import com.hieupro.checklistnote.databinding.ViewPagerActivityBinding;
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

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);

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
