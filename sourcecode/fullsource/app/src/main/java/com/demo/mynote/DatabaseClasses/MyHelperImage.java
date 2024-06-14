package com.demo.mynote.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.demo.mynote.Models.ImageModels;
import com.demo.mynote.paramsClasses.ParamsImgs;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class MyHelperImage extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public MyHelperImage(Context context) {
        super(context, ParamsImgs.DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE image_table(id INTEGER PRIMARY KEY,image Text,note_id INTEGER,image_text Text, recording Text,recording_text Text,font_name Text,color_name INTEGER,gravity_name Text,isDark INTEGER DEFAULT 0)");
    }

    public long addImageforNote(ImageModels imageModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", imageModels.getImage());
        contentValues.put(ParamsImgs.NOTE_ID, Long.valueOf(imageModels.getNoteId()));
        contentValues.put(ParamsImgs.KEY_IMAGE_TEXT, imageModels.getImageText());
        contentValues.put(ParamsImgs.KEY_Recording, imageModels.getFilepath());
        contentValues.put(ParamsImgs.KEY_Recording_TEXT, imageModels.getRecordingText());
        contentValues.put("font_name", imageModels.getFontName());
        contentValues.put(ParamsImgs.GRAVITY, Integer.valueOf(imageModels.getGravity()));
        contentValues.put(ParamsImgs.ISDARK, Integer.valueOf(imageModels.getIsDark()));
        long insert = writableDatabase.insert(ParamsImgs.TABLE_NAME, null, contentValues);
        writableDatabase.close();
        return insert;
    }

    public List<ImageModels> getImagesByNoteId(long j) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        readableDatabase.setMaximumSize(104857600L);
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM image_table WHERE note_id =?", new String[]{String.valueOf(j)});
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("cursorWindow");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rawQuery != null && rawQuery.moveToFirst()) {
            do {
                ImageModels imageModels = new ImageModels();
                imageModels.setId(Long.parseLong(rawQuery.getString(0)));
                imageModels.setImage(rawQuery.getString(1));
                imageModels.setNoteId(Long.parseLong(rawQuery.getString(2)));
                imageModels.setImageText(rawQuery.getString(3));
                imageModels.setFilepath(rawQuery.getString(4));
                imageModels.setRecordingText(rawQuery.getString(5));
                imageModels.setFontName(rawQuery.getString(6));
                imageModels.setGravity(rawQuery.getInt(7));
                imageModels.setIsDark(rawQuery.getInt(8));
                arrayList.add(imageModels);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            Log.d("IMAGE", "" + arrayList.toString());
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        Log.d("IMAGE", "" + arrayList.toString());
        return arrayList;
    }

    public String getImageTextById(long j) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT image_text FROM image_table WHERE id =?", new String[]{String.valueOf(j)});
        readableDatabase.setMaximumSize(104857600L);
        String string = rawQuery.moveToFirst() ? rawQuery.getString(rawQuery.getColumnIndex(ParamsImgs.KEY_IMAGE_TEXT)) : "";
        rawQuery.close();
        readableDatabase.close();
        return string;
    }

    public String getRecordingTextById(long j) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT recording_text FROM image_table WHERE id =?", new String[]{String.valueOf(j)});
        readableDatabase.setMaximumSize(104857600L);
        String string = rawQuery.moveToFirst() ? rawQuery.getString(rawQuery.getColumnIndex(ParamsImgs.KEY_Recording_TEXT)) : "";
        rawQuery.close();
        readableDatabase.close();
        return string;
    }

    public int updateImage(ImageModels imageModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("image", imageModels.getImage());
        contentValues.put(ParamsImgs.KEY_IMAGE_TEXT, imageModels.getImageText());
        contentValues.put(ParamsImgs.KEY_Recording, imageModels.getFilepath());
        contentValues.put(ParamsImgs.KEY_Recording_TEXT, imageModels.getRecordingText());
        contentValues.put("font_name", imageModels.getFontName());
        contentValues.put(ParamsImgs.GRAVITY, Integer.valueOf(imageModels.getGravity()));
        contentValues.put(ParamsImgs.ISDARK, Integer.valueOf(imageModels.getIsDark()));
        int update = writableDatabase.update(ParamsImgs.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(imageModels.getId())});
        writableDatabase.close();
        return update;
    }

    public void deleteImageById(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(ParamsImgs.TABLE_NAME, "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }
}