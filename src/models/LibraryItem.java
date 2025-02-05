package models;

import java.util.*;

abstract class LibraryItem {
    protected String id;
    protected String title;
    protected String author;
    protected String category;

    public LibraryItem(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getCategory() { return category; }

    public abstract String getInfo();
}