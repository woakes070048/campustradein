package com.cti.model;

import com.google.common.base.MoreObjects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ifeify
 */
public class BookInfo {
    // TODO: cover images (should come from open library)
    private String previewLink;
    private String title;
    private List<String> authors;
    private String publisher;
    private String datePublished;
    private String isbn10;
    private String isbn13;
    private List<String> tags = new ArrayList<>();

    public String getPreviewLink() {
        return previewLink;
    }

    public void setPreviewLink(String previewLink) {
        this.previewLink = previewLink;
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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public void setPublishedDate(String datePublished) {
        this.datePublished = datePublished;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookInfo bookInfo = (BookInfo) o;

        if (!isbn10.equals(bookInfo.isbn10)) return false;
        return isbn13.equals(bookInfo.isbn13);

    }

    @Override
    public int hashCode() {
        int result = isbn10.hashCode();
        result = 31 * result + isbn13.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                            .add("title", title)
                            .add("authors", authors)
                            .add("ISBN_10", isbn10)
                            .add("ISBN_13", isbn13)
                            .add("publisher", publisher)
                            .add("datePublished", datePublished)
                            .toString();
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public List<String> getTags() {
        return tags;
    }
}
