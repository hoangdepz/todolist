package com.hieupro.checklistnote.FragmentClasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Calendar;
import com.haibin.calendarview.CalendarView;
import com.hieupro.checklistnote.ActivityMain;
import com.hieupro.checklistnote.AdapterClasses.AdapterTODO;
import com.hieupro.checklistnote.AdapterClasses.NotesAdapter;
import com.hieupro.checklistnote.AddNoteActivity;
import com.hieupro.checklistnote.AddTaskActivity;
import com.hieupro.checklistnote.DatabaseClasses.MyHelperDb;
import com.hieupro.checklistnote.DatabaseClasses.TaskHelper;
import com.hieupro.checklistnote.Listeners.onTaskChanges;
import com.hieupro.checklistnote.Models.Notes;
import com.hieupro.checklistnote.Models.TODOModels;
import com.hieupro.checklistnote.R;
import com.hieupro.checklistnote.custom_Calender.MonthViewCustom;
import com.hieupro.checklistnote.databinding.CalenderFragmentBinding;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class FragmentCalender extends Fragment {
    CalenderFragmentBinding binding;
    Calendar clickedDate;
    Calendar currentCalendar;
    MyHelperDb dbHelper;
    List<Notes> notesForClickedDate;
    Calendar taskClickedDate;
    TaskHelper taskHelper;
    Calendar taskcurrentCalendar;
    List<TODOModels> tasksForClickedDate;
    int theme;
    View view;
    long timesTamp = 0;
    boolean isTrue = true;

    
    public void ontaskChange() {
    }

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        final PopupMenu popupMenu;
        this.binding = CalenderFragmentBinding.inflate(getLayoutInflater());
        this.dbHelper = new MyHelperDb(getContext());
        this.currentCalendar = java.util.Calendar.getInstance();
        this.taskcurrentCalendar = Calendar.getInstance();
        settheme();
        this.taskHelper = new TaskHelper(getActivity());
        this.binding.addNoteBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        FragmentActivity activity = getActivity();
        getActivity();
        boolean z = activity.getSharedPreferences("isTask", 0).getBoolean("isTask", true);
        this.isTrue = z;
        if (z) {
            Task();
        } else {
            Notes();
        }
        if (getActivity() instanceof ActivityMain) {
            ((ActivityMain) getActivity()).showNavigation();
        }
        if (this.theme == 5) {
            popupMenu = new PopupMenu(getActivity(), this.binding.menu, GravityCompat.END, R.style.PopupMenuStyleBlack, R.style.PopupMenuStyleBlack);
        } else {
            popupMenu = new PopupMenu(getActivity(), this.binding.menu, GravityCompat.END, R.style.PopupMenuStylewhite, R.style.PopupMenuStylewhite);
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
        this.binding.menu.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
                    @Override 
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.task) {
                            FragmentCalender.this.isTrue = true;
                            SharedPreferences.Editor edit = FragmentCalender.this.getActivity().getSharedPreferences("isTask", 0).edit();
                            edit.putBoolean("isTask", FragmentCalender.this.isTrue);
                            edit.apply();
                            FragmentCalender.this.Task();
                            return true;
                        }
                        if (itemId != R.id.notes) {
                            return false;
                        }
                        FragmentCalender.this.Notes();
                        FragmentCalender.this.isTrue = false;
                        SharedPreferences.Editor edit2 = FragmentCalender.this.getActivity().getSharedPreferences("isTask", 0).edit();
                        edit2.putBoolean("isTask", false);
                        edit2.apply();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
        this.binding.addNoteBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                if (FragmentCalender.this.isTrue) {
                    Intent intent = new Intent(FragmentCalender.this.getActivity(), (Class<?>) AddTaskActivity.class);
                    intent.putExtra("date", FragmentCalender.this.timesTamp);
                    FragmentCalender.this.startActivity(intent);
                } else {
                    Intent intent2 = new Intent(FragmentCalender.this.getActivity(), (Class<?>) AddNoteActivity.class);
                    intent2.putExtra("date", FragmentCalender.this.timesTamp);
                    FragmentCalender.this.startActivity(intent2);
                }
            }
        });
        return this.binding.getRoot();
    }

    public void Task() {
        this.binding.calendarView.setMonthView(new MonthViewCustom(getActivity()).getClass());
        this.binding.calendarView.setVisibility(View.VISIBLE);
        this.binding.recName.setVisibility(View.VISIBLE);
        this.binding.recyclerView.setVisibility(View.VISIBLE);
        this.binding.noteCalenderView.setVisibility(View.GONE);
        this.binding.name.setVisibility(View.GONE);
        this.binding.notesrecyclerView.setVisibility(View.GONE);
        this.binding.calendarView.clearSchemeDate();
        this.binding.noteright.setVisibility(View.GONE);
        this.binding.noteleft.setVisibility(View.GONE);
        this.binding.noteMonthDate.setVisibility(View.GONE);
        this.binding.right.setVisibility(View.VISIBLE);
        this.binding.left.setVisibility(View.VISIBLE);
        this.binding.monthDate.setVisibility(View.VISIBLE);
        loadEventTask();
        this.binding.calendarView.setSelectDefaultMode();
        updateMonthDateText();
        this.binding.right.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentCalender.this.binding.calendarView.scrollToNext(true);
                FragmentCalender.this.taskcurrentCalendar.add(2, 1);
                FragmentCalender.this.binding.calendarView.scrollToCalendar(FragmentCalender.this.taskcurrentCalendar.get(1), FragmentCalender.this.taskcurrentCalendar.get(2) + 1, 1);
                FragmentCalender.this.updateMonthDateText();
            }
        });
        this.binding.left.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentCalender.this.taskcurrentCalendar.add(2, -1);
                FragmentCalender.this.binding.calendarView.scrollToPre(true);
                FragmentCalender.this.binding.calendarView.scrollToCalendar(FragmentCalender.this.taskcurrentCalendar.get(1), FragmentCalender.this.taskcurrentCalendar.get(2) + 1, 1);
                FragmentCalender.this.updateMonthDateText();
            }
        });
        this.binding.calendarView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override 
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {
            }

            @Override 
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean z) {
                int day = calendar.getDay();
                int year = calendar.getYear();
                int month = calendar.getMonth();
                calendar.setDay(day);
                FragmentCalender.this.taskClickedDate = Calendar.getInstance();
                FragmentCalender.this.taskClickedDate.set(1, year);
                FragmentCalender.this.taskClickedDate.set(2, month - 1);
                FragmentCalender.this.taskClickedDate.set(5, day);
                if (FragmentCalender.this.taskClickedDate != null) {
                    FragmentCalender fragmentCalender = FragmentCalender.this;
                    fragmentCalender.fetchDataTask(fragmentCalender.taskClickedDate);
                }
                FragmentCalender.this.taskcurrentCalendar.set(1, calendar.getYear());
                FragmentCalender.this.taskcurrentCalendar.set(2, calendar.getMonth() - 1);
                FragmentCalender.this.updateMonthDateText();
                if (FragmentCalender.this.taskClickedDate != null) {
                    FragmentCalender fragmentCalender2 = FragmentCalender.this;
                    fragmentCalender2.timesTamp = fragmentCalender2.taskClickedDate.getTimeInMillis();
                }
            }
        });
        if (this.taskClickedDate == null) {
            Calendar calendar = Calendar.getInstance();
            this.taskClickedDate = calendar;
            fetchDataTask(calendar);
        }
    }

    public void Notes() {
        this.binding.noteCalenderView.setMonthView(new MonthViewCustom(getActivity()).getClass());
        this.binding.calendarView.setVisibility(View.GONE);
        this.binding.recName.setVisibility(View.GONE);
        this.binding.recyclerView.setVisibility(View.GONE);
        this.binding.noteCalenderView.setVisibility(View.VISIBLE);
        this.binding.name.setVisibility(View.VISIBLE);
        this.binding.notesrecyclerView.setVisibility(View.VISIBLE);
        this.binding.noteCalenderView.clearSchemeDate();
        this.binding.noteright.setVisibility(View.VISIBLE);
        this.binding.noteleft.setVisibility(View.VISIBLE);
        this.binding.noteMonthDate.setVisibility(View.VISIBLE);
        this.binding.right.setVisibility(View.GONE);
        this.binding.left.setVisibility(View.GONE);
        this.binding.monthDate.setVisibility(View.GONE);
        loadEvents();
        this.binding.noteCalenderView.setSelectDefaultMode();
        noteUpdateMonthDateText();
        this.binding.noteright.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentCalender.this.binding.noteCalenderView.scrollToNext(true);
                FragmentCalender.this.currentCalendar.add(2, 1);
                FragmentCalender.this.binding.noteCalenderView.scrollToCalendar(FragmentCalender.this.currentCalendar.get(1), FragmentCalender.this.currentCalendar.get(2) + 1, 1);
                FragmentCalender.this.noteUpdateMonthDateText();
            }
        });
        this.binding.noteleft.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentCalender.this.currentCalendar.add(2, -1);
                FragmentCalender.this.binding.noteCalenderView.scrollToPre(true);
                FragmentCalender.this.binding.noteCalenderView.scrollToCalendar(FragmentCalender.this.currentCalendar.get(1), FragmentCalender.this.currentCalendar.get(2) + 1, 1);
                FragmentCalender.this.noteUpdateMonthDateText();
            }
        });
        this.binding.noteCalenderView.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() { 
            @Override 
            public void onCalendarOutOfRange(com.haibin.calendarview.Calendar calendar) {
            }

            @Override 
            public void onCalendarSelect(com.haibin.calendarview.Calendar calendar, boolean z) {
                int day = calendar.getDay();
                int year = calendar.getYear();
                int month = calendar.getMonth();
                calendar.setDay(day);
                FragmentCalender.this.clickedDate = Calendar.getInstance();
                FragmentCalender.this.clickedDate.set(1, year);
                FragmentCalender.this.clickedDate.set(2, month - 1);
                FragmentCalender.this.clickedDate.set(5, day);
                if (FragmentCalender.this.clickedDate != null) {
                    FragmentCalender fragmentCalender = FragmentCalender.this;
                    fragmentCalender.fetchData(fragmentCalender.clickedDate);
                }
                FragmentCalender.this.currentCalendar.set(1, calendar.getYear());
                FragmentCalender.this.currentCalendar.set(2, calendar.getMonth() - 1);
                FragmentCalender.this.noteUpdateMonthDateText();
                if (FragmentCalender.this.clickedDate != null) {
                    FragmentCalender fragmentCalender2 = FragmentCalender.this;
                    fragmentCalender2.timesTamp = fragmentCalender2.clickedDate.getTimeInMillis();
                }
            }
        });
        if (this.clickedDate == null) {
            Calendar calendar = Calendar.getInstance();
            this.clickedDate = calendar;
            fetchData(calendar);
        }
    }

    
    public void updateMonthDateText() {
        this.binding.monthDate.setText(new SimpleDateFormat("MMM yyyy", Locale.US).format(this.taskcurrentCalendar.getTime()));
    }

    
    public void noteUpdateMonthDateText() {
        this.binding.noteMonthDate.setText(new SimpleDateFormat("MMM yyyy", Locale.US).format(this.currentCalendar.getTime()));
    }

    public void fetchData(final Calendar calendar) {
        if (calendar != null) {
            long startOfDayTimestamp = getStartOfDayTimestamp(calendar.getTimeInMillis());
            this.notesForClickedDate = this.dbHelper.getNotesForDateRange(startOfDayTimestamp, (86400000 + startOfDayTimestamp) - 1);
        }
        ArrayList arrayList = new ArrayList();
        if (this.notesForClickedDate != null) {
            arrayList = new ArrayList(this.notesForClickedDate);
        }
        NotesAdapter notesAdapter = new NotesAdapter(arrayList, getActivity(), true) { 
            @Override 
            public void onDeleteTrash(Notes notes) {
            }

            @Override 
            public void onDeleteButton(Notes notes) {
                FragmentCalender.this.dbHelper.deleteNoteById(notes.getId());
                FragmentCalender.this.dbHelper.addTrash(notes);
                FragmentCalender.this.fetchData(calendar);
                FragmentCalender.this.binding.noteCalenderView.clearSchemeDate();
                FragmentCalender.this.loadEvents();
            }

            @Override 
            public void onArchived(Notes notes) {
                FragmentCalender.this.dbHelper.deleteNoteById(notes.getId());
                FragmentCalender.this.dbHelper.addArchived(notes);
                FragmentCalender.this.fetchData(calendar);
                FragmentCalender.this.binding.noteCalenderView.clearSchemeDate();
                FragmentCalender.this.loadEvents();
            }
        };
        this.binding.notesrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.notesrecyclerView.setAdapter(notesAdapter);
    }

    public void fetchDataTask(final Calendar calendar) {
        if (calendar != null) {
            long startOfDayTimestamp = getStartOfDayTimestamp(calendar.getTimeInMillis());
            this.tasksForClickedDate = this.taskHelper.getTaskForDateRange(startOfDayTimestamp, (86400000 + startOfDayTimestamp) - 1);
        }
        ArrayList arrayList = new ArrayList();
        if (this.tasksForClickedDate != null) {
            arrayList = new ArrayList(this.tasksForClickedDate);
        }
        AdapterTODO adapterTODO = new AdapterTODO(this.taskHelper, getActivity(), arrayList, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                FragmentCalender.this.ontaskChange();
            }
        }, true) { 
            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
            }

            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                FragmentCalender.this.taskHelper.deleteTask(tODOModels.getId());
                FragmentCalender.this.taskHelper.addTaskTrash(tODOModels);
                FragmentCalender.this.fetchDataTask(calendar);
                FragmentCalender.this.binding.calendarView.clearSchemeDate();
                FragmentCalender.this.loadEventTask();
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                FragmentCalender.this.taskHelper.deleteTask(tODOModels.getId());
                FragmentCalender.this.taskHelper.addTaskArchived(tODOModels);
                FragmentCalender.this.fetchDataTask(calendar);
                FragmentCalender.this.binding.calendarView.clearSchemeDate();
                FragmentCalender.this.loadEventTask();
            }
        };
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.recyclerView.setAdapter(adapterTODO);
    }

    
    public void loadEvents() {
        List<Notes> allNotesAscending = this.dbHelper.getAllNotesAscending();
        ArrayList arrayList = new ArrayList();
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        Iterator<Notes> it = allNotesAscending.iterator();
        while (it.hasNext()) {
            long timestamp = it.next().getTimestamp();
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTimeInMillis(timestamp);
            int i = calendar2.get(5);
            int i2 = calendar2.get(1);
            int i3 = calendar2.get(2) + 1;
            com.haibin.calendarview.Calendar.Scheme scheme = new com.haibin.calendarview.Calendar.Scheme();
            scheme.setScheme(calendarToString(calendar2));
            calendar.setYear(i2);
            calendar.setMonth(i3);
            calendar.setDay(i);
            arrayList.add(scheme);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                calendar.addScheme((com.haibin.calendarview.Calendar.Scheme) it2.next());
            }
            this.binding.noteCalenderView.addSchemeDate(calendar);
        }
    }

    
    public void loadEventTask() {
        List<TODOModels> allTask = this.taskHelper.getAllTask();
        ArrayList arrayList = new ArrayList();
        com.haibin.calendarview.Calendar calendar = new com.haibin.calendarview.Calendar();
        Iterator<TODOModels> it = allTask.iterator();
        while (it.hasNext()) {
            long timestamp = it.next().getTimestamp();
            java.util.Calendar calendar2 = java.util.Calendar.getInstance();
            calendar2.setTimeInMillis(timestamp);
            int i = calendar2.get(5);
            int i2 = calendar2.get(1);
            int i3 = calendar2.get(2) + 1;
            com.haibin.calendarview.Calendar.Scheme scheme = new com.haibin.calendarview.Calendar.Scheme();
            scheme.setScheme(calendarToString(calendar2));
            calendar.setYear(i2);
            calendar.setMonth(i3);
            calendar.setDay(i);
            arrayList.add(scheme);
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                calendar.addScheme((com.haibin.calendarview.Calendar.Scheme) it2.next());
            }
            this.binding.calendarView.addSchemeDate(calendar);
        }
    }

    private long getStartOfDayTimestamp(long j) {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.setTimeInMillis(j);
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar.getTimeInMillis();
    }

    private String calendarToString(java.util.Calendar calendar) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(calendar.getTime());
    }

    public void settheme() {
        FragmentActivity activity = getActivity();
        getActivity();
        int i = activity.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        this.theme = i;
        if (i == 1) {
            addButton(R.color.green);
        } else if (i == 2) {
            addButton(R.color.pink);
        } else if (i == 3) {
            addButton(R.color.blue);
        } else if (i == 4) {
            addButton(R.color.purple);
        } else if (i == 5) {
            addButton(R.color.purple);
        } else if (i == 6) {
            addButton(R.color.parrot);
        } else if (i == 7) {
            addButton(R.color.themedark7);
        } else if (i == 8) {
            addButton(R.color.themedark8);
        } else if (i == 9) {
            addButton(R.color.themedark9);
        } else if (i == 10) {
            addButton(R.color.themedark10);
        } else if (i == 11) {
            addButton(R.color.themedark11);
        } else if (i == 12) {
            addButton(R.color.themedark12);
        } else if (i == 13) {
            addButton(R.color.themedark13);
        } else if (i == 14) {
            addButton(R.color.themedark14);
        } else if (i == 15) {
            addButton(R.color.themedark15);
        } else if (i == 16) {
            addButton(R.color.themedark16);
        } else if (i == 17) {
            addButton(R.color.themedark17);
        }
        int i2 = this.theme;
        if (i2 == 5 || i2 == 15 || i2 == 16 || i2 == 17) {
            textcolor(R.color.white);
        } else {
            textcolor(R.color.black);
        }
    }

    public void addButton(int i) {
        this.binding.addNoteBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void textcolor(int i) {
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.name.setTextColor(getResources().getColor(i));
        this.binding.recName.setTextColor(getResources().getColor(i));
        this.binding.monthDate.setTextColor(getResources().getColor(i));
        this.binding.noteMonthDate.setTextColor(getResources().getColor(i));
        this.binding.menu.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.right.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.noteleft.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.noteright.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.left.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.calendarView.setWeeColor(getResources().getColor(R.color.transparent), getResources().getColor(i));
        this.binding.noteCalenderView.setWeeColor(getResources().getColor(R.color.transparent), getResources().getColor(i));
    }

    @Override 
    public void onResume() {
        super.onResume();
        if (this.isTrue) {
            java.util.Calendar calendar = this.taskClickedDate;
            if (calendar != null) {
                fetchDataTask(calendar);
            } else {
                java.util.Calendar calendar2 = java.util.Calendar.getInstance();
                this.taskClickedDate = calendar2;
                fetchDataTask(calendar2);
            }
            this.binding.calendarView.clearSchemeDate();
            loadEventTask();
            return;
        }
        java.util.Calendar calendar3 = this.clickedDate;
        if (calendar3 != null) {
            fetchData(calendar3);
        } else {
            java.util.Calendar calendar4 = java.util.Calendar.getInstance();
            this.clickedDate = calendar4;
            fetchData(calendar4);
        }
        this.binding.noteCalenderView.clearSchemeDate();
        loadEvents();
    }

    @Override 
    public void onStart() {
        super.onStart();
        if (this.isTrue) {
            this.binding.calendarView.clearSchemeDate();
            loadEventTask();
            java.util.Calendar calendar = this.taskClickedDate;
            if (calendar != null) {
                fetchDataTask(calendar);
                return;
            }
            java.util.Calendar calendar2 = java.util.Calendar.getInstance();
            this.taskClickedDate = calendar2;
            fetchDataTask(calendar2);
            return;
        }
        this.binding.noteCalenderView.clearSchemeDate();
        loadEvents();
        java.util.Calendar calendar3 = this.clickedDate;
        if (calendar3 != null) {
            fetchData(calendar3);
            return;
        }
        java.util.Calendar calendar4 = java.util.Calendar.getInstance();
        this.clickedDate = calendar4;
        fetchData(calendar4);
    }
}
