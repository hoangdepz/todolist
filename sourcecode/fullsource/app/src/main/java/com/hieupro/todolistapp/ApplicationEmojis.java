package com.hieupro.todolistapp;

import android.app.Application;
import com.aghajari.emojiview.AXEmojiManager;
import com.aghajari.emojiview.appleprovider.AXAppleEmojiProvider;


public class ApplicationEmojis extends Application {
    @Override 
    public void onCreate() {
        super.onCreate();
        AXEmojiManager.install(this, new AXAppleEmojiProvider(this));
    }
}
