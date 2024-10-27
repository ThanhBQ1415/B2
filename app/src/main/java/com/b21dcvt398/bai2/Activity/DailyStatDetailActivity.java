package com.b21dcvt398.bai2.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.b21dcvt398.bai2.Dao.DailyStatDAO;
import com.b21dcvt398.bai2.Model.DailyStat;
import com.b21dcvt398.bai2.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DailyStatDetailActivity extends AppCompatActivity {

    private TextView tvDate, tvIncome, tvOutcome;
    private DailyStatDAO dailyStatDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_stat_detail);

        tvDate = findViewById(R.id.tvDate);
        tvIncome = findViewById(R.id.tvIncome);
        tvOutcome = findViewById(R.id.tvOutcome);

        dailyStatDAO = new DailyStatDAO(this);


        String dateStr = getIntent().getStringExtra("date");

        try {

                DailyStat dailyStat = dailyStatDAO.getDailyStatByDate(dateStr);
                tvDate.setText("Ngày: " +dateStr);
                tvIncome.setText("Income: " + (dailyStat != null ? dailyStat.getIncome() : "N/A"));
                tvOutcome.setText("Outcome: " + (dailyStat != null ? dailyStat.getOutcome() : "N/A"));

        } catch (Exception e) {
            e.printStackTrace();
        }



            Button btnBack = findViewById(R.id.btnBack);
            btnBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish(); // Kết thúc Activity hiện tại và quay lại Activity trước đó
                }
            });
        }


}
