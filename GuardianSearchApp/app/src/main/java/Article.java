package com.example.guardiansearch;

public class Article {
    private String id;
    private String title;
    private String section;
    private String url;

    // Constructor with ID
    public Article(String id, String title, String section, String url) {
        this.id = id;
        this.title = title;
        this.section = section;
        this.url = url;
    }

    // Optional alternate constructor (no ID)
    public Article(String title, String section, String url) {
        this(null, title, section, url);
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getSection() { return section; }
    public String getUrl() { return url; }
}
