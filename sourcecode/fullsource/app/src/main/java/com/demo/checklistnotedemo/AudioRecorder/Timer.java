package com.demo.checklistnotedemo.AudioRecorder;

import android.os.Handler;
import android.os.Looper;


public class Timer {
    public long delay = 100;
    private long duration = 0;
    public Handler handler = new Handler(Looper.getMainLooper());
    OnTimeTickListener listener;
    public Runnable runnable;

    
    public interface OnTimeTickListener {
        void onTimerTick(String str);
    }

    static long access(Timer timer, long j) {
        long j2 = timer.duration + j;
        timer.duration = j2;
        return j2;
    }

    public Timer(final OnTimeTickListener onTimeTickListener) {
        this.listener = onTimeTickListener;
        this.runnable = new Runnable() { 
            @Override 
            public void run() {
                Timer timer = Timer.this;
                Timer.access(timer, timer.delay);
                Timer.this.handler.postDelayed(Timer.this.runnable, Timer.this.delay);
                onTimeTickListener.onTimerTick(Timer.this.format());
            }
        };
    }

    public String format() {
        long j = this.duration;
        long j2 = j % 1000;
        long j3 = (j / 1000) % 60;
        long j4 = (j / 60000) % 60;
        return j / 3600000 > 0 ? String.format("%02d:%02d:%02d.%02d", Long.valueOf(j4), Long.valueOf(j3), Long.valueOf(j2 / 10)) : String.format("%02d:%02d.%02d", Long.valueOf(j4), Long.valueOf(j3), Long.valueOf(j2 / 10));
    }

    public void start() {
        this.handler.postDelayed(this.runnable, this.delay);
    }

    public void stop() {
        this.handler.removeCallbacks(this.runnable);
        this.duration = 0L;
    }

    public void pause() {
        this.handler.removeCallbacks(this.runnable);
    }
}
