package com.example.mediastore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ListView;

import java.util.ArrayList;

public class MediaStore_ extends AppCompatActivity {
    ArrayList<MediaItem> list = new ArrayList<>();
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.media_store);
        ListView listView = findViewById(R.id.lvMedia);

        list = setData();

        adapter = new CustomAdapter(getApplicationContext(), R.layout.item_media_store, list);
        listView.setAdapter(adapter);

    }

    private ArrayList<MediaItem> setData() {
        ArrayList<MediaItem> list_ = new ArrayList<>();


        String[] projecttion = {
                MediaStore.Audio.AudioColumns._ID,
                MediaStore.Audio.AudioColumns.ALBUM,
                MediaStore.Audio.AudioColumns.TITLE,
                MediaStore.Audio.AudioColumns.DURATION,
                MediaStore.Audio.AudioColumns.MIME_TYPE,

        };
        // get content uri
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentUri = MediaStore.Audio.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
        } else {
            contentUri = MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
        }

        //
        String where = null;
        String[] whereArg = null;

        // Access to the content media store
        Cursor cursor = getContentResolver().query(contentUri, projecttion, where, whereArg, null);

        int idxID = cursor.getColumnIndex(MediaStore.Audio.AudioColumns._ID);
        int album = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.ALBUM);
        int title = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.TITLE);
        int duration = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION);
        int mimeType = cursor.getColumnIndex(MediaStore.Audio.AudioColumns.MIME_TYPE);

        if (cursor.moveToFirst()) {
            do {
                MediaItem item = new MediaItem();
                item.setTitle(cursor.getString(title));
                item.setAlbumName(cursor.getString(album));
                item.setDuration(cursor.getString(duration));
                item.setMimeType(cursor.getString(mimeType));


                Uri uri = ContentUris.withAppendedId(contentUri, Long.parseLong(cursor.getString(idxID)));

                item.setUri(uri);
list_.add(item);

            } while (cursor.moveToNext());
        } else {

        }


        return list_;
    }
}