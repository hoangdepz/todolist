package com.demo.checklistnotedemo.Models;


public class ImageModels extends ListItems {
    private String filepath;
    String fontName;
    private int gravity;
    private long id;
    private String image;
    private String imageText;
    private int isDark;
    private long noteId;
    private String recordingText;
    int textcolor;

    public int getIsDark() {
        return this.isDark;
    }

    public void setIsDark(int i) {
        this.isDark = i;
    }

    public String getFilepath() {
        return this.filepath;
    }

    public void setFilepath(String str) {
        this.filepath = str;
    }

    public String getRecordingText() {
        return this.recordingText;
    }

    public void setRecordingText(String str) {
        this.recordingText = str;
    }

    public ImageModels(String str, int i) {
        this.filepath = str;
    }

    public String getImageText() {
        return this.imageText;
    }

    public String setImageText(String str) {
        this.imageText = str;
        return str;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String str) {
        this.image = str;
    }

    public long getNoteId() {
        return this.noteId;
    }

    public void setNoteId(long j) {
        this.noteId = j;
    }

    public ImageModels() {
    }

    public ImageModels(String str) {
        this.image = str;
    }

    public int getTextcolor() {
        return this.textcolor;
    }

    public int getGravity() {
        return this.gravity;
    }

    public void setGravity(int i) {
        this.gravity = i;
    }

    public void setTextcolor(int i) {
        this.textcolor = i;
    }

    public String getFontName() {
        return this.fontName;
    }

    public void setFontName(String str) {
        this.fontName = str;
    }
}
