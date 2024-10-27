package com.b21dcvt398.bai2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.b21dcvt398.bai2.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.b21dcvt398.bai2.Dao.TransactionDAO;
public class tkTime extends AppCompatActivity {
    private CustomBarChart customBarChart;
    private Spinner spinnerTime;
    private TransactionDAO transactionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tktime);

        customBarChart = findViewById(R.id.customBarChart);
        spinnerTime = findViewById(R.id.spinnerTime);
        transactionDAO = new TransactionDAO(this);
        Button back = findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Lắng nghe sự kiện thay đổi trên spinner
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Float> incomeData;
                List<Float> expenseData;

                if (position == 0) { // Chọn theo tháng
                    // Gọi phương thức lấy tổng amount theo tháng trong năm 2024
                    List<Double> monthlyAmounts = transactionDAO.getTotalAmountByYear(2024);

                    // Chuyển đổi từ List<Double> sang List<Float> để phù hợp với dữ liệu biểu đồ
                    incomeData = new ArrayList<>();
                    for (Double amount : monthlyAmounts) {
                        incomeData.add(amount.floatValue()); // Chuyển đổi giá trị
                    }


                    // Gọi phương thức lấy tổng chi tiêu theo tháng trong năm 2024
                    List<Double> monthlyExpenses = transactionDAO.getTotalExpenseByYear(2024);

                    // Chuyển đổi từ List<Double> sang List<Float> cho chi tiêu
                    expenseData = new ArrayList<>();
                    for (Double expense : monthlyExpenses) {
                        expenseData.add(expense.floatValue());
                    }

                } else { // Chọn theo năm
                    incomeData = new ArrayList<>();
                    // Gọi phương thức lấy tổng amount theo năm từ 2022 đến 2026 cho thu nhập
                    Map<Integer, Double> yearlyIncomeMap = transactionDAO.getTotalIncomeByYears(2022, 2026);
                    for (int year = 2022; year <= 2026; year++) {
                        incomeData.add(yearlyIncomeMap.getOrDefault(year, 0.0).floatValue());
                    }
                    expenseData = new ArrayList<>();
                    // Gọi phương thức lấy tổng chi tiêu theo năm từ 2022 đến 2026
                    Map<Integer, Double> yearlyExpensesMap = transactionDAO.getTotalExpenseByYears(2022, 2026);
                    for (int year = 2022; year <= 2026; year++) {
                        expenseData.add(yearlyExpensesMap.getOrDefault(year, 0.0).floatValue());
                    }
                }

                // Truyền dữ liệu vào biểu đồ
                customBarChart.setData(incomeData, expenseData);
                customBarChart.invalidate(); // Vẽ lại biểu đồ

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Không làm gì nếu không có gì được chọn
            }
        });
    }

    // Hàm tạo dữ liệu thu nhập theo tháng (12 tháng)
    private List<Float> generateMonthlyIncomeData() {
        List<Float> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(100f + i * 10); // Giá trị mẫu, bạn có thể thay đổi tùy ý
        }
        return data;
    }

    // Hàm tạo dữ liệu chi phí theo tháng (12 tháng)
    private List<Float> generateMonthlyExpenseData() {
        List<Float> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            data.add(80f + i * 8); // Giá trị mẫu, bạn có thể thay đổi tùy ý
        }
        return data;
    }

    // Hàm tạo dữ liệu thu nhập theo năm (5 năm)
    private List<Float> generateYearlyIncomeData() {
        List<Float> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(300f + i * 50); // Giá trị mẫu, bạn có thể thay đổi tùy ý
        }
        return data;
    }

    // Hàm tạo dữ liệu chi phí theo năm (5 năm)
    private List<Float> generateYearlyExpenseData() {
        List<Float> data = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            data.add(200f + i * 30); // Giá trị mẫu, bạn có thể thay đổi tùy ý
        }
        return data;
    }
}
