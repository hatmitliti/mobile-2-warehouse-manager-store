package com.example.mediastore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    Context context;
    int resource;
    ArrayList<MediaItem> list;

    public CustomAdapter(Context context, int resource, ArrayList<MediaItem> list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        Viewholder viewholder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(resource, null);
            viewholder = new Viewholder();


            viewholder.txtalbumName = convertView.findViewById(R.id.txtalbumName);
            viewholder.txtTitle = convertView.findViewById(R.id.txtTitle);
            viewholder.txtmimeType = convertView.findViewById(R.id.txtmimeType);
            viewholder.txtalbumName = convertView.findViewById(R.id.txtalbumName);
            viewholder.txtUri = convertView.findViewById(R.id.txtUri);

            convertView.setTag(viewholder);

        } else {
            viewholder = (Viewholder) convertView.getTag();
        }

        MediaItem mediaItem = list.get(position);

        viewholder.txtalbumName.setText(mediaItem.getAlbumName());
        viewholder.txtTitle.setText(mediaItem.getTitle());
        viewholder.txtmimeType.setText(mediaItem.getMimeType());
        viewholder.txtalbumName.setText(mediaItem.getAlbumName());
        viewholder.txtUri.setText(mediaItem.getUri().toString() );


        return convertView;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public static class Viewholder {
        TextView txtTitle, txtalbumName, txtduration, txtmimeType, txtUri;
    }
}
