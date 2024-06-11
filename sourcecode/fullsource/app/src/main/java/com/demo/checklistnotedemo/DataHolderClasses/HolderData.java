package com.demo.checklistnotedemo.DataHolderClasses;

import com.demo.checklistnotedemo.AdapterClasses.AdapterTODO;
import com.demo.checklistnotedemo.Models.ImageModels;
import java.util.ArrayList;
import java.util.List;


public class HolderData {
    private static HolderData instance;
    AdapterTODO adapter;
    private List<ImageModels> imageList = new ArrayList();

    private HolderData() {
    }

    public static HolderData getInstance() {
        if (instance == null) {
            instance = new HolderData();
        }
        return instance;
    }

    public List<ImageModels> getImageList() {
        return this.imageList;
    }

    public void setImageList(List<ImageModels> list) {
        this.imageList = list;
    }

    public AdapterTODO getAdapter() {
        return this.adapter;
    }

    public void setAdapter(AdapterTODO adapterTODO) {
        this.adapter = adapterTODO;
    }
}
