
package com.b21dcvt398.bai2.Model;

import java.util.Date;

public class Transaction {
    private Long id; // PK
    private String name; // Tên danh mục
    private Double amount;
    private Date day;
    private String note;
    private String type; // Loại giao dịch
    private Category category; // Danh mục

    // Constructors
    public Transaction() {}

    public Transaction(Long id, String name, Category category, Double amount, Date day, String note, String type) {
        this.id = id;
        this.name = category != null ? category.getName() : name; // Lưu tên danh mục vào name
        this.category = category; // Lưu danh mục
        this.amount = amount;
        this.day = day;
        this.note = note;
        this.type = type;
    }

    // Getters and setters
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
        this.name = category != null ? category.getName() : null; // Cập nhật name khi category thay đổi
    }
}






















//package com.b21dcvt398.bai2.Model;
//import java.util.Date;
//
//public class Transaction {
//    private Long id; // PK
//    private String name;
//    private Double amount;
//    private Date day;
//    private String note;
//    private String type; // Thuộc tính mới cho loại giao dịch
//
//    // Constructors, getters, setters
//    public Transaction() {}
//
//    public Transaction(Long id, String name, Category cat, Double amount, Date day, String note, String type) {
//        this.id = id;
//        this.name = name;
//        this.amount = amount;
//        this.day = day;
//        this.note = note;
//        this.type = type; // Khởi tạo thuộc tính type
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//
//
//    public Double getAmount() {
//        return amount;
//    }
//
//    public void setAmount(Double amount) {
//        this.amount = amount;
//    }
//
//    public Date getDay() {
//        return day;
//    }
//
//    public void setDay(Date day) {
//        this.day = day;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
//}
