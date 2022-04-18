package com.example.hp.ishelf.model;

public class Books {
    //use @SerializedName("isbn") private int ISBN to use seperate name
    private String ISBN, BookTitle, Author, DescBook, BookCoverPic;
    public Books(String ISBN, String bookTitle, String author, String descBook, String bookCoverPic) {
        this.ISBN = ISBN;
        BookTitle = bookTitle;
        Author = author;
        DescBook = descBook;
        BookCoverPic = bookCoverPic;
    }
    public Books() {
    }
    public String getISBN() {
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

    public void setBookTitle(String bookTitle) {
        BookTitle = bookTitle;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setDescBook(String descBook) {
        DescBook = descBook;
    }

    public void setBookCoverPic(String bookCoverPic) {
        BookCoverPic = bookCoverPic;
    }
}
