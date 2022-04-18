package com.example.hp.ishelf.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.ishelf.model.DefaultResponse;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.Api.RetrofitClient;
import com.example.hp.ishelf.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextName, editTextEmail, editTextPassword, editTextLondonMetId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.name);
        editTextEmail = findViewById(R.id.email);
        editTextLondonMetId = findViewById(R.id.londonMetId);
        editTextPassword = findViewById(R.id.password);


        findViewById(R.id.btnRegister).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                userSignUp();
                break;
        }
    }

    //if user is logged in
    //directly open the home screen
    //rather than register activity

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userSignUp() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String londonMetId = editTextLondonMetId.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //ALL THE INPUT FIELD VALIDATION
        if (name.isEmpty()) {
            editTextName.setError("Name is required.");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required.");
            editTextEmail.requestFocus();
            return;
        }
        //if  email is in-valid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (londonMetId.isEmpty()) {
            editTextLondonMetId.setError("London met Id is required.");
            editTextLondonMetId.requestFocus();
            return;
        }
        if(londonMetId.length()<8 || londonMetId.length()>8){
            editTextLondonMetId.setError("Enter a valid id");
            editTextLondonMetId.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required.");
            editTextPassword.requestFocus();
            return;
        }
        //validating password strength
        if (password.length() < 6) {
            editTextPassword.setError("Password should be aleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .createUser(name, email,londonMetId,password);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {
                    DefaultResponse dr = response.body();
                    Toast.makeText(MainActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                    //upon successful login
                    //redirect to the login activity
                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                } else if (response.code() == 422) {
                    Toast.makeText(MainActivity.this, "User already exists.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                //Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
