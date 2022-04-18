package com.example.hp.ishelf.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.hp.ishelf.model.Books;

import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteDbHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME ="favorite.db";
    private static final int DATABASE_VERSION =1;
    public static final String LOGTAG ="FAVORITE";
    SQLiteOpenHelper dbhandler;
    SQLiteDatabase db;

    public  FavoriteDbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    public void open(){
        Log.i(LOGTAG,"Database Opened");
        db = dbhandler.getWritableDatabase();
    }

    public void close(){
        Log.i(LOGTAG,"Database Closed");
        dbhandler.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final  String SQL_CREATE_FAVORITE_TABLE ="CREATE TABLE "+FavoriteContract.FavoriteEntry.TABLE_NAME+ "("+
                FavoriteContract.FavoriteEntry._ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                FavoriteContract.FavoriteEntry.COLUMN_BOOKID+" INTEGER, "+
                FavoriteContract.FavoriteEntry.COLUMN_BOOKTITLE+" TEXT NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_BOOKAUTHOR+" TEXT NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_DESC+" TEXT NOT NULL,"+
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE+" TEXT NOT NULL "+
                ");";
        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FavoriteContract.FavoriteEntry.TABLE_NAME);
        onCreate(db);
    }

    //to add
    public void addFavorite(Books books){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FavoriteContract.FavoriteEntry.COLUMN_BOOKID, books.getISBN());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_BOOKTITLE, books.getBookTitle());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_BOOKAUTHOR, books.getAuthor());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_DESC, books.getDescBook());
        values.put(FavoriteContract.FavoriteEntry.COLUMN_IMAGE, books.getBookCoverPic());

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME, null,values);
        db.close();
    }
    //to delete
    public void deleteFavorite(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, FavoriteContract.FavoriteEntry.COLUMN_BOOKID+ "=" +id, null);

    }

    public List<Books> getAllFavorite() {
        String[] columns = {
                FavoriteContract.FavoriteEntry._ID,
                FavoriteContract.FavoriteEntry.COLUMN_BOOKID,
                FavoriteContract.FavoriteEntry.COLUMN_BOOKTITLE,
                FavoriteContract.FavoriteEntry.COLUMN_BOOKAUTHOR,
                FavoriteContract.FavoriteEntry.COLUMN_DESC,
                FavoriteContract.FavoriteEntry.COLUMN_IMAGE
        };
        String sortOrder =
                FavoriteContract.FavoriteEntry._ID + " ASC";
        List<Books> favoriteList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query(FavoriteContract.FavoriteEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                Books books = new Books();
                books.setISBN(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BOOKID)));
                books.setBookTitle(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BOOKTITLE)));
                books.setAuthor(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_BOOKAUTHOR)));
                books.setDescBook(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_DESC)));
                books.setBookCoverPic(cursor.getString(cursor.getColumnIndex(FavoriteContract.FavoriteEntry.COLUMN_IMAGE)));

                favoriteList.add(books);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favoriteList;
    }
}
