package com.b21dcvt398.bai2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.b21dcvt398.bai2.Dao.DailyStatDAO;
import com.b21dcvt398.bai2.Dao.TransactionDAO;
import com.b21dcvt398.bai2.Model.Category;
import com.b21dcvt398.bai2.Model.DailyStat;
import com.b21dcvt398.bai2.Model.Transaction;
import com.b21dcvt398.bai2.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ListView lvTransactions;
    private TransactionDAO transactionDAO;
    private List<Transaction> transactions;
    private TextView tvTotalIncome;
    private TextView tvTotalExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvTransactions = findViewById(R.id.lvTransactions);
        tvTotalIncome = findViewById(R.id.tvTotalIncome);
        tvTotalExpense = findViewById(R.id.tvTotalExpense);
        Button btnAddTransaction = findViewById(R.id.btnAddTransaction);


        Button btnMonth = findViewById(R.id.btnMonth);
        Button btnttk = findViewById(R.id.btntk);
        btnMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MonthActivity.class);
                startActivity(intent);
            }
        });
        btnttk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Calendar calendar = Calendar.getInstance();
//                calendar.set(2024, Calendar.FEBRUARY, 15);
//                Date date = calendar.getTime();
//                Category category = new Category();
//                Transaction transaction = new Transaction(null, "Làm thêm", category, 5000000.0, date, "Mua bút, sổ tay", "Income");
//                 transactionDAO.addTransaction(transaction);


                Intent intent = new Intent(MainActivity.this, thongke.class);
                startActivity(intent);
            }
        });

        transactionDAO = new TransactionDAO(this);
        transactions = new ArrayList<>();



        DailyStatDAO dailyStatDAO = new DailyStatDAO(this);
        List<DailyStat> dailyStats = dailyStatDAO.getAllDailyStats(); // Lấy tất cả DailyStat


        dailyStatDAO.printDailyStats(dailyStats);



        loadTransactions();

        // Xử lý sự kiện cho nút Thêm giao dịch
        btnAddTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddTransactionAct.class);
                startActivity(intent);
            }
        });


    }


    private void loadTransactions() {
        transactions = transactionDAO.getAllTransactions(); // Lấy tất cả giao dịch
        displayTransactions(transactions); // Hiển thị giao dịch trong ListView
        updateTotals(); // Cập nhật tổng thu và chi
    }






    // Hiển thị danh sách giao dịch trong ListView
    private void displayTransactions(List<Transaction> transactions) {
        CustomAdapter adapter = new CustomAdapter(this, R.layout.transaction_item, transactions);
        lvTransactions.setAdapter(adapter);
    }

    // Cập nhật tổng thu và tổng chi
    private void updateTotals() {
        double totalIncome = 0;
        double totalExpense = 0;

        for (Transaction transaction : transactions) {
            if (transaction.getType().equals("Income")) {
                totalIncome += transaction.getAmount();
            } else if (transaction.getType().equals("Expense")) {
                totalExpense += transaction.getAmount();
            }
        }

        // Định dạng số tiền
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        tvTotalIncome.setText("Tổng thu: " + currencyFormat.format(totalIncome));
        tvTotalExpense.setText("Tổng chi: " + currencyFormat.format(totalExpense));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions(); // Tải lại tất cả giao dịch sau khi trở về từ màn hình thêm giao dịch
    }
}
