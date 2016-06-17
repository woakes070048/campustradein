package com.cti.model;

import com.google.common.base.MoreObjects;
import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ifeify
 */
public class Book {
    @SerializedName("id")
    private String bookId;

    private String title;

    private List<String> authors;

    private String isbn10;

    private String isbn13;

    private String listedBy;

    private Date dateListed = new Date();

    private List<String> categories;

    private String condition;

    private double price;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addAuthor(String author) {
        if(authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getIsbn10() {
        return isbn10;
    }

    public void setIsbn10(String isbn10) {
        this.isbn10 = isbn10;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getListedBy() {
        return listedBy;
    }

    public void setListedBy(String listedBy) {
        this.listedBy = listedBy;
    }

    public Date getDateListed() {
        return dateListed;
    }

    public void setDateListed(Date dateListed) {
        this.dateListed = dateListed;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void addCategory(String category) {
        if(this.categories == null) {
            this.categories = new ArrayList<>();
        }
        this.categories.add(category);
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;
        return bookId.equals(book.getBookId());
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = bookId.hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                            .add("id", bookId)
                            .add("title", title)
                            .add("authors", authors)
                            .add("ISBN_13", isbn13)
                            .add("ISBN_10", isbn10)
                            .add("listedBy", listedBy)
                            .add("dateListed", dateListed)
                            .add("categories", categories)
                            .add("condition", condition)
                            .add("price", price)
                            .toString();
    }

}
