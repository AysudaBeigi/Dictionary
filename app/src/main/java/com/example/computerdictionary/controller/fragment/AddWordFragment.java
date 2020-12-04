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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.activity.SearchActivity;
import com.example.computerdictionary.model.Word;
import com.example.computerdictionary.repository.WordDBRepository;
import com.google.android.material.textfield.TextInputEditText;

import org.xml.sax.DTDHandler;


public class AddWordFragment extends DialogFragment {
    private TextInputEditText mEditTextEnglishFormat;
    private TextInputEditText mEditTextPersianFormat;
    private WordDBRepository mWordDBRepository;

    /****************** CONSTRUCTOR *********************/
    public AddWordFragment() {
        // Required empty public constructor
    }

    /******************** NEW INSTANCE *******************/
    public static AddWordFragment newInstance() {
        AddWordFragment fragment = new AddWordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /******************* ON CREATE ***********************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository = WordDBRepository.getInstance(getActivity());

    }

    /****************** ON CREATE DIALOG ************************/
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_add_word, null, false);
        findViews(view);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        insertExtractedWord();
                        sendResult();

                    }
                }).setNegativeButton("Cancel", null);
        return builder.create();

    }

    /************************* FIND VIEWS ***********************/
    void findViews(View view) {
        mEditTextEnglishFormat = view.findViewById(R.id.edit_text_add_english_format);
        mEditTextPersianFormat = view.findViewById(R.id.edit_text_add_persian_format);
    }

    /********************* INSERT EXTRACTED WORD ********************/
    void insertExtractedWord() {
        String englishFormat = mEditTextEnglishFormat.getText().toString();
        String persianFormat = mEditTextPersianFormat.getText().toString();

        Word word = new Word(englishFormat, persianFormat);
        mWordDBRepository.insertWord(word);
    }

    /******************** SEND RESULT *******************************/
    void sendResult() {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = Activity.RESULT_OK;
        Intent intent = new Intent();
        fragment.onActivityResult(requestCode, resultCode, intent);
    }

}