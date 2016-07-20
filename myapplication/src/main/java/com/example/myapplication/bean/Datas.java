package com.example.myapplication.bean;

/**
 * Created by ${Apollo} on 2016/7/2 09:29.
 */
public class Datas {
    private int iconId;
    private String tittle;
    private String body;

    public Datas(int iconId, String tittle, String body) {
        this.iconId = iconId;
        this.tittle = tittle;
        this.body = body;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
