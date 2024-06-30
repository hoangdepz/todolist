package com.demo.exampleapp.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.exampleapp.Models.ItemTheme;
import com.demo.exampleapp.R;
import com.demo.exampleapp.AdapterClasses.AdapterTheme;
import com.demo.exampleapp.Listeners.refreshAdapters;
import com.demo.exampleapp.Lock.LockHolder;
import com.demo.exampleapp.Lock.PatternDialog;
import com.demo.exampleapp.Lock.SharedPrefrence;
import com.demo.exampleapp.databinding.ThemeActivityBinding;
import java.util.ArrayList;

public class ActivityTheme extends AppCompatActivity implements refreshAdapters {
    AdapterTheme adapter;
    ThemeActivityBinding binding;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ThemeActivityBinding inflate = ThemeActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        //setStatusBarTransparentBlack();
        initData();
        setTheme();
        this.binding.backhome.setOnClickListener(view -> ActivityTheme.this.onBackPressed());
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void initData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ItemTheme(R.drawable.im_theme_light, "Light"));
        arrayList.add(new ItemTheme(R.drawable.im_theme_dark, "Dark"));
        arrayList.add(new ItemTheme(R.drawable.im_theme_cute, "Cute"));
        arrayList.add(new ItemTheme(R.drawable.im_theme_cool, "Cool"));
        arrayList.add(new ItemTheme(R.drawable.im_theme_game, "Game"));
        adapter = new AdapterTheme(arrayList, this, ActivityTheme.this::onClick, true, this);
        binding.themeRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.themeRecyclerView.setAdapter(this.adapter);
    }

    public void onClick() {
        this.binding.scroll.setVisibility(View.GONE);
        setTheme();
        this.binding.prograss.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, ActivityMain.class));
        finishAffinity();
    }

    @Override 
    public void refreshAdapter() {
        adapter.notifyDataSetChanged();
    }

    public void setTheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            binding.container.setBackgroundColor(Color.parseColor("#F8F8F8"));
            binding.imBackground.setVisibility(View.GONE);
            return;
        }
        if (i == 2) {
            binding.imBackground.setBackgroundResource(R.drawable.im_bg_theme_dark);
            binding.toolbar.setBackgroundColor(Color.parseColor("#B3353535"));
            binding.textView3.setTextColor(Color.WHITE);
            binding.backhome.setImageResource(R.drawable.ic_back_theme_dark);
            return;
        }
        if (i == 3) {
            addButton(R.color.blue);
            return;
        }
        if (i == 4) {
            addButton(R.color.purple);
            return;
        }
        if (i == 5) {
            addButton(R.color.purple);
            return;
        }
        if (i == 6) {
            addButton(R.color.parrot);
            return;
        }
    }

    private void addButton(int i) {
        this.binding.prograss.setIndeterminate(true);
        this.binding.prograss.getIndeterminateDrawable().setColorFilter(getResources().getColor(i), PorterDuff.Mode.SRC_IN);
    }

    @Override 
    public void onBackPressed() {
        super.onBackPressed();
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
