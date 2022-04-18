package com.example.hp.ishelf.fragments;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hp.ishelf.Api.Api;
import com.example.hp.ishelf.Api.RetrofitClient;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.activities.DetailActivity;
import com.example.hp.ishelf.adapters.BooksAdapter;
import com.example.hp.ishelf.model.Books;
import com.example.hp.ishelf.model.BooksResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragement extends Fragment{

    private RecyclerView recyclerView;
    private List<Books> booksList;
    private BooksAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        //return inflater.inflate(R.layout.fragement_home, container, false);
        View view = inflater.inflate(R.layout.fragement_home, container, false);
        progressBar = view.findViewById(R.id.progresshome);

        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar.setVisibility(View.VISIBLE);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        String key ="";
        if(key==""){
            fetchUsers("ALL");
        }else if (key!=null) {
            fetchUsers(key);
        }
    }

    public void fetchUsers(String key){
        Call<BooksResponse> call = RetrofitClient.getInstance().getApi().getBooks(key);
        call.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                progressBar.setVisibility(View.GONE);
                booksList=response.body().getBooks();
                adapter = new BooksAdapter(getActivity(),booksList);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                //Toast.makeText(getActivity(), "Error on: "+ t.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Error failed to fetch data.", Toast.LENGTH_LONG).show();
            }
        });
    }
}
