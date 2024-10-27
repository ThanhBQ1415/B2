package com.b21dcvt398.bai2.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.b21dcvt398.bai2.Model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    private SQLiteDatabase db;
    private static final String DATABASE_NAME = "AppDatabase.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CATEGORY = "Category";

    // Column names
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PARENT = "parent";
    private static final String COLUMN_ICON = "icon";
    private static final String COLUMN_NOTE = "note";
    private static final String COLUMN_TYPE = "type"; // New column

    private final Context context;

    public CategoryDAO(Context context) {
        this.context = context;
        open();
    }

    private class DatabaseHelper extends SQLiteOpenHelper {
        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_PARENT + " TEXT, "
                    + COLUMN_ICON + " TEXT, "
                    + COLUMN_NOTE + " TEXT, "
                    + COLUMN_TYPE + " TEXT)"; // Add type column
            db.execSQL(CREATE_CATEGORY_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
            onCreate(db);
        }
    }

    public void open() throws SQLException {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void close() { db.close(); }

    public long insertCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, category.getName());
        values.put(COLUMN_PARENT, category.getParent());
        values.put(COLUMN_ICON, category.getIcon());
        values.put(COLUMN_NOTE, category.getNote());
        values.put(COLUMN_TYPE, category.getType()); // Insert type

        return db.insert(TABLE_CATEGORY, null, values);
    }



    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        Cursor cursor = db.query(TABLE_CATEGORY, null, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String parent = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT));
                String icon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ICON));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)); // Retrieve type

                Category category = new Category(id, name, parent, icon, note, type);
                categories.add(category);
            }
            cursor.close();
        }
        return categories;
    }

    public Category getCategoryById(int categoryId) {
        Category category = null;
        Cursor cursor = db.query(TABLE_CATEGORY, null, COLUMN_ID + " = ?", new String[]{String.valueOf(categoryId)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String parent = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT));
                String icon = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ICON));
                String note = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTE));
                String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)); // Retrieve type

                category = new Category(id, name, parent, icon, note, type);
            }
            cursor.close();
        }
        return category;
    }
}
