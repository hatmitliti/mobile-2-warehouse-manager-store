package com.example.warehousemanager.User;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.warehousemanager.Product.Product;
import com.example.warehousemanager.Product.ProductAdapter;
import com.example.warehousemanager.R;
import com.example.warehousemanager.UpdateCustomerActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {
    Context context;
    int resource;
    ArrayList<User> data;



    public UserAdapter(Context context, int resource, ArrayList<User> data) {
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
    private  static  class ViewHolder{
        TextView tvTenUser;
        ImageView btnUpdateUser;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null)
        {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource,viewGroup,false);
            viewHolder.tvTenUser = view.findViewById(R.id.tvUserItems);
            viewHolder.btnUpdateUser = view.findViewById(R.id.btnUpdateUser);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tvTenUser.setText(data.get(i).getName());

        User user = data.get(i);
        viewHolder.btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateCustomerActivity.class);
                intent.putExtra("UserData",user);
                context.startActivity(intent);
            }
        });

        return view;
    }
}
