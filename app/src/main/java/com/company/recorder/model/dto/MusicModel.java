package com.company.recorder.model.dto;

import android.net.Uri;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MusicModel {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("uri")
    @Expose
    private Uri uri;
    @SerializedName("album")
    @Expose
    private String album;
    @SerializedName("artist")
    @Expose
    private String artist;
    @SerializedName("genre")
    @Expose
    private String genre;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("trackNumber")
    @Expose
    private Integer trackNumber;
    @SerializedName("totalTrackCount")
    @Expose
    private Integer totalTrackCount;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("site")
    @Expose
    private String site;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getImage() {
        return image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getTrackNumber() {
        return trackNumber;
    }

    public void setTrackNumber(Integer trackNumber) {
        this.trackNumber = trackNumber;
    }

    public Integer getTotalTrackCount() {
        return totalTrackCount;
    }

    public void setTotalTrackCount(Integer totalTrackCount) {
        this.totalTrackCount = totalTrackCount;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

}
