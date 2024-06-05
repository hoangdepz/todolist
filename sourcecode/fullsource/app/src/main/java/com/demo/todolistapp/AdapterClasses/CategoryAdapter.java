package com.demo.todolistapp.AdapterClasses;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.demo.todolistapp.Listeners.Category;
import com.demo.todolistapp.Models.CategoryModel;
import com.demo.todolistapp.R;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {
    Category category;
    boolean check;
    Context context;
    boolean isTrue;
    List<CategoryModel> list;
    private int selectedPosition = 0;
    private long selectedCategoryName = -1;

    public CategoryAdapter(Context context, List<CategoryModel> list, boolean z, Category category) {
        this.context = context;
        this.list = list;
        this.isTrue = z;
        this.category = category;
    }

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
    public void onBindViewHolder(final viewHolder viewholder, @SuppressLint("RecyclerView") final int i) {
        int color;
        int color2;
        final CategoryModel categoryModel = this.list.get(i);
        viewholder.toggleButton.setTextOn(categoryModel.getName());
        viewholder.toggleButton.setTextOff(categoryModel.getName());
        viewholder.toggleButton.setText(categoryModel.getName());
        int i2 = this.context.getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (this.selectedPosition == i) {
            if (i2 == 0) {
                viewholder.selected(R.color.green);
            }
            if (i2 == 1) {
                viewholder.selected(R.color.green);
            } else if (i2 == 2) {
                viewholder.selected(R.color.pink);
            } else if (i2 == 3) {
                viewholder.selected(R.color.blue);
            } else if (i2 == 4) {
                viewholder.selected(R.color.purple);
            } else if (i2 == 5) {
                viewholder.selected(R.color.purple);
            } else if (i2 == 6) {
                viewholder.selected(R.color.parrot);
            } else if (i2 == 7) {
                viewholder.selected(R.color.themedark7);
            } else if (i2 == 8) {
                viewholder.selected(R.color.themedark8);
            } else if (i2 == 9) {
                viewholder.selected(R.color.themedark9);
            } else if (i2 == 10) {
                viewholder.selected(R.color.themedark10);
            } else if (i2 == 11) {
                viewholder.selected(R.color.themedark11);
            } else if (i2 == 12) {
                viewholder.selected(R.color.themedark12);
            } else if (i2 == 13) {
                viewholder.selected(R.color.themedark13);
            } else if (i2 == 14) {
                viewholder.selected(R.color.themedark14);
            } else if (i2 == 15) {
                viewholder.selected(R.color.themedark15);
            } else if (i2 == 16) {
                viewholder.selected(R.color.themedark16);
            } else if (i2 == 17) {
                viewholder.selected(R.color.themedark17);
            }
            ToggleButton toggleButton = viewholder.toggleButton;
            color2 = ContextCompat.getColor(this.context, R.color.white);
            toggleButton.setTextColor(color2);
        } else {
            if (i2 == 0) {
                viewholder.selected(R.color.light_green);
            }
            if (i2 == 1) {
                viewholder.selected(R.color.light_green);
            } else if (i2 == 2) {
                viewholder.selected(R.color.light_pink);
            } else if (i2 == 3) {
                viewholder.selected(R.color.light_blue);
            } else if (i2 == 4) {
                viewholder.selected(R.color.light_purple);
            } else if (i2 == 5) {
                viewholder.selected(R.color.light_purple);
            } else if (i2 == 6) {
                viewholder.selected(R.color.light_parrot);
            } else if (i2 == 7) {
                viewholder.selected(R.color.themelight7);
            } else if (i2 == 8) {
                viewholder.selected(R.color.themelight8);
            } else if (i2 == 9) {
                viewholder.selected(R.color.themelight9);
            } else if (i2 == 10) {
                viewholder.selected(R.color.themelight10);
            } else if (i2 == 11) {
                viewholder.selected(R.color.themelight11);
            } else if (i2 == 12) {
                viewholder.selected(R.color.themelight12);
            } else if (i2 == 13) {
                viewholder.selected(R.color.themelight13);
            } else if (i2 == 14) {
                viewholder.selected(R.color.themelight14);
            } else if (i2 == 15) {
                viewholder.selected(R.color.themelight15);
            } else if (i2 == 16) {
                viewholder.selected(R.color.themelight16);
            } else if (i2 == 17) {
                viewholder.selected(R.color.themelight17);
            }
            ToggleButton toggleButton2 = viewholder.toggleButton;
            color = ContextCompat.getColor(this.context, R.color.black);
            toggleButton2.setTextColor(color);
        }
        if (this.isTrue && this.check && this.selectedCategoryName != -1) {
            CategoryModel categoryModel2 = new CategoryModel();
            categoryModel2.setId(this.selectedCategoryName);
            this.category.category(categoryModel2);
        }
        if (this.isTrue) {
            if (i == 0) {
                viewholder.toggleButton.setText(R.string.none);
                viewholder.toggleButton.setTextOff(this.context.getString(R.string.none));
                viewholder.toggleButton.setTextOn(this.context.getString(R.string.none));
            }
        } else if (i == 0) {
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
                if (CategoryAdapter.this.isTrue) {
                    CategoryAdapter.this.category.category(categoryModel);
                    return;
                }
                if (i == 0) {
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

        public void selected(int i) {
            this.toggleButton.setBackgroundTintList(ContextCompat.getColorStateList(CategoryAdapter.this.context, i));
        }
    }
}
