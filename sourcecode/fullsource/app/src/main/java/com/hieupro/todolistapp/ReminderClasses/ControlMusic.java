package com.hieupro.todolistapp.ReminderClasses;

import android.content.Context;
import android.media.MediaPlayer;
import com.hieupro.todolistapp.R;


public class ControlMusic {
    private static ControlMusic sInstance;
    private Context mContext;
    private MediaPlayer mMediaPlayer;

    public ControlMusic(Context context) {
        this.mContext = context;
    }

    public static ControlMusic getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ControlMusic(context);
        }
        return sInstance;
    }

    public void playMusic() {
        MediaPlayer create = MediaPlayer.create(this.mContext, R.raw.reminder_sound);
        this.mMediaPlayer = create;
        create.start();
    }

    public void playClickSound() {
        MediaPlayer create = MediaPlayer.create(this.mContext, R.raw.click_sound);
        this.mMediaPlayer = create;
        create.start();
    }

    public void stopMusic() {
        MediaPlayer mediaPlayer = this.mMediaPlayer;
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            this.mMediaPlayer.seekTo(0);
        }
    }
}
