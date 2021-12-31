package com.example.mediastore;

import android.net.Uri;

import java.net.URI;

public class MediaItem {
    private String title;
    private String albumName;
    private String duration;
    private String mimeType;
    private Uri uri;

    public MediaItem() {
    }

    public MediaItem(String title, String albumName, String duration, String mimeType, Uri uri) {
        this.title = title;
        this.albumName = albumName;
        this.duration = duration;
        this.mimeType = mimeType;
        this.uri = uri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}
