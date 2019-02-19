package com.example.kaspary.player.service;

import com.example.kaspary.player.model.AlbumOfArtista;
import com.example.kaspary.player.model.FullAlbum;
import com.example.kaspary.player.model.FullMusica;
import com.example.kaspary.player.model.SearchAlbumResponse;
import com.example.kaspary.player.model.SearchArtistaResponse;
import com.example.kaspary.player.model.SearchMusicaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchService {

    //https://developers.themoviedb.org/3/search/search-movies
    //@GET("/3/search/movie?language=pt-BR")
    @GET("/search/album?")
    public Call<SearchAlbumResponse> searchAlbum(@Query("q") String query);


    @GET("/search/artist?")
    public Call<SearchArtistaResponse> searchArtista(@Query("q") String query);


    @GET("/search/track?")
    public Call<SearchMusicaResponse> searchMusica(@Query("q") String query);
}
