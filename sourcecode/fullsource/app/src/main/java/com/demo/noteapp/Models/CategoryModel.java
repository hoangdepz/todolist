package com.demo.noteapp.Models;


public class CategoryModel {
    long builtIn;
    long id;
    String name;

    public long getBuiltIn() {
        return this.builtIn;
    }

    public void setBuiltIn(long j) {
        this.builtIn = j;
    }

    public CategoryModel() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long j) {
        this.id = j;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public CategoryModel(long j, String str) {
        this.id = j;
        this.name = str;
    }
}
