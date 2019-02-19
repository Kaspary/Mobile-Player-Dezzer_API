package com.example.kaspary.player.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SearchAlbumResponse {

    private List<AlbumModel> data;

    public List<AlbumModel> getData() {
        return data;
    }

    public void setData(List<AlbumModel> data) {
        this.data = data;
    }
}
