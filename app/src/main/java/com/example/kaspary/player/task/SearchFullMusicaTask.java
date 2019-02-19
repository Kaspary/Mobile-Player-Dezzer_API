package com.example.kaspary.player.task;

import android.os.AsyncTask;

import com.example.kaspary.player.model.FullMusica;
import com.example.kaspary.player.service.MusicaService;
import com.example.kaspary.player.util.RetrofitUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SearchFullMusicaTask extends AsyncTask<Long, Void, FullMusica> {

    public SearchFullMusicaTaskDelegate delegate;


    public SearchFullMusicaTask(SearchFullMusicaTaskDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    protected FullMusica doInBackground(Long... longs) {


        MusicaService service = RetrofitUtil.getInstance().createMusicaService();
        Call<FullMusica> call = service.carregaTodaMusica(longs[0]);

        try {
            Response<FullMusica> response = call.execute();
            if(response.isSuccessful())
                //response.raw().body().string() response em formato string
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(FullMusica fullMusica) {

        if(fullMusica != null)
            delegate.onSearchFullMusicaSuccess(fullMusica);
        else
            delegate.onSearchFullMusicaError("Não foi possível realizar a busca");
    }

    public interface SearchFullMusicaTaskDelegate {
        public void onSearchFullMusicaSuccess(FullMusica response);
        public void onSearchFullMusicaError(String error);
    }


}
