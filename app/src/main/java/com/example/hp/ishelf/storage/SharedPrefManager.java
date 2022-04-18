package com.example.hp.ishelf.storage;


import android.content.Context;
import android.content.SharedPreferences;
import com.example.hp.ishelf.model.User;

//singelton class
public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "my_shared_preff";
    private static SharedPrefManager mInstance;
    private Context mCtx;

    private SharedPrefManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public static synchronized SharedPrefManager getInstance(Context mCtx){
        if(mInstance == null){
            mInstance = new SharedPrefManager(mCtx);
        }
        return mInstance;
    }

    //to store user information in sharedPreferences

    public void saveUser(User user){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("id", user.getId());
        editor.putString("name", user.getName());
        editor.putString("email", user.getEmail());

        editor.apply();
    }

    //to check if someone is logedin or not
    public boolean isLoggedIn(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
            return sharedPreferences.getInt("id", -1)!= -1;
    }

    public User getUser(){
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
//        User user = new User(
//                sharedPreferences.getInt("id",-1),
//                sharedPreferences.getString("name", null),
//                sharedPreferences.getString("email",null)
//        );
//        return user;
        //OR simplified version below
        return new User(
                sharedPreferences.getInt("id",-1),
                sharedPreferences.getString("name", null),
                sharedPreferences.getString("email",null)
        );
    }

    //for logout method
    public void Clear(){

        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();

    }


}
