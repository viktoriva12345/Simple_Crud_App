package com.example.zadatak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Akademija.db";
    private static final String TABLE_NAME = "Polaznik";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "ime";
    private static final String COL_3 = "prezime";
    private static final String COL_4 = "godina_upisa";
    private static final String COL_5 = "broj_poena";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, ime TEXT, prezime TEXT, godina_upisa INTEGER, broj_poena INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String ime, String prezime, int godina_upisa, int broj_poena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, ime);
        contentValues.put(COL_3, prezime);
        contentValues.put(COL_4, godina_upisa);
        contentValues.put(COL_5, broj_poena);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getTopPolaznici() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE broj_poena > 80 ORDER BY broj_poena DESC LIMIT 5", null);
    }

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}