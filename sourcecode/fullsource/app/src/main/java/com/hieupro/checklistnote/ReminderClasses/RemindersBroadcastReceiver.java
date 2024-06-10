package com.hieupro.checklistnote.ReminderClasses;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import com.hieupro.checklistnote.ActivityMain;
import com.hieupro.checklistnote.AddNoteActivity;
import com.hieupro.checklistnote.R;
import com.hieupro.checklistnote.paramsClasses.Param;


public class RemindersBroadcastReceiver extends BroadcastReceiver {
    @SuppressLint("WrongConstant")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent intent2;
        long longExtra = intent.getLongExtra("noteId", -1L);
        String stringExtra = intent.getStringExtra(Param.KEY_TITLE);
        String stringExtra2 = intent.getStringExtra(Param.KEY_CONTENT);
        int intExtra = intent.getIntExtra("check", 0);
        Log.d("noteId", "" + longExtra);
        ControlMusic.getInstance(context).playMusic();
        Intent intent3 = new Intent(context, (Class<?>) SnoozeReceivers.class);
        intent3.setAction(SnoozeReceivers.Snooze);
        intent3.setFlags(603979776);
        int i = (int) longExtra;
        PendingIntent broadcast = PendingIntent.getBroadcast(context, i, intent3, 201326592);
        if (intExtra == 2) {
            intent2 = new Intent(context, (Class<?>) AddNoteActivity.class);
            intent2.putExtra("reminderId", String.valueOf(longExtra));
        } else if (intExtra == 3) {
            intent2 = new Intent(context, (Class<?>) AddNoteActivity.class);
        } else {
            intent2 = new Intent(context, (Class<?>) ActivityMain.class);
            intent2.putExtra("id", longExtra);
        }
        PendingIntent activity = PendingIntent.getActivity(context, i, intent2, 167772160);
        NotificationCompat.Action build = new NotificationCompat.Action.Builder(R.drawable.baseline_stop_24, context.getResources().getString(R.string.snooze), broadcast).build();
        Intent intent4 = new Intent(context, (Class<?>) NotificationsCancelledReceiver.class);
        intent4.setAction("com.example.diary.NOTIFICATION_CANCELLED");
        intent4.putExtra("noteId", longExtra);
        PendingIntent broadcast2 = PendingIntent.getBroadcast(context, i, intent4, 301989888);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        @SuppressLint("RestrictedApi") NotificationCompat.Builder groupSummary = new NotificationCompat.Builder(context, "default_channel").setSmallIcon(R.drawable.notes).setContentTitle(stringExtra).setContentText(stringExtra2).setContentIntent(activity).setSound(RingtoneManager.getDefaultUri(2)).setPriority(0).setLights(0xffff0000, PathInterpolatorCompat.MAX_NUM_POINTS, PathInterpolatorCompat.MAX_NUM_POINTS).setVibrate(new long[]{0, 1000, 1000, 1000, 1000}).setAutoCancel(true).addAction(build).setDeleteIntent(broadcast2).setNotificationSilent().setGroupSummary(true);
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel m = new NotificationChannel("default_channel", "Notifications", 3);
            m.setDescription(context.getResources().getString(R.string.reminder));
            notificationManager.createNotificationChannel(m);

        }
        notificationManager.notify(i, groupSummary.build());
    }
}
