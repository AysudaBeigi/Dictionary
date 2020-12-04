package com.example.computerdictionary.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.computerdictionary.R;
import com.example.computerdictionary.model.Word;
import com.example.computerdictionary.repository.WordDBRepository;
import com.google.android.material.textfield.TextInputEditText;


public class EditWordFragment extends DialogFragment {

    public static final String ARGS_WORD = "argsWord";
    public static final String EXTRA_WORD = "com.example.computerdictionary.extraWord";
    private TextInputEditText mEditTextEnglishFormat;
    private TextInputEditText mEditTextPersianFormat;
    private WordDBRepository mWordDBRepository;
    private Word mWord;

    /******************* CONSTRUCTOR *********************/
    public EditWordFragment() {
        // Required empty public constructor
    }

    /******************* NEW INSTANCE **************************/

    public static EditWordFragment newInstance(Word word) {
        Log.d("TAG","edit word  F new Instance ");

        EditWordFragment fragment = new EditWordFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_WORD, word);
        fragment.setArguments(args);
        return fragment;
    }

    /********************** ON CREATE ***********************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TAG","edit word  F onCreate ");

        super.onCreate(savedInstanceState);
        mWordDBRepository = WordDBRepository.getInstance(getActivity());
        mWord = (Word) getArguments().getSerializable(ARGS_WORD);

    }

    /************************ ON CREATE DIALOG *******************/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Log.d("TAG","edit word  F onCreateDialog ");

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_edit_word,null);
        findViews(view);
        initViews();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updateWord();
                        sendResult();

                    }
                }).setNegativeButton("Cancel", null);
        return builder.create();

    }

    /*********************** FIND VIEWS ***************************/
    void findViews(View view) {
        mEditTextEnglishFormat = view.findViewById(R.id.edit_text_edit_english_format);
        mEditTextPersianFormat = view.findViewById(R.id.edit_text_edit_persian_format);
    }

    /******************** INIT VIEWS **********************/
    void initViews() {
        mEditTextEnglishFormat.setText(mWord.getEnglishFormat());
        mEditTextPersianFormat.setText(mWord.getPersianFormat());
    }

    /********************* UPDATE WORD ***********************/
    void updateWord() {
        mWord.setEnglishFormat(mEditTextEnglishFormat.getText().toString());
        mWord.setPersianFormat(mEditTextPersianFormat.getText().toString());
        mWordDBRepository.updateWord(mWord);
    }

    /********************* SEND RESULT *************************/
    void sendResult() {
        Log.d("TAG","EditF sendResult "+mWord.getEnglishFormat());
        Log.d("TAG","EditF sendResult "+mWord.getPersianFormat());

        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        intent.putExtra(EXTRA_WORD, mWord);
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

}