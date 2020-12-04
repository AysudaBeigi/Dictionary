package com.example.computerdictionary.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.activity.SearchActivity;
import com.example.computerdictionary.model.Word;
import com.example.computerdictionary.repository.WordDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

public class WordFragment extends Fragment {

    public static final String ARGS_WORD = "argsWord";
    public static final int REQUEST_CODE_EDIT_WORD_FRAGMENT = 1;
    public static final String TAG_EDIT_WORD_FRAGMENT = "tagEditWordFragment";
    private Word mWord;

    private MaterialTextView mTextViewEnglishFormat;
    private MaterialTextView mTextViewPersianFormat;
    private MaterialButton mButtonEdit;
    private MaterialButton mButtonDelete;
    private MaterialButton mButtonShare;
    private WordDBRepository mWordDBRepository;

    /****************** CONSTRUCTOR **************************/


    public WordFragment() {
        // Required empty public constructor
    }

    /******************* NEW INSTANCE ************************/
    public static WordFragment newInstance(Word word) {
        WordFragment fragment = new WordFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WORD, word);

        fragment.setArguments(args);
        return fragment;
    }

    /********************* ON CREATE *********************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWord = (Word) getArguments().getSerializable(ARGS_WORD);

    }

    /********************** ON CREATE VIEW *****************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word, container, false);
        findViews(view);
        updateUi();
        setListeners();

        return view;
    }

    /****************** FIND VIEWS *******************/
    void findViews(View view) {
        Log.d("TAG", "word F findViews ");
        mTextViewEnglishFormat = view.findViewById(R.id.text_view_word_english_format);
        mTextViewPersianFormat = view.findViewById(R.id.text_view_word_persian_format);
        mButtonDelete = view.findViewById(R.id.btn_delete_word);
        mButtonEdit = view.findViewById(R.id.btn_edit_word);
        mButtonShare = view.findViewById(R.id.btn_share_word);
    }

    /***************** UPDATE UI**************************/
    void updateUi() {
        Log.d("TAG", "word F updateUI ");
        Log.d("TAG", mWord.getEnglishFormat());
        Log.d("TAG", mWord.getPersianFormat());
        mWordDBRepository = WordDBRepository.getInstance(getActivity());
        Word word = mWordDBRepository.getWord(mWord.getUUID());

        mTextViewEnglishFormat.setText(word.getEnglishFormat());
        mTextViewPersianFormat.setText(word.getPersianFormat());
    }

    /********************SET LISTENERS **************************/
    void setListeners() {
        mButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWordDBRepository.deleteWord(mWord);
                startSearchActivity();

            }
        });
        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWordFragment editWordFragment = EditWordFragment.newInstance(mWord);
                editWordFragment.
                        setTargetFragment(WordFragment.this, REQUEST_CODE_EDIT_WORD_FRAGMENT);
                editWordFragment.show(getFragmentManager(), TAG_EDIT_WORD_FRAGMENT);

            }
        });
        mButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getWordReport());
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, getString(R.string.send_word));
                if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null) {

                    startActivity(shareIntent);
                }

            }
        });
    }

    private String getWordReport() {
        String englishFormat = mWord.getEnglishFormat();
        String persianFormat = mWord.getPersianFormat();
        String report = getString(R.string.word_report, englishFormat, persianFormat);
        return report;
    }

    /************************ START SEARCH ACTIVITY ******************/
    void startSearchActivity() {
        Intent intent = SearchActivity.newIntent(getActivity());
        startActivity(intent);
    }


    /***************** ON ACTIVITY RESULT *******************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d("TAG", "onAcitivty Result");
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_EDIT_WORD_FRAGMENT) {
            Log.d("TAG", "onAcitivty Result request code edit ");

            mWord = (Word) data.getSerializableExtra(EditWordFragment.EXTRA_WORD);
            Log.d("TAG", mWord.getEnglishFormat());
            Log.d("TAG", mWord.getPersianFormat());
            updateUi();
        }


    }

    /***************** GENERATE SNACK BAR ***********************/
    private void generateSnackbar(View view, int stringId) {
        Snackbar snackbar = Snackbar
                .make(view, stringId, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    /********************SET TOOL BAR SUBTITLE ***********************/
    private void setToolbarSubtitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().
                setSubtitle("Number of words is : " + mWordDBRepository.getWords().size());
        activity.getSupportActionBar().setTitle("Dictionary");
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
        setToolbarSubtitle();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateUi();
        setToolbarSubtitle();
    }
}