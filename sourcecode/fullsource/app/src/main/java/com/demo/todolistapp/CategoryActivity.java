package com.demo.todolistapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.demo.todolistapp.R;
import com.demo.todolistapp.AdapterClasses.AllCategoryAdapter;
import com.demo.todolistapp.DatabaseClasses.MyCatgoryhelper;
import com.demo.todolistapp.DatabaseClasses.TaskHelper;
import com.demo.todolistapp.Lock.LockHolder;
import com.demo.todolistapp.Lock.PatternDialog;
import com.demo.todolistapp.Lock.SharedPrefrence;
import com.demo.todolistapp.Models.CategoryModel;
import com.demo.todolistapp.databinding.ActivityCategoryBinding;


public class CategoryActivity extends AppCompatActivity {
    ActivityCategoryBinding binding;
    MyCatgoryhelper db;
    TextView ok;
    TaskHelper taskHelper;
    boolean isBackpressed = true;
    private boolean isActivityVisible = false;

    
    @Override 
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ActivityCategoryBinding inflate = ActivityCategoryBinding.inflate(getLayoutInflater());
        this.binding = inflate;
        setContentView(inflate.getRoot());

        AdsGoogle adsGoogle = new AdsGoogle(this);
        adsGoogle.Banner_Show((RelativeLayout) findViewById(R.id.banner), this);
        adsGoogle.Interstitial_Show_Counter(this);


