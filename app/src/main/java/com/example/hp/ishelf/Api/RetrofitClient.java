package com.example.hp.ishelf.Api;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//singleton class
public class RetrofitClient {
    //do not use localhost
    //for home
    private static final String BASE_URL = "http://192.168.1.103:8012/ishelf/public/";
    //for college server
    //private static final String BASE_URL="http://10.10.100.214:8012/ishelf/public/";
    private Retrofit retrofit;
    private static RetrofitClient mInstance;
    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }
        return mInstance;
    }
    //to get the API
    public Api getApi() {
        return retrofit.create(Api.class);
    }
}
