/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package library.model;

import java.sql.Date;

public class Book {
    private int bookId;
    private int authorId;
    private String title;
    private int year;
    private int numberOfPages;
    private String cover;
    private String about;
    private int read;
    private int rating;
    private String comments;
    private Date releaseDate;

    // Constructor
    public Book(int authorId, String title, int year, int numberOfPages, String cover, String about,
                int read, int rating, String comments, Date releaseDate) {
        this.authorId = authorId;
        this.title = title;
        this.year = year;
        this.numberOfPages = numberOfPages;
        this.cover = cover;
        this.about = about;
        this.read = read;
        this.rating = rating;
        this.comments = comments;
        this.releaseDate = releaseDate;
    }

    // Getters and Setters
    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getAuthorId() { return authorId; }
    public String getTitle() { return title; }
    public int getYear() { return year; }
    public int getNumberOfPages() { return numberOfPages; }
    public String getCover() { return cover; }
    public String getAbout() { return about; }
    public int getRead() { return read; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public Date getReleaseDate() { return releaseDate; }

   
}
