package com.demo.exampleapp.DatabaseClasses;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.demo.exampleapp.Models.TODOModels;
import com.demo.exampleapp.paramsClasses.ParamsTasks;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* loaded from: classes2.dex */
public class TaskHelper extends SQLiteOpenHelper {
    Context context;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public TaskHelper(Context context) {
        super(context, ParamsTasks.DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
        this.context = context;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE task_table(id INTEGER PRIMARY KEY ,task_name Text,status Text,reminder INTEGER,switch_status INTEGER,todo_description TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,category INTEGER )");
        sQLiteDatabase.execSQL("CREATE TABLE notes_table_archived(id INTEGER PRIMARY KEY ,task_name Text,status Text,reminder INTEGER,switch_status INTEGER,todo_description TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,category INTEGER )");
        sQLiteDatabase.execSQL("CREATE TABLE notes_table_trash(id INTEGER PRIMARY KEY ,task_name Text,status Text,reminder INTEGER,switch_status INTEGER,todo_description TEXT,timestamp TEXT DEFAULT CURRENT_TIMESTAMP,category INTEGER )");
    }

    public List<TODOModels> getTaskForDateRange(long j, long j2) {
        ArrayList arrayList = new ArrayList();
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE timestamp BETWEEN ? AND ?", new String[]{String.valueOf(j), String.valueOf(j2)});
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public long addTask(TODOModels tODOModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsTasks.TASK, tODOModels.getTask());
        contentValues.put("status", Integer.valueOf(tODOModels.getStatus()));
        contentValues.put("reminder", Long.valueOf(tODOModels.getReminder()));
        contentValues.put(ParamsTasks.SWITCH_STATUS, Integer.valueOf(tODOModels.getSwitchStatus()));
        contentValues.put(ParamsTasks.TODO_DESCRIPTION, tODOModels.getDescription());
        contentValues.put(ParamsTasks.CATEGORY, Long.valueOf(tODOModels.getCategoryId()));
        if (tODOModels.getTimestamp() > 0) {
            contentValues.put("timestamp", Long.valueOf(tODOModels.getTimestamp()));
        } else {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date());
            contentValues.put("timestamp", format);
            Log.d("Condition", "else" + format);
        }
        long insert = writableDatabase.insert(ParamsTasks.TABLE_NAME, null, contentValues);
        Log.d("reminder", "Inserted noteId: " + insert);
        writableDatabase.close();
        return insert;
    }

