package com.demo.mynote.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.demo.mynote.R;
import com.demo.mynote.Models.CategoryModel;
import com.demo.mynote.paramsClasses.ParamsCategory;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class CategoryHelper extends SQLiteOpenHelper {
    Context context;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public CategoryHelper(Context context) {
        super(context, ParamsCategory.DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE category_table(id INTEGER PRIMARY KEY,name TEXT,built_in INTEGER)");
    }

    public long addCategory(CategoryModel categoryModel) {
        if (isCategoryExists(categoryModel.getName())) {
            Context context = this.context;
            Toast.makeText(context, context.getString(R.string.category_is_already_exist), Toast.LENGTH_SHORT).show();
            return -1L;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsCategory.NAME, categoryModel.getName());
        contentValues.put(ParamsCategory.BUILT_IN, Long.valueOf(categoryModel.getBuiltIn()));
        long insertOrThrow = writableDatabase.insertOrThrow(ParamsCategory.TABLE_NAME, null, contentValues);
        writableDatabase.close();
        return insertOrThrow;
    }

    public boolean isCategoryExists(String str) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM category_table WHERE name=?", new String[]{str});
        boolean moveToFirst = rawQuery.moveToFirst();
        rawQuery.close();
        readableDatabase.close();
        return moveToFirst;
    }

    public List<CategoryModel> getAllCategory() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM category_table", null);
        if (rawQuery.moveToFirst()) {
            do {
                CategoryModel categoryModel = new CategoryModel();
                categoryModel.setId(rawQuery.getLong(0));
                categoryModel.setName(rawQuery.getString(1));
                categoryModel.setBuiltIn(rawQuery.getLong(2));
                arrayList.add(categoryModel);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public int updateCategory(CategoryModel categoryModel) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsCategory.NAME, categoryModel.getName());
        int update = writableDatabase.update(ParamsCategory.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(categoryModel.getId())});
        writableDatabase.close();
        return update;
    }

    public void deletCategoryById(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(ParamsCategory.TABLE_NAME, "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }
}