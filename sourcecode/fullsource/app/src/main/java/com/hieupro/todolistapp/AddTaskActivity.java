package com.hieupro.todolistapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hieupro.todolistapp.AdapterClasses.CategoryAdapter;
import com.hieupro.todolistapp.DataHolderClasses.HolderTODODialog;
import com.hieupro.todolistapp.DatabaseClasses.CategoryHelper;
import com.hieupro.todolistapp.DatabaseClasses.TaskHelper;
import com.hieupro.todolistapp.Listeners.Category;
import com.hieupro.todolistapp.Lock.LockHolder;
import com.hieupro.todolistapp.Lock.PatternDialog;
import com.hieupro.todolistapp.Lock.SharedPrefrence;
import com.hieupro.todolistapp.Models.CategoryModel;
import com.hieupro.todolistapp.Models.TODOModels;
import com.hieupro.todolistapp.ReminderClasses.RemindersBroadcastReceiver;
import com.hieupro.todolistapp.databinding.ActivityAddTaskBinding;
import com.hieupro.todolistapp.paramsClasses.Param;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class AddTaskActivity extends AppCompatActivity {
    CategoryAdapter adapter;
    ActivityAddTaskBinding binding;
    private Calendar calendar1;
    TaskHelper db;
    CategoryHelper dbCt;
    private String docId;
    List<CategoryModel> list;
    TODOModels model1;
    TextView ok;
    long reminderDateInMillis;
    int theme;
    long timesTamp;
    private boolean isEditMode = false;
    int status = -1;
    private boolean isMarkedAsDone = false;
    long categoryName = -1;
    long calenderTime = 0;
    private Calendar calendar = Calendar.getInstance();
    private boolean dateChanged = false;
    boolean showDialog = false;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    
    @Override 
    public void onCreate(Bundle bundle) {
        final PopupMenu popupMenu;
        int color;
        int color2;
        super.onCreate(bundle);
        ActivityAddTaskBinding inflate = ActivityAddTaskBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);

        this.calendar1 = Calendar.getInstance();
        this.theme = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        setStatusBarTransparentBlack();
        settheme();
        this.db = new TaskHelper(this);
        this.dbCt = new CategoryHelper(this);
        this.docId = getIntent().getStringExtra("docId");
        updateButtonDate();
        fetchData(this);
        this.binding.taskNameet.requestFocus();
        String str = this.docId;
        if (str != null && !str.isEmpty()) {
            this.binding.taskNameet.post(new Runnable() { 
                @Override 
                public void run() {
                    AddTaskActivity.this.binding.taskNameet.setSelection(AddTaskActivity.this.binding.taskNameet.getText().length());
                    AddTaskActivity.this.binding.taskNameet.requestFocus();
                }
            });
            this.isEditMode = true;
            this.calendar1 = Calendar.getInstance();
            TODOModels taskById = this.db.getTaskById(Long.parseLong(this.docId));
            this.model1 = taskById;
            this.adapter.setCategoryNameAndSelection(taskById.getCategoryId(), false);
            if (this.model1.getStatus() == 1) {
                FrameLayout frameLayout = this.binding.frameLayout;
                color2 = getColor(R.color.framegrey);
                frameLayout.setBackgroundColor(color2);
                isEnabled(false);
            } else {
                FrameLayout frameLayout2 = this.binding.frameLayout;
                color = getColor(android.R.color.transparent);
                frameLayout2.setBackgroundColor(color);
                isEnabled(true);
            }
            this.binding.taskNameet.setText(this.model1.getTask());
            this.binding.descriptionet.setText(this.model1.getDescription());
            this.calendar1.setTimeInMillis(this.model1.getTimestamp());
            updateButtonDate();
            if (this.model1.getReminder() > 0) {
                this.calendar.setTimeInMillis(this.model1.getReminder());
                updateReminderTxt();
            }
        }
        long longExtra = getIntent().getLongExtra("date", 0L);
        this.calenderTime = longExtra;
        if (longExtra != 0) {
            this.calendar1.setTimeInMillis(longExtra);
            updateButtonDate();
        }
        this.binding.createCt.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddTaskActivity.this.addCategory();
            }
        });
        if (this.theme == 5) {
            popupMenu = new PopupMenu(this, this.binding.menu, GravityCompat.END, R.style.PopupMenuStyleBlack, R.style.PopupMenuStyleBlack);
        } else {
            popupMenu = new PopupMenu(this, this.binding.menu, GravityCompat.END, R.style.PopupMenuStylewhite, R.style.PopupMenuStylewhite);
        }
        popupMenu.getMenuInflater().inflate(R.menu.status_delete_menu, popupMenu.getMenu());
        MenuItem findItem = popupMenu.getMenu().findItem(R.id.asDone);
        if (this.isEditMode) {
            if (this.model1.getStatus() == 1) {
                findItem.setTitle(R.string.mark_as_undone);
                this.status = 1;
                this.isMarkedAsDone = true;
            } else {
                findItem.setTitle(R.string.mark_as_done);
                this.status = 0;
                this.isMarkedAsDone = false;
            }
        } else {
            findItem.setTitle(getString(R.string.mark_as_done));
            this.isMarkedAsDone = false;
        }
        final Menu menu = popupMenu.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spannableString = new SpannableString(item.getTitle());
            if (this.theme == 5) {
                spannableString.setSpan(new ForegroundColorSpan(-1), 0, spannableString.length(), 18);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(ViewCompat.MEASURED_STATE_MASK), 0, spannableString.length(), 18);
            }
            item.setTitle(spannableString);
        }
        this.binding.menu.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
                    @Override 
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.asDone) {
                            if (!AddTaskActivity.this.isMarkedAsDone) {
                                menuItem.setTitle(R.string.mark_as_undone);
                                AddTaskActivity.this.status = 1;
                                AddTaskActivity.this.isEnabled(false);
                                AddTaskActivity.this.binding.frameLayout.setBackgroundColor(AddTaskActivity.this.getResources().getColor(R.color.framegrey));
                            } else {
                                menuItem.setTitle(R.string.mark_as_done);
                                AddTaskActivity.this.status = 0;
                                AddTaskActivity.this.isEnabled(true);
                                AddTaskActivity.this.binding.frameLayout.setBackgroundColor(AddTaskActivity.this.getResources().getColor(android.R.color.transparent));
                            }
                            AddTaskActivity.this.isMarkedAsDone = !AddTaskActivity.this.isMarkedAsDone;
                            for (int i2 = 0; i2 < menu.size(); i2++) {
                                MenuItem item2 = menu.getItem(i2);
                                SpannableString spannableString2 = new SpannableString(item2.getTitle());
                                if (AddTaskActivity.this.theme == 5) {
                                    spannableString2.setSpan(new ForegroundColorSpan(-1), 0, spannableString2.length(), 18);
                                } else {
                                    spannableString2.setSpan(new ForegroundColorSpan(ViewCompat.MEASURED_STATE_MASK), 0, spannableString2.length(), 18);
                                }
                                item2.setTitle(spannableString2);
                            }
                            popupMenu.dismiss();
                            return true;
                        }
                        if (itemId != R.id.delete) {
                            return false;
                        }
                        if (AddTaskActivity.this.isEditMode) {
                            AddTaskActivity.this.DeleteTask(Long.parseLong(AddTaskActivity.this.docId));
                        } else {
                            AddTaskActivity.this.DeleteTask(-1L);
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        this.binding.saveTask.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddTaskActivity.this.saveTask();
                AddTaskActivity.this.closeKeyboard();
                LockHolder.getInstance().setboolean(false);
                AddTaskActivity.this.showDialog = true;
            }
        });
        this.binding.datebtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddTaskActivity.this.showDatePicker();
                AddTaskActivity.this.closeKeyboard();
            }
        });
        this.binding.reminder.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (AddTaskActivity.this.areNotificationsEnabled()) {
                    AddTaskActivity.this.showDateAndTimePicker();
                    AddTaskActivity.this.closeKeyboard();
                } else {
                    AddTaskActivity.this.showPermissionDialog();
                }
            }
        });
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AddTaskActivity.this.onBackPressed();
            }
        });
    }

    public void DeleteTask(final long j) {
        if (j == -1) {
            finish();
        } else {
            new AlertDialog.Builder(this).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialogInterface, int i) {
                    AddTaskActivity.this.db.addTaskTrash(AddTaskActivity.this.db.getTaskById(j));
                    AddTaskActivity.this.db.deleteTask(j);
                    AddTaskActivity.this.finish();
                }
            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                @Override 
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        }
    }

    public void isEnabled(boolean z) {
        if (z) {
            this.binding.datebtn.setEnabled(true);
            this.binding.reminder.setEnabled(true);
            this.binding.createCt.setEnabled(true);
        } else {
            this.binding.datebtn.setEnabled(false);
            this.binding.reminder.setEnabled(false);
            this.binding.createCt.setEnabled(false);
        }
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    
    public void addCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_box_category, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        this.ok = (TextView) inflate.findViewById(R.id.yes);
        TextView textView = (TextView) inflate.findViewById(R.id.no);
        final EditText editText = (EditText) inflate.findViewById(R.id.categoryName);
        settheme();
        this.ok.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setName(editText.getText().toString());
                long addCategory = AddTaskActivity.this.dbCt.addCategory(categoryModel);
                AddTaskActivity addTaskActivity = AddTaskActivity.this;
                addTaskActivity.fetchData(addTaskActivity);
                AddTaskActivity.this.adapter.setCategoryNameAndSelection(addCategory, true);
                create.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                create.dismiss();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void fetchData(Context context) {
        List<CategoryModel> allCategory = this.dbCt.getAllCategory();
        this.list = allCategory;
        if (allCategory == null || allCategory.isEmpty()) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setName(getString(R.string.none));
            this.dbCt.addCategory(categoryModel);
            CategoryModel categoryModel2 = new CategoryModel();
            categoryModel2.setName(getString(R.string.work));
            this.dbCt.addCategory(categoryModel2);
            CategoryModel categoryModel3 = new CategoryModel();
            categoryModel3.setName(getString(R.string.personal));
            this.dbCt.addCategory(categoryModel3);
            CategoryModel categoryModel4 = new CategoryModel();
            categoryModel4.setName(getString(R.string.wishlist));
            this.dbCt.addCategory(categoryModel4);
            CategoryModel categoryModel5 = new CategoryModel();
            categoryModel5.setName(getString(R.string.birthday));
            this.dbCt.addCategory(categoryModel5);
            fetchData(this);
        }
        this.adapter = new CategoryAdapter(this, this.list, true, new Category() { 
            @Override 
            public final void category(CategoryModel categoryModel6) {
                AddTaskActivity.this.categoryName(categoryModel6);
            }
        });
        this.binding.catagoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        this.binding.catagoryRecyclerView.setAdapter(this.adapter);
    }

    
    public void categoryName(CategoryModel categoryModel) {
        this.categoryName = categoryModel.getId();
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
                intent.putExtra("android.provider.extra.APP_PACKAGE", AddTaskActivity.this.getPackageName());
                AddTaskActivity.this.startActivity(intent);
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

    
    public void saveTask() {
        final String obj = this.binding.taskNameet.getText().toString();
        String obj2 = this.binding.descriptionet.getText().toString();
        final TODOModels tODOModels = new TODOModels();
        tODOModels.setTask(obj);
        tODOModels.setDescription(obj2);
        long j = this.calenderTime;
        if (j != 0) {
            tODOModels.setTimestamp(j);
        } else if (this.dateChanged) {
            tODOModels.setTimestamp(this.timesTamp);
        } else {
            tODOModels.setTimestamp(System.currentTimeMillis());
        }
        long j2 = this.reminderDateInMillis;
        if (j2 > 0) {
            tODOModels.setReminder(j2);
        }
        long j3 = this.categoryName;
        if (j3 >= 0) {
            tODOModels.setCategoryId(j3);
        } else {
            TODOModels tODOModels2 = this.model1;
            if (tODOModels2 != null && tODOModels2.getCategoryId() >= 0) {
                tODOModels.setCategoryId(this.model1.getCategoryId());
            }
        }
        int i = this.status;
        if (i != -1) {
            tODOModels.setStatus(i);
        } else {
            tODOModels.setStatus(0);
        }
        this.binding.animationlayout.setVisibility(View.VISIBLE);
        this.binding.animation.setAnimation("done.json");
        this.binding.animation.playAnimation();
        this.binding.animation.addAnimatorListener(new AnimatorListenerAdapter() { 
            @Override 
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (AddTaskActivity.this.isEditMode) {
                    tODOModels.setId(Long.parseLong(AddTaskActivity.this.docId));
                    if (AddTaskActivity.this.db.updateTask(tODOModels) > 0) {
                        AddTaskActivity addTaskActivity = AddTaskActivity.this;
                        Toast.makeText(addTaskActivity, addTaskActivity.getString(R.string.update_task), Toast.LENGTH_SHORT).show();
                        if (AddTaskActivity.this.reminderDateInMillis > 0) {
                            tODOModels.setReminder(AddTaskActivity.this.reminderDateInMillis);
                        } else if (AddTaskActivity.this.model1.getReminder() > 0) {
                            tODOModels.setReminder(AddTaskActivity.this.model1.getReminder());
                        }
                        if (AddTaskActivity.this.reminderDateInMillis > 0) {
                            AddTaskActivity.this.setReminder(tODOModels.getId(), AddTaskActivity.this.reminderDateInMillis, obj);
                        }
                    }
                } else {
                    long addTask = AddTaskActivity.this.db.addTask(tODOModels);
                    if (AddTaskActivity.this.reminderDateInMillis > 0) {
                        AddTaskActivity addTaskActivity2 = AddTaskActivity.this;
                        addTaskActivity2.setReminder(addTask, addTaskActivity2.reminderDateInMillis, obj);
                    }
                    HolderTODODialog.getInstance().setVariable(true);
                }
                AddTaskActivity.this.startActivity(new Intent(AddTaskActivity.this, (Class<?>) ActivityMain.class));
                AddTaskActivity.this.finishAffinity();
            }
        });
    }

    
    public void setReminder(long j, long j2, String str) {
        PendingIntent broadcast;
        int checkSelfPermission;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, (Class<?>) RemindersBroadcastReceiver.class);
        intent.putExtra("noteId", j);
        intent.putExtra(Param.KEY_TITLE, str);
        intent.putExtra("check", 1);
        if (Build.VERSION.SDK_INT >= 31) {
            checkSelfPermission = checkSelfPermission("android.permission.SCHEDULE_EXACT_ALARM");
            if (checkSelfPermission != 0) {
                return;
            } else {
                broadcast = PendingIntent.getBroadcast(this, (int) j, intent, PendingIntent.FLAG_MUTABLE);
            }
        } else {
            broadcast = PendingIntent.getBroadcast(this, (int) j, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, j2, broadcast);
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, j2, broadcast);
        }
    }

    
    public void showDateAndTimePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() { 
            @Override 
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                AddTaskActivity.this.m355xd57ccab4(datePicker, i, i2, i3);
            }
        }, this.calendar.get(1), this.calendar.get(2), this.calendar.get(5));
        datePickerDialog.show();
        Button button = datePickerDialog.getButton(-1);
        button.setTextColor(getResources().getColor(R.color.black));
        button.setBackgroundColor(0);
        Button button2 = datePickerDialog.getButton(-2);
        button2.setTextColor(getResources().getColor(R.color.black));
        button2.setBackgroundColor(0);
    }

    
    
    public  void m355xd57ccab4(DatePicker datePicker, int i, int i2, int i3) {
        this.calendar.set(1, i);
        this.calendar.set(2, i2);
        this.calendar.set(5, i3);
        showTimePicker();
    }

    public void updateReminderTxt() {
        this.binding.reminderTime.setText(new SimpleDateFormat("MMM dd,yyyy  hh:mm", Locale.getDefault()).format(this.calendar.getTime()));
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.TimePickerTheme, new TimePickerDialog.OnTimeSetListener() { 
            @Override 
            public final void onTimeSet(TimePicker timePicker, int i, int i2) {
                AddTaskActivity.this.m358x2f3d7d28(timePicker, i, i2);
            }
        }, this.calendar.get(11), this.calendar.get(12), false);
        timePickerDialog.show();
        Button button = timePickerDialog.getButton(-1);
        button.setTextColor(getResources().getColor(R.color.black));
        button.setBackgroundColor(0);
        Button button2 = timePickerDialog.getButton(-2);
        button2.setTextColor(getResources().getColor(R.color.black));
        button2.setBackgroundColor(0);
    }

    
    
    public  void m358x2f3d7d28(TimePicker timePicker, int i, int i2) {
        this.calendar.set(11, i);
        this.calendar.set(12, i2);
        this.calendar.set(13, 0);
        this.reminderDateInMillis = this.calendar.getTimeInMillis();
        updateReminderTxt();
    }

    
    public void closeKeyboard() {
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    
    public void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth, new DatePickerDialog.OnDateSetListener() {
            @Override 
            public final void onDateSet(DatePicker datePicker, int i, int i2, int i3) {
                AddTaskActivity.this.m356x2345fa86(datePicker, i, i2, i3);
            }
        }, this.calendar1.get(1), this.calendar1.get(2), this.calendar1.get(5));
        datePickerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { 
            @Override 
            public final void onDismiss(DialogInterface dialogInterface) {
                AddTaskActivity.this.m357x3d617925(dialogInterface);
            }
        });
        datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        datePickerDialog.show();
    }

    
    
    public  void m356x2345fa86(DatePicker datePicker, int i, int i2, int i3) {
        this.calendar1.set(1, i);
        this.calendar1.set(2, i2);
        this.calendar1.set(5, i3);
        this.dateChanged = true;
        this.timesTamp = this.calendar1.getTimeInMillis();
        updateButtonDate();
    }

    
    
    public  void m357x3d617925(DialogInterface dialogInterface) {
        if (this.dateChanged) {
            return;
        }
        this.dateChanged = false;
    }

    private void updateButtonDate() {
        this.binding.date.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(this.calendar1.getTime()));
    }

    public void settheme() {
        int color;
        int color2;
        int i = this.theme;
        if (i == 1) {
            addButton(R.color.light_green);
            savebutton(R.color.green);
        } else if (i == 2) {
            savebutton(R.color.pink);
            addButton(R.color.light_pink);
        } else if (i == 3) {
            addButton(R.color.light_blue);
            savebutton(R.color.blue);
        } else if (i == 4) {
            addButton(R.color.light_purple);
            savebutton(R.color.purple);
        } else if (i == 5) {
            addButton(R.color.light_purple);
            savebutton(R.color.purple);
        } else if (i == 6) {
            addButton(R.color.light_parrot);
            savebutton(R.color.parrot);
        } else if (i == 7) {
            addButton(R.color.themelight7);
            savebutton(R.color.themedark7);
        } else if (i == 8) {
            addButton(R.color.themelight8);
            savebutton(R.color.themedark8);
        } else if (i == 9) {
            addButton(R.color.themelight9);
            savebutton(R.color.themedark9);
        } else if (i == 10) {
            addButton(R.color.themelight10);
            savebutton(R.color.themedark10);
        } else if (i == 11) {
            addButton(R.color.themelight11);
            savebutton(R.color.themedark11);
        } else if (i == 12) {
            addButton(R.color.themelight12);
            savebutton(R.color.themedark12);
        } else if (i == 13) {
            addButton(R.color.themelight13);
            savebutton(R.color.themedark13);
        } else if (i == 14) {
            addButton(R.color.themelight14);
            savebutton(R.color.themedark14);
        } else if (i == 15) {
            addButton(R.color.themelight15);
            savebutton(R.color.themedark15);
        } else if (i == 16) {
            addButton(R.color.themelight16);
            savebutton(R.color.themedark16);
        } else if (i == 17) {
            addButton(R.color.themelight17);
            savebutton(R.color.themedark17);
        }
        if (this.theme == 5) {
            RelativeLayout relativeLayout = this.binding.activity;
            color2 = getColor(R.color.black);
            relativeLayout.setBackgroundColor(color2);
            textcolor(R.color.white);
            image(R.color.white);
            return;
        }
        RelativeLayout relativeLayout2 = this.binding.activity;
        color = getColor(R.color.white);
        relativeLayout2.setBackgroundColor(color);
        textcolor(R.color.black);
        image(R.color.brown);
    }

    public void addButton(int i) {
        this.binding.date.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.reminderTime.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.createCt.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void savebutton(int i) {
        this.binding.saveTask.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        TextView textView = this.ok;
        if (textView != null) {
            textView.setTextColor(getResources().getColor(i));
        }
    }

    public void textcolor(int i) {
        this.binding.back.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.menu.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.taskNametxt.setTextColor(getResources().getColor(i));
        this.binding.selectCatagory.setTextColor(getResources().getColor(i));
        this.binding.descriptiontxt.setTextColor(getResources().getColor(i));
    }

    public void image(int i) {
        this.binding.dateimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.clockimg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.descriptionet.setTextColor(getResources().getColor(i));
        this.binding.descriptionet.setHintTextColor(getResources().getColor(i));
        this.binding.taskNameet.setTextColor(getResources().getColor(i));
        this.binding.taskNameet.setHintTextColor(getResources().getColor(i));
        this.binding.datetxt.setTextColor(getResources().getColor(i));
        this.binding.remindertxt.setTextColor(getResources().getColor(i));
    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        this.isBackpressed = false;
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
        }
        if (this.showDialog) {
            LockHolder.getInstance().setboolean(false);
            this.showDialog = !this.showDialog;
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
