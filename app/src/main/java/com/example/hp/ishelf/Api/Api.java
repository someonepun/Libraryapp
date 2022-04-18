package com.example.hp.ishelf.Api;

import com.example.hp.ishelf.model.Books;
import com.example.hp.ishelf.model.BooksResponse;
import com.example.hp.ishelf.model.DefaultResponse;
import com.example.hp.ishelf.model.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {

    //for create students
    @FormUrlEncoded //this anotation is compulsory
    @POST ("createuser")
    Call<DefaultResponse> createUser(
            @Field("name") String name,
            @Field("email") String email,
            @Field("LondonMetID") String LondonMetID,
            @Field("password") String password
    );

    //for book request by students
    @FormUrlEncoded
    @POST ("requestbook")
    Call<DefaultResponse> bookRequest(
            @Field("name") String Name,
            @Field("email") String email,
            @Field("ISBN") String Isbn,
            @Field("BookTitle") String BookTitle,
            @Field("Author") String BookAuthor
    );

    //for user login call
    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin(
        @Field("email") String email,
        @Field("password") String password
    );

//    @GET("allbooks")
//    Call<BooksResponse> getBooks();

    @GET("allbooks/{key}")
    Call<BooksResponse> getBooks(@Path("key") String keyword);

}
