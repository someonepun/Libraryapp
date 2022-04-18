package com.example.hp.ishelf.activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.ishelf.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnlogin, btnRegister;
    RelativeLayout relativeLayout;


    //to check whether or not application is
    //connected with internet or not
    private boolean connectedOrNot() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        relativeLayout = findViewById(R.id.reLativeLayoutProfile);
        if (connectedOrNot()) {
            //snackbar design custom
            Snackbar snackbar = Snackbar.make(relativeLayout, "Connected", Snackbar.LENGTH_LONG);
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);

            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        } else {
            //snackbar design custom
            Snackbar snackbar = Snackbar
                    .make(relativeLayout, "No internet connection", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                    });
            // Changing message text color
            snackbar.setActionTextColor(Color.RED);
            // Changing action button text color
            View sbView = snackbar.getView();
            TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
            textView.setTextColor(Color.YELLOW);
            snackbar.show();
        }
        //setStatusBarTransparent();
        setContentView(R.layout.activity_welcome);
        //btnlogin = (Button) findViewById(R.id.btnLogin);
        //btnRegister = (Button) findViewById(R.id.btnRegister);

        findViewById(R.id.btnLogin).setOnClickListener(this);
        findViewById(R.id.btnRegister).setOnClickListener(this);
    }

    //to handel button click events
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.btnRegister:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    // To make the status bar transparent
//    private void setStatusBarTransparent(){
//        if(Build.VERSION.SDK_INT >=21){
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
}
