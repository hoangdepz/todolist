package com.hieupro.todolistapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.hieupro.todolistapp.Listeners.Category;
import com.hieupro.todolistapp.Models.CategoryModel;
import com.hieupro.todolistapp.R;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    Category category;
    boolean check;
    Context context;
    boolean isAllCategoriesMode;
    List<CategoryModel> list;
    private int selectedPosition = 0;
    private long selectedCategoryName = -1;

    public CategoryAdapter(Context context, List<CategoryModel> list, boolean isAllMode, Category category) {
        this.context = context;
        this.list = list;
        this.isAllCategoriesMode = isAllMode;
        this.category = category;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new viewHolder(LayoutInflater.from(this.context).inflate(R.layout.category_item, viewGroup, false));
    }

    public void setCategoryNameAndSelection(long j, boolean z) {
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getId() == j) {
                int i2 = this.selectedPosition;
                this.selectedPosition = i;
                notifyItemChanged(i2);
                notifyItemChanged(this.selectedPosition);
                this.selectedCategoryName = j;
                this.check = z;
                return;
            }
        }
    }

    @Override 
    public void onBindViewHolder(final viewHolder viewholder, @SuppressLint("RecyclerView") final int position) {
        int color;
        int color2;
        final CategoryModel categoryModel = this.list.get(position);
        viewholder.toggleButton.setTextOn(categoryModel.getName());
        viewholder.toggleButton.setTextOff(categoryModel.getName());
        viewholder.toggleButton.setText(categoryModel.getName());
        int selectedThemePrefId = this.context.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        boolean isSelectedItem = (this.selectedPosition == position);
        if (isSelectedItem) {
            if (selectedThemePrefId == 0 || selectedThemePrefId == 1) {
                viewholder.setSelected(R.color.green);
            } else if (selectedThemePrefId == 2) {
                viewholder.setSelected(R.color.pink);
            } else if (selectedThemePrefId == 3) {
                viewholder.setSelected(R.color.blue);
            } else if (selectedThemePrefId == 4) {
                viewholder.setSelected(R.color.purple);
            } else if (selectedThemePrefId == 5) {
                viewholder.setSelected(R.color.purple);
            } else if (selectedThemePrefId == 6) {
                viewholder.setSelected(R.color.parrot);
            } else if (selectedThemePrefId == 7) {
                viewholder.setSelected(R.color.themedark7);
            } else if (selectedThemePrefId == 8) {
                viewholder.setSelected(R.color.themedark8);
            } else if (selectedThemePrefId == 9) {
                viewholder.setSelected(R.color.themedark9);
            } else if (selectedThemePrefId == 10) {
                viewholder.setSelected(R.color.themedark10);
            } else if (selectedThemePrefId == 11) {
                viewholder.setSelected(R.color.themedark11);
            } else if (selectedThemePrefId == 12) {
                viewholder.setSelected(R.color.themedark12);
            } else if (selectedThemePrefId == 13) {
                viewholder.setSelected(R.color.themedark13);
            } else if (selectedThemePrefId == 14) {
                viewholder.setSelected(R.color.themedark14);
            } else if (selectedThemePrefId == 15) {
                viewholder.setSelected(R.color.themedark15);
            } else if (selectedThemePrefId == 16) {
                viewholder.setSelected(R.color.themedark16);
            } else if (selectedThemePrefId == 17) {
                viewholder.setSelected(R.color.themedark17);
            }
            ToggleButton toggleButton = viewholder.toggleButton;
            color2 = ContextCompat.getColor(this.context, R.color.white);
            toggleButton.setTextColor(color2);
        } else {
            if (selectedThemePrefId == 0 || selectedThemePrefId == 1) {
                viewholder.setSelected(R.color.light_green);
            } else if (selectedThemePrefId == 2) {
                viewholder.setSelected(R.color.light_pink);
            } else if (selectedThemePrefId == 3) {
                viewholder.setSelected(R.color.light_blue);
            } else if (selectedThemePrefId == 4) {
                viewholder.setSelected(R.color.light_purple);
            } else if (selectedThemePrefId == 5) {
                viewholder.setSelected(R.color.light_purple);
            } else if (selectedThemePrefId == 6) {
                viewholder.setSelected(R.color.light_parrot);
            } else if (selectedThemePrefId == 7) {
                viewholder.setSelected(R.color.themelight7);
            } else if (selectedThemePrefId == 8) {
                viewholder.setSelected(R.color.themelight8);
            } else if (selectedThemePrefId == 9) {
                viewholder.setSelected(R.color.themelight9);
            } else if (selectedThemePrefId == 10) {
                viewholder.setSelected(R.color.themelight10);
            } else if (selectedThemePrefId == 11) {
                viewholder.setSelected(R.color.themelight11);
            } else if (selectedThemePrefId == 12) {
                viewholder.setSelected(R.color.themelight12);
            } else if (selectedThemePrefId == 13) {
                viewholder.setSelected(R.color.themelight13);
            } else if (selectedThemePrefId == 14) {
                viewholder.setSelected(R.color.themelight14);
            } else if (selectedThemePrefId == 15) {
                viewholder.setSelected(R.color.themelight15);
            } else if (selectedThemePrefId == 16) {
                viewholder.setSelected(R.color.themelight16);
            } else if (selectedThemePrefId == 17) {
                viewholder.setSelected(R.color.themelight17);
            }
            ToggleButton toggleButton2 = viewholder.toggleButton;
            color = ContextCompat.getColor(this.context, R.color.black);
            toggleButton2.setTextColor(color);
        }
        if (this.isAllCategoriesMode && this.check && this.selectedCategoryName != -1) {
            CategoryModel categoryModel2 = new CategoryModel();
            categoryModel2.setId(this.selectedCategoryName);
            this.category.category(categoryModel2);
        }
        if (this.isAllCategoriesMode) {
            if (position == 0) {
                viewholder.toggleButton.setText(R.string.none);
                viewholder.toggleButton.setTextOff(this.context.getString(R.string.none));
                viewholder.toggleButton.setTextOn(this.context.getString(R.string.none));
            }
        } else if (position == 0) {
            viewholder.toggleButton.setText(R.string.all);
            viewholder.toggleButton.setTextOff(this.context.getString(R.string.all));
            viewholder.toggleButton.setTextOn(this.context.getString(R.string.all));
        }
        if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Work")) {
            viewholder.toggleButton.setText(R.string.work);
            viewholder.toggleButton.setTextOff(this.context.getString(R.string.work));
            viewholder.toggleButton.setTextOn(this.context.getString(R.string.work));
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Personal")) {
            viewholder.toggleButton.setText(R.string.personal);
            viewholder.toggleButton.setTextOff(this.context.getString(R.string.personal));
            viewholder.toggleButton.setTextOn(this.context.getString(R.string.personal));
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Wishlist")) {
            viewholder.toggleButton.setText(R.string.wishlist);
            viewholder.toggleButton.setTextOff(this.context.getString(R.string.wishlist));
            viewholder.toggleButton.setTextOn(this.context.getString(R.string.wishlist));
        } else if (categoryModel.getBuiltIn() == 2 && categoryModel.getName().equals("Birthday")) {
            viewholder.toggleButton.setText(R.string.birthday);
            viewholder.toggleButton.setTextOff(this.context.getString(R.string.birthday));
            viewholder.toggleButton.setTextOn(this.context.getString(R.string.birthday));
        }
        viewholder.toggleButton.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                int i3 = CategoryAdapter.this.selectedPosition;
                CategoryAdapter.this.selectedPosition = viewholder.getAdapterPosition();
                CategoryAdapter.this.notifyItemChanged(i3);
                CategoryAdapter categoryAdapter = CategoryAdapter.this;
                categoryAdapter.notifyItemChanged(categoryAdapter.selectedPosition);
                if (CategoryAdapter.this.isAllCategoriesMode) {
                    CategoryAdapter.this.category.category(categoryModel);
                    return;
                }
                if (position == 0) {
                    CategoryModel categoryModel3 = new CategoryModel();
                    categoryModel3.setName(CategoryAdapter.this.context.getString(R.string.all));
                    categoryModel3.setId(categoryModel.getId());
                    CategoryAdapter.this.category.category(categoryModel3);
                    return;
                }
                CategoryAdapter.this.category.category(categoryModel);
            }
        });
    }

    @Override 
    public int getItemCount() {
        return this.list.size();
    }

    
    public class viewHolder extends RecyclerView.ViewHolder {
        ToggleButton toggleButton;

        public viewHolder(View view) {
            super(view);
            this.toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
        }

        public void setSelected(int i) {
            this.toggleButton.setBackgroundTintList(ContextCompat.getColorStateList(CategoryAdapter.this.context, i));
        }
    }
}
