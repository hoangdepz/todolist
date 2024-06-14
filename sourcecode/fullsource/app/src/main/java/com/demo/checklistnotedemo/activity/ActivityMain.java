package com.demo.checklistnotedemo.activity;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;

import com.demo.checklistnotedemo.AdapterClasses.AdapterTab;
import com.demo.checklistnotedemo.FragmentClasses.FragmentCalender;
import com.demo.checklistnotedemo.FragmentClasses.FragmentHome;
import com.demo.checklistnotedemo.FragmentClasses.FragmentSetting;
import com.demo.checklistnotedemo.FragmentClasses.FragmentTODO;
import com.demo.checklistnotedemo.Lock.LockHolder;
import com.demo.checklistnotedemo.Lock.PasswordOptionsActivity;
import com.demo.checklistnotedemo.Lock.PatternDialog;
import com.demo.checklistnotedemo.Lock.SharedPrefrence;
import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.databinding.MainActivityBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ActivityMain extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 100;
    boolean a;
    MainActivityBinding binding;
    View headerView;
    private boolean isActivityVisible = false;
    RelativeLayout layout;
    int notes;
    int profile;
    int reminder;
    AdapterTab tabAdapter;


    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.binding = MainActivityBinding.inflate(getLayoutInflater());
        requestWindowFeature(1);
        setContentView(this.binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ActivityMain.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 11);
            }
        }



        boolean booleanExtra = getIntent().getBooleanExtra("splash", false);
        this.a = booleanExtra;
        if (booleanExtra && SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty()) {
            new PatternDialog(this).showDialog();
        }
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        this.tabAdapter = new AdapterTab(this);
        getWindow().setSoftInputMode(32);
        showNavigation();
        settheme();
        this.tabAdapter.addFragment(new FragmentTODO(), R.drawable.task_icon);
        this.tabAdapter.addFragment(new FragmentCalender(), R.drawable.calender);
        this.tabAdapter.addFragment(new FragmentHome(), R.drawable.home);
        this.tabAdapter.addFragment(new FragmentSetting(), R.drawable.setting);
        this.binding.viewPager.setOffscreenPageLimit(4);
        this.binding.viewPager.setAdapter(this.tabAdapter);
        this.notes = getIntent().getIntExtra("note1", -1);
        this.profile = getIntent().getIntExtra("profile", -2);
        this.reminder = getIntent().getIntExtra("reminder", -3);
        for (int i = 0; i < this.tabAdapter.getItemCount(); i++) {
            TabLayout.Tab tabAt = this.binding.tabLayout.getTabAt(i);
            if (tabAt != null) {
                tabAt.setIcon(this.tabAdapter.getIcon(i));
            }
        }
        new TabLayoutMediator(this.binding.tabLayout, this.binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public final void onConfigureTab(TabLayout.Tab tab, int i2) {
                ActivityMain.this.m338xbc75d7b7(tab, i2);
            }
        }).attach();
        this.binding.viewPager.setUserInputEnabled(false);
        if (this.notes != -1) {
            this.binding.viewPager.setCurrentItem(2, false);
        } else if (this.profile != -2) {
            this.binding.viewPager.setCurrentItem(3, false);
        } else if (this.reminder != -3) {
            this.binding.viewPager.setCurrentItem(3, false);
        }
        this.binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 1 && ActivityMain.this.getSharedPreferences("MyPreferences", 0).getBoolean("istrue", true)) {
                    new RateUsDialogs(ActivityMain.this).RateUsDialogbox();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(ActivityMain.this.tabAdapter.getIcon(tab.getPosition()));
            }
        });
        this.binding.navmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int itemId = menuItem.getItemId();
                if (itemId == R.id.trash) {
                    ActivityMain.this.startActivity(new Intent(ActivityMain.this, (Class<?>) TrashActivity.class));
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (itemId == R.id.category) {
                    ActivityMain.this.startActivity(new Intent(ActivityMain.this, (Class<?>) CategoryActivity.class));
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (itemId == R.id.lock) {
                    ActivityMain.this.startActivity(new Intent(ActivityMain.this, (Class<?>) PasswordOptionsActivity.class));
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (itemId == R.id.share_us) {
                    Intent intent = new Intent("android.intent.action.SEND");
                    intent.setType("text/plain");
                    intent.putExtra("android.intent.extra.SUBJECT", "SUBJECT");
                    intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + ActivityMain.this.getPackageName());
                    ActivityMain activityMain = ActivityMain.this;
                    activityMain.startActivity(Intent.createChooser(intent, activityMain.getResources().getString(R.string.share_us)));
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (itemId == R.id.rate_us) {
                    try {
                        ActivityMain.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + ActivityMain.this.getPackageName())));
                    } catch (ActivityNotFoundException unused) {
                        ActivityMain.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + ActivityMain.this.getPackageName())));
                    }
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                    return true;
                }
                if (itemId != R.id.privacy) {
                    return true;
                }
                ActivityMain.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://doc-hosting.flycricket.io/smart-tech-labs-ind/1d074628-53cd-4ebb-8776-cf7e591c2833/privacy")));
                ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    public void m338xbc75d7b7(TabLayout.Tab tab, int i) {
        tab.setIcon(this.tabAdapter.getIcon(i));
    }

    private void setStatusBarTransparentWhite() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(1280);
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void navigationtheme(int i) {
        this.layout.setBackgroundColor(getResources().getColor(i));
        this.binding.navmenu.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void settheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_green));
            tablayoutcolor(R.color.light_green);
            navigationtheme(R.color.green);
        } else if (i == 2) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_pink));
            tablayoutcolor(R.color.light_pink);
            navigationtheme(R.color.pink);
        } else if (i == 3) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_blue));
            tablayoutcolor(R.color.light_blue);
            navigationtheme(R.color.blue);
        } else if (i == 4) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_purple));
            tablayoutcolor(R.color.light_purple);
            navigationtheme(R.color.purple);
        } else if (i == 5) {
            setbackgroundcolor(getResources().getColor(R.color.black));
            tablayoutcolor(R.color.light_black);
            navigationtheme(R.color.black);
            this.binding.navmenu.setItemIconTintList(ColorStateList.valueOf(getResources().getColor(R.color.purple)));
        } else if (i == 6) {
            setbackgroundcolor(getResources().getColor(R.color.full_light_parrot));
            tablayoutcolor(R.color.light_parrot);
            navigationtheme(R.color.parrot);
        } else if (i == 7) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_7));
            navigationtheme(R.color.themedark7);
        } else if (i == 8) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_8));
            navigationtheme(R.color.themedark8);
        } else if (i == 9) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_9));
            navigationtheme(R.color.themedark9);
        } else if (i == 10) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_10));
            navigationtheme(R.color.themedark10);
        } else if (i == 11) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_11));
            navigationtheme(R.color.themedark11);
        } else if (i == 12) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_12));
            navigationtheme(R.color.themedark12);
        } else if (i == 13) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_13));
            navigationtheme(R.color.themedark13);
        } else if (i == 14) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_14));
            navigationtheme(R.color.themedark14);
        } else if (i == 15) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_15));
            navigationtheme(R.color.themedark15);
        } else if (i == 16) {
            navigationtheme(R.color.themedark16);
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_17));
            navigationtheme(R.color.themedark17);
        }
        if (i == 5 || i == 15 || i == 16 || i == 17) {
            toggle(getResources().getColor(R.color.white));
            setStatusBarTransparentWhite();
        } else {
            toggle(getResources().getColor(R.color.black));
            if (Build.VERSION.SDK_INT >= 23) {
                setStatusBarTransparentBlack();
            }
        }
        if (i > 6) {
            tablayoutcolor(R.color.themetab);
        }
        if (i == 5) {
            this.binding.navmenu.setBackgroundColor(getResources().getColor(R.color.black));
            this.binding.navmenu.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        } else {
            this.binding.navmenu.setBackgroundColor(getResources().getColor(R.color.white));
            this.binding.navmenu.setItemTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
        }
    }

    public void setbackgroundcolor(int i) {
        this.binding.mainActivity.setBackgroundColor(i);
    }

    public void tablayoutcolor(int i) {
        this.binding.tabLayout.setBackgroundColor(getResources().getColor(i));
    }

    public void toggle(int i) {
        this.binding.toggle.setImageTintList(ColorStateList.valueOf(i));
    }

    public void showNavigation() {
        this.binding.mainActivity.closeDrawer(GravityCompat.START);
        View headerView = this.binding.navmenu.getHeaderView(0);
        this.headerView = headerView;
        this.layout = (RelativeLayout) headerView.findViewById(R.id.navheader);
        if (this.binding.mainActivity.isDrawerOpen(GravityCompat.START)) {
            this.binding.mainActivity.closeDrawer(GravityCompat.START);
        }
        this.binding.toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityMain.this.binding.mainActivity.isDrawerOpen(GravityCompat.START)) {
                    ActivityMain.this.binding.mainActivity.closeDrawer(GravityCompat.START);
                } else {
                    ActivityMain.this.binding.mainActivity.openDrawer(GravityCompat.START);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        setResult(-1, new Intent());
        LockHolder.getInstance().setboolean(false);
        finish();
    }


    @Override
    public void onStart() {
        super.onStart();
        boolean z = LockHolder.getInstance().getboolean();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z) {
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
