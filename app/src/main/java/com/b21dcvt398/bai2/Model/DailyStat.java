package com.b21dcvt398.bai2.Model;
import java.util.Date;

public class DailyStat {
    private Date day;  // Kiểu dữ liệu Date để lưu ngày tháng
    private String income;  // Thu nhập (kiểu String)
    private String outcome; // Chi phí (kiểu String)

    // Constructor
    public DailyStat(Date day, String income, String outcome) {
        this.day = day;
        this.income = income;
        this.outcome = outcome;
    }

    public DailyStat() {
    }

    // Getter và Setter cho trường day
    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    // Getter và Setter cho income
    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    // Getter và Setter cho outcome
    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    // Phương thức tính số dư (không còn áp dụng do income và outcome là String)
    // Bạn có thể bỏ hoặc sửa lại logic tính toán nếu cần.

    @Override
    public String toString() {
        return "DailyStat{" +
                "day=" + day +
                ", income='" + income + '\'' +
                ", outcome='" + outcome + '\'' +
                '}';
    }
}
