package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class MusicaModel implements Serializable {

    private long id;
    @JsonProperty("title_short")
    private String name;
    private double duration;
    private ArtistaModel artist;
    private AlbumModel album;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public ArtistaModel getArtist() {
        return artist;
    }

    public void setArtist(ArtistaModel artist) {
        this.artist = artist;
    }

    public AlbumModel getAlbum() {
        return album;
    }

    public void setAlbum(AlbumModel album) {
        this.album = album;
    }

    public String getMinutsDurationString() {

        DecimalFormat df = new DecimalFormat("00");

        if(duration<3600){
            return df.format((int) duration/60) + ":" + df.format(((duration/60) - (int)duration/60) * 60);
        }else{
            return df.format((int) ((duration/60) / 60)) + ":" +
                    df.format((duration/60) - (60 * ((int)(duration/60) / 60))) + ":" +
                    df.format((((duration/60) - (60 * ((int)(duration/60) / 60))) - (int) ((duration/60) - (60 * ((int)(duration/60) / 60)))) * 60);
        }
    }
}
