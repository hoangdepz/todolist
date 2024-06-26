package com.demo.exampleapp.Models;


public class TODOModels {
    long categoryId;
    private String description;
    private long id;
    private long reminder;
    private int status;
    private int switchStatus;
    private String task;
    private long timestamp;

    public long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(long j) {
        this.timestamp = j;
    }

    public long getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(long j) {
        this.categoryId = j;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public int getSwitchStatus() {
        return this.switchStatus;
    }

    public void setSwitchStatus(int i) {
        this.switchStatus = i;
    }

    public long getReminder() {
        return this.reminder;
    }

    public void setReminder(long j) {
        this.reminder = j;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getTask() {
        return this.task;
    }

    public void setTask(String str) {
        this.task = str;
    }
}
