package com.demo.todolistapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.todolistapp.R;
import com.demo.todolistapp.AdapterClasses.AdapterTODO;
import com.demo.todolistapp.AdapterClasses.NotesAdapter;
import com.demo.todolistapp.DatabaseClasses.MyHelperDb;
import com.demo.todolistapp.DatabaseClasses.TaskHelper;
import com.demo.todolistapp.Listeners.onTaskChanges;
import com.demo.todolistapp.Lock.LockHolder;
import com.demo.todolistapp.Lock.PatternDialog;
import com.demo.todolistapp.Lock.SharedPrefrence;
import com.demo.todolistapp.Models.Notes;
import com.demo.todolistapp.Models.TODOModels;
import com.demo.todolistapp.databinding.ArchivedActivityBinding;
import java.util.ArrayList;
import java.util.Iterator;


public class ActivityArchived extends AppCompatActivity {
    NotesAdapter adapter;
    ArchivedActivityBinding binding;
    MyHelperDb db;
    ArrayList<Notes> list;
    int theme;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    
    public void onchange() {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        final PopupMenu popupMenu;
        super.onCreate(bundle);
        ArchivedActivityBinding inflate = ArchivedActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);

        settheme();
        this.db = new MyHelperDb(this);
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivityArchived.this.onBackPressed();
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
                            ActivityArchived.this.fetchDataTask();
                            return true;
                        }
                        if (itemId != R.id.notes) {
                            return false;
                        }
                        ActivityArchived.this.fetchData();
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
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_7));
        } else if (i == 8) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_8));
        } else if (i == 9) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_9));
        } else if (i == 10) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_10));
        } else if (i == 11) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_11));
        } else if (i == 12) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_12));
        } else if (i == 13) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_13));
        } else if (i == 14) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_14));
        } else if (i == 15) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_15));
        } else if (i == 16) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.archivedActivity.setBackground(getDrawable(R.drawable.theme_s_17));
        }
        int i2 = this.theme;
        if (i2 == 5 || i2 == 15 || i2 == 16 || i2 == 17) {
            color2 = getColor(R.color.white);
            toggle(color2);
        } else {
            color3 = getColor(R.color.black);
            toggle(color3);
        }
    }

    public void setbackgroundcolor(int i) {
        this.binding.archivedActivity.setBackgroundColor(i);
    }

    public void toggle(int i) {
        this.binding.back.setImageTintList(ColorStateList.valueOf(i));
        this.binding.menu.setImageTintList(ColorStateList.valueOf(i));
        this.binding.pageTitle.setTextColor(i);
    }

    public void fetchData() {
        this.list = new ArrayList<>();
        Iterator<Notes> it = this.db.getAllArchived().iterator();
        while (it.hasNext()) {
            this.list.add(it.next());
        }
        this.adapter = new NotesAdapter(this.list, this, false) { 
            @Override 
            public void onArchived(Notes notes) {
            }

            @Override 
            public void onDeleteButton(Notes notes) {
                ActivityArchived.this.db.deleteArchivedById(notes.getId());
                ActivityArchived.this.db.addNotes(notes);
                ActivityArchived.this.fetchData();
            }

            @Override 
            public void onDeleteTrash(Notes notes) {
                ActivityArchived.this.db.deleteArchivedById(notes.getId());
                ActivityArchived.this.db.addTrash(notes);
                ActivityArchived.this.fetchData();
            }
        };
        this.binding.recyclerViewArchived.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerViewArchived.setAdapter(this.adapter);
    }

    public void fetchDataTask() {
        final TaskHelper taskHelper = new TaskHelper(this);
        ArrayList arrayList = new ArrayList();
        Iterator<TODOModels> it = taskHelper.getAllTaskArchived().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next());
        }
        AdapterTODO adapterTODO = new AdapterTODO(taskHelper, this, arrayList, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                ActivityArchived.this.onchange();
            }
        }, false) { 
            @Override 
            public void onArchived(TODOModels tODOModels) {
            }

            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                taskHelper.deleteTaskArchived(tODOModels.getId());
                taskHelper.addTask(tODOModels);
                ActivityArchived.this.fetchData();
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                taskHelper.deleteTaskArchived(tODOModels.getId());
                taskHelper.addTaskTrash(tODOModels);
                ActivityArchived.this.fetchData();
            }
        };
        this.binding.recyclerViewArchived.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerViewArchived.setAdapter(adapterTODO);
    }

    @Override 
    public void onBackPressed() {
        this.isBackpressed = false;
        setResult(-1, new Intent());
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
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z) {
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
