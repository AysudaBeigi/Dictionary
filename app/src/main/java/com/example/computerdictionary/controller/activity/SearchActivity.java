package com.example.computerdictionary.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.fragment.SearchFragment;

public class SearchActivity extends AppCompatActivity {

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,SearchActivity.class);
        return intent;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.search_fragment_container);

        if (fragment == null) {
            fragmentManager.
                    beginTransaction().
                    add(R.id.search_fragment_container, SearchFragment.newInstance()).
                    commit();
        }


    }
}