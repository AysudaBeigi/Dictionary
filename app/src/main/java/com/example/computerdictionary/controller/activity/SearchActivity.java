package com.example.computerdictionary.controller.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.fragment.SearchFragment;
import com.example.computerdictionary.repository.WordDBRepository;

import java.util.zip.Inflater;

public class SearchActivity   extends SingleFragmentActivity{

    public static Intent newIntent(Context context){
        Intent intent=new Intent(context,SearchActivity.class);
        return intent;
    }


    @Override
    public Fragment createFragment() {
        return SearchFragment.newInstance();
    }
}