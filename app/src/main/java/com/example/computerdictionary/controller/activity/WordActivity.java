package com.example.computerdictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.fragment.SearchFragment;
import com.example.computerdictionary.controller.fragment.WordFragment;
import com.example.computerdictionary.model.Word;

import java.util.UUID;


public class WordActivity extends   SingleFragmentActivity {

    public static final String EXTRA_WORD = "com.example.computerdictionary.extraWord";

    public static Intent newIntent(Context context , Word word){
        Intent intent=new Intent(context,WordActivity.class);
        intent.putExtra(EXTRA_WORD, word);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        Word word= (Word) getIntent().getSerializableExtra(EXTRA_WORD);
        return WordFragment.newInstance(word);

    }
}