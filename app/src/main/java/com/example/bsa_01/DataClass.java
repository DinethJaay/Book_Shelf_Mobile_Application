package com.example.bsa_01;

public class DataClass {
    private  String bookCategories;
    private  String bookTitle;
    private  String bookAuthor;
    private  String bookIsbn;
    private  String bookImage;

    private String bookprice;
    private String bookquanitiy;
    private String description;

    private String key;

    public String getBookCategories() {
        return bookCategories;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public String getBookImage() {
        return bookImage;
    }
    public String getBookprice() {
        return bookprice;
    }

    public String getBookquanitiy() {
        return bookquanitiy;
    }

    public String getDescription() {
        return description;
    }
    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }


    public DataClass(String bookIsbn, String bookTitle, String bookCategories, String bookAuthor, String bookprice,String bookquanitiy,String description,String bookImage) {
        this.bookIsbn = bookIsbn;
        this.bookTitle = bookTitle;
        this.bookCategories = bookCategories;
        this.bookAuthor = bookAuthor;
        this.bookprice=bookprice;
        this.bookquanitiy=bookquanitiy;
        this.description= description;
        this.bookImage = bookImage;

    }
    public DataClass(){

    }
}