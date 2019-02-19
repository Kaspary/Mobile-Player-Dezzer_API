package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FullMusica extends MusicaModel {

    @JsonProperty("release_date")
    private String releaseDate;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
