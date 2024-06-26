package com.demo.exampleapp.Lock;

import android.app.Activity;
import android.content.SharedPreferences;


public class SharedPrefrence {
    public static String[] getInformation(Activity activity) {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("user", 0);
        return new String[]{sharedPreferences.getString("question", ""), sharedPreferences.getString("answer", "")};
    }

    public static void setInformation(Activity activity, String str, String str2) {
        SharedPreferences.Editor edit = activity.getSharedPreferences("user", 0).edit();
        edit.putString("answer", str);
        edit.putString("question", str2);
        edit.apply();
    }

    public static void setPasswordSwitch(boolean z, Activity activity) {
        SharedPreferences.Editor edit = activity.getSharedPreferences("user", 0).edit();
        edit.putBoolean("passwordSwitch", z);
        edit.apply();
    }

    public static void setFingerprintEnabled(boolean z, Activity activity) {
        SharedPreferences.Editor edit = activity.getSharedPreferences("finger", 0).edit();
        edit.putBoolean("FingerPrintEnabled", z);
        edit.apply();
    }

    public static boolean getFingerPrintEnabled(Activity activity) {
        return activity.getSharedPreferences("finger", 0).getBoolean("FingerPrintEnabled", false);
    }

    public static boolean getPasswordSwitch(Activity activity) {
        return activity.getSharedPreferences("user", 0).getBoolean("passwordSwitch", false);
    }

    public static String getSavedPattern(Activity activity) {
        return activity.getSharedPreferences("passcode", 0).getString("pattern", "");
    }

    public static void savePattern(String str, Activity activity) {
        SharedPreferences.Editor edit = activity.getSharedPreferences("passcode", 0).edit();
        edit.putString("pattern", str);
        edit.apply();
    }
}
