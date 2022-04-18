package com.example.hp.ishelf.activities;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.ishelf.R;
import com.example.hp.ishelf.fragments.HomeFragement;
import com.example.hp.ishelf.fragments.profileFragement;
import com.example.hp.ishelf.fragments.savedFragement;
import com.example.hp.ishelf.model.User;
import com.example.hp.ishelf.storage.SharedPrefManager;

import java.lang.reflect.Field;

public class ProfileActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SearchActivity.class));
            }
        });
//        getSupportActionBar().setTitle("Profile"); //for changing title text
        BottomNavigationView navigationView = findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(navigationView);
        navigationView.setOnNavigationItemSelectedListener(this);
        displayFragement(new profileFragement());
    }

    private void displayFragement(Fragment fragment) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.reLativeLayout, fragment)
                .commit();
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MainActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.profile:
                fragment = new profileFragement();
                getSupportActionBar().setTitle("Profile"); //for changing title text
                break;
            case R.id.home:
                fragment = new HomeFragement();
                getSupportActionBar().setTitle("Home");
                break;
            case R.id.Saved:
                fragment = new savedFragement();
                getSupportActionBar().setTitle("Saved");
                break;
        }

        if (fragment != null) {
            displayFragement(fragment);
        }
        return false;
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
                Bundle bundle = new Bundle();
                bundle.putString("key", query);
                HomeFragement homeFragement = new HomeFragement();
                homeFragement.setArguments(bundle);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Bundle bundle = new Bundle();
                bundle.putString("key", newText);
                HomeFragement homeFragement = new HomeFragement();
                homeFragement.setArguments(bundle);
                return false;
            }
        });
        return true;
    }
}
