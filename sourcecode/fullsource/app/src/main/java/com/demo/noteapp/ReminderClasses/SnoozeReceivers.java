package com.demo.noteapp.ReminderClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class SnoozeReceivers extends BroadcastReceiver {
    public static final String Snooze = "com.akash.SnoozeReceiver";

    @Override 
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Snooze)) {
            ControlMusic.getInstance(context).stopMusic();
        }
    }
}
