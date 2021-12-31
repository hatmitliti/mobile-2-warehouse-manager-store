package tdc.edu.vn.ngobayhen_27042021;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<PhanCong> data;
    CardView cardView;

    public CustomAdapter(Context context, int resource, ArrayList<PhanCong> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);

            viewHolder = new ViewHolder();
            viewHolder.manv = convertView.findViewById(R.id.txtNV);
            viewHolder.mada = convertView.findViewById(R.id.txtDA);
            viewHolder.tungay = convertView.findViewById(R.id.txtTN);
            viewHolder.denngay = convertView.findViewById(R.id.txtDN);

            convertView.setTag(viewHolder);


        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PhanCong pc = data.get(position);
        viewHolder.manv.setText(pc.getMaNV());
        viewHolder.mada.setText(pc.getMaDA());
        viewHolder.tungay.setText(pc.getTuNgay() + "");
        viewHolder.denngay.setText(pc.getDeNgay() + "");
        return convertView;
    }

    // viewholder
    private static class ViewHolder {
        TextView manv;
        TextView mada;
        TextView tungay;
        TextView denngay;

    }
}
