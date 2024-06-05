package com.demo.todolistapp.DataHolderClasses;


public class HolderTODODialog {
    private static HolderTODODialog instance;
    private boolean aBoolean;

    private HolderTODODialog() {
    }

    public static HolderTODODialog getInstance() {
        if (instance == null) {
            instance = new HolderTODODialog();
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
