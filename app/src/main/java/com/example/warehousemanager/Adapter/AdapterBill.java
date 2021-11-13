package com.example.warehousemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanager.Object.Bill;
import com.example.warehousemanager.R;

import java.util.ArrayList;

public class AdapterBill extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<Bill> data;

    public AdapterBill(Context context, int resource, ArrayList<Bill> data) {
        super(context, resource, data);
        this.data = data;
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);

        TextView txtDiaChiBillStatus = convertView.findViewById(R.id.txtDiaChiBillStatus);
        TextView txtTenNguoiNhanBillStatus = convertView.findViewById(R.id.txtTenNguoiNhanBillStatus);
        TextView txtMaDonHangBillStatus = convertView.findViewById(R.id.txtMaDonHangBillStatus);
        TextView txtTienTraBillStatus = convertView.findViewById(R.id.txtTienTraBillStatus);


        txtDiaChiBillStatus.setText("Địa chỉ: " + data.get(position).getAddress());
        txtTenNguoiNhanBillStatus.setText("Người nhận: " + data.get(position).getName());
        txtMaDonHangBillStatus.setText("Mã: " + data.get(position).getId());
        txtTienTraBillStatus.setText("Tổng: " + data.get(position).getTotalMoney());


        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
