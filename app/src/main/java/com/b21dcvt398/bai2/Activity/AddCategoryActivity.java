package com.b21dcvt398.bai2.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b21dcvt398.bai2.Dao.CategoryDAO;
import com.b21dcvt398.bai2.Model.Category;
import com.b21dcvt398.bai2.R;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity {

    private Spinner spinnerTransactionType, spinnerParentCategory;
    private EditText etCategoryName;
    private Button btnSaveCategory, btnSelectIcon;
    private CategoryDAO categoryDAO;

    private int selectedIcon; // Biến để lưu icon đã chọn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryDAO = new CategoryDAO(this);

        spinnerTransactionType = findViewById(R.id.spinnerTransactionType);
        spinnerParentCategory = findViewById(R.id.spinnerParentCategory);
        etCategoryName = findViewById(R.id.etCategoryName);
        btnSaveCategory = findViewById(R.id.btnSaveCategory);
        btnSelectIcon = findViewById(R.id.btnSelectIcon); // Nút chọn icon

        loadParentCategorySpinner();

        // Thiết lập sự kiện cho nút chọn icon
        btnSelectIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showIconSelectionDialog();
            }
        });

        btnSaveCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCategory();
            }
        });
    }

    private void showIconSelectionDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_icon_selection);

        GridView gridView = dialog.findViewById(R.id.gridIcons);

        // Tạo danh sách biểu tượng
        final int[] icons = {R.drawable.tienxe, R.drawable.tiennha, R.drawable.edit}; // Các biểu tượng

        // Tạo Adapter cho GridView
        IconAdapter iconAdapter = new IconAdapter(this, icons);
        gridView.setAdapter(iconAdapter);

        // Thiết lập sự kiện khi chọn biểu tượng
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedIcon = icons[position]; // Lưu icon đã chọn
                dialog.dismiss(); // Đóng dialog
                // Cập nhật UI với biểu tượng đã chọn nếu cần thiết
                // Thí dụ: bạn có thể hiển thị icon đã chọn bên cạnh nút chọn icon
                // imageViewSelectedIcon.setImageResource(selectedIcon); // Nếu bạn có ImageView
            }
        });

        dialog.show();
    }

    private void loadParentCategorySpinner() {
        List<Category> categories = categoryDAO.getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }

        ArrayAdapter<String> parentAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        parentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerParentCategory.setAdapter(parentAdapter);
    }

    private void saveCategory() {
        String transactionType = spinnerTransactionType.getSelectedItem().toString();
        String categoryName = etCategoryName.getText().toString();

        String parentCategory = spinnerParentCategory.getSelectedItem().toString();

        if (categoryName.isEmpty()) {
            Toast.makeText(this, "Please enter a category name.", Toast.LENGTH_SHORT).show();
            return;
        }

        Category category = new Category();
        category.setType(transactionType.equals("Thu") ? "Income" : "Expense");
        category.setName(categoryName);
        category.setIcon(getResources().getResourceEntryName(selectedIcon)); // Lưu tên icon
        category.setParent(parentCategory);

        long result = categoryDAO.insertCategory(category);

        if (result > 0) {
            Toast.makeText(this, "Category added successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add category.", Toast.LENGTH_SHORT).show();
        }
    }
}
