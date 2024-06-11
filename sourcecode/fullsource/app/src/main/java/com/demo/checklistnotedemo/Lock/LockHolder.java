package com.demo.checklistnotedemo.Lock;


public class LockHolder {
    private static LockHolder instance;
    boolean istrue;

    private LockHolder() {
    }

    public static LockHolder getInstance() {
        if (instance == null) {
            instance = new LockHolder();
        }
        return instance;
    }

    public boolean getboolean() {
        return this.istrue;
    }

    public void setboolean(boolean z) {
        this.istrue = z;
    }
}
