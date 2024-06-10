package com.hieupro.checklistnote;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import androidx.appcompat.app.AppCompatActivity;
import com.hieupro.checklistnote.databinding.SplashActivityBinding;


public class ActivitySplash extends AppCompatActivity {
    SplashActivityBinding binding;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SplashActivityBinding inflate = SplashActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());
        setStatusBarTransparent();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { 
            @Override 
            public void run() {
                ActivitySplash.this.startActivity(new Intent(ActivitySplash.this, (Class<?>) ActivityMain.class).putExtra("splash", true));
                ActivitySplash.this.finish();
            }
        }, 2000L);
    }

    private void setStatusBarTransparent() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        window.setFlags(512, 512);
    }
}
