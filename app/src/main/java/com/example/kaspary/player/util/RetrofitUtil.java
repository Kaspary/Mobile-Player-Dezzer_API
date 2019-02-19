package com.example.kaspary.player.util;

import com.example.kaspary.player.service.AlbumService;
import com.example.kaspary.player.service.ArtistaService;
import com.example.kaspary.player.service.MusicaService;
import com.example.kaspary.player.service.SearchService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitUtil {

    //public static final String API_kEY = "8cc65cc237509b082427cce84df4fe28";
    private static RetrofitUtil instance;
    private Retrofit retrofit;

    private RetrofitUtil() {

        ObjectMapper jacksoMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.deezer.com")
                .addConverterFactory(JacksonConverterFactory.create(jacksoMapper))
                .build();
    }

    public static RetrofitUtil getInstance() {

        if (instance == null)
            instance = new RetrofitUtil();

        return instance;
    }

    public SearchService createSearchService() {
        return retrofit.create(SearchService.class);
    }

    public AlbumService createAlbumService() {
        return retrofit.create(AlbumService.class);
    }

    public ArtistaService createArtistaService() {
        return retrofit.create(ArtistaService.class);
    }

    public MusicaService createMusicaService() {
        return retrofit.create(MusicaService.class);
    }
}

