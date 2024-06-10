package com.hieupro.todolistapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.hieupro.todolistapp.R;
import com.hieupro.todolistapp.Lock.LockHolder;
import com.hieupro.todolistapp.Lock.PatternDialog;
import com.hieupro.todolistapp.Lock.SharedPrefrence;
import com.hieupro.todolistapp.ReminderClasses.RemindersBroadcastReceiver;
import com.hieupro.todolistapp.databinding.ReminderActivityBinding;
import com.hieupro.todolistapp.paramsClasses.Param;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Locale;


public class ActivityReminder extends AppCompatActivity {
    private static final String PREFS_NAME = "MyPrefs";
    ReminderActivityBinding binding;
    boolean reminderSwitchState;
    private SharedPreferences sharedPreferences;
    private int PERMISSION_REQUEST_CODE_Notification = 321;
    private int PERMISSION_REQUEST_CODE = 23;
    Calendar dateCalender = Calendar.getInstance();
    long reminderDateInMillis = 0;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    private long generateNoteId(long j, int i) {
        return j + (i * 2);
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        int checkSelfPermission;
        super.onCreate(bundle);
        ReminderActivityBinding inflate = ReminderActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);

        this.sharedPreferences = getSharedPreferences(PREFS_NAME, 0);
        settheme();
        loadSavedTime();
        this.binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityReminder.this.onBackPressed();
            }
        });
        this.binding.showTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityReminder.this.areNotificationsEnabled()) {
                    ActivityReminder.this.showTimePicker();
                } else {
                    ActivityReminder.this.showPermissionDialog();
                }
            }
        });
        this.reminderSwitchState = getSharedPreferences(PREFS_NAME, 0).getBoolean("settingSwitchState", false);
        this.binding.switch3.setChecked(this.reminderSwitchState);
        loadSavedPreferences();
        if (this.binding.switch3.isChecked()) {
            this.binding.save.setVisibility(View.VISIBLE);
        } else {
            this.binding.save.setVisibility(View.GONE);
        }
        if (this.binding.switch3.isChecked()) {
            setthemeSwitch();
        } else {
            this.binding.switch3.getTrackDrawable().setColorFilter(getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
        }
        this.binding.switch3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                SharedPreferences.Editor edit = ActivityReminder.this.getSharedPreferences(ActivityReminder.PREFS_NAME, 0).edit();
                edit.putBoolean("settingSwitchState", z);
                edit.apply();
                if (ActivityReminder.this.binding.switch3.isChecked()) {
                    ActivityReminder.this.binding.save.setVisibility(View.VISIBLE);
                } else {
                    ActivityReminder.this.binding.save.setVisibility(View.GONE);
                }
                if (z) {
                    ActivityReminder.this.setthemeSwitch();
                } else {
                    ActivityReminder.this.binding.switch3.getTrackDrawable().setColorFilter(ActivityReminder.this.getResources().getColor(R.color.switchoff), PorterDuff.Mode.SRC_IN);
                }
            }
        });
        this.binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                ActivityReminder.this.m346x3b846370(view);
            }
        });
        checkSelfPermission = checkSelfPermission("android.permission.POST_NOTIFICATIONS");
        if (checkSelfPermission != 0) {
            requestPermissionNotification();
        }
        if (Build.VERSION.SDK_INT < 31 || ContextCompat.checkSelfPermission(this, "android.permission.SCHEDULE_EXACT_ALARM") == 0) {
            return;
        }
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.SCHEDULE_EXACT_ALARM"}, this.PERMISSION_REQUEST_CODE);
    }
    
    public  void m346x3b846370(View view) {
        if (this.reminderDateInMillis > 0) {
            if (this.binding.monday.isChecked()) {
                scheduleDailyReminder(generateNoteId(1L, 2), this.reminderDateInMillis, 2, this.binding.nameet.getText().toString());
            }
            if (this.binding.tuesday.isChecked()) {
                scheduleDailyReminder(generateNoteId(2L, 3), this.reminderDateInMillis, 3, this.binding.nameet.getText().toString());
            }
            if (this.binding.wednesday.isChecked()) {
                scheduleDailyReminder(generateNoteId(3L, 4), this.reminderDateInMillis, 4, this.binding.nameet.getText().toString());
            }
            if (this.binding.thurday.isChecked()) {
                scheduleDailyReminder(generateNoteId(4L, 5), this.reminderDateInMillis, 5, this.binding.nameet.getText().toString());
            }
            if (this.binding.friday.isChecked()) {
                scheduleDailyReminder(generateNoteId(5L, 6), this.reminderDateInMillis, 6, this.binding.nameet.getText().toString());
            }
            if (this.binding.saturday.isChecked()) {
                scheduleDailyReminder(generateNoteId(6L, 7), this.reminderDateInMillis, 7, this.binding.nameet.getText().toString());
            }
            if (this.binding.sunday.isChecked()) {
                scheduleDailyReminder(generateNoteId(7L, 1), this.reminderDateInMillis, 1, this.binding.nameet.getText().toString());
            }
        }
        if (this.binding.monday.isChecked() || this.binding.tuesday.isChecked() || this.binding.wednesday.isChecked() || this.binding.thurday.isChecked() || this.binding.friday.isChecked() || this.binding.saturday.isChecked() || this.binding.sunday.isChecked()) {
            startActivity(new Intent(this, (Class<?>) ActivityMain.class).putExtra("reminder", 3));
            saveCheckedState();
            saveSelectedTime();
            return;
        }
        Toast.makeText(this, getString(R.string.select_day), Toast.LENGTH_SHORT).show();
    }

    private void requestPermissionNotification() {
        ActivityCompat.requestPermissions(this, new String[]{"android.permission.POST_NOTIFICATIONS"}, this.PERMISSION_REQUEST_CODE_Notification);
    }

    @Override 
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == this.PERMISSION_REQUEST_CODE_Notification && iArr.length > 0) {
            int i2 = iArr[0];
        }
        if (i == this.PERMISSION_REQUEST_CODE) {
            if (iArr.length <= 0 || iArr[0] != 0) {
                Intent intent = new Intent("android.settings.REQUEST_SCHEDULE_EXACT_ALARM");
                intent.setData(Uri.fromParts("package", getPackageName(), null));
                startActivity(intent);
            }
        }
    }

    
    public boolean areNotificationsEnabled() {
        return NotificationManagerCompat.from(this).areNotificationsEnabled();
    }

    
    public void showPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.enable_notifications);
        builder.setMessage(R.string.notifications_are_currently_disabled_do_you_want_to_enable_them);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
                dialogInterface.dismiss();
                LockHolder.getInstance().setboolean(false);
                intent.putExtra("android.provider.extra.APP_PACKAGE", ActivityReminder.this.getPackageName());
                ActivityReminder.this.startActivity(intent);
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
            @Override 
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void scheduleDailyReminder(long j, long j2, int i, String str) {
        cancelExistingReminder(j);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(j2);
        calendar.add(6, ((i - calendar.get(7)) + 7) % 7);
        calendar.set(13, 0);
        calendar.set(14, 0);
        Intent intent = new Intent(this, (Class<?>) RemindersBroadcastReceiver.class);
        intent.putExtra("noteId", j);
        if (str.isEmpty()) {
            intent.putExtra(Param.KEY_CONTENT, getString(R.string.how_was_your_day));
        } else {
            intent.putExtra(Param.KEY_CONTENT, str);
        }
        intent.putExtra(Param.KEY_TITLE, getString(R.string.my_diary_time_to_write));
        intent.putExtra("check", 3);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int i2 = (int) j;
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(this, i2, intent, PendingIntent.FLAG_IMMUTABLE));
        } else if (Build.VERSION.SDK_INT >= 29) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(this, i2, intent, PendingIntent.FLAG_IMMUTABLE));
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), PendingIntent.getBroadcast(this, i2, intent, PendingIntent.FLAG_IMMUTABLE));
        }
        if (this.reminderSwitchState) {
            return;
        }
        cancelExistingReminder(j);
    }

    private void cancelExistingReminder(long j) {
        @SuppressLint("WrongConstant") PendingIntent broadcast = PendingIntent.getBroadcast(this, (int) j, new Intent(this, (Class<?>) RemindersBroadcastReceiver.class), 603979776);
        if (broadcast != null) {
            ((AlarmManager) getSystemService(Context.ALARM_SERVICE)).cancel(broadcast);
            broadcast.cancel();
        }
    }

    private void saveSelectedTime() {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putLong("selected_time", this.reminderDateInMillis);
        edit.apply();
    }

    private void loadSavedTime() {
        long j = this.sharedPreferences.getLong("selected_time", 0L);
        this.reminderDateInMillis = j;
        if (j > 0) {
            this.dateCalender.setTimeInMillis(j);
            updateButtonDate();
        }
    }

    private void saveCheckedState() {
        SharedPreferences.Editor edit = this.sharedPreferences.edit();
        edit.putBoolean("monday_checked", this.binding.monday.isChecked());
        edit.putBoolean("tuesday_checked", this.binding.tuesday.isChecked());
        edit.putBoolean("wednesday_checked", this.binding.wednesday.isChecked());
        edit.putBoolean("thursday_checked", this.binding.thurday.isChecked());
        edit.putBoolean("friday_checked", this.binding.friday.isChecked());
        edit.putBoolean("saturday_checked", this.binding.saturday.isChecked());
        edit.putBoolean("sunday_checked", this.binding.sunday.isChecked());
        edit.apply();
    }

    private void loadSavedPreferences() {
        this.binding.monday.setChecked(this.sharedPreferences.getBoolean("monday_checked", false));
        this.binding.tuesday.setChecked(this.sharedPreferences.getBoolean("tuesday_checked", false));
        this.binding.wednesday.setChecked(this.sharedPreferences.getBoolean("wednesday_checked", false));
        this.binding.thurday.setChecked(this.sharedPreferences.getBoolean("thursday_checked", false));
        this.binding.friday.setChecked(this.sharedPreferences.getBoolean("friday_checked", false));
        this.binding.saturday.setChecked(this.sharedPreferences.getBoolean("saturday_checked", false));
        this.binding.sunday.setChecked(this.sharedPreferences.getBoolean("sunday_checked", false));
    }

    public void ischeckedColor(final int i) {
        int color;
        AppCompatButton appCompatButton = this.binding.save;
        color = getColor(R.color.white);
        appCompatButton.setTextColor(color);
        this.binding.save.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.monday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m339xd9ca205c(i, compoundButton, z);
            }
        });
        this.binding.tuesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m340x31e759d(i, compoundButton, z);
            }
        });
        this.binding.wednesday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m341x2c72cade(i, compoundButton, z);
            }
        });
        this.binding.thurday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m342x55c7201f(i, compoundButton, z);
            }
        });
        this.binding.friday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m343x7f1b7560(i, compoundButton, z);
            }
        });
        this.binding.saturday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m344xa86fcaa1(i, compoundButton, z);
            }
        });
        this.binding.sunday.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { 
            @Override 
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                ActivityReminder.this.m345xd1c41fe2(i, compoundButton, z);
            }
        });
    }

    
    
    public  void m339xd9ca205c(int i, CompoundButton compoundButton, boolean z) {
        if (z) {
            this.binding.monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            this.binding.monday.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.binding.monday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
            this.binding.monday.setTextColor(getResources().getColor(R.color.black));
        }
    }

    
    
    public  void m340x31e759d(int i, CompoundButton compoundButton, boolean z) {
        int color;
        int color2;
        if (z) {
            this.binding.tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            ToggleButton toggleButton = this.binding.tuesday;
            color2 = getColor(R.color.white);
            toggleButton.setTextColor(color2);
            return;
        }
        this.binding.tuesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
        ToggleButton toggleButton2 = this.binding.tuesday;
        color = getColor(R.color.black);
        toggleButton2.setTextColor(color);
    }

    
    
    public  void m341x2c72cade(int i, CompoundButton compoundButton, boolean z) {
        int color;
        int color2;
        if (z) {
            this.binding.wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            ToggleButton toggleButton = this.binding.wednesday;
            color2 = getColor(R.color.white);
            toggleButton.setTextColor(color2);
            return;
        }
        this.binding.wednesday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
        ToggleButton toggleButton2 = this.binding.wednesday;
        color = getColor(R.color.black);
        toggleButton2.setTextColor(color);
    }

    
    
    public  void m342x55c7201f(int i, CompoundButton compoundButton, boolean z) {
        int color;
        int color2;
        if (z) {
            this.binding.thurday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            ToggleButton toggleButton = this.binding.thurday;
            color2 = getColor(R.color.white);
            toggleButton.setTextColor(color2);
            return;
        }
        this.binding.thurday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
        ToggleButton toggleButton2 = this.binding.thurday;
        color = getColor(R.color.black);
        toggleButton2.setTextColor(color);
    }

    
    
    public  void m343x7f1b7560(int i, CompoundButton compoundButton, boolean z) {
        int color;
        int color2;
        if (z) {
            this.binding.friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            ToggleButton toggleButton = this.binding.friday;
            color2 = getColor(R.color.white);
            toggleButton.setTextColor(color2);
            return;
        }
        this.binding.friday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
        ToggleButton toggleButton2 = this.binding.friday;
        color = getColor(R.color.black);
        toggleButton2.setTextColor(color);
    }

    
    
    public  void m344xa86fcaa1(int i, CompoundButton compoundButton, boolean z) {
        int color;
        int color2;
        if (z) {
            this.binding.saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            ToggleButton toggleButton = this.binding.saturday;
            color2 = getColor(R.color.white);
            toggleButton.setTextColor(color2);
            return;
        }
        this.binding.saturday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
        ToggleButton toggleButton2 = this.binding.saturday;
        color = getColor(R.color.black);
        toggleButton2.setTextColor(color);
    }

    
    
    public  void m345xd1c41fe2(int i, CompoundButton compoundButton, boolean z) {
        int color;
        if (z) {
            this.binding.sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
            this.binding.sunday.setTextColor(getResources().getColor(R.color.white));
        } else {
            this.binding.sunday.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.button_bg)));
            ToggleButton toggleButton = this.binding.sunday;
            color = getColor(R.color.black);
            toggleButton.setTextColor(color);
        }
    }

    
    public void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() { 
            @Override 
            public final void onTimeSet(TimePicker timePicker, int i, int i2) {
                ActivityReminder.this.m347xb5ed195(timePicker, i, i2);
            }
        }, this.dateCalender.get(11), this.dateCalender.get(12), false);
        timePickerDialog.show();
        Button button = timePickerDialog.getButton(-1);
        button.setTextColor(getResources().getColor(R.color.black));
        button.setBackgroundColor(0);
        Button button2 = timePickerDialog.getButton(-2);
        button2.setTextColor(getResources().getColor(R.color.black));
        button2.setBackgroundColor(0);
    }

    
    
    public  void m347xb5ed195(TimePicker timePicker, int i, int i2) {
        this.dateCalender.set(11, i);
        this.dateCalender.set(12, i2);
        this.dateCalender.set(13, 0);
        this.reminderDateInMillis = this.dateCalender.getTimeInMillis();
        updateButtonDate();
    }

    private void updateButtonDate() {
        this.binding.time.setText(new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(this.dateCalender.getTime()));
    }

    private void setStatusBarTransparentWhite() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(1280);
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void cardBackground(int i) {
        this.binding.setReminder.setCardBackgroundColor(getResources().getColor(i));
        this.binding.EditText.setCardBackgroundColor(getResources().getColor(i));
        this.binding.showTimePicker.setCardBackgroundColor(getResources().getColor(i));
        this.binding.repeatReminder.setCardBackgroundColor(getResources().getColor(i));
    }

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            ischeckedColor(R.color.green);
        }
        if (i == 1) {
            color6 = getColor(R.color.full_light_green);
            setbackgroundcolor(color6);
            ischeckedColor(R.color.green);
        } else if (i == 2) {
            color5 = getColor(R.color.full_light_pink);
            setbackgroundcolor(color5);
            ischeckedColor(R.color.pink);
        } else if (i == 3) {
            color4 = getColor(R.color.full_light_blue);
            setbackgroundcolor(color4);
            ischeckedColor(R.color.blue);
        } else if (i == 4) {
            color3 = getColor(R.color.full_light_purple);
            setbackgroundcolor(color3);
            ischeckedColor(R.color.purple);
        } else if (i == 5) {
            color2 = getColor(R.color.black);
            setbackgroundcolor(color2);
            ischeckedColor(R.color.purple);
        } else if (i == 6) {
            color = getColor(R.color.full_light_parrot);
            setbackgroundcolor(color);
            ischeckedColor(R.color.parrot);
        } else if (i == 7) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_7));
            ischeckedColor(R.color.themedark7);
        } else if (i == 8) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_8));
            ischeckedColor(R.color.themedark8);
        } else if (i == 9) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_9));
            ischeckedColor(R.color.themedark9);
        } else if (i == 10) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_10));
            ischeckedColor(R.color.themedark10);
        } else if (i == 11) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_11));
            ischeckedColor(R.color.themedark11);
        } else if (i == 12) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_12));
            ischeckedColor(R.color.themedark12);
        } else if (i == 13) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_13));
            ischeckedColor(R.color.themedark13);
        } else if (i == 14) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_14));
            ischeckedColor(R.color.themedark14);
        } else if (i == 15) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_15));
            ischeckedColor(R.color.themedark15);
        } else if (i == 16) {
            ischeckedColor(R.color.themedark16);
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.mainActivity.setBackground(getDrawable(R.drawable.theme_s_17));
            ischeckedColor(R.color.themedark17);
        }
        if (i == 5) {
            cardBackground(R.color.light_black);
        } else {
            cardBackground(R.color.white);
        }
        if (i == 5) {
            textcolors(R.color.white);
            setStatusBarTransparentWhite();
        } else {
            if (i == 15 || i == 16 || i == 17) {
                textcolors(R.color.black);
                this.binding.fdname.setTextColor(getResources().getColor(R.color.white));
                this.binding.back.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                setStatusBarTransparentWhite();
                return;
            }
            textcolors(R.color.black);
            setStatusBarTransparentBlack();
        }
    }

    public void isSwitchColor(int i) {
        this.binding.switch3.getTrackDrawable().setColorFilter(getResources().getColor(i), PorterDuff.Mode.SRC_IN);
    }

    public void setthemeSwitch() {
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            isSwitchColor(R.color.green);
        }
        if (i == 1) {
            isSwitchColor(R.color.green);
            return;
        }
        if (i == 2) {
            isSwitchColor(R.color.pink);
            return;
        }
        if (i == 3) {
            isSwitchColor(R.color.blue);
            return;
        }
        if (i == 4) {
            isSwitchColor(R.color.purple);
            return;
        }
        if (i == 5) {
            isSwitchColor(R.color.purple);
            return;
        }
        if (i == 6) {
            isSwitchColor(R.color.parrot);
            return;
        }
        if (i == 7) {
            isSwitchColor(R.color.themedark7);
            return;
        }
        if (i == 8) {
            isSwitchColor(R.color.themedark8);
            return;
        }
        if (i == 9) {
            isSwitchColor(R.color.themedark9);
            return;
        }
        if (i == 10) {
            isSwitchColor(R.color.themedark10);
            return;
        }
        if (i == 11) {
            isSwitchColor(R.color.themedark11);
            return;
        }
        if (i == 12) {
            isSwitchColor(R.color.themedark12);
            return;
        }
        if (i == 13) {
            isSwitchColor(R.color.themedark13);
            return;
        }
        if (i == 14) {
            isSwitchColor(R.color.themedark14);
            return;
        }
        if (i == 15) {
            isSwitchColor(R.color.themedark15);
        } else if (i == 16) {
            isSwitchColor(R.color.themedark16);
        } else if (i == 17) {
            isSwitchColor(R.color.themedark17);
        }
    }

    public void textcolors(int i) {
        this.binding.notification.setTextColor(getResources().getColor(i));
        this.binding.fdname.setTextColor(getResources().getColor(i));
        this.binding.content.setTextColor(getResources().getColor(i));
        this.binding.nameet.setTextColor(getResources().getColor(i));
        this.binding.nameet.setHintTextColor(getResources().getColor(i));
        this.binding.setTime.setTextColor(getResources().getColor(i));
        this.binding.time.setTextColor(getResources().getColor(i));
        this.binding.repeat.setTextColor(getResources().getColor(i));
        this.binding.back.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void setbackgroundcolor(int i) {
        this.binding.mainActivity.setBackgroundColor(i);
    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        LockHolder.getInstance().setboolean(false);
        this.isBackpressed = false;
        finish();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
        }
    }

    
    @Override 
    public void onStart() {
        super.onStart();
        boolean z = LockHolder.getInstance().getboolean();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z && this.isActivityVisible) {
            new PatternDialog(this).showDialog();
        }
        LockHolder.getInstance().setboolean(true);
        this.isActivityVisible = false;
    }

    
    @Override 
    public void onStop() {
        super.onStop();
        this.isActivityVisible = true;
    }
}
