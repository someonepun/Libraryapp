package com.example.hp.ishelf.model;

import java.util.List;

public class BooksResponse
{
    private boolean error;
    private List<Books> books;

    public BooksResponse(boolean error, List<Books> books) {
        this.error = error;
        this.books = books;
    }

    public boolean isError() {
        return error;
    }

    public List<Books> getBooks() {
        return books;
    }

}
