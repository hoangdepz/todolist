package com.demo.mynote.FragmentClasses;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.mynote.activity.ActivitySearch;
import com.demo.mynote.AdapterClasses.AdapterTODO;
import com.demo.mynote.AdapterClasses.CategoryAdapter;
import com.demo.mynote.activity.AddTaskActivity;
import com.demo.mynote.DataHolderClasses.HolderTODODialog;
import com.demo.mynote.DatabaseClasses.CategoryHelper;
import com.demo.mynote.DatabaseClasses.TaskHelper;
import com.demo.mynote.Listeners.Category;
import com.demo.mynote.Listeners.onTaskChanges;
import com.demo.mynote.Models.CategoryModel;
import com.demo.mynote.Models.TODOModels;
import com.demo.mynote.R;
import com.demo.mynote.activity.RateUsDialogs;
import com.demo.mynote.ReminderClasses.ControlMusic;
import com.demo.mynote.databinding.FragmentTodolistBinding;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;


public class FragmentTODO extends Fragment implements onTaskChanges {
    AdapterTODO adapter;
    CategoryAdapter adapter1;
    FragmentTodolistBinding binding;
    List<CategoryModel> categorylist;
    TaskHelper db;
    List<TODOModels> list;
    private Calendar calendar = java.util.Calendar.getInstance();
    boolean a = true;
    List<TODOModels> list1 = new ArrayList();
    List<CategoryModel> ctmodel = new ArrayList();

