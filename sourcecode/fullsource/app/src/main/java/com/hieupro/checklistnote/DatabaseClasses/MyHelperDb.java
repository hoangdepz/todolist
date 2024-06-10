package com.hieupro.checklistnote.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.hieupro.checklistnote.Models.Notes;
import com.hieupro.checklistnote.paramsClasses.Param;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class MyHelperDb extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public MyHelperDb(Context context) {
        super(context, Param.DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE notes_table(id INTEGER PRIMARY KEY,title TEXT, content TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,background_image TEXT,image Text,reminder INTEGER ,folder_name Text,font_name Text,color_name Text,gravity INTEGER,isdark INTEGER DEFAULT 0)");
        sQLiteDatabase.execSQL("CREATE TABLE notes_table_trash(id INTEGER PRIMARY KEY,title TEXT, content TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,background_image TEXT,image Text,reminder INTEGER ,folder_name Text,font_name Text,color_name Text,gravity INTEGER)");
        sQLiteDatabase.execSQL("CREATE TABLE notes_table_archived(id INTEGER PRIMARY KEY,title TEXT, content TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,background_image TEXT,image Text,reminder INTEGER ,folder_name Text,font_name Text,color_name Text,gravity INTEGER)");
    }

    public Notes getNoteById(long j) {
        Notes notes;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor query = readableDatabase.query(Param.TABLE_NAME, new String[]{"id", Param.KEY_TITLE, Param.KEY_CONTENT, "image", Param.KEY_BACKGROUND_IMAGE, "reminder", Param.FOLDER_NAME, "font_name", "color_name", Param.GRAVITY, "timestamp"}, "id=?", new String[]{String.valueOf(j)}, null, null, null, null);
        if (query == null || !query.moveToFirst()) {
            notes = null;
        } else {
            notes = new Notes();
            notes.setId(Integer.parseInt(query.getString(0)));
            notes.setTitle(query.getString(1));
            notes.setContent(query.getString(2));
            notes.setImage(query.getString(3));
            notes.setColor(query.getString(4));
            notes.setReminderDate(Long.parseLong(query.getString(5)));
            notes.setFontName(query.getString(6));
            notes.setFolderName(query.getString(7));
            notes.setTextColor(Integer.parseInt(query.getString(8)));
            notes.setGravity(Integer.parseInt(query.getString(9)));
            notes.setTimestamp(query.getLong(10));
            query.close();
        }
        readableDatabase.close();
        return notes;
    }

    public long addNotes(Notes notes) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Param.KEY_TITLE, notes.getTitle());
        contentValues.put(Param.KEY_CONTENT, notes.getContent());
        contentValues.put("reminder", Long.valueOf(notes.getReminderDate()));
        if (notes.getTimestamp() > 0) {
            contentValues.put("timestamp", Long.valueOf(notes.getTimestamp()));
            Log.d("Condition", "if" + notes.getTimestamp());
        } else {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date());
            contentValues.put("timestamp", format);
            Log.d("Condition", "else" + format);
        }
        contentValues.put(Param.KEY_BACKGROUND_IMAGE, notes.getColor());
        contentValues.put("image", notes.getImage());
        contentValues.put(Param.FOLDER_NAME, notes.getFolderName());
        contentValues.put("font_name", notes.getFontName());
        contentValues.put("color_name", Integer.valueOf(notes.getTextColor()));
        contentValues.put(Param.GRAVITY, Integer.valueOf(notes.getGravity()));
        contentValues.put(Param.ISDARK, Integer.valueOf(notes.getIsDark()));
        long insertOrThrow = writableDatabase.insertOrThrow(Param.TABLE_NAME, null, contentValues);
        writableDatabase.close();
        return insertOrThrow;
    }

    public long addTrash(Notes notes) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Param.KEY_TITLE, notes.getTitle());
        contentValues.put(Param.KEY_CONTENT, notes.getContent());
        contentValues.put("reminder", Long.valueOf(notes.getReminderDate()));
        contentValues.put("timestamp", Long.valueOf(notes.getTimestamp()));
        contentValues.put(Param.KEY_BACKGROUND_IMAGE, notes.getColor());
        contentValues.put("image", notes.getImage());
        contentValues.put(Param.FOLDER_NAME, notes.getFolderName());
        contentValues.put("font_name", notes.getFontName());
        contentValues.put("color_name", Integer.valueOf(notes.getTextColor()));
        contentValues.put(Param.GRAVITY, Integer.valueOf(notes.getGravity()));
        contentValues.put(Param.ISDARK, Integer.valueOf(notes.isDark));
        long insert = writableDatabase.insert("notes_table_trash", null, contentValues);
        writableDatabase.close();
        return insert;
    }

    public long addArchived(Notes notes) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Param.KEY_TITLE, notes.getTitle());
        contentValues.put(Param.KEY_CONTENT, notes.getContent());
        contentValues.put("reminder", Long.valueOf(notes.getReminderDate()));
        contentValues.put("timestamp", Long.valueOf(notes.getTimestamp()));
        contentValues.put(Param.KEY_BACKGROUND_IMAGE, notes.getColor());
        contentValues.put("image", notes.getImage());
        contentValues.put(Param.FOLDER_NAME, notes.getFolderName());
        contentValues.put("font_name", notes.getFontName());
        contentValues.put("color_name", Integer.valueOf(notes.getTextColor()));
        contentValues.put(Param.GRAVITY, Integer.valueOf(notes.getGravity()));
        long insert = writableDatabase.insert("notes_table_archived", null, contentValues);
        writableDatabase.close();
        return insert;
    }

    public List<Notes> getAllTrash() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table_trash", null);
        readableDatabase.setMaximumSize(104857600L);
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("cursorWindow");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                notes.setIsDark(Integer.parseInt(rawQuery.getString(11)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<Notes> getNotesForDateRange(long j, long j2) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table WHERE timestamp BETWEEN ? AND ?", new String[]{String.valueOf(j), String.valueOf(j2)});
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                notes.setIsDark(Integer.parseInt(rawQuery.getString(11)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<Notes> getAllArchived() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table_archived", null);
        readableDatabase.setMaximumSize(104857600L);
        try {
            Field declaredField = CursorWindow.class.getDeclaredField("cursorWindow");
            declaredField.setAccessible(true);
            declaredField.set(null, 104857600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public int updateNote(Notes notes) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Param.KEY_TITLE, notes.getTitle());
        contentValues.put(Param.KEY_CONTENT, notes.getContent());
        contentValues.put("reminder", Long.valueOf(notes.getReminderDate()));
        contentValues.put("timestamp", Long.valueOf(notes.getTimestamp()));
        contentValues.put(Param.KEY_BACKGROUND_IMAGE, notes.getColor());
        contentValues.put("image", notes.getImage());
        contentValues.put(Param.FOLDER_NAME, notes.getFolderName());
        contentValues.put("font_name", notes.getFontName());
        contentValues.put("color_name", Integer.valueOf(notes.getTextColor()));
        contentValues.put(Param.ISDARK, Integer.valueOf(notes.getIsDark()));
        if (notes.getGravity() != -1) {
            contentValues.put(Param.GRAVITY, Integer.valueOf(notes.getGravity()));
        }
        int update = writableDatabase.update(Param.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(notes.getId())});
        writableDatabase.close();
        return update;
    }

    public void deleteNoteById(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(Param.TABLE_NAME, "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void deleteTrashById(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("notes_table_trash", "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void deleteArchivedById(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("notes_table_archived", "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public List<Notes> getAllNotesAscending() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table ORDER BY timestamp ASC", null);
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                notes.setIsDark(Integer.parseInt(rawQuery.getString(11)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<Notes> getAllNotesDescending() {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table ORDER BY timestamp DESC", null);
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                notes.setIsDark(Integer.parseInt(rawQuery.getString(11)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<Notes> getNotesByFolderName(String str) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table WHERE folder_name=?", new String[]{str});
        if (rawQuery.moveToFirst()) {
            do {
                Notes notes = new Notes();
                notes.setId(Integer.parseInt(rawQuery.getString(0)));
                notes.setTitle(rawQuery.getString(1));
                notes.setContent(rawQuery.getString(2));
                notes.setTimestamp(rawQuery.getLong(3));
                notes.setColor(rawQuery.getString(4));
                notes.setImage(rawQuery.getString(5));
                notes.setReminderDate(Long.parseLong(rawQuery.getString(6)));
                notes.setFolderName(rawQuery.getString(7));
                notes.setFontName(rawQuery.getString(8));
                notes.setTextColor(Integer.parseInt(rawQuery.getString(9)));
                notes.setGravity(Integer.parseInt(rawQuery.getString(10)));
                arrayList.add(notes);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }
}