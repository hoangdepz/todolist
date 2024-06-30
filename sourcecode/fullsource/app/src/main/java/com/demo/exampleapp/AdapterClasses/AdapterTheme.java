package com.demo.exampleapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.exampleapp.Models.ItemTheme;
import com.google.android.material.card.MaterialCardView;
import com.demo.exampleapp.Listeners.Finishs;
import com.demo.exampleapp.Listeners.refreshAdapters;
import com.demo.exampleapp.R;
import java.util.List;

public class AdapterTheme extends RecyclerView.Adapter<AdapterTheme.ThemeViewHolder> {
    Context context;
    Finishs finish;
    private boolean isTrue;
    List<ItemTheme> list;
    refreshAdapters refreshAdapters;
    private int selectedPosition;
    int theme = 0;
    private boolean isStrokeEnabled = false;

    public AdapterTheme
            (
                    List<ItemTheme> list,
                    Context context,
                    Finishs finishs,
                    boolean z,
                    refreshAdapters refreshadapters
            ) {
        this.selectedPosition = 0;
        this.list = list;
        this.context = context;
        this.finish = finishs;
        this.isTrue = z;
        this.refreshAdapters = refreshadapters;
        if (z) {
            this.selectedPosition = 0;
        } else {
            this.selectedPosition = -1;
        }
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ThemeViewHolder(LayoutInflater.from(this.context).inflate(R.layout.themes_layout, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(ThemeViewHolder themeviewholder, @SuppressLint("RecyclerView") final int i) {
        themeviewholder.imageView.setImageResource(this.list.get(i).getImageTheme());
        themeviewholder.nameTheme.setText(this.list.get(i).getNameTheme());
        this.selectedPosition = loadLastSelectedPosition();
        int i2 = context.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == selectedPosition) {
            if (i == 0) {
                themeviewholder.cardView.setStrokeWidth(12);
                themeviewholder.cardView.setStrokeColor(Color.BLACK);
                themeviewholder.nameTheme.setTextColor(Color.BLACK);
            } else if (i == 1) {
                themeviewholder.cardView.setStrokeWidth(12);
                themeviewholder.cardView.setStrokeColor(Color.parseColor("#EE9106"));
                themeviewholder.nameTheme.setTextColor(Color.WHITE);
            } else if (i == 2) {
                themeviewholder.cardView.setStrokeWidth(12);
                themeviewholder.cardView.setStrokeColor(Color.parseColor("#FF7878"));
                themeviewholder.nameTheme.setTextColor(Color.BLACK);
            } else if (i == 3) {
                themeviewholder.cardView.setStrokeWidth(12);
                themeviewholder.cardView.setStrokeColor(Color.parseColor("#EB5559"));
                themeviewholder.nameTheme.setTextColor(Color.WHITE);
            } else if (i == 4) {
                themeviewholder.cardView.setStrokeWidth(12);
                themeviewholder.cardView.setStrokeColor(Color.parseColor("#29BABD"));
                themeviewholder.nameTheme.setTextColor(Color.WHITE);
            }
        } else {
            themeviewholder.cardView.setStrokeWidth(0);
            themeviewholder.cardView.setStrokeColor(0);
        }
        themeviewholder.itemView.setOnClickListener(view -> {
            if (isTrue) {
                int i3 = i;
                if (i3 == 0) {
                    theme = 1;
                } else if (i3 == 1) {
                    theme = 2;
                } else if (i3 == 2) {
                    theme = 3;
                } else if (i3 == 3) {
                    theme = 4;
                } else if (i3 == 4) {
                    theme = 5;
                }
            }
            SharedPreferences.Editor edit = AdapterTheme.this.context.getSharedPreferences("my_prefs", 0).edit();
            edit.putInt("my_key", theme).apply();
            finish.onClick();
            selectedPosition = i;
            saveLastSelectedPosition(i);
            notifyDataSetChanged();
            refreshAdapters.refreshAdapter();
        });
    }

    
    public void saveLastSelectedPosition(int i) {
        SharedPreferences.Editor edit = this.context.getSharedPreferences("my_prefs", 0).edit();
        edit.putInt("last_selected_position", i);
        edit.apply();
    }

    private int loadLastSelectedPosition() {
        return this.context.getSharedPreferences("my_prefs", 0).getInt("last_selected_position", 0);
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
    public static class ThemeViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageView;
        TextView nameTheme;

        public ThemeViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.themeImage);
            this.cardView = (MaterialCardView) view.findViewById(R.id.themeCardView);
            this.nameTheme = (TextView) view.findViewById(R.id.nameTheme);
        }
    }
}
