package com.example.hp.ishelf.activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.hp.ishelf.Api.RetrofitClient;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.adapters.BooksAdapter;
import com.example.hp.ishelf.fragments.HomeFragement;
import com.example.hp.ishelf.model.Books;
import com.example.hp.ishelf.model.BooksResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Books> booksList;
    private BooksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);


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
                booksList=response.body().getBooks();
                adapter = new BooksAdapter(getApplicationContext(),booksList);
                StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Error on: "+ t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchUsers(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fetchUsers(newText);
                return false;
            }
        });
        return true;
    }
}
