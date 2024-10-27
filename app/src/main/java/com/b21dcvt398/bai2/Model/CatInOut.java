package com.b21dcvt398.bai2.Model;

public class CatInOut {
    private Long id; // PK
    private Category cat; // FK đến Category
    private InOut inout; // FK đến InOut

    // Constructors, getters, setters
    public CatInOut() {}

    public CatInOut(Long id, Category cat, InOut inout) {
        this.id = id;
        this.cat = cat;
        this.inout = inout;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    public InOut getInOut() {
        return inout;
    }

    public void setInOut(InOut inout) {
        this.inout = inout;
    }
}

