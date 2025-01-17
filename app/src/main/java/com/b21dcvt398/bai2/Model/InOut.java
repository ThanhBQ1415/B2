package com.b21dcvt398.bai2.Model;

public class InOut {
    private Long id; // PK
    private String name;
    private String type;

    // Constructors, getters, setters
    public InOut() {}

    public InOut(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
