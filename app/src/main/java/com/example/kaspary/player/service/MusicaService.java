package com.example.kaspary.player.service;

import com.example.kaspary.player.model.AlbumOfArtista;
import com.example.kaspary.player.model.FullAlbum;
import com.example.kaspary.player.model.FullMusica;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MusicaService {

    //https://developers.themoviedb.org/3/search/search-movies
    //@GET("/3/search/movie?language=pt-BR")
    @GET("track/{id}")
    public Call<FullMusica> carregaTodaMusica(@Path("id") long id);

}
