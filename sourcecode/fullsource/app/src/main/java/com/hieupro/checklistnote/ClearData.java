package com.hieupro.checklistnote;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import java.io.File;


public class ClearData extends Application {
    private static ClearData instance;

    @Override 
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static ClearData getInstance() {
        if (instance == null) {
            instance = new ClearData();
        }
        return instance;
    }

    public void clearApplicationData(Context context) {
        File file = new File(context.getCacheDir().getParent());
        if (file.exists()) {
            for (String str : file.list()) {
                if (!str.equals("lib")) {
                    deleteDir(new File(file, str));
                    Log.i("TAG", "**************** File /data/data/mypackage/" + str + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            for (String str : file.list()) {
                if (!deleteDir(new File(file, str))) {
                    return false;
                }
            }
        }
        return file.delete();
    }
}
