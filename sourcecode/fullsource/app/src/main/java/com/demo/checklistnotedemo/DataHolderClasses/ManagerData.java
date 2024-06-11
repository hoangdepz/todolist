package com.demo.checklistnotedemo.DataHolderClasses;


public class ManagerData {
    private static ManagerData instance;
    private boolean aBoolean;
    private String sharedVariable;

    private ManagerData() {
    }

    public static ManagerData getInstance() {
        if (instance == null) {
            instance = new ManagerData();
        }
        return instance;
    }

    public String getSharedVariable() {
        return this.sharedVariable;
    }

    public void setSharedVariable(String str) {
        this.sharedVariable = str;
    }

    public boolean getVariable() {
        return this.aBoolean;
    }

    public void setVariable(boolean z) {
        this.aBoolean = z;
    }
}
