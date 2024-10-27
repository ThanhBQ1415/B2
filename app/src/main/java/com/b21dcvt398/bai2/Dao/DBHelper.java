package com.b21dcvt398.bai2.Dao;



import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "transactions.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng
    private static final String TABLE_TRANSACTION = "Transaction";

    // Các cột trong bảng
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TYPE = "type";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Transaction nếu chưa tồn tại
        String CREATE_TRANSACTION_TABLE = "CREATE TABLE " + TABLE_TRANSACTION + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_AMOUNT + " REAL NOT NULL, " +
                COLUMN_DAY + " INTEGER  NOT NULL, " + // Có thể chuyển đổi thành DATE nếu muốn
                COLUMN_NOTE + " TEXT, " +
                COLUMN_TYPE + " TEXT" +
                ")";
        db.execSQL(CREATE_TRANSACTION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu nó tồn tại và tạo lại bảng mới
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTION);
        onCreate(db);
    }
}
