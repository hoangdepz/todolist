package com.demo.checklistnotedemo.Lock;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import com.demo.checklistnotedemo.activity.ActivityMain;
import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.databinding.ActivityPasswordQuestionsBinding;


public class PasswordQuestionsActivity extends AppCompatActivity {
    ActivityPasswordQuestionsBinding binding;
    String pattern;
    private String[] questionSpinnerString;
    int theme;
    boolean edit = false;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityPasswordQuestionsBinding inflate = ActivityPasswordQuestionsBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());



        setStatusBarTransparent();
        settheme();
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                PasswordQuestionsActivity.this.onBackPressed();
            }
        });
        this.questionSpinnerString = new String[]{getString(R.string.what_is_your_favourite_color), getString(R.string.what_is_your_favourite_food), getString(R.string.what_is_your_pet_name)};
        this.pattern = getIntent().getStringExtra("pattern");
        this.edit = getIntent().getBooleanExtra("edit", false);
        setQuestionsSpinner();
        if (!this.edit) {
            this.binding.saveBtn.setText(R.string.ok);
            this.binding.questiontxt.setText(SharedPrefrence.getInformation(this)[0]);
            this.binding.questiontxt.setVisibility(View.VISIBLE);
            this.binding.questionsSpinner.setVisibility(View.GONE);
            this.binding.CardView.setVisibility(View.GONE);
            this.binding.text.setVisibility(View.GONE);
            this.binding.text2.setVisibility(View.GONE);
            this.binding.text3.setVisibility(View.GONE);
        }
        this.binding.saveBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (PasswordQuestionsActivity.this.edit) {
                    if (PasswordQuestionsActivity.this.binding.asnEt.getText().toString().isEmpty()) {
                        PasswordQuestionsActivity passwordQuestionsActivity = PasswordQuestionsActivity.this;
                        Toast.makeText(passwordQuestionsActivity, passwordQuestionsActivity.getString(R.string.please_fill_all_fields), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    PasswordQuestionsActivity passwordQuestionsActivity2 = PasswordQuestionsActivity.this;
                    SharedPrefrence.setInformation(passwordQuestionsActivity2, passwordQuestionsActivity2.binding.asnEt.getText().toString(), PasswordQuestionsActivity.this.questionSpinnerString[PasswordQuestionsActivity.this.binding.questionsSpinner.getSelectedItemPosition()]);
                    SharedPrefrence.setPasswordSwitch(true, PasswordQuestionsActivity.this);
                    if (PasswordQuestionsActivity.this.getIntent().getBooleanExtra("changeQuestion", false)) {
                        PasswordQuestionsActivity passwordQuestionsActivity3 = PasswordQuestionsActivity.this;
                        Toast.makeText(passwordQuestionsActivity3, passwordQuestionsActivity3.getString(R.string.information_saved), Toast.LENGTH_SHORT).show();
                        PasswordQuestionsActivity.this.finish();
                        LockHolder.getInstance().setboolean(false);
                        return;
                    }
                    SharedPrefrence.savePattern(PasswordQuestionsActivity.this.pattern, PasswordQuestionsActivity.this);
                    PasswordQuestionsActivity.this.startActivity(new Intent(PasswordQuestionsActivity.this, (Class<?>) ShowPasscodeActivity.class));
                    PasswordQuestionsActivity.this.finishAffinity();
                    return;
                }
                if (PasswordQuestionsActivity.this.binding.asnEt.getText().toString().equals(SharedPrefrence.getInformation(PasswordQuestionsActivity.this)[1])) {
                    if (PasswordQuestionsActivity.this.getIntent().getBooleanExtra("question", false)) {
                        Intent intent = new Intent(PasswordQuestionsActivity.this, (Class<?>) PasswordQuestionsActivity.class);
                        intent.putExtra("edit", true);
                        intent.putExtra("changeQuestion", true);
                        PasswordQuestionsActivity.this.startActivity(intent);
                        PasswordQuestionsActivity.this.finish();
                        return;
                    }
                    if (!PasswordQuestionsActivity.this.getIntent().getBooleanExtra("change", false)) {
                        PasswordQuestionsActivity.this.startActivity(new Intent(PasswordQuestionsActivity.this, (Class<?>) ActivityMain.class));
                    } else {
                        PasswordQuestionsActivity.this.startActivity(new Intent(PasswordQuestionsActivity.this, (Class<?>) AddPasswordActivity.class));
                    }
                    PasswordQuestionsActivity.this.finishAffinity();
                    return;
                }
                PasswordQuestionsActivity passwordQuestionsActivity4 = PasswordQuestionsActivity.this;
                Toast.makeText(passwordQuestionsActivity4, passwordQuestionsActivity4.getString(R.string.wrong_answer), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setStatusBarTransparent() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.setNavigationBarColor(0);
        window.setFlags(512, 512);
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

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 0) {
            ischeckedColor(R.color.green);
        }
        int i2 = this.theme;
        if (i2 == 1) {
            color6 = getColor(R.color.full_light_green);
            setbackgroundcolor(color6);
            ischeckedColor(R.color.green);
        } else if (i2 == 2) {
            color5 = getColor(R.color.full_light_pink);
            setbackgroundcolor(color5);
            ischeckedColor(R.color.pink);
        } else if (i2 == 3) {
            color4 = getColor(R.color.full_light_blue);
            setbackgroundcolor(color4);
            ischeckedColor(R.color.blue);
        } else if (i2 == 4) {
            color3 = getColor(R.color.full_light_purple);
            setbackgroundcolor(color3);
            ischeckedColor(R.color.purple);
        } else if (i2 == 5) {
            color2 = getColor(R.color.black);
            setbackgroundcolor(color2);
            ischeckedColor(R.color.purple);
        } else if (i2 == 6) {
            color = getColor(R.color.full_light_parrot);
            setbackgroundcolor(color);
            ischeckedColor(R.color.parrot);
        } else if (i2 == 7) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_7));
            ischeckedColor(R.color.themedark7);
        } else if (i2 == 8) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_8));
            ischeckedColor(R.color.themedark8);
        } else if (i2 == 9) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_9));
            ischeckedColor(R.color.themedark9);
        } else if (i2 == 10) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_10));
            ischeckedColor(R.color.themedark10);
        } else if (i2 == 11) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_11));
            ischeckedColor(R.color.themedark11);
        } else if (i2 == 12) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_12));
            ischeckedColor(R.color.themedark12);
        } else if (i2 == 13) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_13));
            ischeckedColor(R.color.themedark13);
        } else if (i2 == 14) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_14));
            ischeckedColor(R.color.themedark14);
        } else if (i2 == 15) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_15));
            ischeckedColor(R.color.themedark15);
        } else if (i2 == 16) {
            ischeckedColor(R.color.themedark16);
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i2 == 17) {
            this.binding.parentLayout.setBackground(getDrawable(R.drawable.theme_s_17));
            ischeckedColor(R.color.themedark17);
        }
        if (this.theme == 5) {
            textcolors(R.color.white);
            setStatusBarTransparentWhite();
        } else {
            textcolors(R.color.black);
            setStatusBarTransparentBlack();
        }
    }

    private void setbackgroundcolor(int i) {
        this.binding.parentLayout.setBackgroundColor(i);
    }

    public void textcolors(int i) {
        this.binding.asnEt.setTextColor(getResources().getColor(i));
        this.binding.questiontxt.setTextColor(getResources().getColor(i));
        this.binding.asnEt.setHintTextColor(getResources().getColor(i));
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

    public void ischeckedColor(int i) {
        int color;
        AppCompatButton appCompatButton = this.binding.saveBtn;
        color = getColor(R.color.white);
        appCompatButton.setTextColor(color);
        this.binding.saveBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    private void setQuestionsSpinner() {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout, this.questionSpinnerString) { 
            @Override 
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView textView = (TextView) super.getView(i, view, viewGroup);
                if (PasswordQuestionsActivity.this.theme == 5) {
                    textView.setTextColor(ContextCompat.getColor(PasswordQuestionsActivity.this.getApplicationContext(), R.color.white));
                } else {
                    textView.setTextColor(ContextCompat.getColor(PasswordQuestionsActivity.this.getApplicationContext(), R.color.black));
                }
                return textView;
            }

            @Override 
            public View getDropDownView(int i, View view, ViewGroup viewGroup) {
                return (TextView) super.getDropDownView(i, view, viewGroup);
            }
        };
        arrayAdapter.setDropDownViewResource(R.layout.spinner_layout);
        this.binding.questionsSpinner.setAdapter((SpinnerAdapter) arrayAdapter);
    }
}
