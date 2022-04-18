package com.example.hp.ishelf.fragments;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.ishelf.R;
import com.example.hp.ishelf.adapters.BooksAdapter;
import com.example.hp.ishelf.data.FavoriteDbHelper;
import com.example.hp.ishelf.model.Books;

import java.util.ArrayList;
import java.util.List;

public class savedFragement extends Fragment {

    private RecyclerView recyclerView;
    private List<Books> booksList;
    private BooksAdapter adapter;
    private FavoriteDbHelper favoriteDbHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        booksList = new ArrayList<>();
        adapter = new BooksAdapter(getActivity(), booksList);

        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        favoriteDbHelper = new FavoriteDbHelper(getActivity());
        getAllFavorite();
    }

    private void getAllFavorite() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                booksList.clear();
                booksList.addAll(favoriteDbHelper.getAllFavorite());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }
}
