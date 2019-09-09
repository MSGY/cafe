package com.example.cafe;

public class Data{
    private String title;
    private String content;
    private String price;
    private int resId;
    public Data(String title, String content, String price, int resId) {
        this.title = title;
        this.content = content;
        this.price = price;
        this.resId = resId;
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

    public int getResId() {
        return resId;
    }
}