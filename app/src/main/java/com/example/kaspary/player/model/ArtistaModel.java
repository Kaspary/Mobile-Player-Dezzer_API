package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ArtistaModel implements Serializable {

    private long id;
    private String name;
    @JsonProperty("picture_small")
    private String pictureSmall;
    @JsonProperty("picture_medium")
    private String pictureMedium;
    @JsonProperty("nb_album")
    private int numberAlbum;
    @JsonProperty("nb_fan")
    private int numberFans;


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

    public String getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(String pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public String getPictureMedium() {
        return pictureMedium;
    }

    public void setPictureMedium(String pictureMedium) {
        this.pictureMedium = pictureMedium;
    }

    public int getNumberAlbum() {
        return numberAlbum;
    }

    public void setNumberAlbum(int numberAlbum) {
        this.numberAlbum = numberAlbum;
    }

    public int getNumberFans() {
        return numberFans;
    }

    public void setNumberFans(int numberFans) {
        this.numberFans = numberFans;
    }
}
