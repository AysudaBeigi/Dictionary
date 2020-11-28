package com.example.computerdictionary.database;

public class WordDBSchema {
    public static final String NAME = "computerDictionary.db";
    public static final int VERSION = 1;

    public static final class WordTable {
        public static final String NAME = "word_table";

        public static final class WordCols {
            public static final String ENGLISH_FORMAT = "english_format";
            public static final String PERSIAN_FORMAT = "persian_format";
            public static final String UUID = "uuid";
            public static final String ID = "id";
        }

    }







}



