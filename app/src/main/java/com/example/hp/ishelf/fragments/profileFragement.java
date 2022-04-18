package com.example.hp.ishelf.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.ishelf.R;
import com.example.hp.ishelf.activities.LoginActivity;
import com.example.hp.ishelf.activities.ProfileActivity;
import com.example.hp.ishelf.storage.SharedPrefManager;

public class profileFragement extends Fragment {


    private TextView textViewEmail, textViewName, textViewId;
    private Button buttonLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragement_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewEmail = view.findViewById(R.id.textViewEmail);
        textViewName = view.findViewById(R.id.textViewName);
        buttonLogout = view.findViewById(R.id.btnLogout);

        textViewEmail.setText(SharedPrefManager.getInstance(getActivity()).getUser().getEmail());
        textViewName.setText(SharedPrefManager.getInstance(getActivity()).getUser().getName());

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        //to debug
        //checking if value is present or not
        //Log.d("ValueFromSharedPref", "Name: " + SharedPrefManager.getInstance(getActivity()).getUser().getName());
    }

    //removing the username and password from shared Preference manager.
    private void logout(){
        SharedPrefManager.getInstance(getActivity()).Clear();
        Intent intent = new Intent(getActivity(),LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
