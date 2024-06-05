package com.demo.todolistapp.Listeners;

import com.demo.todolistapp.Models.TODOModels;


public interface OnTaskStatusChangedListener {
    void onTaskChecked(TODOModels tODOModels);

    void onTaskUnchecked(TODOModels tODOModels);
}
