package com.demo.exampleapp.activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.demo.exampleapp.R;
import com.demo.exampleapp.AdapterClasses.AdapterTheme;
import com.demo.exampleapp.Listeners.Finishs;
import com.demo.exampleapp.Listeners.refreshAdapters;
import com.demo.exampleapp.Lock.LockHolder;
import com.demo.exampleapp.Lock.PatternDialog;
import com.demo.exampleapp.Lock.SharedPrefrence;
import com.demo.exampleapp.databinding.ThemeActivityBinding;
import java.util.ArrayList;


public class ActivityTheme extends AppCompatActivity implements refreshAdapters {
    AdapterTheme adapter;
    AdapterTheme adapter1;
    ThemeActivityBinding binding;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;


    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ThemeActivityBinding inflate = ThemeActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());




        setStatusBarTransparentBlack();
        firstRecyclerView();
        this.binding.backhome.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityTheme.this.onBackPressed();
            }
        });
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void firstRecyclerView() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(R.drawable.theme1));
        arrayList.add(Integer.valueOf(R.drawable.theme2));
        arrayList.add(Integer.valueOf(R.drawable.theme3));
        arrayList.add(Integer.valueOf(R.drawable.theme4));
        arrayList.add(Integer.valueOf(R.drawable.theme5));
        arrayList.add(Integer.valueOf(R.drawable.theme6));
        this.adapter = new AdapterTheme(arrayList, this, new Finishs() { 
            @Override 
            public final void onClick() {
                ActivityTheme.this.onClick();
            }
        }, true, this);
        this.binding.themeRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.binding.themeRecyclerView.setAdapter(this.adapter);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(Integer.valueOf(R.drawable.theme7));
        arrayList2.add(Integer.valueOf(R.drawable.theme8));
        arrayList2.add(Integer.valueOf(R.drawable.theme9));
        arrayList2.add(Integer.valueOf(R.drawable.theme10));
        arrayList2.add(Integer.valueOf(R.drawable.theme11));
        arrayList2.add(Integer.valueOf(R.drawable.theme12));
        arrayList2.add(Integer.valueOf(R.drawable.theme13));
        arrayList2.add(Integer.valueOf(R.drawable.theme14));
        arrayList2.add(Integer.valueOf(R.drawable.theme15));
        arrayList2.add(Integer.valueOf(R.drawable.theme16));
        arrayList2.add(Integer.valueOf(R.drawable.theme17));
        this.adapter1 = new AdapterTheme(arrayList2, this, new Finishs() { 
            @Override 
            public final void onClick() {
                ActivityTheme.this.onClick();
            }
        }, false, this);
        this.binding.modernRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.binding.modernRecyclerView.setAdapter(this.adapter1);
    }

    
    public void onClick() {
        this.binding.scroll.setVisibility(View.GONE);
        settheme();
        this.binding.prograss.setVisibility(View.VISIBLE);
        startActivity(new Intent(this, (Class<?>) ActivityMain.class));
        finishAffinity();
    }

    @Override 
    public void refreshAdapter() {
        this.adapter.notifyDataSetChanged();
        this.adapter1.notifyDataSetChanged();
    }

    public void settheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            addButton(R.color.green);
            return;
        }
        if (i == 2) {
            addButton(R.color.pink);
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
        if (i == 7) {
            addButton(R.color.themedark7);
            return;
        }
        if (i == 8) {
            addButton(R.color.themedark8);
            return;
        }
        if (i == 9) {
            addButton(R.color.themedark9);
            return;
        }
        if (i == 10) {
            addButton(R.color.themedark10);
            return;
        }
        if (i == 11) {
            addButton(R.color.themedark11);
            return;
        }
        if (i == 12) {
            addButton(R.color.themedark12);
            return;
        }
        if (i == 13) {
            addButton(R.color.themedark13);
            return;
        }
        if (i == 14) {
            addButton(R.color.themedark14);
            return;
        }
        if (i == 15) {
            addButton(R.color.themedark15);
        } else if (i == 16) {
            addButton(R.color.themedark16);
        } else if (i == 17) {
            addButton(R.color.themedark17);
        }
    }

    private void addButton(int i) {
        this.binding.prograss.setIndeterminate(true);
        this.binding.prograss.getIndeterminateDrawable().setColorFilter(getResources().getColor(i), PorterDuff.Mode.SRC_IN);
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
