package com.example.Weather;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class RecyclerViewWeather extends RecyclerView.Adapter<RecyclerViewWeather.MyViewHolder> {
    private Activity context;
    private int layoutID;
    private ArrayList<Weather> listWeaTher;

    public RecyclerViewWeather(Activity context, int layoutID, ArrayList<Weather> listWeaTher) {
        this.context = context;
        this.layoutID = layoutID;
        this.listWeaTher = listWeaTher;
    }

    @NonNull
    @Override
    public RecyclerViewWeather.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        CardView view = (CardView) layoutInflater.inflate(layoutID,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Weather weather = listWeaTher.get(position);

        holder.tvNhietDo.setText("Nhiệt Độ: "+weather.getNhietDo());
        holder.tvTamNhin.setText("Tầm Nhìn: "+weather.getTamNhin()+ "m");
        holder.tvTocDoGio.setText("Tốc Độ Gió: "+weather.getTocDoGio() + "Km/h");
        holder.tvThoiGian.setText("Thời Gian: "+weather.getThoiGian());

    }

    @Override
    public int getItemCount() {
        return listWeaTher.size();
    }
    public  static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tvNhietDo, tvTocDoGio,tvTamNhin,tvThoiGian;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvNhietDo = itemView.findViewById(R.id.tvNhietDo);
            this.tvTamNhin = itemView.findViewById(R.id.tvTamNhin);
            this.tvThoiGian = itemView.findViewById(R.id.tvThoiGian);
            this.tvTocDoGio = itemView.findViewById(R.id.tvTocDoGio);
        }
    }
}
