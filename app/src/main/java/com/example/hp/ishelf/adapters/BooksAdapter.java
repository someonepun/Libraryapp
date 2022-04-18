package com.example.hp.ishelf.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.hp.ishelf.R;
import com.example.hp.ishelf.activities.DetailActivity;
import com.example.hp.ishelf.model.Books;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BooksViewHolder> {

    private Context mCtx;
    private List<Books> booksList;

    public BooksAdapter(Context mCtx, List<Books> booksList) {
        this.mCtx = mCtx;
        this.booksList = booksList;
    }

    @NonNull
    @Override
    public BooksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx)
                .inflate(R.layout.recyclerview_book, parent, false);
        return new BooksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BooksViewHolder holder, int position) {
        final Books books = booksList.get(position);
        //home server
        final String imageUri = "http://192.168.1.103:8012/img/";
        //college server
        //final String imageUri = "http://10.10.100.214:8012/img/";
        holder.textViewBookTitle.setText(books.getBookTitle());
        holder.textViewBookAuthor.setText(books.getAuthor());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(mCtx)
                .load(imageUri + books.getBookCoverPic())
                .apply(requestOptions)
                .into(holder.imageViewBookCover);

    }

    @Override
    public int getItemCount() {
        return booksList.size();
    }

    class BooksViewHolder extends RecyclerView.ViewHolder {

        TextView textViewBookTitle, textViewBookAuthor;
        ImageView imageViewBookCover;

        public BooksViewHolder(View itemView) {
            super(itemView);

            textViewBookTitle = itemView.findViewById(R.id.textView_BookTitle);
            textViewBookAuthor = itemView.findViewById(R.id.textView_BookAuthor);
            imageViewBookCover = itemView.findViewById(R.id.imageView_BookCover);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        Books clickedDataItem = booksList.get(pos);

                        //passing data by using putExtra to next details activity
                        Intent intent = new Intent(mCtx, DetailActivity.class);

                        intent.putExtra("ISBN", booksList.get(pos).getISBN());
                        intent.putExtra("Image", booksList.get(pos).getBookCoverPic());
                        intent.putExtra("Title", booksList.get(pos).getBookTitle());
                        intent.putExtra("Author", booksList.get(pos).getAuthor());
                        intent.putExtra("Desc", booksList.get(pos).getDescBook());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);

                        //Toast.makeText(v.getContext(),"You clicked "+clickedDataItem.getISBN(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
