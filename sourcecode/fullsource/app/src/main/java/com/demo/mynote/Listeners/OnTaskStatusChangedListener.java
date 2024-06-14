package com.demo.mynote.Listeners;

import com.demo.mynote.Models.TODOModels;


public interface OnTaskStatusChangedListener {
    void onTaskChecked(TODOModels tODOModels);

    void onTaskUnchecked(TODOModels tODOModels);
}
