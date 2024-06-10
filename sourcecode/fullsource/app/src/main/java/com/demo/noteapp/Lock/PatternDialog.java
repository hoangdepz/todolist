package com.demo.noteapp.Lock;

import android.app.Activity;
import android.content.Intent;


public class PatternDialog {
    private static final int PASSCODE = 100;
    Activity context;

    public PatternDialog(Activity activity) {
        this.context = activity;
    }

    public void showDialog() {
        Intent intent = new Intent(this.context, (Class<?>) ShowPasscodeActivity.class);
        intent.putExtra("passcode", true);
        this.context.startActivityForResult(intent, 100);
    }
}