    public long addTaskTrash(TODOModels tODOModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsTasks.TASK, tODOModels.getTask());
        contentValues.put("status", Integer.valueOf(tODOModels.getStatus()));
        contentValues.put("reminder", Long.valueOf(tODOModels.getReminder()));
        contentValues.put(ParamsTasks.SWITCH_STATUS, Integer.valueOf(tODOModels.getSwitchStatus()));
        contentValues.put(ParamsTasks.TODO_DESCRIPTION, tODOModels.getDescription());
        contentValues.put(ParamsTasks.CATEGORY, Long.valueOf(tODOModels.getCategoryId()));
        if (tODOModels.getTimestamp() > 0) {
            contentValues.put("timestamp", Long.valueOf(tODOModels.getTimestamp()));
        } else {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date());
            contentValues.put("timestamp", format);
            Log.d("Condition", "else" + format);
        }
        long insert = writableDatabase.insert("notes_table_trash", null, contentValues);
        Log.d("reminder", "Inserted noteId: " + insert);
        writableDatabase.close();
        return insert;
    }

    public long addTaskArchived(TODOModels tODOModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsTasks.TASK, tODOModels.getTask());
        contentValues.put("status", Integer.valueOf(tODOModels.getStatus()));
        contentValues.put("reminder", Long.valueOf(tODOModels.getReminder()));
        contentValues.put(ParamsTasks.SWITCH_STATUS, Integer.valueOf(tODOModels.getSwitchStatus()));
        contentValues.put(ParamsTasks.TODO_DESCRIPTION, tODOModels.getDescription());
        contentValues.put(ParamsTasks.CATEGORY, Long.valueOf(tODOModels.getCategoryId()));
        if (tODOModels.getTimestamp() > 0) {
            contentValues.put("timestamp", Long.valueOf(tODOModels.getTimestamp()));
        } else {
            String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).format(new Date());
            contentValues.put("timestamp", format);
            Log.d("Condition", "else" + format);
        }
        long insert = writableDatabase.insert("notes_table_archived", null, contentValues);
        Log.d("reminder", "Inserted noteId: " + insert);
        writableDatabase.close();
        return insert;
    }

    public TODOModels getTaskById(long j) {
        TODOModels tODOModels;
        SQLiteDatabase readableDatabase = getReadableDatabase();
        Cursor query = readableDatabase.query(ParamsTasks.TABLE_NAME, new String[]{"id", ParamsTasks.TASK, "status", "reminder", ParamsTasks.SWITCH_STATUS, ParamsTasks.TODO_DESCRIPTION, "timestamp", ParamsTasks.CATEGORY}, "id=?", new String[]{String.valueOf(j)}, null, null, null, null);
        if (query == null || !query.moveToFirst()) {
            tODOModels = null;
        } else {
            tODOModels = new TODOModels();
            tODOModels.setId(Integer.parseInt(query.getString(0)));
            tODOModels.setTask(query.getString(1));
            tODOModels.setStatus(Integer.parseInt(query.getString(2)));
            tODOModels.setReminder(Long.parseLong(query.getString(3)));
            tODOModels.setSwitchStatus(Integer.parseInt(query.getString(4)));
            tODOModels.setDescription(query.getString(5));
            tODOModels.setTimestamp(Long.parseLong(query.getString(6)));
            tODOModels.setCategoryId(Long.parseLong(query.getString(7)));
            query.close();
        }
        readableDatabase.close();
        return tODOModels;
    }

    public List<TODOModels> getAllCompletedTask() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE status = 1", null);
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            Log.d("reminder", "Task list size: " + arrayList.size());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        Log.d("reminder", "Task list size: " + arrayList.size());
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getAllUnCompletedTask() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE status = 0", null);
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            Log.d("reminder", "Task list size: " + arrayList.size());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        Log.d("reminder", "Task list size: " + arrayList.size());
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getAllTask() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table", null);
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            Log.d("reminder", "Task list size: " + arrayList.size());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        Log.d("reminder", "Task list size: " + arrayList.size());
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getAllTaskTrash() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table_trash", null);
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            Log.d("reminder", "Task list size: " + arrayList.size());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        Log.d("reminder", "Task list size: " + arrayList.size());
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getAllTaskArchived() {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM notes_table_archived", null);
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            Log.d("reminder", "Task list size: " + arrayList.size());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        Log.d("reminder", "Task list size: " + arrayList.size());
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public void updateStatus(long j, long j2) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("status", Long.valueOf(j2));
        writableDatabase.update(ParamsTasks.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public int updateTask(TODOModels tODOModels) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ParamsTasks.TASK, tODOModels.getTask());
        contentValues.put("reminder", Long.valueOf(tODOModels.getReminder()));
        contentValues.put(ParamsTasks.SWITCH_STATUS, Integer.valueOf(tODOModels.getSwitchStatus()));
        contentValues.put(ParamsTasks.TODO_DESCRIPTION, tODOModels.getDescription());
        contentValues.put(ParamsTasks.CATEGORY, Long.valueOf(tODOModels.getCategoryId()));
        contentValues.put("status", Integer.valueOf(tODOModels.getStatus()));
        int update = writableDatabase.update(ParamsTasks.TABLE_NAME, contentValues, "id = ?", new String[]{String.valueOf(tODOModels.getId())});
        writableDatabase.close();
        return update;
    }

    public List<TODOModels> getTaskbyCategoryName(long j) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE category=? AND status=0", new String[]{String.valueOf(j)});
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getTaskbyCategoryNameChecked(long j) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE category=? AND status=1", new String[]{String.valueOf(j)});
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public List<TODOModels> getTaskbyCategoryNameforlist(long j) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
        ArrayList arrayList = new ArrayList();
        Cursor rawQuery = readableDatabase.rawQuery("SELECT * FROM task_table WHERE category=? ", new String[]{String.valueOf(j)});
        if (rawQuery.moveToFirst()) {
            do {
                TODOModels tODOModels = new TODOModels();
                tODOModels.setId(Integer.parseInt(rawQuery.getString(0)));
                tODOModels.setTask(rawQuery.getString(1));
                tODOModels.setStatus(Integer.parseInt(rawQuery.getString(2)));
                tODOModels.setReminder(Long.parseLong(rawQuery.getString(3)));
                tODOModels.setSwitchStatus(Integer.parseInt(rawQuery.getString(4)));
                tODOModels.setDescription(rawQuery.getString(5));
                tODOModels.setTimestamp(Long.parseLong(rawQuery.getString(6)));
                tODOModels.setCategoryId(Long.parseLong(rawQuery.getString(7)));
                arrayList.add(tODOModels);
            } while (rawQuery.moveToNext());
            rawQuery.close();
            readableDatabase.close();
            return arrayList;
        }
        rawQuery.close();
        readableDatabase.close();
        return arrayList;
    }

    public void deleteTask(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(ParamsTasks.TABLE_NAME, "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void deleteTaskTrash(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("notes_table_trash", "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void deleteTaskArchived(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete("notes_table_archived", "id=?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }

    public void deleteNoteByCategory(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
        writableDatabase.delete(ParamsTasks.TABLE_NAME, "category =?", new String[]{String.valueOf(j)});
        writableDatabase.close();
    }
}