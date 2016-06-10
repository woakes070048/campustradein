package com.cti.model;

import com.google.common.base.MoreObjects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ifeify
 */
public class Book {
    private String title;
    private String isbn13;
    private String isbn10;
    private Date listedOn;
    private String listedBy;
    private String condition;
    private List<String> tags;
    private List<String> authors;

    public Book() {}

    public Book(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDateListed() {
        return listedOn;
    }

    public String listedBy() {
        return listedBy;
    }

    public void setDateListed(Date dateListed) {
        this.listedOn = dateListed;
    }

    public void setListedBy(String username) {
        this.listedBy = username;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String toString() {
        return MoreObjects.toStringHelper(this)
                            .add("title", title)
                            .add("isbn13", isbn13)
                            .add("isbn10", isbn10)
                            .add("listedOn", listedOn)
                            .add("listedBy", listedBy)
                            .toString();
    }
}
