package com.example.computerdictionary.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.activity.WordActivity;
import com.example.computerdictionary.model.Word;
import com.example.computerdictionary.repository.WordDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.TextInputEditText;

public class SearchFragment extends Fragment {
    //todo here this is change

    public static final int REQUEST_CODE_ADD_WORD_FRAGMENT = 1;
    public static final String TAG_ADD_WORD_FRAGMENT = "tagAddWordFragment";
    private TextInputEditText mEditTextSearched;
    private MaterialButton mButtonAddWord;
    private ShapeableImageView mImageViewDoSearch;
    private WordDBRepository mWordDBRepository;


    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository = WordDBRepository.getInstance();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        findViews(view);
        setListeners();

        return view;
    }

    private void setListeners() {
        mButtonAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddWordFragment addWordFragment = AddWordFragment.newInstance();
                addWordFragment.setTargetFragment(
                        SearchFragment.this, REQUEST_CODE_ADD_WORD_FRAGMENT);
                addWordFragment.show(getFragmentManager(), TAG_ADD_WORD_FRAGMENT);
            }
        });
        mImageViewDoSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchedWord = mEditTextSearched.getText().toString();

                Word findedWord = mWordDBRepository.getWord(searchedWord);
                if (findedWord!=null) {
                    if(findedWord.getEnglishFormat().equals(searchedWord)){
                        String  persianTranslate=findedWord.getPersianFormat();
                        Intent intent= WordActivity.newIntent(getActivity());
                        intent.putExtra("englishformat",findedWord.getEnglishFormat());
                        intent.putExtra("persianFormat",persianTranslate);
                        startActivity(intent);
                    }


                }


            }
        });
    }


    private void findViews(View view) {
        mEditTextSearched = view.findViewById(R.id.edit_text_search_word);
        mImageViewDoSearch = view.findViewById(R.id.img_search_word);
        mButtonAddWord = view.findViewById(R.id.btn_add_word);
    }
}