        setStatusBarTransparentBlack();
        settheme();
        this.db = new MyCatgoryhelper(this);
        this.taskHelper = new TaskHelper(this);
        this.binding.back.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                CategoryActivity.this.onBackPressed();
            }
        });
        fetchData();
        this.binding.add.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                CategoryActivity.this.addCategory();
            }
        });
    }



    public void addCategory() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_box_category, (ViewGroup) null);
        builder.setView(inflate);
        final AlertDialog create = builder.create();
        this.ok = (TextView) inflate.findViewById(R.id.yes);
        TextView textView = (TextView) inflate.findViewById(R.id.no);
        final EditText editText = (EditText) inflate.findViewById(R.id.categoryName);
        settheme();
        this.ok.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setName(editText.getText().toString());
                CategoryActivity.this.db.addCategory(categoryModel);
                CategoryActivity.this.fetchData();
                create.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { 
            @Override 
            public void onClick(View view) {
                create.dismiss();
            }
        });
        create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        create.show();
    }

    public void fetchData() {
        AllCategoryAdapter allCategoryAdapter = new AllCategoryAdapter(this.db.getAllCategory(), this) { 
            @Override 
            public void onDelete(final CategoryModel categoryModel) {
                new AlertDialog.Builder(CategoryActivity.this).setTitle(R.string.delete).setMessage(R.string.are_you_sure_you_want_to_delete).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i) {
                        CategoryActivity.this.db.deletCategoryById(categoryModel.getId());
                        CategoryActivity.this.taskHelper.deleteNoteByCategory(categoryModel.getId());
                        CategoryActivity.this.fetchData();
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() { 
                    @Override 
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
            }

            @Override 
            public void onEdit(final CategoryModel categoryModel) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CategoryActivity.this);
                View inflate = LayoutInflater.from(CategoryActivity.this).inflate(R.layout.dialog_box_category, (ViewGroup) null);
                builder.setView(inflate);
                final AlertDialog create = builder.create();
                CategoryActivity.this.ok = (TextView) inflate.findViewById(R.id.yes);
                TextView textView = (TextView) inflate.findViewById(R.id.no);
                final EditText editText = (EditText) inflate.findViewById(R.id.categoryName);
                editText.setText(categoryModel.getName());
                CategoryActivity.this.settheme();
                CategoryActivity.this.ok.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        CategoryModel categoryModel2 = new CategoryModel();
                        categoryModel2.setName(editText.getText().toString());
                        categoryModel2.setId(categoryModel.getId());
                        if (CategoryActivity.this.db.isCategoryExists(editText.getText().toString())) {
                            Toast.makeText(CategoryActivity.this, CategoryActivity.this.getString(R.string.category_is_already_exist), Toast.LENGTH_SHORT).show();
                        } else {
                            CategoryActivity.this.db.updateCategory(categoryModel2);
                            CategoryActivity.this.fetchData();
                        }
                        create.dismiss();
                    }
                });
                textView.setOnClickListener(new View.OnClickListener() { 
                    @Override 
                    public void onClick(View view) {
                        create.dismiss();
                    }
                });
                create.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                create.show();
            }
        };
        this.binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.binding.recyclerView.setAdapter(allCategoryAdapter);
    }

    private void setStatusBarTransparentBlack() {
        Window window = getWindow();
        window.addFlags(Integer.MIN_VALUE);
        window.clearFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        window.setStatusBarColor(0);
        window.getDecorView().setSystemUiVisibility(9472);
    }

    public void settheme() {
        int color;
        int color2;
        int color3;
        int color4;
        int color5;
        int color6;
        int color7;
        int color8;
        int color9;
        int color10;
        int color11;
        int color12;
        int color13;
        int color14;
        int color15;
        int color16;
        int color17;
        int i = getSharedPreferences("my_prefs", 0).getInt("my_key", 1);
        if (i == 0) {
            ischeckedColor(R.color.green);
        }
        if (i == 1 || i == 0) {
            color = getColor(R.color.white);
            setbackgroundcolor(color);
            ischeckedColor(R.color.green);
        } else if (i == 2) {
            color17 = getColor(R.color.white);
            setbackgroundcolor(color17);
            ischeckedColor(R.color.pink);
        } else if (i == 3) {
            color16 = getColor(R.color.white);
            setbackgroundcolor(color16);
            ischeckedColor(R.color.blue);
        } else if (i == 4) {
            color15 = getColor(R.color.white);
            setbackgroundcolor(color15);
            ischeckedColor(R.color.purple);
        } else if (i == 5) {
            color14 = getColor(R.color.black);
            setbackgroundcolor(color14);
            ischeckedColor(R.color.purple);
        } else if (i == 6) {
            color13 = getColor(R.color.white);
            setbackgroundcolor(color13);
            ischeckedColor(R.color.parrot);
        } else if (i == 7) {
            color12 = getColor(R.color.white);
            setbackgroundcolor(color12);
        } else if (i == 8) {
            color11 = getColor(R.color.white);
            setbackgroundcolor(color11);
            ischeckedColor(R.color.themedark8);
        } else if (i == 9) {
            color10 = getColor(R.color.white);
            setbackgroundcolor(color10);
            ischeckedColor(R.color.themedark9);
        } else if (i == 10) {
            color9 = getColor(R.color.white);
            setbackgroundcolor(color9);
            ischeckedColor(R.color.themedark10);
        } else if (i == 11) {
            color8 = getColor(R.color.white);
            setbackgroundcolor(color8);
            ischeckedColor(R.color.themedark11);
        } else if (i == 12) {
            color7 = getColor(R.color.white);
            setbackgroundcolor(color7);
            ischeckedColor(R.color.themedark12);
        } else if (i == 13) {
            color6 = getColor(R.color.white);
            setbackgroundcolor(color6);
            ischeckedColor(R.color.themedark13);
        } else if (i == 14) {
            color5 = getColor(R.color.white);
            setbackgroundcolor(color5);
            ischeckedColor(R.color.themedark14);
        } else if (i == 15) {
            color4 = getColor(R.color.white);
            setbackgroundcolor(color4);
            ischeckedColor(R.color.themedark15);
        } else if (i == 16) {
            ischeckedColor(R.color.themedark16);
            color3 = getColor(R.color.white);
            setbackgroundcolor(color3);
        } else if (i == 17) {
            color2 = getColor(R.color.white);
            setbackgroundcolor(color2);
            ischeckedColor(R.color.themedark17);
        }
        if (i == 5) {
            textcolors(R.color.white);
        } else {
            textcolors(R.color.black);
        }
    }

    private void setbackgroundcolor(int i) {
        this.binding.mainActivity.setBackgroundColor(i);
    }

    private void ischeckedColor(int i) {
        this.binding.plus.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
        this.binding.create.setTextColor(getResources().getColor(i));
        TextView textView = this.ok;
        if (textView != null) {
            textView.setTextColor(getResources().getColor(i));
        }
    }

    private void textcolors(int i) {
        this.binding.fdname.setTextColor(getResources().getColor(i));
        this.binding.back.setImageTintList(ColorStateList.valueOf(getResources().getColor(i)));
    }

    @Override 
    public void onBackPressed() {
        setResult(-1, new Intent());
        this.isBackpressed = false;
        LockHolder.getInstance().setboolean(false);
        finish();
    }

    
    @Override 
    public void onPause() {
        super.onPause();
        if (this.isBackpressed) {
            LockHolder.getInstance().setboolean(true);
        }
    }

    
    @Override 
    public void onStart() {
        super.onStart();
        boolean z = LockHolder.getInstance().getboolean();
        if (SharedPrefrence.getPasswordSwitch(this) && !SharedPrefrence.getSavedPattern(this).isEmpty() && z && this.isActivityVisible) {
            new PatternDialog(this).showDialog();
        }
        LockHolder.getInstance().setboolean(true);
        this.isActivityVisible = false;
    }

    
    @Override 
    public void onStop() {
        super.onStop();
        this.isActivityVisible = true;
    }
}
