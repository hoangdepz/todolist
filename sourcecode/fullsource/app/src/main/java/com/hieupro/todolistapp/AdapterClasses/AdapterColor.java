package com.hieupro.todolistapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.hieupro.todolistapp.Listeners.ColorsChange;
import com.hieupro.todolistapp.R;
import java.util.List;


public class AdapterColor extends RecyclerView.Adapter<AdapterColor.ViewHolder> {
    ColorsChange colorChange;
    Context context;
    List<String> list;
    private int position2 = -1;
    private int selectedItemPosition = -1;
    int theme;

    public AdapterColor(ColorsChange colorsChange, Context context, List<String> list) {
        this.colorChange = colorsChange;
        this.context = context;
        this.list = list;
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(this.context).inflate(R.layout.snack_color_items, viewGroup, false));
    }

    public void selectColorByValue(int i) {
        int indexOf = this.list.indexOf(String.format("#%06X", Integer.valueOf(i & ViewCompat.MEASURED_SIZE_MASK)));
        if (indexOf != -1) {
            int i2 = this.selectedItemPosition;
            this.selectedItemPosition = indexOf;
            notifyItemChanged(i2);
            notifyItemChanged(this.selectedItemPosition);
        }
    }

    @Override 
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        String str = this.list.get(i);
        this.position2 = i;
        final int parseColor = Color.parseColor(str);
        this.theme = this.context.getSharedPreferences("my_prefs", 0).getInt("my_key", 0);
        viewHolder.settheme();
        viewHolder.cardView.setStrokeWidth(4);
        viewHolder.cardView.setCardBackgroundColor(parseColor);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AdapterColor.this.colorChange.colorChange(parseColor);
                int i2 = AdapterColor.this.selectedItemPosition;
                AdapterColor.this.selectedItemPosition = viewHolder.getAdapterPosition();
                AdapterColor.this.notifyItemChanged(i2);
                AdapterColor adapterColor = AdapterColor.this;
                adapterColor.notifyItemChanged(adapterColor.selectedItemPosition);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;

        public ViewHolder(View view) {
            super(view);
            this.cardView = (MaterialCardView) view.findViewById(R.id.colorCardView);
        }

        public void settheme() {
            Context context = AdapterColor.this.context;
            Context context2 = AdapterColor.this.context;
            int i = context.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
            if (i == 1) {
                addButton(R.color.green);
                return;
            }
            if (i == 2) {
                addButton(R.color.pink);
                return;
            }
            if (i == 3) {
                addButton(R.color.blue);
                return;
            }
            if (i == 4) {
                addButton(R.color.purple);
                return;
            }
            if (i == 5) {
                addButton(R.color.purple);
                return;
            }
            if (i == 6) {
                addButton(R.color.parrot);
                return;
            }
            if (i == 7) {
                addButton(R.color.themedark7);
                return;
            }
            if (i == 8) {
                addButton(R.color.themedark8);
                return;
            }
            if (i == 9) {
                addButton(R.color.themedark9);
                return;
            }
            if (i == 10) {
                addButton(R.color.themedark10);
                return;
            }
            if (i == 11) {
                addButton(R.color.themedark11);
                return;
            }
            if (i == 12) {
                addButton(R.color.themedark12);
                return;
            }
            if (i == 13) {
                addButton(R.color.themedark13);
                return;
            }
            if (i == 14) {
                addButton(R.color.themedark14);
                return;
            }
            if (i == 15) {
                addButton(R.color.themedark15);
            } else if (i == 16) {
                addButton(R.color.themedark16);
            } else if (i == 17) {
                addButton(R.color.themedark17);
            }
        }

        public void addButton(int i) {
            this.cardView.setStrokeColor(AdapterColor.this.position2 == AdapterColor.this.selectedItemPosition ? ContextCompat.getColor(AdapterColor.this.context,i): 0);
        }
    }
}
