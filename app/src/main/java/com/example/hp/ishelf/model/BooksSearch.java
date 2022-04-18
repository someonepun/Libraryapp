package com.example.hp.ishelf.model;

public class BooksSearch {
    private int ISBN;
    private String BookTitle, Author,DescBook,BookCoverPic;

    public BooksSearch(int ISBN, String bookTitle, String author, String descBook, String bookCoverPic) {
        this.ISBN = ISBN;
        BookTitle = bookTitle;
        Author = author;
        DescBook = descBook;
        BookCoverPic = bookCoverPic;
    }

    public int getISBN() {
        return ISBN;
    }

    public String getBookTitle() {
        return BookTitle;
    }

    public String getAuthor() {
        return Author;
    }

    public String getDescBook() {
        return DescBook;
    }

    public String getBookCoverPic() {
        return BookCoverPic;
    }
}
