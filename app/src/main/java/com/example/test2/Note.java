package com.example.test2;

public class Note {
    private int id;
    private String Category;
    private String Desc;

    public Note(String category, String desc) {
        Category = category;
        Desc = desc;
    }

    public Note(int id, String category, String desc) {
        this.id = id;
        Category = category;
        Desc = desc;
    }

    public Note() {

    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public int getId() {
        return id;
    }
}
