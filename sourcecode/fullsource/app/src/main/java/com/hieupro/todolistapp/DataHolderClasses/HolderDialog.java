package com.hieupro.todolistapp.DataHolderClasses;


public class HolderDialog {
    private static HolderDialog instance;
    private boolean aBoolean;

    private HolderDialog() {
    }

    public static HolderDialog getInstance() {
        if (instance == null) {
            instance = new HolderDialog();
        }
        return instance;
    }

    public boolean getVariable() {
        return this.aBoolean;
    }

    public void setVariable(boolean z) {
        this.aBoolean = z;
    }
}
