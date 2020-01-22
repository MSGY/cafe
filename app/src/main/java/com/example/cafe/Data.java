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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

