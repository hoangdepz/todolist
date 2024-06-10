package com.hieupro.checklistnote.Listeners;

import com.hieupro.checklistnote.Models.TODOModels;


public interface OnTaskStatusChangedListener {
    void onTaskChecked(TODOModels tODOModels);

    void onTaskUnchecked(TODOModels tODOModels);
}
