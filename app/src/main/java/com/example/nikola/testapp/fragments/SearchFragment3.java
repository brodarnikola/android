package com.example.nikola.testapp.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.nikola.testapp.R;
import com.example.nikola.testapp.Settings.Constants;
import com.example.nikola.testapp.Settings.MyApplication;
import com.example.nikola.testapp.adapters.searchAdapter.SearchAdapter2;
import com.example.nikola.testapp.adapters.searchAdapter.SearchAdapter3;
import com.example.nikola.testapp.customControls.SwipeHelper;
import com.example.nikola.testapp.models.pojo.TeamCategory;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment3 extends Fragment {


    private RecyclerView categoryRecylerView;
    private ArrayList<TeamCategory> categoryLinkedHashMap;
    private ArrayList<TeamCategory> categoryFilteredList;
    private SearchAdapter3 searchAdapter;

    private EditText etSearch;

    private String userInput = "";

    private SwipeController swipeController;

    public static SearchFragment3 newInstance() {
        SearchFragment3 fragment = new SearchFragment3();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_3, container, false);

        categoryLinkedHashMap = (ArrayList<TeamCategory>) MyApplication.getInstance().get(Constants.teamCategories);


        etSearch = (EditText) view.findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userInput = etSearch.getText().toString();
                categoryFilteredList = new ArrayList<>();
                for (TeamCategory teamCategory : categoryLinkedHashMap) {
                    if ( teamCategory.getTerm().toLowerCase().contains(userInput.toLowerCase())) {
                        Log.d("pronasli", "pronasli smo itema:" + teamCategory.getTerm().toString());
                        categoryFilteredList.add(teamCategory);
                    }
                }
                searchAdapter.refresh(categoryFilteredList);
            }

            @Override
            public void afterTextChanged(Editable s) {
                categoryFilteredList = new ArrayList<>();
            }
        });


        categoryRecylerView = (RecyclerView) view.findViewById(R.id.recylerView);

        searchAdapter = new SearchAdapter3(view.getContext(), getFragmentManager(), categoryLinkedHashMap);

        RecyclerView.LayoutManager  layoutManager = new LinearLayoutManager(view.getContext());
        categoryRecylerView.setLayoutManager(layoutManager);
        categoryRecylerView.setItemAnimator(new DefaultItemAnimator());
        categoryRecylerView.setAdapter(searchAdapter);

        SwipeHelper swipeHelper = new SwipeHelper( getContext(), categoryRecylerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: onDelete
                                searchAdapter.removeItem(pos);
                                searchAdapter.notifyItemRemoved(pos);
                                searchAdapter.notifyItemRangeChanged(pos, searchAdapter.getItemCount());
                            }
                        }
                ));

                /*underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Transfer",
                        0,
                        Color.parseColor("#FF9502"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnTransfer
                            }
                        }
                ));
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Unshare",
                        0,
                        Color.parseColor("#C7C7CB"),
                        new SwipeHelper.UnderlayButtonClickListener() {
                            @Override
                            public void onClick(int pos) {
                                // TODO: OnUnshare
                            }
                        }
                )); */
            }
        };

        return view;
    }



}