    @Override 
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.binding = FragmentTodolistBinding.inflate(layoutInflater, viewGroup, false);
        this.db = new TaskHelper(getActivity());
        fetchData();
        fetchCheckedData();
        fetchCategory();
        ControlMusic.getInstance(getActivity()).stopMusic();
        this.binding.addTaskBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
        this.binding.addTaskBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentTODO.this.startActivity(new Intent(FragmentTODO.this.getActivity(), (Class<?>) AddTaskActivity.class));
            }
        });
        this.binding.searchBtn.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                FragmentTODO.this.startActivity(new Intent(FragmentTODO.this.getActivity(), (Class<?>) ActivitySearch.class).putExtra("true", true));
            }
        });
        settheme();
        this.binding.addTaskBtn.setBackground(getActivity().getDrawable(R.drawable.rectangle));
        boolean z = getActivity().getSharedPreferences("MyPreferences", 0).getBoolean("istrue", false);
        boolean variable = HolderTODODialog.getInstance().getVariable();
        if (z && this.list.size() % 2 == 0 && this.list.size() != 0 && variable) {
            closeKeyboard();
            new RateUsDialogs(getActivity()).RateUsDialogbox();
            HolderTODODialog.getInstance().setVariable(false);
        }
        return this.binding.getRoot();
    }

    public void fetchData() {
        this.list = this.db.getAllUnCompletedTask();
        this.adapter = new AdapterTODO(this.db, getActivity(), this.list, this, true) { 
            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskTrash(tODOModels);
                FragmentTODO.this.fetchData();
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTaskTrash(tODOModels.getId());
                FragmentTODO.this.fetchData();
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskArchived(tODOModels);
                FragmentTODO.this.fetchData();
            }
        };
        this.binding.taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.taskRecyclerView.setAdapter(this.adapter);
        if (this.list.isEmpty()) {
            this.binding.pending.setVisibility(View.GONE);
        } else {
            this.binding.pending.setVisibility(View.VISIBLE);
        }
    }

    public void fetchCheckedData() {
        this.list1 = this.db.getAllCompletedTask();
        this.adapter = new AdapterTODO(this.db, getActivity(), this.list1, this, true) { 
            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskTrash(tODOModels);
                FragmentTODO.this.fetchCheckedData();
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTaskTrash(tODOModels.getId());
                FragmentTODO.this.fetchCheckedData();
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskArchived(tODOModels);
                FragmentTODO.this.fetchCheckedData();
            }
        };
        this.binding.checkedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.checkedRecyclerView.setAdapter(this.adapter);
        if (this.list1.isEmpty()) {
            this.binding.completed.setVisibility(View.GONE);
        } else {
            this.binding.completed.setVisibility(View.VISIBLE);
        }
    }

    public void fetchCategory() {
        CategoryHelper categoryHelper = new CategoryHelper(getActivity());
        List<CategoryModel> allCategory = categoryHelper.getAllCategory();
        this.categorylist = allCategory;
        if (allCategory == null || allCategory.isEmpty()) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setName(getString(R.string.none));
            categoryModel.setBuiltIn(2L);
            categoryHelper.addCategory(categoryModel);
            CategoryModel categoryModel2 = new CategoryModel();
            categoryModel2.setName(getString(R.string.work));
            categoryModel2.setBuiltIn(2L);
            categoryHelper.addCategory(categoryModel2);
            CategoryModel categoryModel3 = new CategoryModel();
            categoryModel3.setName(getString(R.string.personal));
            categoryModel3.setBuiltIn(2L);
            categoryHelper.addCategory(categoryModel3);
            CategoryModel categoryModel4 = new CategoryModel();
            categoryModel4.setName(getString(R.string.wishlist));
            categoryModel4.setBuiltIn(2L);
            categoryHelper.addCategory(categoryModel4);
            CategoryModel categoryModel5 = new CategoryModel();
            categoryModel5.setName(getString(R.string.birthday));
            categoryModel5.setBuiltIn(2L);
            categoryHelper.addCategory(categoryModel5);
            fetchCategory();
        }
        this.adapter1 = new CategoryAdapter(getActivity(), this.categorylist, false, new Category() { 
            @Override 
            public final void category(CategoryModel categoryModel6) {
                FragmentTODO.this.Category(categoryModel6);
            }
        });
        this.binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        this.binding.categoryRecyclerView.setAdapter(this.adapter1);
    }

    public void fetchCheckedDataCategory(final CategoryModel categoryModel) {
        List<TODOModels> taskbyCategoryName = this.db.getTaskbyCategoryName(categoryModel.getId());
        this.adapter = new AdapterTODO(this.db, getActivity(), taskbyCategoryName, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                FragmentTODO.this.category();
            }
        }, true) { 
            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskTrash(tODOModels);
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTaskTrash(tODOModels.getId());
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskArchived(tODOModels);
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }
        };
        this.binding.taskRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.taskRecyclerView.setAdapter(this.adapter);
        List<TODOModels> taskbyCategoryNameChecked = this.db.getTaskbyCategoryNameChecked(categoryModel.getId());
        this.adapter = new AdapterTODO(this.db, getActivity(), taskbyCategoryNameChecked, new onTaskChanges() { 
            @Override 
            public final void onTaskChange() {
                FragmentTODO.this.category();
            }
        }, true) { 
            @Override 
            public void onDeleteButton(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskTrash(tODOModels);
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }

            @Override 
            public void onDeleteTrash(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTaskTrash(tODOModels.getId());
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }

            @Override 
            public void onArchived(TODOModels tODOModels) {
                FragmentTODO.this.db.deleteTask(tODOModels.getId());
                FragmentTODO.this.db.addTaskArchived(tODOModels);
                FragmentTODO.this.fetchCheckedDataCategory(categoryModel);
            }
        };
        this.binding.checkedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.binding.checkedRecyclerView.setAdapter(this.adapter);
        this.ctmodel.add(categoryModel);
        if (taskbyCategoryName.isEmpty()) {
            this.binding.pending.setVisibility(View.GONE);
        } else {
            this.binding.pending.setVisibility(View.VISIBLE);
        }
        if (taskbyCategoryNameChecked.isEmpty()) {
            this.binding.completed.setVisibility(View.GONE);
        } else {
            this.binding.completed.setVisibility(View.VISIBLE);
        }
    }

    
    public void category() {
        CategoryModel categoryModel = new CategoryModel();
        Iterator<CategoryModel> it = this.ctmodel.iterator();
        while (it.hasNext()) {
            categoryModel = it.next();
        }
        fetchCheckedDataCategory(categoryModel);
    }

    
    public void Category(CategoryModel categoryModel) {
        if (categoryModel.getName().equals("All")) {
            fetchData();
            fetchCheckedData();
        } else {
            fetchCheckedDataCategory(categoryModel);
        }
    }

    @Override 
    public void onResume() {
        super.onResume();
        fetchCheckedData();
        fetchData();
        fetchCategory();
        this.adapter1.setCategoryNameAndSelection(0L, false);
    }

    @Override 
    public void onStart() {
        super.onStart();
        fetchCheckedData();
        fetchData();
        fetchCategory();
        this.adapter1.setCategoryNameAndSelection(0L, false);
    }

    private void closeKeyboard() {
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @Override 
    public void onTaskChange() {
        fetchData();
        fetchCheckedData();
    }

    public void settheme() {
        FragmentActivity activity = getActivity();
        getActivity();
        int i = activity.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 1) {
            textcolor(R.color.black);
            addButton(R.color.green);
            return;
        }
        if (i == 2) {
            textcolor(R.color.black);
            addButton(R.color.pink);
            return;
        }
        if (i == 3) {
            textcolor(R.color.black);
            addButton(R.color.blue);
            return;
        }
        if (i == 4) {
            addButton(R.color.purple);
            textcolor(R.color.black);
            return;
        }
        if (i == 5) {
            textcolor(R.color.white);
            addButton(R.color.purple);
            return;
        }
        if (i == 6) {
            textcolor(R.color.black);
            addButton(R.color.parrot);
            return;
        }
        if (i == 7) {
            addButton(R.color.themedark7);
            textcolor(R.color.black);
            return;
        }
        if (i == 8) {
            textcolor(R.color.black);
            addButton(R.color.themedark8);
            return;
        }
        if (i == 9) {
            textcolor(R.color.black);
            addButton(R.color.themedark9);
            return;
        }
        if (i == 10) {
            addButton(R.color.themedark10);
            textcolor(R.color.black);
            return;
        }
        if (i == 11) {
            textcolor(R.color.black);
            addButton(R.color.themedark11);
            return;
        }
        if (i == 12) {
            textcolor(R.color.black);
            addButton(R.color.themedark12);
            return;
        }
        if (i == 13) {
            textcolor(R.color.black);
            addButton(R.color.themedark13);
            return;
        }
        if (i == 14) {
            textcolor(R.color.black);
            addButton(R.color.themedark14);
            return;
        }
        if (i == 15) {
            addButton(R.color.themedark15);
            textcolor(R.color.white);
        } else if (i == 16) {
            textcolor(R.color.white);
            addButton(R.color.themedark16);
        } else if (i == 17) {
            addButton(R.color.themedark17);
            textcolor(R.color.white);
        }
    }

    public void addButton(int i) {
        this.binding.addTaskBtn.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    public void textcolor(int i) {
        this.binding.pageTitle.setTextColor(getResources().getColor(i));
        this.binding.pending.setTextColor(getResources().getColor(i));
        this.binding.completed.setTextColor(getResources().getColor(i));
        this.binding.searchBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }
}
