package com.example.warehousemanager.ManufacturerAD;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.warehousemanager.Category.Category;
import com.example.warehousemanager.Category.CategoryAdapter;
import com.example.warehousemanager.R;

import java.util.ArrayList;

public class ManufacturerAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<Manufacturer> data;

    public ManufacturerAdapter(Context context, int resource, ArrayList<Manufacturer> data) {
        this.context = context;
        this.resource = resource;
        this.data = data;
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
    public static class ViewHolder{
        private TextView nameManufacturer;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource,viewGroup,false);
            viewHolder.nameManufacturer = view.findViewById(R.id.tvTenSPItemProductManufacturer);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.nameManufacturer.setText(data.get(i).getName());
        return view;
    }
}
