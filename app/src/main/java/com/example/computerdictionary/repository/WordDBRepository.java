package com.example.computerdictionary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.computerdictionary.database.WordCursorWrapper;
import com.example.computerdictionary.database.WordDBHelper;
import com.example.computerdictionary.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.computerdictionary.database.WordDBSchema.WordTable;
import static com.example.computerdictionary.database.WordDBSchema.WordTable.WordCols;

public class WordDBRepository {
    // private List<Word> mWords;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static WordDBRepository sInstance;

    private WordDBRepository(Context context) {
        mContext = context.getApplicationContext();
        WordDBHelper wordDBHelper = new WordDBHelper(mContext);
        mDatabase = wordDBHelper.getWritableDatabase();

        /*mWords = new ArrayList<>();
         */
    }

    public static WordDBRepository getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new WordDBRepository(context);
            return sInstance;
        }
        return sInstance;
    }

    public List<Word> getWords() {
        List<Word> words = new ArrayList<>();

        WordCursorWrapper wordCursorWrapper = queryWordCursor(null, null);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return words;
        try {
            wordCursorWrapper.moveToFirst();
            while (!wordCursorWrapper.isAfterLast()) {
                Word word=wordCursorWrapper.getWord();
                words.add(word);
                wordCursorWrapper.moveToNext();

            }

        } finally {
            wordCursorWrapper.close();
        }
        return words;


    }

    public Word getWord(String EOrPFormat) {
        String whereClause=WordCols.ENGLISH_FORMAT+" = ? or persian_format = ?";
        String [] whereArgs=new String[]{
                EOrPFormat,EOrPFormat
        };

        WordCursorWrapper wordCursorWrapper = queryWordCursor(whereClause, whereArgs);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return null;
        try {
            wordCursorWrapper.moveToFirst();
            return wordCursorWrapper.getWord();

        } finally {
            wordCursorWrapper.close();
        }

        /*for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getEnglishFormat().equals(EOrPFormat)
                    || mWords.get(i).getPersianFormat().equals(EOrPFormat)) {
                return mWords.get(i);
            }
        }
        return null;
        */
    }

    public Word getWord(UUID uuid) {
        String whereClause=WordCols.UUID+" = ?";
        String [] whereArgs=new String[]{
                uuid.toString()
        };

        WordCursorWrapper wordCursorWrapper = queryWordCursor(whereClause, whereArgs);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return null;
        try {
            wordCursorWrapper.moveToFirst();
                return wordCursorWrapper.getWord();

        } finally {
            wordCursorWrapper.close();
        }

      /*  for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getUUID().equals(uuid)) {
                return mWords.get(i);
            }
        }

        return null;*/
    }

    private WordCursorWrapper queryWordCursor(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(WordTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        WordCursorWrapper wordCursorWrapper = new WordCursorWrapper(cursor);
        return wordCursorWrapper;
    }

    public String getPersianFormat(String englishFormat) {
        String whereClause = WordCols.ENGLISH_FORMAT + " = ?";
        String[] whereArgs = new String[]{
                englishFormat
        };

        WordCursorWrapper wordCursorWrapper = queryWordCursor(whereClause, whereArgs);

        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return null;
        try {
            wordCursorWrapper.moveToFirst();
            Word word= wordCursorWrapper.getWord();
            return word.getPersianFormat();

        } finally {
            wordCursorWrapper.close();
        }


       /* for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getEnglishFormat().equals(englishFormat)) {
                return mWords.get(i).getPersianFormat();
            }
        }
        //todo : implement exception
        return null;
    }*/
    }

    public String getEnglishFormat(String persianFormat) {
        String whereClause = WordCols.PERSIAN_FORMAT + " = ?";
        String[] whereArgs = new String[]{
                persianFormat
        };
        WordCursorWrapper wordCursorWrapper = queryWordCursor(whereClause, whereArgs);
        if (wordCursorWrapper == null || wordCursorWrapper.getCount() == 0)
            return null;
        try {
            wordCursorWrapper.moveToFirst();
            Word word= wordCursorWrapper.getWord();
            return word.getEnglishFormat();

        } finally {
            wordCursorWrapper.close();
        }



       /* for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getPersianFormat().equals(persianFormat)) {
                return mWords.get(i).getEnglishFormat();
            }
        }
        return null;*/
    }

    public void insertWord(Word word) {
        ContentValues values = getContentValues(word);
        mDatabase.insert(WordTable.NAME,
                null,
                values);

        /*  mWords.add(word);
         */
    }

    public ContentValues getContentValues(Word word) {
        ContentValues values = new ContentValues();
        values.put(WordCols.UUID, word.getUUID().toString());
        values.put(WordCols.ENGLISH_FORMAT, word.getEnglishFormat());
        values.put(WordCols.PERSIAN_FORMAT, word.getPersianFormat());
        return values;
    }

    public void deleteWord(Word word) {
        String whereClause = WordCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                word.getUUID().toString()
        };
        mDatabase.delete(WordTable.NAME,
                whereClause,
                whereArgs);
/*
        mWords.remove(word);
*/
    }

    public void updateWord(Word word) {
        ContentValues values = getContentValues(word);
        String whereClause = WordCols.UUID + " = ?";
        String[] whereArgs = new String[]{
                word.getUUID().toString()
        };
        mDatabase.update(WordTable.NAME,
                values,
                whereClause,
                whereArgs);



       /* for (int i = 0; i < mWords.size(); i++) {
            if(mWords.get(i).getUUID().equals(word.getUUID())){
                mWords.get(i).setEnglishFormat(word.getEnglishFormat());
                mWords.get(i).setPersianFormat(word.getPersianFormat());
            }
        }*/

    }


}
