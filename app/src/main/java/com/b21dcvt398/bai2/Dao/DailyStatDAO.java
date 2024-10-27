package com.b21dcvt398.bai2.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.b21dcvt398.bai2.Model.DailyStat;
import android.util.Log;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DailyStatDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DailyStat.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_DAILYSTAT = "dailystat";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_INCOME = "income";
    private static final String COLUMN_OUTCOME = "outcome";

    // Câu lệnh SQL để tạo bảng
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_DAILYSTAT + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DAY + " TEXT NOT NULL, "
            + COLUMN_INCOME + " TEXT, "  // Đổi sang TEXT
            + COLUMN_OUTCOME + " TEXT);";  // Đổi sang TEXT

    public DailyStatDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo bảng trong CSDL
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }
    public void printDailyStats(List<DailyStat> stats) {
        for (DailyStat stat : stats) {
            // Lấy thông tin của DailyStat
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String formattedDate = dateFormat.format(stat.getDay());

            // In ra thông tin của DailyStat
            Log.d("DailyStat", "Date: " + formattedDate +
                    ", Income: " + stat.getIncome() +
                    ", Outcome: " + stat.getOutcome());
        }
    }
    // Nâng cấp bảng (nếu cần)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILYSTAT);
        onCreate(db);
    }

    // Thêm bản ghi mới vào bảng dailystat
    public void addDailyStat(DailyStat dailyStat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        values.put(COLUMN_DAY, dateFormat.format(dailyStat.getDay()));  // Chuyển ngày sang chuỗi
        values.put(COLUMN_INCOME, dailyStat.getIncome());  // Lưu income dạng chuỗi
        values.put(COLUMN_OUTCOME, dailyStat.getOutcome());  // Lưu outcome dạng chuỗi

        db.insert(TABLE_DAILYSTAT, null, values);
        db.close();  // Đóng kết nối sau khi thêm
    }



    // Lấy tất cả bản ghi từ bảng dailystat
    public List<DailyStat> getAllDailyStats() {
        List<DailyStat> stats = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DAILYSTAT, new String[]{COLUMN_ID, COLUMN_DAY, COLUMN_INCOME, COLUMN_OUTCOME},
                null, null, null, null, null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        if (cursor.moveToFirst()) {
            do {
                DailyStat stat = new DailyStat();
                try {
                    // Chuyển chuỗi thành ngày
                    String dayString = cursor.getString(1);
                    Date day = sdf.parse(dayString);  // Chuyển chuỗi thành đối tượng Date
                    stat.setDay(day);  // Set đối tượng Date vào stat
                } catch (ParseException e) {
                    e.printStackTrace();  // Xử lý lỗi nếu không thể parse ngày
                }
                stat.setIncome(cursor.getString(2));  // Lưu income kiểu String
                stat.setOutcome(cursor.getString(3));  // Lưu outcome kiểu String
                stats.add(stat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();  // Đóng kết nối sau khi truy vấn
        return stats;
    }
    public void addOrUpdateDailyStat(DailyStat dailyStat) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String dateStr = dateFormat.format(dailyStat.getDay());

        ContentValues values = new ContentValues();
        values.put(COLUMN_DAY, dateStr);
        values.put(COLUMN_INCOME, dailyStat.getIncome());
        values.put(COLUMN_OUTCOME, dailyStat.getOutcome());

        // Thêm vào cơ sở dữ liệu
        db.insert(TABLE_DAILYSTAT, null, values);
        db.close();
    }

    public DailyStat getDailyStatByDate(String dateString) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Truy vấn bảng dailystat với cột day có giá trị khớp với dateString
        Cursor cursor = db.query(TABLE_DAILYSTAT, new String[]{COLUMN_ID, COLUMN_DAY, COLUMN_INCOME, COLUMN_OUTCOME},
                COLUMN_DAY + "=?", new String[]{dateString}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            DailyStat stat = new DailyStat();
            try {
                // Chuyển chuỗi ngày thành đối tượng Date theo định dạng "EEE MMM dd HH:mm:ss z yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                Date day = sdf.parse(cursor.getString(1));
                stat.setDay(day);  // Set đối tượng Date vào stat
            } catch (ParseException e) {
                e.printStackTrace();  // Xử lý lỗi nếu không thể parse ngày
            }
            stat.setIncome(cursor.getString(2));  // Lưu income kiểu String
            stat.setOutcome(cursor.getString(3));  // Lưu outcome kiểu String
            cursor.close();
            return stat;
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }



}
