package com.b21dcvt398.bai2.Activity;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class CustomBarChart extends View {
    private Paint incomePaint;
    private Paint expensePaint;
    private Paint linePaint;
    private List<Float> incomeData = new ArrayList<>();
    private List<Float> expenseData = new ArrayList<>();

    public CustomBarChart(Context context) {
        super(context);
        init();
    }

    public CustomBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Khởi tạo Paint cho income (màu xanh)
        incomePaint = new Paint();
        incomePaint.setColor(Color.GREEN);
        incomePaint.setStyle(Paint.Style.FILL);

        // Khởi tạo Paint cho expense (màu đỏ)
        expensePaint = new Paint();
        expensePaint.setColor(Color.RED);
        expensePaint.setStyle(Paint.Style.FILL);

        // Khởi tạo Paint cho đường thời gian
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(5);
    }

    public void setData(List<Float> incomeData, List<Float> expenseData) {
        this.incomeData = incomeData;
        this.expenseData = expenseData;
        invalidate(); // Vẽ lại khi dữ liệu thay đổi
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight() / 2;
        int padding = 50;
        int barWidth;

        // Xác định chiều rộng cột
        if (incomeData.size() == 12) {
            barWidth = 40;
        } else {
            barWidth = 80;
        }

        // Tìm giá trị lớn nhất trong dữ liệu để chuẩn hóa
        float maxIncome = 0;
        float maxExpense = 0;

        for (Float income : incomeData) {
            if (income > maxIncome) {
                maxIncome = income;
            }
        }

        for (Float expense : expenseData) {
            if (expense > maxExpense) {
                maxExpense = expense;
            }
        }

        float maxValue = Math.max(maxIncome, maxExpense);
        float scale = (height - padding * 2) / maxValue; // Tính tỷ lệ chuẩn hóa

        // Vẽ đường mốc thời gian nằm ngang
        float timeLineY = height / 2;
        canvas.drawLine(padding, timeLineY, width - padding, timeLineY, linePaint);

        // Vẽ đường thẳng đứng ở đầu biểu đồ
        canvas.drawLine(padding, padding, padding, height - padding, linePaint);

        // Vẽ mũi tên ở cuối đường nằm ngang
        float arrowX = width - padding;
        float arrowSize = 20; // Kích thước của mũi tên
        Path arrowPath = new Path();
        arrowPath.moveTo(arrowX, timeLineY); // Điểm giữa mũi tên
        arrowPath.lineTo(arrowX - arrowSize, timeLineY - arrowSize / 2); // Đỉnh trái
        arrowPath.lineTo(arrowX - arrowSize, timeLineY + arrowSize / 2); // Đỉnh phải
        arrowPath.close();
        canvas.drawPath(arrowPath, linePaint);

        int x = padding + barWidth;

        for (int i = 0; i < incomeData.size(); i++) {
            // Vẽ cột income trên đường mốc thời gian
            float incomeHeight = incomeData.get(i) * scale; // Chiều cao chuẩn hóa
            canvas.drawRect(x, timeLineY - incomeHeight, x + barWidth, timeLineY, incomePaint);

            // Vẽ cột expense dưới đường mốc thời gian
            float expenseHeight = expenseData.get(i) * scale; // Chiều cao chuẩn hóa
            canvas.drawRect(x, timeLineY, x + barWidth, timeLineY + expenseHeight, expensePaint);

            // Khoảng cách giữa các cột
            x += barWidth * 1.5;
        }
    }


}

