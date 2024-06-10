package com.hieupro.checklistnote.FragmentClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.CursorWindow;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.hieupro.checklistnote.ActivitySearch;
import com.hieupro.checklistnote.AdapterClasses.NotesAdapter;
import com.hieupro.checklistnote.AddNoteActivity;
import com.hieupro.checklistnote.DataHolderClasses.HolderDialog;
import com.hieupro.checklistnote.DatabaseClasses.MyHelperDb;
import com.hieupro.checklistnote.Models.Notes;
import com.hieupro.checklistnote.R;
import com.hieupro.checklistnote.RateUsDialogs;
import com.hieupro.checklistnote.databinding.HomeFragmentBinding;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class FragmentHome extends Fragment {
    private static final String KEY_LAYOUT_CHOICE = "layout_choice";
    private static final String KEY_SORT_ORDER = "sort_order";
    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREFS_NAME2 = "MyPrefsFiles";
    NotesAdapter adapter;
    HomeFragmentBinding binding;
    MyHelperDb db;
    ArrayList<Notes> list;
    boolean a = true;
    private boolean isLinearLayoutManager = true;
    private boolean isAscending = true;
//    private FragmentHome r3;

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.binding = HomeFragmentBinding.inflate(getLayoutInflater());
        this.db = new MyHelperDb(getActivity());
        this.binding.addNoteBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("sCursorWindowSize");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        getActivity().getWindow().setSoftInputMode(32);
        this.binding.addNoteBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentHome.this.startActivity(new Intent(FragmentHome.this.getActivity(), (Class<?>) AddNoteActivity.class));
            }
        });
        this.binding.searchBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentHome.this.startActivity(new Intent(FragmentHome.this.getActivity(), (Class<?>) ActivitySearch.class).putExtra("true", false));
            }
        });
        layouts();
        ascDsc();
        settheme();
        boolean z = getActivity().getSharedPreferences("MyPreferences", 0).getBoolean("istrue", true);
        boolean variable = HolderDialog.getInstance().getVariable();
        if (z && this.list.size() % 2 == 0 && variable) {
            new RateUsDialogs(getActivity()).RateUsDialogbox();
            HolderDialog.getInstance().setVariable(false);
        }
        return this.binding.getRoot();
    }

    @Override 
    public void onStart() {
        super.onStart();
        fetchData();
    }

    public void ascDsc() {
        this.isAscending = getActivity().getSharedPreferences(PREFS_NAME2, 0).getBoolean(KEY_SORT_ORDER, true);
        fetchData();
        this.binding.dateShorting.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentHome.this.isAscending = !FragmentHome.this.isAscending;
                FragmentHome.this.fetchData();
                SharedPreferences.Editor edit = FragmentHome.this.getActivity().getSharedPreferences(FragmentHome.PREFS_NAME2, 0).edit();
                edit.putBoolean(FragmentHome.KEY_SORT_ORDER, FragmentHome.this.isAscending);
                edit.apply();
            }
        });
    }

    public void layouts() {
        this.isLinearLayoutManager = getActivity().getSharedPreferences(PREFS_NAME, 0).getBoolean(KEY_LAYOUT_CHOICE, true);
        setLayoutManager();
        this.binding.layoutch.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentHome.this.isLinearLayoutManager = !FragmentHome.this.isLinearLayoutManager;
                FragmentHome.this.setLayoutManager();
                SharedPreferences.Editor edit = FragmentHome.this.getActivity().getSharedPreferences(FragmentHome.PREFS_NAME, 0).edit();
                edit.putBoolean(FragmentHome.KEY_LAYOUT_CHOICE, FragmentHome.this.isLinearLayoutManager);
                edit.apply();
            }
        });
    }

    public void fetchData() {
        this.list = new ArrayList<>(this.isAscending ? this.db.getAllNotesAscending() : this.db.getAllNotesDescending());
        this.adapter = new NotesAdapter(this.list, getActivity(), true) { 
            @Override 
            public void onDeleteButton(Notes notes) {
                FragmentHome.this.db.deleteNoteById(notes.getId());
                FragmentHome.this.db.addTrash(notes);
                FragmentHome.this.fetchData();
            }

            @Override 
            public void onDeleteTrash(Notes notes) {
                FragmentHome.this.db.deleteTrashById(notes.getId());
                FragmentHome.this.fetchData();
            }

            @Override 
            public void onArchived(Notes notes) {
                FragmentHome.this.db.deleteNoteById(notes.getId());
                FragmentHome.this.db.addArchived(notes);
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date(notes.getTimestamp()));
                FragmentHome.this.fetchData();
            }
        };
        if (this.list.isEmpty() || this.list == null) {
            this.a = false;
            this.binding.emptyListTextView.setVisibility(View.VISIBLE);
        } else {
            this.binding.emptyListTextView.setVisibility(View.GONE);
            this.a = true;
        }
        setLayoutManager();
        this.binding.recyclerView.setAdapter(this.adapter);
    }

    
    public void setLayoutManager() {
        RecyclerView.LayoutManager staggeredGridLayoutManager;
        if (this.isLinearLayoutManager) {
            staggeredGridLayoutManager = new LinearLayoutManager(getActivity());
            this.binding.txtlayouts.setText(R.string.list);
            this.binding.imglayout.setImageResource(R.drawable.list);
        } else {
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
            this.binding.txtlayouts.setText(R.string.grid);
            this.binding.imglayout.setImageResource(R.drawable.grid);
        }
        this.binding.recyclerView.setLayoutManager(staggeredGridLayoutManager);
    }

    public void settheme() {
        FragmentActivity activity = getActivity();
        getActivity();
        int i = activity.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            textcolor(R.color.black);
            addButton(R.color.green);
        } else if (i == 2) {
            textcolor(R.color.black);
            addButton(R.color.pink);
        } else if (i == 3) {
            textcolor(R.color.black);
            addButton(R.color.blue);
        } else if (i == 4) {
            addButton(R.color.purple);
            textcolor(R.color.black);
        } else if (i == 5) {
            textcolor(R.color.white);
            addButton(R.color.purple);
        } else if (i == 6) {
            textcolor(R.color.black);
            addButton(R.color.parrot);
        } else if (i == 7) {
            addButton(R.color.themedark7);
            textcolor(R.color.black);
        } else if (i == 8) {
            addButton(R.color.themedark8);
            textcolor(R.color.black);
        } else if (i == 9) {
            addButton(R.color.themedark9);
            textcolor(R.color.black);
        } else if (i == 10) {
            addButton(R.color.themedark10);
            textcolor(R.color.black);
        } else if (i == 11) {
            addButton(R.color.themedark11);
            textcolor(R.color.black);
        } else if (i == 12) {
            addButton(R.color.themedark12);
            textcolor(R.color.black);
        } else if (i == 13) {
            addButton(R.color.themedark13);
            textcolor(R.color.black);
        } else if (i == 14) {
            addButton(R.color.themedark14);
            textcolor(R.color.black);
        } else if (i == 15) {
            addButton(R.color.themedark15);
            textcolor(R.color.white);
        } else if (i == 16) {
            addButton(R.color.themedark16);
            textcolor(R.color.white);
        } else if (i == 17) {
            addButton(R.color.themedark17);
            textcolor(R.color.white);
        }
        if (i == 5) {
            this.binding.layoutch.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.light_black)));
            this.binding.layoutch.setCardBackgroundColor(getResources().getColor(R.color.light_black));
        } else {
            this.binding.layoutch.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
            this.binding.layoutch.setCardBackgroundColor(getResources().getColor(R.color.white));
        }
    }

    public void addButton(int i) {
        this.binding.addNoteBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.imglayout.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.dateShorting.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.dateShorting.setCardBackgroundColor(getResources().getColor(i));
        this.binding.txtlayouts.setTextColor(getActivity().getResources().getColor(i));
    }

    public void textcolor(int i) {
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.searchBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }
}
