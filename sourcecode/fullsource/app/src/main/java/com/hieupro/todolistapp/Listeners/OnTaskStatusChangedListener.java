package com.hieupro.todolistapp.Listeners;

import com.hieupro.todolistapp.Models.TODOModels;


public interface OnTaskStatusChangedListener {
    void onTaskChecked(TODOModels tODOModels);

    void onTaskUnchecked(TODOModels tODOModels);
}
