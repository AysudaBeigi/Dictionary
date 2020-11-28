package com.example.computerdictionary.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.computerdictionary.model.Word;

import java.util.Date;
import java.util.UUID;
import com.example.computerdictionary.database.WordDBSchema.WordTable.WordCols;
public class WordCursorWrapper extends CursorWrapper {

    public WordCursorWrapper(Cursor cursor) {
        super(cursor);
    }
    public Word getWord(){
        UUID uuid = UUID.fromString(getString(getColumnIndex(WordCols.UUID)));
        String englishFormat = getString(getColumnIndex(WordCols.ENGLISH_FORMAT));
        String persianFormat = getString(getColumnIndex(WordCols.PERSIAN_FORMAT));
         return new Word(uuid,englishFormat,persianFormat);

    }
}
