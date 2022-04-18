package com.example.hp.ishelf.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.ishelf.Api.RetrofitClient;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.data.FavoriteDbHelper;
import com.example.hp.ishelf.model.Books;
import com.example.hp.ishelf.model.DefaultResponse;
import com.example.hp.ishelf.storage.SharedPrefManager;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import org.w3c.dom.Text;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";
    Button btnCheckAvailability;
    TextView tvTitle, tvAuthor, tvDesc, tvImage;
    ImageView ivImage;
    private FavoriteDbHelper favoriteDbHelper;
    private Books favorite;
    private final AppCompatActivity activity = DetailActivity.this;
    //final String imageUri = "http://10.10.100.214:8012/img/";
    final String imageUri = "http://192.168.1.103:8012/img/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tvTitle = (TextView) findViewById(R.id.textViewTitle);
        tvAuthor = (TextView) findViewById(R.id.textViewAuthor);
        tvDesc = (TextView) findViewById(R.id.textViewDescription);
        ivImage = (ImageView) findViewById(R.id.imageViewDetails);
        btnCheckAvailability = (Button) findViewById(R.id.buttonCheckAvailability);

        //click event to check book availability
        btnCheckAvailability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });
        //click event end

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("Title")) {
            String image = imageUri + getIntent().getExtras().getString("Image");
            String title = getIntent().getExtras().getString("Title");
            String author = getIntent().getExtras().getString("Author");
            String desc = getIntent().getExtras().getString("Desc");
            String isbn = getIntent().getExtras().getString("ISBN");

            Glide.with(this)
                    .load(image)
                    .into(ivImage);

            tvTitle.setText(title);
            tvAuthor.setText(author);
            tvDesc.setText(desc);
        } else {
            Toast.makeText(getApplicationContext(), "No Data to show", Toast.LENGTH_SHORT).show();
        }

        MaterialFavoriteButton materialFavoriteButtonNice =
                (MaterialFavoriteButton) findViewById(R.id.favorite_button);

        //to store the state
        //shared preference
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        materialFavoriteButtonNice.setOnFavoriteChangeListener(
                new MaterialFavoriteButton.OnFavoriteChangeListener() {
                    @Override
                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                        if (favorite) {
                            SharedPreferences.Editor editor = getSharedPreferences("package com.example.hp.ishelf.activities", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Added", true);
                            editor.commit();
                            saveFavorite();
                            Snackbar.make(buttonView, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
                        } else {
                            //int book_id = getIntent().getExtras().getInt("ISBN");
                            String isbn = getIntent().getExtras().getString("ISBN");
                            int ibn = Integer.parseInt(isbn);

                            favoriteDbHelper = new FavoriteDbHelper(DetailActivity.this);
                            favoriteDbHelper.deleteFavorite(ibn);

                            SharedPreferences.Editor editor = getSharedPreferences("package com.example.hp.ishelf.activities", MODE_PRIVATE).edit();
                            editor.putBoolean("Favorite Removed", true);
                            editor.commit();
                            Snackbar.make(buttonView, "Removed from Favorite", Snackbar.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void saveFavorite() {
        favoriteDbHelper = new FavoriteDbHelper(activity);
        favorite = new Books();

        String isbn = getIntent().getExtras().getString("ISBN");
        String title = getIntent().getExtras().getString("Title");
        String author = getIntent().getExtras().getString("Author");
        String desc = getIntent().getExtras().getString("Desc");
        String image = getIntent().getExtras().getString("Image");

        favorite.setISBN(isbn);
        favorite.setBookTitle(tvTitle.getText().toString().trim());
        favorite.setAuthor(tvAuthor.getText().toString().trim());
        favorite.setDescBook(tvDesc.getText().toString().trim());
        favorite.setBookCoverPic(image);
        //Toast.makeText(getApplicationContext(), book_id, Toast.LENGTH_LONG).show();
        favoriteDbHelper.addFavorite(favorite);
    }

    public void makeRequest(){
        String name = SharedPrefManager.getInstance(getApplicationContext()).getUser().getName();
        String email = SharedPrefManager.getInstance(getApplicationContext()).getUser().getEmail();
        String isbn = getIntent().getExtras().getString("ISBN");
        String booktitle = getIntent().getExtras().getString("Title");
        String bookauthor = getIntent().getExtras().getString("Author");

        Call<DefaultResponse> call = RetrofitClient
                .getInstance()
                .getApi()
                .bookRequest(name,email,isbn,booktitle,bookauthor);

        call.enqueue(new Callback<DefaultResponse>() {
            @Override
            public void onResponse(Call<DefaultResponse> call, Response<DefaultResponse> response) {
                if (response.code() == 201) {
                    DefaultResponse dr = response.body();
                    Toast.makeText(DetailActivity.this, dr.getMsg(), Toast.LENGTH_LONG).show();
                    //upon successful book request
                } else if (response.code() == 422) {
                    Toast.makeText(DetailActivity.this, "Book Not available. Please Bookmark for next time", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<DefaultResponse> call, Throwable t) {
                Toast.makeText(DetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
