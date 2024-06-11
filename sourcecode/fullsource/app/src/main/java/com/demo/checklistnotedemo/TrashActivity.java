package com.demo.checklistnotedemo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.CursorWindow;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.checklistnotedemo.R;
import com.demo.checklistnotedemo.AdapterClasses.AdapterTODO;
import com.demo.checklistnotedemo.AdapterClasses.NotesAdapter;
import com.demo.checklistnotedemo.DatabaseClasses.MyHelperDb;
import com.demo.checklistnotedemo.DatabaseClasses.TaskHelper;
import com.demo.checklistnotedemo.Listeners.onTaskChanges;
import com.demo.checklistnotedemo.Lock.LockHolder;
import com.demo.checklistnotedemo.Lock.PatternDialog;
import com.demo.checklistnotedemo.Lock.SharedPrefrence;
import com.demo.checklistnotedemo.Models.Notes;
import com.demo.checklistnotedemo.Models.TODOModels;
import com.demo.checklistnotedemo.databinding.ActivityTrashBinding;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;


public class TrashActivity extends AppCompatActivity {
    NotesAdapter adapter;
    ActivityTrashBinding binding;
    MyHelperDb db;
    ArrayList<Notes> list;
    int theme;
    boolean a = true;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    
    public void onchange() {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        final PopupMenu popupMenu;
        super.onCreate(bundle);
        ActivityTrashBinding inflate = ActivityTrashBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());


        settheme();
        this.db = new MyHelperDb(this);
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                TrashActivity.this.onBackPressed();
            }
        });
        if (this.theme == 5) {
            popupMenu = new PopupMenu(this, this.binding.menu, GravityCompat.END, R.style.PopupMenuStyleBlack, R.style.PopupMenuStyleBlack);
        } else {
            popupMenu = new PopupMenu(this, this.binding.menu, GravityCompat.END, R.style.PopupMenuStylewhite, R.style.PopupMenuStylewhite);
        }

        popupMenu.getMenuInflater().inflate(R.menu.custom_menu, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();
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
        fetchDataTask();
        this.binding.menu.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
                    @Override 
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.task) {
                            TrashActivity.this.fetchDataTask();
                            return true;
                        }
                        if (itemId != R.id.notes) {
                            return false;
                        }
                        TrashActivity.this.fetchData();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int color7;
        int color8;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 1 || i == 0) {
            color = getColor(R.color.full_light_green);
            setbackgroundcolor(color);
        } else if (i == 2) {
            color8 = getColor(R.color.full_light_pink);
            setbackgroundcolor(color8);
        } else if (i == 3) {
            color7 = getColor(R.color.full_light_blue);
            setbackgroundcolor(color7);
        } else if (i == 4) {
            color6 = getColor(R.color.full_light_purple);
            setbackgroundcolor(color6);
        } else if (i == 5) {
            color5 = getColor(R.color.black);
            setbackgroundcolor(color5);
        } else if (i == 6) {
            color4 = getColor(R.color.full_light_parrot);
            setbackgroundcolor(color4);
        } else if (i == 7) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_7));
        } else if (i == 8) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_8));
        } else if (i == 9) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_9));
        } else if (i == 10) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_10));
        } else if (i == 11) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_11));
        } else if (i == 12) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_12));
        } else if (i == 13) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_13));
        } else if (i == 14) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_14));
        } else if (i == 15) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_15));
        } else if (i == 16) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.trashActivity.setBackground(getDrawable(R.drawable.theme_s_17));
        }
        int i2 = this.theme;
        if (i2 == 5 || i2 == 15 || i2 == 16 || i2 == 17) {
            color2 = getColor(R.color.white);
            toggle(color2);
            setStatusBarTransparentWhite();
        } else {
            color3 = getColor(R.color.black);
            toggle(color3);
            setStatusBarTransparentBlack();
        }
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

    public void setbackgroundcolor(int i) {
        this.binding.trashActivity.setBackgroundColor(i);
    }

    public void toggle(int i) {
        this.binding.back.setImageTintList(ColorStateList.valueOf(i));
        this.binding.menu.setImageTintList(ColorStateList.valueOf(i));
        this.binding.pageTitle.setTextColor(i);
    }

    public void fetchDataTask() {
        final TaskHelper taskHelper = new TaskHelper(this);
        ArrayList arrayList = new ArrayList();
        Iterator<TODOModels> it = taskHelper.getAllTaskTrash().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        AdapterTODO adapterTODO = new AdapterTODO(taskHelper, this, arrayList, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                TrashActivity.this.onchange();
            }
        }, false) { 
            @Override 
            public void onArchived(TODOModels tODOModels) {
            }

            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                taskHelper.deleteTaskTrash(tODOModels.getId());
                taskHelper.addTask(tODOModels);
                TrashActivity.this.fetchDataTask();
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                taskHelper.deleteTaskTrash(tODOModels.getId());
                TrashActivity.this.fetchDataTask();
            }
        };
        this.binding.recyclerViewTrash.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerViewTrash.setAdapter(adapterTODO);
    }

    public void fetchData() {
        this.list = new ArrayList<>();
        Iterator<Notes> it = this.db.getAllTrash().iterator();
        while (it.hasNext()) {
            this.list.add(it.next());
        }
        this.adapter = new NotesAdapter(this.list, this, false) { 
            @Override 
            public void onArchived(Notes notes) {
            }

            @Override 
            public void onDeleteButton(Notes notes) {
                TrashActivity.this.db.deleteTrashById(notes.getId());
                TrashActivity.this.db.addNotes(notes);
                TrashActivity.this.fetchData();
            }

            @Override 
            public void onDeleteTrash(Notes notes) {
                TrashActivity.this.db.deleteTrashById(notes.getId());
                TrashActivity.this.fetchData();
            }
        };
        this.binding.recyclerViewTrash.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerViewTrash.setAdapter(this.adapter);
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
