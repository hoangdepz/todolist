package com.demo.exampleapp.Listeners;

import com.demo.exampleapp.Models.TODOModels;


public interface OnTaskStatusChangedListener {
    void onTaskChecked(TODOModels tODOModels);

    void onTaskUnchecked(TODOModels tODOModels);
}
