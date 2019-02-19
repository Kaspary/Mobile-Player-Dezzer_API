package com.example.kaspary.player.task;

import android.os.AsyncTask;

import com.example.kaspary.player.model.AlbumOfArtista;
import com.example.kaspary.player.service.ArtistaService;
import com.example.kaspary.player.util.RetrofitUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SearchAlbumOfArtistaTask extends AsyncTask<Long, Void, AlbumOfArtista> {

    public SearchAlbumOfArtistaTaskDelegate delegate;


    public SearchAlbumOfArtistaTask(SearchAlbumOfArtistaTaskDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    protected AlbumOfArtista doInBackground(Long... longs) {

        ArtistaService service = RetrofitUtil.getInstance().createArtistaService();
        Call<AlbumOfArtista> call = service.carregaAlbumArtista(longs[0]);

        try {
            Response<AlbumOfArtista> response = call.execute();
            if(response.isSuccessful())
                //response.raw().body().string() response em formato string
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(AlbumOfArtista AlbumOfArtista) {

        if(AlbumOfArtista != null)
            delegate.onSearchAlbumOfArtistaSuccess(AlbumOfArtista);
        else
            delegate.onSearchAlbumOfArtistaError("Não foi possível realizar a busca");
    }


    public interface SearchAlbumOfArtistaTaskDelegate {
        public void onSearchAlbumOfArtistaSuccess(AlbumOfArtista response);
        public void onSearchAlbumOfArtistaError(String error);
    }
}
