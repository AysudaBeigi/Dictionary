package com.example.computerdictionary.repository;

import android.widget.LinearLayout;

import com.example.computerdictionary.model.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WordDBRepository {
    private List<Word> mWords;
    private static WordDBRepository sInstance;

    private WordDBRepository() {
        mWords = new ArrayList<>();

    }

    public static WordDBRepository getInstance() {
        if (sInstance == null) {
            sInstance = new WordDBRepository();
            return sInstance;
        }
        return sInstance;
    }

    public List<Word> getWords() {
        return mWords;
    }

    public Word getWord(String EOrPFormat) {
        for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getEnglishFormat().equals(EOrPFormat)
                    || mWords.get(i).getPersianFormat().equals(EOrPFormat)) {
                return mWords.get(i);
            }
        }
        return null;
    }

    public Word getWord(UUID uuid) {
        for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getUUID().equals(uuid)) {
                return mWords.get(i);
            }
        }
        //todo : implement exception

        return null;
    }

    public String getPersianFormat(String englishFormat) {
        for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getEnglishFormat().equals(englishFormat)) {
                return mWords.get(i).getPersianFormat();
            }
        }
        //todo : implement exception
        return null;
    }

    public String getEnglishFormat(String persianFormat) {
        for (int i = 0; i < mWords.size(); i++) {
            if (mWords.get(i).getPersianFormat().equals(persianFormat)) {
                return mWords.get(i).getEnglishFormat();
            }
        }
        //todo : implement exception

        return null;
    }
    public void insertWord(Word word){
        mWords.add(word);
    }
    public void deleteWord(Word word){
        mWords.remove(word);
    }
    public void updateWord(Word word){
        for (int i = 0; i < mWords.size(); i++) {
            if(mWords.get(i).getUUID().equals(word.getUUID())){
                mWords.get(i).setEnglishFormat(word.getEnglishFormat());
                mWords.get(i).setPersianFormat(word.getPersianFormat());
            }
        }

    }





}
