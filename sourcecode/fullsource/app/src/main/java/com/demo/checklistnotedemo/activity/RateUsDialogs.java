package com.demo.checklistnotedemo.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.demo.checklistnotedemo.R;


public class RateUsDialogs {
    Context context;
    Button rateus;
    RatingBar ratingBar;
    int theme;


    public RateUsDialogs(Context context) {
        this.context = context;
    }

    public void RateUsDialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.context);
        View inflate = LayoutInflater.from(this.context).inflate(R.layout.rate_us_dialog, (ViewGroup) null);
        builder.setView(inflate);
        AlertDialog create = builder.create();
        this.ratingBar = (RatingBar) inflate.findViewById(R.id.ratingBar);
        this.rateus = (Button) inflate.findViewById(R.id.ratus_btn);
        final ImageView imageView = (ImageView) inflate.findViewById(R.id.rateusImg);
        this.rateus.setEnabled(false);
        this.ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() { 
            @Override 
            public void onRatingChanged(RatingBar ratingBar, float f, boolean z) {
                if (z) {
                    ratingBar.setRating(Math.round(f));
                    if (f <= 3.0f) {
                        RateUsDialogs.this.rateus.setText(RateUsDialogs.this.context.getString(R.string.feedback));
                    } else {
                        RateUsDialogs.this.rateus.setText(RateUsDialogs.this.context.getString(R.string.rate_us));
                    }
                    double d = f;
                    if (d == 1.0d) {
                        imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r1));
                    } else if (d == 2.0d) {
                        imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r2));
                    } else if (d == 3.0d) {
                        imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r3));
                    } else if (d == 4.0d) {
                        imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r4));
                    } else if (d == 5.0d) {
                        imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r5));
                    }
                    RateUsDialogs.this.rateus.setEnabled(true);
                    RateUsDialogs.this.settheme();
                    return;
                }
                imageView.setBackground(RateUsDialogs.this.context.getResources().getDrawable(R.drawable.r1));
                RateUsDialogs.this.rateus.setBackgroundTintList(ColorStateList.valueOf(RateUsDialogs.this.context.getResources().getColor(R.color.darkGrey)));
                RateUsDialogs.this.rateus.setEnabled(false);
            }
        });
        this.ratingBar.setStepSize(1.0f);
        this.rateus.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                try {
                    RateUsDialogs.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + RateUsDialogs.this.context.getPackageName())));
                } catch (ActivityNotFoundException unused) {
                    RateUsDialogs.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + RateUsDialogs.this.context.getPackageName())));
                }
                SharedPreferences.Editor edit = RateUsDialogs.this.context.getSharedPreferences("MyPreferences", 0).edit();
                edit.putBoolean("istrue", false);
                edit.apply();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void settheme() {
        int i = this.context.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 0) {
            ischeckedColor(R.color.green);
        }
        int i2 = this.theme;
        if (i2 == 1) {
            ischeckedColor(R.color.green);
            return;
        }
        if (i2 == 2) {
            ischeckedColor(R.color.pink);
            return;
        }
        if (i2 == 3) {
            ischeckedColor(R.color.blue);
            return;
        }
        if (i2 == 4) {
            ischeckedColor(R.color.purple);
            return;
        }
        if (i2 == 5) {
            ischeckedColor(R.color.purple);
            return;
        }
        if (i2 == 6) {
            ischeckedColor(R.color.parrot);
            return;
        }
        if (i2 == 7) {
            ischeckedColor(R.color.themedark7);
            return;
        }
        if (i2 == 8) {
            ischeckedColor(R.color.themedark8);
            return;
        }
        if (i2 == 9) {
            ischeckedColor(R.color.themedark9);
            return;
        }
        if (i2 == 10) {
            ischeckedColor(R.color.themedark10);
            return;
        }
        if (i2 == 11) {
            ischeckedColor(R.color.themedark11);
            return;
        }
        if (i2 == 12) {
            ischeckedColor(R.color.themedark12);
            return;
        }
        if (i2 == 13) {
            ischeckedColor(R.color.themedark13);
            return;
        }
        if (i2 == 14) {
            ischeckedColor(R.color.themedark14);
            return;
        }
        if (i2 == 15) {
            ischeckedColor(R.color.themedark15);
        } else if (i2 == 16) {
            ischeckedColor(R.color.themedark16);
        } else if (i2 == 17) {
            ischeckedColor(R.color.themedark17);
        }
    }

    private void ischeckedColor(int i) {
        this.rateus.setTextColor(this.context.getResources().getColor(R.color.white));
        this.rateus.setBackgroundTintList(ColorStateList.valueOf(this.context.getResources().getColor(i)));
        this.ratingBar.setProgressTintList(ColorStateList.valueOf(this.context.getResources().getColor(i)));
        this.ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(this.context.getResources().getColor(i)));
        this.ratingBar.setThumbTintList(ColorStateList.valueOf(this.context.getResources().getColor(i)));
    }
}
