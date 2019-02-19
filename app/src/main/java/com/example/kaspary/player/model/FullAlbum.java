package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.regex.Pattern;

public class FullAlbum extends AlbumModel implements Serializable {

    private double duration;
    private int fans;
    @JsonProperty("tracks")
    private MusicasOfAlbum tracks;

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public MusicasOfAlbum getTracks() {
        return tracks;
    }

    public void setTracks(MusicasOfAlbum tracks) {
        this.tracks = tracks;
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
