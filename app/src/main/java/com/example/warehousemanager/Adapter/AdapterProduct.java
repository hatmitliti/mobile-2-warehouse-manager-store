package com.example.warehousemanager.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanager.Product.Product;
import com.example.warehousemanager.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class AdapterProduct extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<Product> data;

    public AdapterProduct(Context context, int resource, ArrayList<Product> data) {
        super(context, resource, data);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.imgProducts = convertView.findViewById(R.id.imgProducts);
            viewHolder.txtnameProduct = convertView.findViewById(R.id.txtnameProduct);
            viewHolder.txtProceProduct = convertView.findViewById(R.id.txtProceProduct);
            viewHolder.txtKhoProduct = convertView.findViewById(R.id.txtKhoProduct);
            viewHolder.txtDaBan = convertView.findViewById(R.id.txtDaBan);
            viewHolder.txtLoaiProduct = convertView.findViewById(R.id.txtLoaiProduct);
            viewHolder.txtHangProduct = convertView.findViewById(R.id.txtHangProduct);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = data.get(position);
        Picasso.get().load(product.getHinhAnh()).into(viewHolder.imgProducts);
        viewHolder.txtnameProduct.setText(product.getTenSanPham());
        viewHolder.txtProceProduct.setText(NumberFormat.getInstance().format(product.getGiaTien()) + " VNĐ");
        viewHolder.txtKhoProduct.setText("Kho: " + product.getStock());
        viewHolder.txtDaBan.setText("Đã bán: " + product.getSold());
        viewHolder.txtLoaiProduct.setText(product.getCategory());
        viewHolder.txtHangProduct.setText(product.getManufacturer());

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public static class ViewHolder {
        ImageView imgProducts;
        TextView txtnameProduct;
        TextView txtProceProduct;
        TextView txtKhoProduct;
        TextView txtDaBan;
        TextView txtLoaiProduct;
        TextView txtHangProduct;
    }

}
