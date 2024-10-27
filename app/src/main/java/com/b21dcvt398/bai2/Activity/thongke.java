package com.b21dcvt398.bai2.Activity;



import android.content.Intent;
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

public class thongke extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thongke);
        Button btntg = findViewById(R.id.btntktime);
        Button btncate = findViewById(R.id.btntkcategory);
        btncate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongke.this, tkCate.class);
                startActivity(intent);
            }
        });

        btntg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(thongke.this, tkTime.class);
                startActivity(intent);
            }
        });
        Button back = findViewById(R.id.btnback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
