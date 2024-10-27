package com.b21dcvt398.bai2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b21dcvt398.bai2.Dao.CategoryDAO;
import com.b21dcvt398.bai2.Dao.DailyStatDAO;
import com.b21dcvt398.bai2.Dao.TransactionDAO;
import com.b21dcvt398.bai2.Model.Category;
import com.b21dcvt398.bai2.Model.Transaction;
import com.b21dcvt398.bai2.Model.DailyStat;
import com.b21dcvt398.bai2.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddTransactionAct extends AppCompatActivity {

    private TransactionDAO transactionDAO;
    private CategoryDAO categoryDAO;
    private DailyStatDAO dailyStatDAO;
    private RadioGroup radioGroupTransactionType;
    private Spinner spinnerCategory;
    private EditText etAmount;
    private TimePicker timePicker;
    private EditText etNote;
    private Button btnAdd;
    private ImageButton btnAddCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtransaction);

        transactionDAO = new TransactionDAO(this);
        categoryDAO = new CategoryDAO(this);

        radioGroupTransactionType = findViewById(R.id.radioGroupTransactionType);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        etAmount = findViewById(R.id.etAmount);
        timePicker = findViewById(R.id.timePicker);
        etNote = findViewById(R.id.etNote);
        btnAdd = findViewById(R.id.btnAdd);
        btnAddCategory = findViewById(R.id.btnAddCategory);

        loadCategories(true);

        radioGroupTransactionType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbThu) {
                    loadCategories(true);
                } else if (checkedId == R.id.rbChi) {
                    loadCategories(false);
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTransaction();
                Intent intent = new Intent(AddTransactionAct.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddTransactionAct.this,AddCategoryActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadCategories(boolean isIncome) {
        List<Category> categories = categoryDAO.getAllCategories();
        List<String> categoryNames = new ArrayList<>();

        String type = isIncome ? "Income" : "Expense";
        for (Category category : categories) {
            if (category.getType().equals(type)) {
                categoryNames.add(category.getName());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
    }

    private void addTransaction() {
        int selectedTypeId = radioGroupTransactionType.getCheckedRadioButtonId();
        String type;
        if (selectedTypeId == R.id.rbThu) {
            type = "Income";
        } else if (selectedTypeId == R.id.rbChi) {
            type = "Expense";
        } else {
            Toast.makeText(this, "Please select a transaction type.", Toast.LENGTH_SHORT).show();
            return;
        }

        String amountStr = etAmount.getText().toString();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount.", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount = Double.parseDouble(amountStr);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        calendar.set(Calendar.MINUTE, timePicker.getMinute());
        Date day = calendar.getTime();

        String note = etNote.getText().toString();
        String category = spinnerCategory.getSelectedItem().toString();

        Transaction transaction = new Transaction();
        transaction.setName(category);
        transaction.setAmount(amount);
        transaction.setDay(day);
        transaction.setNote(note);
        transaction.setType(type);



        long result = transactionDAO.addTransaction(transaction);

        if (result > 0) {
            Toast.makeText(this, "Transaction added successfully!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add transaction.", Toast.LENGTH_SHORT).show();
        }
        DailyStat dailyStat = new DailyStat();
        dailyStat.setDay(day);
        dailyStatDAO.addOrUpdateDailyStat(dailyStat);
    }
}
