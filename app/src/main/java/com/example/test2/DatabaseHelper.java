package com.example.test2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "NoteDB";
    private final String TAG = "DatabaseHelper";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "LiveCat";

    private static final String COLUMN_ID = "id";
    private static final String CAT = "cat_name";
    private static final String DESC = "note_desc";
    //private static final String COL3 = "";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: Creating DB");

        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                CAT + " VARCHAR(20) NOT NULL, " +
                DESC + " VARCHAR(20) NOT NULL " +
                ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertNewNote(Note note) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAT, note.getCategory());
        contentValues.put(DESC, note.getDesc());

        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues) != -1;
    }

    public Cursor selectAll() {
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_NAME + ";";

        return sqLiteDatabase.rawQuery(sql, null);
    }

    public boolean deleteItem(int id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Log.d(TAG,String.valueOf(id));
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID+"=?", new String[]{String.valueOf(id)}) > 0;

    }

    public Cursor getUniqueCat() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String sql = "SELECT DISTINCT " + CAT + " from " + TABLE_NAME;

        return sqLiteDatabase.rawQuery(sql, null);
    }

    public Cursor getAllNotesFromCategory(String category){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "Select cat_name,note_desc,id from "+ TABLE_NAME + " where cat_name= '" + category + "' ;";
        return sqLiteDatabase.rawQuery(sql,null);
    }

    public int deleteCategory(String category){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        //String sql = "DELETE FROM "+ TABLE_NAME + " WHERE "+ CAT + "= '" + category + "' ;";
        int rowCount = sqLiteDatabase.delete(TABLE_NAME, CAT+"=?", new String[]{category});
        Log.d(TAG, "deleteCategory: " + rowCount);
        return rowCount;

    }

    public int updateNote(int id, String newNote){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DESC, newNote);
        int rowsUpdated =  sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID+ "=?", new String[]{String.valueOf(id)});
        Log.d(TAG, "updateNote:  " + rowsUpdated);

        return rowsUpdated;
    }

    public Cursor getNoteByDesc(String searchParam){
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        String sql = "SELECT * FROM "+ TABLE_NAME + " WHERE " + DESC + " LIKE '%" + searchParam + "%' COLLATE NOCASE;";
        return sqLiteDatabase.rawQuery(sql,null);
    }


}
