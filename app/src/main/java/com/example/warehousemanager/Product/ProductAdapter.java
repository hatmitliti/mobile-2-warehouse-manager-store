package com.example.warehousemanager.Product;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.warehousemanager.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ProductAdapter extends BaseAdapter {

    Context context;
    int resource;
    ArrayList<Product> data;

    public ProductAdapter(Context context, int resource, ArrayList<Product> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    private  static  class ViewHolder{
        ImageView imgAnhSP;
        TextView tvTenSP,tvGiaSP;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        //
        NumberFormat currentLocale = NumberFormat.getInstance();
        Locale localeEN = new Locale("en", "EN");
        NumberFormat en = NumberFormat.getInstance(localeEN);
        //
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource,viewGroup,false);
            viewHolder.imgAnhSP = view.findViewById(R.id.imgSPItemProduct);
            viewHolder.tvTenSP = view.findViewById(R.id.tvTenSPItemProduct);
            viewHolder.tvGiaSP = view.findViewById(R.id.tvGiaSPItemProduct);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        Product product = data.get(i);
        Picasso.get().load(product.getHinhAnh()).into(viewHolder.imgAnhSP);
        viewHolder.tvTenSP.setText("Tên : "+product.getTenSanPham());
        viewHolder.tvGiaSP.setText("Giá : "+en.format(product.getGiaTien()) + " VNĐ");

        return view;
    }
}
