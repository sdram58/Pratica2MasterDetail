package com.catata.pratica2masterdetail.modelo;

public class LibroItem {
    public String id;
    public String author;
    public String description;
    public String publication_date;
    public String title;
    public String url_image;


    public LibroItem(String id, String author, String description, String publication_date, String title, String url_image) {
        this.id = id;
        this.author = author;
        this.description = description;
        this.publication_date = publication_date;
        this.title = title;
        this.url_image = url_image;
    }


    @Override
    public String toString() {
        return this.author + " " + this.title;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPublication_date(String publication_date) {
        this.publication_date = publication_date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUrl_image(String url_image) {
        this.url_image = url_image;
    }

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublication_date() {
        return publication_date;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl_image() {
        return url_image;
    }
}
