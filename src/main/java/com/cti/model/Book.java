package com.cti.model;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author ifeify
 */
public class Book {
    @NotNull
    private String thumbnailImageLink;
    @NotNull
    private String coverImageLink;
    @NotNull
    private String title;
    @NotNull
    private List<String> authors;
    @NotNull
    private String isbn10;
    @NotNull
    private String isbn13;
    @NotNull
    private String listedBy;
    @NotNull
    private Date dateListed = new Date();
    @NotNull
    private List<String> tags;
    @NotNull
    private String condition;
    @NotNull
    private int price;
    @NotNull
    private int quantity;

    public String getThumbnailImageLink() {
        return thumbnailImageLink;
    }

    public void setThumbnailImageLink(String thumbnailImageLink) {
        this.thumbnailImageLink = thumbnailImageLink;
    }

    public String getCoverImageLink() {
        return coverImageLink;
    }

    public void setCoverImageLink(String coverImageLink) {
        this.coverImageLink = coverImageLink;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
