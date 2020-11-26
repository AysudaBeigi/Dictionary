package com.example.computerdictionary.model;

import java.util.UUID;

public class Word {

    private String mEnglishFormat;
    private String mPersianFormat;
    private UUID mUUID;

    public Word(String englishFormat, String persianFormat) {
        mEnglishFormat = englishFormat;
        mPersianFormat = persianFormat;
        mUUID=UUID.randomUUID();
    }

    public String getEnglishFormat() {
        return mEnglishFormat;
    }

    public String getPersianFormat() {
        return mPersianFormat;
    }

    public UUID getUUID() {
        return mUUID;
    }

    public void setEnglishFormat(String englishFormat) {
        mEnglishFormat = englishFormat;
    }

    public void setPersianFormat(String persianFormat) {
        mPersianFormat = persianFormat;
    }
}
