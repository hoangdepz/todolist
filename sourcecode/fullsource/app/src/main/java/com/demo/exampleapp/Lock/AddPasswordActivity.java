package com.demo.exampleapp.Lock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.demo.exampleapp.R;
import com.demo.exampleapp.databinding.ActivityAddPasswordBinding;
import com.itsxtt.patternlock.PatternLockView;

import java.util.ArrayList;
import java.util.Iterator;


public class AddPasswordActivity extends AppCompatActivity {
    ActivityAddPasswordBinding binding;
    String firstPattern = "";
    String secondPattern;
    int theme;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityAddPasswordBinding inflate = ActivityAddPasswordBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        settheme();
        addPattern();
    }

    public void addPattern() {
        this.binding.patternLockView.setOnPatternListener(new PatternLockView.OnPatternListener() {
            @Override 
            public void onProgress(ArrayList<Integer> arrayList) {
            }

            @Override 
            public void onStarted() {
                if (AddPasswordActivity.this.firstPattern.isEmpty()) {
                    return;
                }
                AddPasswordActivity.this.binding.headingTv.setTextColor(AddPasswordActivity.this.getResources().getColor(R.color.white));
                AddPasswordActivity.this.binding.headingTv.setText(R.string.draw_the_unlock_pattern_again_to_confirm_it);
            }

            @Override 
            public boolean onComplete(ArrayList<Integer> arrayList) {
                if (AddPasswordActivity.this.firstPattern.isEmpty()) {
                    AddPasswordActivity addPasswordActivity = AddPasswordActivity.this;
                    addPasswordActivity.firstPattern = addPasswordActivity.patternToString(arrayList);
                    AddPasswordActivity.this.binding.patternLockView.clearFocus();
                    new Handler().postDelayed(new Runnable() { 
                        @Override 
                        public void run() {
                            AddPasswordActivity.this.binding.headingTv.setText(R.string.draw_the_unlock_pattern_again_to_confirm_it);
                        }
                    }, 400L);
                } else {
                    AddPasswordActivity addPasswordActivity2 = AddPasswordActivity.this;
                    addPasswordActivity2.secondPattern = addPasswordActivity2.patternToString(arrayList);
                    if (AddPasswordActivity.this.secondPattern.equals(AddPasswordActivity.this.firstPattern)) {
                        Intent intent = new Intent(AddPasswordActivity.this, (Class<?>) PasswordQuestionsActivity.class);
                        intent.putExtra("pattern", AddPasswordActivity.this.firstPattern);
                        intent.putExtra("edit", true);
                        AddPasswordActivity.this.startActivity(intent);
                    } else {
                        AddPasswordActivity.this.binding.headingTv.setText(R.string.pattern_do_not_match_with_previous);
                        AddPasswordActivity.this.binding.headingTv.setTextColor(AddPasswordActivity.this.getResources().getColor(R.color.redcolor));
                        AddPasswordActivity.this.binding.patternLockView.clearFocus();
                    }
                }
                return true;
            }
        });
    }

    private void setbackgroundcolor(int i) {
        this.binding.mainActivity.setBackgroundColor(i);
    }

    public void settheme() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 1 || i == 0) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 2) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 3) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 4) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }
        if (i == 5) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.lockbac));
            return;
        }

    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    
    public String patternToString(ArrayList<Integer> arrayList) {
        StringBuilder sb = new StringBuilder();
        Iterator<Integer> it = arrayList.iterator();
        while (it.hasNext()) {
            sb.append(it.next());
        }
        return sb.toString();
    }
}
