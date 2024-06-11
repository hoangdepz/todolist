package com.demo.checklistnotedemo;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
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
import com.demo.checklistnotedemo.databinding.SearchActivityBinding;
import java.util.ArrayList;
import java.util.List;


public class ActivitySearch extends AppCompatActivity {
    AdapterTODO adapter;
    NotesAdapter adapter1;
    SearchActivityBinding binding;
    boolean isTrue;
    List<TODOModels> list = new ArrayList();
    List<Notes> list1 = new ArrayList();
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    
    public void onchange() {
    }

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        SearchActivityBinding inflate = SearchActivityBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());





        this.isTrue = getIntent().getBooleanExtra("true", false);
        Drawable drawable = getResources().getDrawable(R.drawable.search_icon);
        drawable.setBounds(0, 0, 34, 34);
        this.binding.searchedittext.setCompoundDrawables(null, null, drawable, null);
        this.binding.searchedittext.setCompoundDrawables(null, null, drawable, null);
        settheme();
        if (this.isTrue) {
            Task();
        } else {
            Notes();
        }
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                ActivitySearch.this.onBackPressed();
            }
        });
    }

    public void Notes() {
        this.binding.pageTitle.setText(getString(R.string.notes));
        fetchDataNotes();
        this.binding.searchedittext.addTextChangedListener(new TextWatcher() { 
            @Override 
            public void afterTextChanged(Editable editable) {
            }

            @Override 
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override 
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ActivitySearch.this.performSearchNotes(charSequence.toString());
            }
        });
    }


    public void Task() {
        this.binding.pageTitle.setText(getString(R.string.tasks_list));
        fetchDataTask();
        this.binding.searchedittext.addTextChangedListener(new TextWatcher() { 
            @Override 
            public void afterTextChanged(Editable editable) {
            }

            @Override 
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            }

            @Override 
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                ActivitySearch.this.performSearch(charSequence.toString());
            }
        });
    }

    public void fetchDataNotes() {
        final MyHelperDb myHelperDb = new MyHelperDb(this);
        this.list1 = myHelperDb.getAllNotesAscending();
        this.adapter1 = new NotesAdapter(this.list1, this, true) { 
            @Override 
            public void onDeleteButton(Notes notes) {
                myHelperDb.deleteNoteById(notes.getId());
                myHelperDb.addTrash(notes);
                ActivitySearch.this.fetchDataNotes();
            }

            @Override 
            public void onDeleteTrash(Notes notes) {
                myHelperDb.deleteTrashById(notes.getId());
                ActivitySearch.this.fetchDataNotes();
            }

            @Override 
            public void onArchived(Notes notes) {
                myHelperDb.deleteNoteById(notes.getId());
                myHelperDb.addArchived(notes);
                ActivitySearch.this.fetchDataNotes();
            }
        };
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(this.adapter1);
    }

    public void fetchDataTask() {
        final TaskHelper taskHelper = new TaskHelper(this);
        this.list = taskHelper.getAllTask();
        this.adapter = new AdapterTODO(taskHelper, this, this.list, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                ActivitySearch.this.onchange();
            }
        }, true) { 
            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                taskHelper.deleteTask(tODOModels.getId());
                taskHelper.addTaskTrash(tODOModels);
                ActivitySearch.this.fetchDataTask();
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                taskHelper.deleteTaskTrash(tODOModels.getId());
                ActivitySearch.this.fetchDataTask();
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                taskHelper.deleteTask(tODOModels.getId());
                taskHelper.addTaskArchived(tODOModels);
                ActivitySearch.this.fetchDataTask();
            }
        };
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(this.adapter);
    }

    
    public void performSearch(String str) {
        ArrayList arrayList = new ArrayList();
        for (TODOModels tODOModels : this.list) {
            if (tODOModels.getTask().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(tODOModels);
            }
        }
        this.adapter.setList(arrayList);
        this.adapter.notifyDataSetChanged();
        if (arrayList.isEmpty()) {
            this.binding.nomatch.setVisibility(View.VISIBLE);
            this.binding.nomatchImg.setVisibility(View.VISIBLE);
        } else {
            this.binding.nomatch.setVisibility(View.GONE);
            this.binding.nomatchImg.setVisibility(View.GONE);
        }
    }

    
    public void performSearchNotes(String str) {
        ArrayList arrayList = new ArrayList();
        for (Notes notes : this.list1) {
            if (notes.getTitle().toLowerCase().contains(str.toLowerCase())) {
                arrayList.add(notes);
            }
            this.adapter1.notifyDataSetChanged();
            this.adapter1.setNotes(arrayList);
        }
        if (arrayList.isEmpty()) {
            this.binding.nomatch.setVisibility(View.VISIBLE);
            this.binding.nomatchImg.setVisibility(View.VISIBLE);
        } else {
            this.binding.nomatch.setVisibility(View.GONE);
            this.binding.nomatchImg.setVisibility(View.GONE);
        }
    }

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            color6 = getColor(R.color.full_light_green);
            setbackgroundcolor(color6);
        } else if (i == 2) {
            color5 = getColor(R.color.full_light_pink);
            setbackgroundcolor(color5);
        } else if (i == 3) {
            color4 = getColor(R.color.full_light_blue);
            setbackgroundcolor(color4);
        } else if (i == 4) {
            color3 = getColor(R.color.full_light_purple);
            setbackgroundcolor(color3);
        } else if (i == 5) {
            color2 = getColor(R.color.black);
            setbackgroundcolor(color2);
        } else if (i == 6) {
            color = getColor(R.color.full_light_parrot);
            setbackgroundcolor(color);
        } else if (i == 7) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_7));
        } else if (i == 8) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_8));
        } else if (i == 9) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_9));
        } else if (i == 10) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_10));
        } else if (i == 11) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_11));
        } else if (i == 12) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_12));
        } else if (i == 13) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_13));
        } else if (i == 14) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_14));
        } else if (i == 15) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_15));
        } else if (i == 16) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_16));
        } else if (i == 17) {
            this.binding.activity.setBackground(getDrawable(R.drawable.theme_s_17));
        }
        if (i == 5 || i == 15 || i == 16 || i == 17) {
            textcolors(R.color.white);
            setStatusBarTransparentWhite();
        } else {
            textcolors(R.color.black);
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

    private void textcolors(int i) {
        this.binding.back.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.nomatch.setTextColor(getResources().getColor(i));
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.nomatchImg.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.nomatchImg.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.searchedittext.setTextColor(getResources().getColor(i));
        this.binding.searchedittext.setHintTextColor(getResources().getColor(i));
        this.binding.searchedittext.setCompoundDrawableTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void setbackgroundcolor(int i) {
        this.binding.activity.setBackgroundColor(i);
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
