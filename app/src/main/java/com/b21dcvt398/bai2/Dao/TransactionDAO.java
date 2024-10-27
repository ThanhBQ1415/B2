package com.b21dcvt398.bai2.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.b21dcvt398.bai2.Model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionDAO extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "transaction_db";

    // Tên bảng và các cột
    private static final String TABLE_TRANSACTIONS = "Transactions";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AMOUNT = "amount";
    private static final String COLUMN_DAY = "day";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TYPE = "type";

    public TransactionDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_TRANSACTIONS + " ("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_AMOUNT + " REAL, "
                + COLUMN_DAY + " INTEGER, "
                + COLUMN_NOTE + " TEXT, "
                + COLUMN_TYPE + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
        onCreate(db);
    }

    // Thêm giao dịch mới
    public long addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, transaction.getName());
        values.put(COLUMN_AMOUNT, transaction.getAmount());
        values.put(COLUMN_DAY, transaction.getDay().getTime());
        values.put(COLUMN_NOTE, transaction.getNote());
        values.put(COLUMN_TYPE, transaction.getType());

        long id = db.insert(TABLE_TRANSACTIONS, null, values);
        db.close();
        return id;
    }

    // Lấy tất cả giao dịch
    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction();
                transaction.setId(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                transaction.setName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
                transaction.setAmount(cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_AMOUNT)));
                transaction.setDay(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_DAY))));
                transaction.setNote(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE)));
                transaction.setType(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)));

                transactions.add(transaction);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return transactions;
    }










    // Lấy tổng amount cho từng tháng trong năm 2024
    public List<Double> getTotalAmountByYear(int year) {
        List<Double> monthlyExpenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        for (int month = 1; month <= 12; month++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, 1); // Tháng bắt đầu từ 0

            long startOfMonth = calendar.getTimeInMillis();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            long endOfMonth = calendar.getTimeInMillis();

            String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS
                    + " WHERE " + COLUMN_TYPE + " = 'Income' AND " + COLUMN_DAY + " BETWEEN ? AND ?";

            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(startOfMonth), String.valueOf(endOfMonth)});

            double totalExpense = 0;
            if (cursor.moveToFirst()) {
                totalExpense = cursor.getDouble(0);
            }
            monthlyExpenses.add(totalExpense);
            cursor.close();
        }

        db.close();
        return monthlyExpenses;
    }

    public List<Double> getTotalExpenseByYear(int year) {
        List<Double> monthlyExpenses = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        for (int month = 1; month <= 12; month++) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, 1); // Tháng bắt đầu từ 0

            long startOfMonth = calendar.getTimeInMillis();
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            long endOfMonth = calendar.getTimeInMillis();

            String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS
                    + " WHERE " + COLUMN_TYPE + " = 'Expense' AND " + COLUMN_DAY + " BETWEEN ? AND ?";

            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(startOfMonth), String.valueOf(endOfMonth)});

            double totalExpense = 0;
            if (cursor.moveToFirst()) {
                totalExpense = cursor.getDouble(0);
            }
            monthlyExpenses.add(totalExpense);
            cursor.close();
        }

        db.close();
        return monthlyExpenses;
    }
    // Trong lớp TransactionDAO
    public Map<Integer, Double> getTotalIncomeByYears(int startYear, int endYear) {
        Map<Integer, Double> yearlyIncomeTotals = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        for (int year = startYear; year <= endYear; year++) {
            String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS
                    + " WHERE " + COLUMN_DAY + " BETWEEN ? AND ? AND " + COLUMN_TYPE + " = ?";

            // Lấy ngày bắt đầu và kết thúc cho mỗi năm
            long startOfYear = new GregorianCalendar(year, Calendar.JANUARY, 1).getTimeInMillis();
            long endOfYear = new GregorianCalendar(year, Calendar.DECEMBER, 31).getTimeInMillis();

            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(startOfYear), String.valueOf(endOfYear), "Income"});

            double totalIncome = 0;
            if (cursor.moveToFirst()) {
                totalIncome = cursor.getDouble(0);
            }
            yearlyIncomeTotals.put(year, totalIncome);
            cursor.close();
        }

        db.close();
        return yearlyIncomeTotals;
    }

    public Map<Integer, Double> getTotalExpenseByYears(int startYear, int endYear) {
        Map<Integer, Double> yearlyExpenseTotals = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        for (int year = startYear; year <= endYear; year++) {
            String selectQuery = "SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TABLE_TRANSACTIONS
                    + " WHERE " + COLUMN_DAY + " BETWEEN ? AND ? AND " + COLUMN_TYPE + " = ?";

            // Lấy ngày bắt đầu và kết thúc cho mỗi năm
            long startOfYear = new GregorianCalendar(year, Calendar.JANUARY, 1).getTimeInMillis();
            long endOfYear = new GregorianCalendar(year, Calendar.DECEMBER, 31).getTimeInMillis();

            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(startOfYear), String.valueOf(endOfYear), "Expense"});

            double totalExpense = 0;
            if (cursor.moveToFirst()) {
                totalExpense = cursor.getDouble(0);
            }
            yearlyExpenseTotals.put(year, totalExpense);
            cursor.close();
        }

        db.close();
        return yearlyExpenseTotals;
    }


}
