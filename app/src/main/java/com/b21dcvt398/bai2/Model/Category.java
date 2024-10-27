package com.b21dcvt398.bai2.Model;

public class Category {
    private int id;
    private String name;
    private String parent; // Đối tượng Category cha
    private String icon; // Đường dẫn đến biểu tượng
    private String note;
    private String type; // New field

    // Constructor
    public Category(int id, String name, String parent, String icon, String note, String type) {
        this.id = id;
        this.name = name;
        this.parent = parent;
        this.icon = icon;
        this.note = note;
        this.type = type;
    }
    public Category() {
    }
    // Getter và Setter
    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getParent() { return parent; }

    public void setParent(String parent) { this.parent = parent; }

    public String getIcon() { return icon; }

    public void setIcon(String icon) { this.icon = icon; }

    public String getNote() { return note; }

    public void setNote(String note) { this.note = note; }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    @Override
    public String toString() {
        return name; // Display name in Spinner
    }
}
