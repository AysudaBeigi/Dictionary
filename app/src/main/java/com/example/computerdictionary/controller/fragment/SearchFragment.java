package com.example.computerdictionary.controller.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.SearchView;

import com.example.computerdictionary.R;
import com.example.computerdictionary.controller.activity.WordActivity;
import com.example.computerdictionary.model.Word;
import com.example.computerdictionary.repository.WordDBRepository;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchFragment extends Fragment {

    public static final int REQUEST_CODE_ADD_WORD_FRAGMENT = 1;
    public static final String TAG_ADD_WORD_FRAGMENT = "tagAddWordFragment";
    private ShapeableImageView mButtonAddWord;
    private WordDBRepository mWordDBRepository;
    private RecyclerView mRecyclerView;
    private WordListAdapter mWordListAdapter;

    /******************** CONSTRUCTOR ***************************/
    public SearchFragment() {
        // Required empty public constructor
    }

    /******************** NEW INSTANCE *******************/
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /****************** ON CREATE *******************/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWordDBRepository = WordDBRepository.getInstance(getActivity());
        setHasOptionsMenu(true);

    }

    /********************** ON CREATE VIEW *********************/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        findViews(view);

        initViews();

        setListeners();

        return view;
    }

    void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateView();

    }

    private void updateView() {
        if (mWordDBRepository != null) {

            mWordDBRepository = mWordDBRepository.getInstance(getActivity());
            List<Word> words = mWordDBRepository.getWords();
            if (mWordListAdapter != null) {

                mWordListAdapter.setWords(words);
                mWordListAdapter.notifyDataSetChanged();
            } else {
                mWordListAdapter = new WordListAdapter(words);
                mRecyclerView.setAdapter(mWordListAdapter);
            }
        }
    }

    public class WordListAdapter extends RecyclerView.Adapter<WordViewHolder> implements Filterable {
        List<Word> mWords;

        List<Word> mFilteredWordList;

        public WordListAdapter(List<Word> words) {
            mWords = words;
            mFilteredWordList = new ArrayList<>(words);
        }

        public void setWords(List<Word> words) {
            mWords = words;
        }

        public List<Word> getWords() {
            return mWords;
        }

        @Override
        public int getItemCount() {
            return mWords.size();
        }

        @NonNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View itemView = layoutInflater.inflate(R.layout.word_item_view, parent, false);

            return new WordViewHolder(itemView);

        }

        @Override
        public void onBindViewHolder(@NonNull WordViewHolder holder, int position) {
            Word word = mWords.get(position);
            holder.bindView(word);

        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence searchedChars) {
                    List<Word> filteredList = new ArrayList<>();

                    if (searchedChars == null || searchedChars.toString().isEmpty()) {
                        filteredList.addAll(mFilteredWordList);
                    } else {
                        for (Word word : mFilteredWordList) {
                            if (getCondition(searchedChars, word)) {
                                filteredList.add(word);
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    return results;
                }

                private boolean getCondition(CharSequence searchedChars, Word word) {
                    return word.getPersianFormat().toLowerCase().trim()
                            .contains(searchedChars.toString().toLowerCase().trim()) ||
                            word.getEnglishFormat().toLowerCase().trim()
                                    .contains(searchedChars.toString().toLowerCase().trim());
                }

                @Override
                protected void publishResults(CharSequence searchedChars, FilterResults filterResults) {
                    if (mWords != null)
                        mWords.clear();
                    if (filterResults.values != null)
                        mWords.addAll((Collection<? extends Word>) filterResults.values);
                    notifyDataSetChanged();

                }

            };

        }

    }

    public class WordViewHolder extends RecyclerView.ViewHolder {
        private MaterialTextView mTextViewEnglish;
        private MaterialTextView mTextViewPersian;
        private Word mWord;


        public WordViewHolder(@NonNull View itemView) {
            super(itemView);
            findViews(itemView);
            setWordItemListeners(itemView);

        }

        private void findViews(View itemView) {
            mTextViewEnglish = itemView.findViewById(R.id.text_view_english_item);
            mTextViewPersian = itemView.findViewById(R.id.text_view_persian_item);
        }

        private void setWordItemListeners(@NonNull View itemView) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startWordActivity(mWord);
                }
            });
        }

        public void bindView(Word word) {

            mWord = word;
            mTextViewPersian.setText(word.getPersianFormat());
            mTextViewEnglish.setText(word.getEnglishFormat());
        }

    }

    /*********************** FID VIEWS *************************/
    private void findViews(View view) {
        mButtonAddWord = view.findViewById(R.id.img_view_add_word);
        mRecyclerView = view.findViewById(R.id.recycler_view_word);
    }

    /***************** SET LISTENERS **************************/
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


    }


    /********************* START WORD ACTIVITY ************************/
    void startWordActivity(Word word) {
        Intent intent = WordActivity.newIntent(getActivity(), word);

        startActivity(intent);
    }


    /********************** ON ACTIVITY ************************/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;
        if (requestCode == REQUEST_CODE_ADD_WORD_FRAGMENT) {
            setToolbarSubtitle();
            updateView();

        }

    }


    /********************SET TOOL BAR SUBTITLE ***********************/
    private void setToolbarSubtitle() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().
                setSubtitle("Number of words is : " + mWordDBRepository.getWords().size());
        activity.getSupportActionBar().setTitle("Dictionary");
    }

    /****************** ON CREATE OPTION MENU ********************/
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mWordListAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }


    /********************* ON PAUSE ************************/
    @Override
    public void onPause() {
        super.onPause();
        setToolbarSubtitle();
        updateView();
    }

    /********************* ON RESUME *************************/
    @Override
    public void onResume() {
        super.onResume();
        setToolbarSubtitle();
        updateView();

    }
}
