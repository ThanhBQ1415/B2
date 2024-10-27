package com.b21dcvt398.bai2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.b21dcvt398.bai2.Model.DailyStat;
import com.b21dcvt398.bai2.Dao.DailyStatDAO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.b21dcvt398.bai2.R;

import com.google.android.material.card.MaterialCardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b21dcvt398.bai2.Dao.DailyStatDAO;
import com.b21dcvt398.bai2.Model.DailyStat;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MonthActivity extends AppCompatActivity {

    private TextView tvMonthTitle;
    private GridLayout gridMonth;
    private Button btnPreviousMonth, btnNextMonth;
    private int currentMonth, currentYear;
    private DailyStatDAO dailyStatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        tvMonthTitle = findViewById(R.id.tvMonthTitle);
        gridMonth = findViewById(R.id.gridMonth);
        btnPreviousMonth = findViewById(R.id.btnPreviousMonth);
        btnNextMonth = findViewById(R.id.btnNextMonth);
        dailyStatDAO = new DailyStatDAO(this);

        // Lấy tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1; // Tháng 1 là 0
        currentYear = calendar.get(Calendar.YEAR);

        // Hiển thị tháng hiện tại
        displayMonth(currentMonth, currentYear);

        // Xử lý sự kiện click cho nút Tháng trước
        btnPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMonth == 1) {
                    currentMonth = 12;
                    currentYear--;
                } else {
                    currentMonth--;
                }
                displayMonth(currentMonth, currentYear);
            }
        });

        // Xử lý sự kiện click cho nút Tháng sau
        btnNextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentMonth == 12) {
                    currentMonth = 1;
                    currentYear++;
                } else {
                    currentMonth++;
                }
                displayMonth(currentMonth, currentYear);
            }
        });
    }



    private void displayMonth(int month, int year) {
        // Xóa nội dung cũ của grid
        gridMonth.removeAllViews();

        // Cập nhật tiêu đề của tháng
        tvMonthTitle.setText("Tháng " + month + " - " + year);

        // Duyệt qua các ngày trong tháng
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, 1); // Đặt về ngày đầu tháng
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Tính chiều rộng của mỗi ô
        int totalWidth = getResources().getDisplayMetrics().widthPixels;
        int cellSize = totalWidth / 8;  // Mỗi hàng có 7 ô

        // Lấy ngày trong tuần của ngày đầu tiên trong tháng (1: CN, 2: T2, ...)
        int firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        // Thêm các ô trống để thụt vào các ngày đầu tháng
        for (int i = 1; i < firstDayOfWeek; i++) {
            View emptyView = new View(this);
            GridLayout.LayoutParams emptyParams = new GridLayout.LayoutParams();
            emptyParams.width = cellSize;
            emptyParams.height = cellSize;
            emptyView.setLayoutParams(emptyParams);
            gridMonth.addView(emptyView);
        }

        // Thêm các ô ngày vào grid
        for (int day = 1; day <= daysInMonth; day++) {
            MaterialCardView cardDay = new MaterialCardView(this);
            cardDay.setClickable(true);
            cardDay.setCardElevation(4); // Độ nổi của card
            cardDay.setRadius(16); // Bo tròn góc

            TextView tvDay = new TextView(this);
            tvDay.setPadding(16, 16, 16, 16);
            tvDay.setGravity(View.TEXT_ALIGNMENT_CENTER);
            tvDay.setTextSize(14);

            // Lấy ngày hiện tại
            calendar.set(year, month - 1, day);
            Date currentDate = calendar.getTime();

            // Tìm DailyStat cho ngày hiện tại
            String displayText = day + "\n";

            tvDay.setText(displayText);

            // Thêm TextView vào CardView
            cardDay.addView(tvDay);

            // Cài đặt kích thước cho MaterialCardView
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = cellSize;
            params.height = cellSize;
            cardDay.setLayoutParams(params);

            cardDay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                    // Chuyển sang DailyStatDetailActivity với ngày được chọn
                    Intent intent = new Intent(MonthActivity.this, DailyStatDetailActivity.class);
                    intent.putExtra("date", dateFormat.format(currentDate));
                    startActivity(intent);
                }
            });

            gridMonth.addView(cardDay);
            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }








}
