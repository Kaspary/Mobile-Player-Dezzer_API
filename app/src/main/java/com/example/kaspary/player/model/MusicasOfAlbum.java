package com.example.kaspary.player.model;

import java.io.Serializable;
import java.util.List;

public class MusicasOfAlbum implements Serializable {

    private List<MusicaModel> data;

    public List<MusicaModel> getData() {
        return data;
    }

    public void setData(List<MusicaModel> data) {
        this.data = data;
    }
}
