package com.example.computerdictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.fragment.SearchFragment;
import com.example.computerdictionary.controller.fragment.WordFragment;

public class WordActivity extends AppCompatActivity {
    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,WordActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.word_fragment_container);

        if (fragment == null) {
            fragmentManager.
                    beginTransaction().
                    add(R.id.word_fragment_container, WordFragment.newInstance()).
                    commit();
        }


    }
}