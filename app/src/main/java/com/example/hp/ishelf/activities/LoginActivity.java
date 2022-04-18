
package com.example.hp.ishelf.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hp.ishelf.Api.RetrofitClient;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.model.LoginResponse;
import com.example.hp.ishelf.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(SharedPrefManager.getInstance(this).isLoggedIn()){
            Intent intent = new Intent(this,ProfileActivity.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                userLogin();
                break;
            case R.id.textViewRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    private  void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Password should be atleast 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        Call<LoginResponse> call = RetrofitClient
                .getInstance().getApi().userLogin(email,password);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginResponse loginResponse = response.body();
                
                //if there is no error
                if(!loginResponse.isError()){
                    //store the user detials &
                    //open profile
                    SharedPrefManager.getInstance(LoginActivity.this)
                            .saveUser(loginResponse.getUser());

                    Intent intent = new Intent(LoginActivity.this,ProfileActivity.class);

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    Toast.makeText(LoginActivity.this,loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this,loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
