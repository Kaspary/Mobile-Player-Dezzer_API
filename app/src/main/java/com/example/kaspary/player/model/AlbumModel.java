package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AlbumModel implements Serializable {

    private long id;
    private String title;
    @JsonProperty("cover_small")
    private String coverSmall;
    @JsonProperty("cover_medium")
    private String coverMedium;
    private ArtistaModel artist;
    @JsonProperty("nb_tracks")
    private int numberTracks;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public ArtistaModel getArtist() {
        return artist;
    }

    public void setArtist(ArtistaModel artist) {
        this.artist = artist;
    }

    public int getNumberTracks() {
        return numberTracks;
    }

    public void setNumberTracks(int numberTracks) {
        this.numberTracks = numberTracks;
    }
}
