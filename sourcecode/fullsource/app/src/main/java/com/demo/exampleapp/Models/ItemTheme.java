package com.demo.exampleapp.Models;

public class ItemTheme {
    private String nameTheme;
    private int imageTheme;

    public ItemTheme() {}

    public ItemTheme(int imageTheme, String nameTheme) {
        this.imageTheme = imageTheme;
        this.nameTheme = nameTheme;
    }

    public String getNameTheme() {
        return nameTheme;
    }

    public void setNameTheme(String nameTheme) {
        this.nameTheme = nameTheme;
    }

    public int getImageTheme() {
        return imageTheme;
    }

    public void setImageTheme(int imageTheme) {
        this.imageTheme = imageTheme;
    }
}
