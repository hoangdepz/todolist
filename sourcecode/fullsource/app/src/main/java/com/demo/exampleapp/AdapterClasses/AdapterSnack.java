package com.demo.exampleapp.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.demo.exampleapp.Listeners.ColorClickListeners;
import com.demo.exampleapp.R;
import java.util.ArrayList;


public class AdapterSnack extends RecyclerView.Adapter<AdapterSnack.snackViewHolder> {
    private ColorClickListeners colorClickListener;
    Context context;
    ArrayList<Integer> list;
    private int position2;
    private int selectedItemPosition = -1;

    public AdapterSnack(ArrayList<Integer> arrayList, Context context, ColorClickListeners colorClickListeners) {
        this.list = arrayList;
        this.context = context;
        this.colorClickListener = colorClickListeners;
    }

    @Override 
    public snackViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new snackViewHolder(LayoutInflater.from(this.context).inflate(R.layout.snack_recyclerview_items, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(final snackViewHolder snackviewholder, final int i) {
        final int intValue = this.list.get(i).intValue();
        snackviewholder.imageView.setImageResource(intValue);
        snackviewholder.cardView.setStrokeWidth(5);
        this.position2 = i;
        snackviewholder.settheme();
        snackviewholder.itemView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                int i2 = AdapterSnack.this.selectedItemPosition;
                AdapterSnack.this.selectedItemPosition = snackviewholder.getAdapterPosition();
                AdapterSnack.this.notifyItemChanged(i2);
                AdapterSnack adapterSnack = AdapterSnack.this;
                adapterSnack.notifyItemChanged(adapterSnack.selectedItemPosition);
                AdapterSnack.this.colorClickListener.onColorClick(intValue, i);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
    public class snackViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;
        ImageView imageView;

        public snackViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.backgroundImage);
            this.cardView = (MaterialCardView) view.findViewById(R.id.cardView);
        }

        public void settheme() {
            Context context = AdapterSnack.this.context;
            Context context2 = AdapterSnack.this.context;
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
            this.cardView.setStrokeColor(AdapterSnack.this.position2 == AdapterSnack.this.selectedItemPosition ? AdapterSnack.this.context.getColor(i) : 0);
        }
    }
}
