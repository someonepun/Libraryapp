package com.example.hp.ishelf.data;

import android.provider.BaseColumns;

public class FavoriteContract {
    public static final class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "Favorite";
        public static final String COLUMN_BOOKID = "id";
        public static final String COLUMN_BOOKTITLE = "title";
        public static final String COLUMN_BOOKAUTHOR = "author";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_IMAGE = "imagepath";
    }
}
