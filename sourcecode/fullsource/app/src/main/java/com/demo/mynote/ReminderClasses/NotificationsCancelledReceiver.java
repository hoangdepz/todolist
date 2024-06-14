package com.demo.mynote.ReminderClasses;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class NotificationsCancelledReceiver extends BroadcastReceiver {
    @Override 
    public void onReceive(Context context, Intent intent) {
        if (intent.getLongExtra("noteId", -1L) != -1) {
            ControlMusic.getInstance(context).stopMusic();
        }
    }
}
