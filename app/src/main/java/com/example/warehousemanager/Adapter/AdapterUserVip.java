package com.example.warehousemanager.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.warehousemanager.Object.Bill;
import com.example.warehousemanager.R;
import com.example.warehousemanager.User.User;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;

public class AdapterUserVip extends ArrayAdapter {
    Context context;
    int resource;
    ArrayList<User> data;

    public AdapterUserVip(Context context, int resource, ArrayList<User> data) {
        super(context, resource, data);
        this.data = data;
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.imgUser = convertView.findViewById(R.id.imgUser);
            viewHolder.txtNameUser = convertView.findViewById(R.id.txtNameUser);
            viewHolder.txtSDTUser = convertView.findViewById(R.id.txtSDTUser);
            viewHolder.txtmailUser = convertView.findViewById(R.id.txtmailUser);
            viewHolder.txtDaMuaUser = convertView.findViewById(R.id.txtDaMuaUser);
            viewHolder.txtDiaChiUser = convertView.findViewById(R.id.txtDiaChiUser);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        User user = data.get(position);
        Picasso.get().load(user.getImgUser()).into(viewHolder.imgUser);
        viewHolder.txtNameUser.setText(user.getName());
        viewHolder.txtSDTUser.setText(user.getPhone());
        viewHolder.txtmailUser.setText(user.getEmail());
        viewHolder.txtDaMuaUser.setText(NumberFormat.getInstance().format(user.getTotalMoney()));
        viewHolder.txtDiaChiUser.setText(user.getAddress());

        return convertView;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    public static class ViewHolder {
        ImageView imgUser;
        TextView txtNameUser;
        TextView txtSDTUser;
        TextView txtmailUser;
        TextView txtDaMuaUser;
        TextView txtDiaChiUser;
    }
}
