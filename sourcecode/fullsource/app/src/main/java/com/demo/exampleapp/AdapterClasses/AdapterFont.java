package com.demo.exampleapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.demo.exampleapp.Listeners.FontClickListeners;
import com.demo.exampleapp.Models.Notes;
import com.demo.exampleapp.R;
import java.util.List;


public class AdapterFont extends RecyclerView.Adapter<AdapterFont.ViewHolder> {
    private Context context;
    private FontClickListeners fontClickListener;
    private List<Notes> fontNames;
    int position2;
    int theme;
    private int selectedItemPosition = -1;
    private String selectedFontName = null;

    public AdapterFont(Context context, List<Notes> list, FontClickListeners fontClickListeners) {
        this.context = context;
        this.fontNames = list;
        this.fontClickListener = fontClickListeners;
    }

    public void selectFontByName(String str) {
        for (int i = 0; i < this.fontNames.size(); i++) {
            if (this.fontNames.get(i).getFontName().equals(str)) {
                int i2 = this.selectedItemPosition;
                this.selectedItemPosition = i;
                notifyItemChanged(i2);
                notifyItemChanged(this.selectedItemPosition);
                return;
            }
        }
    }

    @Override 
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.font_reyclerview_items, viewGroup, false));
    }

    public void setSelectedFont(String str) {
        this.selectedFontName = str;
        notifyDataSetChanged();
    }

    @Override 
    public void onBindViewHolder(final ViewHolder viewHolder, @SuppressLint("RecyclerView") int i) {
        final Notes notes = this.fontNames.get(i);
        this.position2 = i;
        viewHolder.settheme();
        viewHolder.cardView.setStrokeWidth(6);
        viewHolder.fontButton.setTypeface(Typeface.createFromAsset(this.context.getAssets(), "font/" + notes.getFontName() + ".ttf"));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                AdapterFont.this.fontClickListener.onFontClick(notes);
                int i2 = AdapterFont.this.selectedItemPosition;
                AdapterFont.this.selectedItemPosition = viewHolder.getAdapterPosition();
                AdapterFont.this.notifyItemChanged(i2);
                AdapterFont adapterFont = AdapterFont.this;
                adapterFont.notifyItemChanged(adapterFont.selectedItemPosition);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.fontNames.size();
    }

    
    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        TextView fontButton;

        public ViewHolder(View view) {
            super(view);
            this.fontButton = (TextView) view.findViewById(R.id.fonttxt);
            this.cardView = (MaterialCardView) view.findViewById(R.id.cardViewfont);
        }

        public void settheme() {
            Context context = AdapterFont.this.context;
            Context unused = AdapterFont.this.context;
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
        }

        public void addButton(int i) {
            this.cardView.setStrokeColor(AdapterFont.this.position2 == AdapterFont.this.selectedItemPosition ?  ContextCompat.getColor(AdapterFont.this.context,i): 0);
        }
    }
}
