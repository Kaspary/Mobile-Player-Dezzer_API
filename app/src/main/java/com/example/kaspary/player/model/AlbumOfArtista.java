package com.example.kaspary.player.model;

import java.io.Serializable;
import java.util.List;

public class AlbumOfArtista implements Serializable {

    private List<AlbumModel> data;

    public List<AlbumModel> getData() {
        return data;
    }

    public void setData(List<AlbumModel> data) {
        this.data = data;
    }
}
