package com.demo.exampleapp.AdapterClasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.exampleapp.DatabaseClasses.TaskHelper;
import com.demo.exampleapp.Models.CategoryModel;
import com.demo.exampleapp.R;
import java.util.List;


public abstract class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.viewHolder> {
    Context context;
    List<CategoryModel> list;
    int theme;

    public abstract void onDelete(CategoryModel categoryModel);

    public abstract void onEdit(CategoryModel categoryModel);

    public AllCategoryAdapter(List<CategoryModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override 
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.all_category, viewGroup, false));
    }

    @Override 
    public void onBindViewHolder(viewHolder viewholder, int i) {
        final PopupMenu popupMenu;
        final CategoryModel categoryModel = this.list.get(i);
        viewholder.setheme();
        viewholder.categoryName.setText(categoryModel.getName());
        String valueOf = String.valueOf(new TaskHelper(this.context).getTaskbyCategoryNameforlist(categoryModel.getId()).size());
        viewholder.itemView.setVisibility(View.GONE);
        viewholder.categorySize.setText(valueOf);
        if (i == 0) {
            viewholder.itemView.setVisibility(View.GONE);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewholder.itemView.getLayoutParams();
            layoutParams.height = 0;
            layoutParams.width = 0;
            viewholder.itemView.setLayoutParams(layoutParams);
        } else {
            viewholder.itemView.setVisibility(View.VISIBLE);
            RecyclerView.LayoutParams layoutParams2 = (RecyclerView.LayoutParams) viewholder.itemView.getLayoutParams();
            layoutParams2.height = -2;
            layoutParams2.width = -1;
            viewholder.itemView.setLayoutParams(layoutParams2);
        }
        if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Work")) {
            viewholder.categoryName.setText(R.string.work);
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Personal")) {
            viewholder.categoryName.setText(R.string.personal);
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Wishlist")) {
            viewholder.categoryName.setText(R.string.wishlist);
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Birthday")) {
            viewholder.categoryName.setText(R.string.birthday);
        }
        if (this.theme == 5) {
            popupMenu = new PopupMenu(this.context, viewholder.menu, GravityCompat.END, R.style.PopupMenuStyleBlack, R.style.PopupMenuStyleBlack);
        } else {
            popupMenu = new PopupMenu(this.context, viewholder.menu, GravityCompat.END, R.style.PopupMenuStylewhite, R.style.PopupMenuStylewhite);
        }
        popupMenu.getMenuInflater().inflate(R.menu.categorymenu, popupMenu.getMenu());
        Menu menu = popupMenu.getMenu();
        for (int i2 = 0; i2 < menu.size(); i2++) {
            MenuItem item = menu.getItem(i2);
            SpannableString spannableString = new SpannableString(item.getTitle());
            if (this.theme == 5) {
                spannableString.setSpan(new ForegroundColorSpan(-1), 0, spannableString.length(), 18);
            } else {
                spannableString.setSpan(new ForegroundColorSpan(ViewCompat.MEASURED_STATE_MASK), 0, spannableString.length(), 18);
            }
            item.setTitle(spannableString);
        }
        viewholder.menu.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() { 
                    @Override 
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        int itemId = menuItem.getItemId();
                        if (itemId == R.id.edit) {
                            AllCategoryAdapter.this.onEdit(categoryModel);
                            popupMenu.dismiss();
                            return true;
                        }
                        if (itemId != R.id.deletect) {
                            return false;
                        }
                        AllCategoryAdapter.this.onDelete(categoryModel);
                        popupMenu.dismiss();
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
    public class viewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        TextView categorySize;
        ImageView menu;

        public viewHolder(View view) {
            super(view);
            this.categorySize = (TextView) view.findViewById(R.id.size);
            this.categoryName = (TextView) view.findViewById(R.id.categoryName);
            this.menu = (ImageView) view.findViewById(R.id.menu);
        }

        public void setheme() {
            int color;
            int color2;
            int color3;
            int color4;
            int color5;
            int color6;
            Context context = AllCategoryAdapter.this.context;
            Context context2 = AllCategoryAdapter.this.context;
            SharedPreferences sharedPreferences = context.getSharedPreferences("my_prefs", 0);
            AllCategoryAdapter.this.theme = sharedPreferences.getInt("my_key", 1);
            if (AllCategoryAdapter.this.theme == 5) {
                TextView textView = this.categoryName;
                color4 = AllCategoryAdapter.this.context.getColor(R.color.white);
                textView.setTextColor(color4);
                TextView textView2 = this.categorySize;
                color5 = AllCategoryAdapter.this.context.getColor(R.color.white);
                textView2.setTextColor(color5);
                ImageView imageView = this.menu;
                color6 = AllCategoryAdapter.this.context.getColor(R.color.white);
                imageView.setImageTintList(ColorStateList.valueOf(color6));
                return;
            }
            TextView textView3 = this.categoryName;
            color = AllCategoryAdapter.this.context.getColor(R.color.black);
            textView3.setTextColor(color);
            TextView textView4 = this.categorySize;
            color2 = AllCategoryAdapter.this.context.getColor(R.color.black);
            textView4.setTextColor(color2);
            ImageView imageView2 = this.menu;
            color3 = AllCategoryAdapter.this.context.getColor(R.color.black);
            imageView2.setImageTintList(ColorStateList.valueOf(color3));
        }
    }
}
