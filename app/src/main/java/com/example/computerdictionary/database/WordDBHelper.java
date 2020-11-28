package com.example.computerdictionary.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.computerdictionary.database.WordDBSchema;
import com.example.computerdictionary.database.WordDBSchema.WordTable.WordCols;

public class WordDBHelper extends SQLiteOpenHelper {
    public WordDBHelper(@Nullable Context context) {
        super(context, WordDBSchema.NAME, null, WordDBSchema.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        generateWordQuery(db);

    }

    private void generateWordQuery(SQLiteDatabase db) {
        Log.d("TAG", "generateWordQuery");
        db.execSQL("CREATE TABLE " + WordDBSchema.WordTable.NAME + "(" +
                WordCols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                WordCols.UUID + "  TEXT NOT NULL ," +
                WordCols.PERSIAN_FORMAT + "  TEXT NOT NULL ," +
                WordCols.ENGLISH_FORMAT + " TEXT  NOT NULL );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
