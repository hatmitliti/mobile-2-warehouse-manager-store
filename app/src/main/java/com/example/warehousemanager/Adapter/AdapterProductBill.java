package com.example.warehousemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanager.Object.ProductBill;
import com.example.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProductBill extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<ProductBill> data;

    public AdapterProductBill(Context context, int resource, ArrayList<ProductBill> data) {
        super(context, resource, data);
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        ProductBill productBill = data.get(position);

        TextView txtNameProductInCartDetail = convertView.findViewById(R.id.txtNameProductInCartDetail);
        TextView txtPriceProductInCartDetail = convertView.findViewById(R.id.txtPriceProductInCartDetail);
        TextView txtQualityProductInCartDetail = convertView.findViewById(R.id.txtQualityProductInCartDetail);

        txtNameProductInCartDetail.setText(productBill.getName());
        txtPriceProductInCartDetail.setText(productBill.getPrice() + "");
        txtQualityProductInCartDetail.setText("x" + productBill.getQuality());


        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }
}
