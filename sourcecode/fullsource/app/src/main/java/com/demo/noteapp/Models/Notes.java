package com.demo.noteapp.Models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Notes {
    private String color;
    private String content;
    private String folderName;
    private long folderTimesTamp;
    private String fontName;
    private int gravity;
    private long id;
    private String image;
    public int isDark;
    private long reminderDate;
    int textColor;
    private long timestamp;
    private String title;

    public int getIsDark() {
        return this.isDark;
    }

    public void setIsDark(int i) {
        this.isDark = i;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String str) {
        this.fontName = str;
    }

    public Notes(long j, String str) {
        this.id = j;
        this.folderName = str;
    }

    public String getFolderName() {
        return this.folderName;
    }

    public void setFolderName(String str) {
        this.folderName = str;
    }

    public String getImage() {
        return this.image;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public int getGravity() {
        return this.gravity;
    }

    public void setGravity(int i) {
        this.gravity = i;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String str) {
        this.color = str;
    }

    public Notes(String str) {
        this.color = str;
    }

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public Notes(String str, String str2) {
        this.title = str;
        this.content = str2;
    }

    public Notes() {
    }

    public long getFolderTimesTamp() {
        return this.folderTimesTamp;
    }

    public void setFolderTimesTamp(long j) {
        this.folderTimesTamp = j;
    }

    public Notes(long j, String str, String str2, long j2) {
        this.id = j;
        this.title = str;
        this.content = str2;
        this.timestamp = j2;
    }

    public Notes(long j) {
        this.id = j;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String str) {
        this.content = str;
    }

    public String getFolderFormattedTimestamp() {
        return new SimpleDateFormat("dd-MM-yyyy ", Locale.US).format((Object) new Date(this.folderTimesTamp));
    }

    public String getFormattedTimestamp() {
        return new SimpleDateFormat("dd/MM/yyyy ", Locale.US).format((Object) new Date(this.timestamp));
    }

    public long getReminderDate() {
        return this.reminderDate;
    }

    public void setReminderDate(long j) {
        this.reminderDate = j;
    }
}
