package com.example.cafe;

public class Data{
    private String title;
    private String content;
    private String price;
    private String url;
    public Data( String title, String content, String price, String url) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.url  = url;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() { return url; }
}

