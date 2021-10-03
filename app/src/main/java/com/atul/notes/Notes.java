package com.atul.notes;

public class Notes {

    private int ID;
    private String title;
    private String description;

    public Notes(int ID, String title, String description) {
        this.ID = ID;
        this.title = title;
        this.description = description;
    }

    public int getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
