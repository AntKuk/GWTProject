package com.netcracker.shared;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Book implements Serializable, Comparable<Book> {
    private int id;
    private String author;
    private String title;
    private int pages;
    private int year;
    private Date date;

    public Book(int id, String author, String title, int pages, int year, Date date) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.pages = pages;
        this.year = year;
        this.date = date;
    }

    public Book() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(short year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(Book o) {
        return title.compareTo(o.getTitle());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
