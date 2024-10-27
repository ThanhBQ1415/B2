package com.b21dcvt398.bai2.Activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.b21dcvt398.bai2.Model.Transaction;
import com.b21dcvt398.bai2.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Transaction> transactionList;

    // Constructor
    public CustomAdapter(Context context, int layout, List<Transaction> transactionList) {
        this.context = context;
        this.layout = layout;
        this.transactionList = transactionList;
    }

    @Override
    public int getCount() {
        return transactionList.size();
    }

    @Override
    public Object getItem(int position) {
        return transactionList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout, null);
            holder = new ViewHolder();
            holder.tvDateTime = convertView.findViewById(R.id.tvDateTime);
            holder.tvTransactionName = convertView.findViewById(R.id.tvTransactionName);
            holder.tvAmount = convertView.findViewById(R.id.tvAmount);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Lấy đối tượng Transaction tại vị trí position
        Transaction transaction = transactionList.get(position);

        // Chuyển đổi timestamp thành định dạng ngày tháng
        long dateInMillis = transaction.getDay().getTime(); // Giả sử timestamp ở mili giây
        Date date = new Date(dateInMillis);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        holder.tvDateTime.setText(dateFormat.format(date)); // Hiển thị ngày giờ

        // Thiết lập tên giao dịch (giới hạn độ dài)
        String transactionName = transaction.getName();
        if (transactionName.length() > 30) {
            transactionName = transactionName.substring(0, 27) + "...";
        }
        holder.tvTransactionName.setText(transactionName);

        // Định dạng số tiền theo yêu cầu
        double amount = transaction.getAmount();
        if (transaction.getType().equals("Income")) {
            holder.tvAmount.setTextColor(Color.parseColor("#00A676")); // Màu xanh cho tiền vào
            holder.tvAmount.setText(String.format(Locale.getDefault(), "+%s ", formatAmount(amount)));
        } else {
            holder.tvAmount.setTextColor(Color.RED); // Màu đỏ cho tiền ra
            holder.tvAmount.setText(String.format(Locale.getDefault(), "-%s ", formatAmount(amount)));
        }

        return convertView;
    }

    // Hàm định dạng số tiền
    private String formatAmount(double amount) {
        if (amount % 1_000_000_000 == 0) {
            return String.format(Locale.getDefault(), "%.0fB", amount / 1_000_000_000); // Billion
        } else if (amount % 1_000_000 == 0) {
            return String.format(Locale.getDefault(), "%.0fM", amount / 1_000_000); // Million
        } else if (amount % 1_000 == 0) {
            return String.format(Locale.getDefault(), "%.0fK", amount / 1_000); // Thousand
        } else {
            return String.format(Locale.getDefault(), "%,.0f đ", amount);
        }
    }

    // ViewHolder giúp tái sử dụng view để tăng hiệu suất
    private static class ViewHolder {
        TextView tvDateTime;
        TextView tvTransactionName;
        TextView tvAmount;
    }
}
