package com.example.kaspary.player.task;

import android.os.AsyncTask;

import com.example.kaspary.player.model.FullAlbum;
import com.example.kaspary.player.service.AlbumService;
import com.example.kaspary.player.util.RetrofitUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SearchFullAlbumTask extends AsyncTask<Long, Void, FullAlbum> {

    public SearchFullAlbumTaskDelegate delegate;


    public SearchFullAlbumTask(SearchFullAlbumTaskDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    protected FullAlbum doInBackground(Long... longs) {


        AlbumService service = RetrofitUtil.getInstance().createAlbumService();
        Call<FullAlbum> call = service.carregaTodoAlbum(longs[0]);

        try {
            Response<FullAlbum> response = call.execute();
            if(response.isSuccessful())
                //response.raw().body().string() response em formato string
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(FullAlbum fullAlbum) {

        if(fullAlbum != null)
            delegate.onSearchFullAlbumSuccess(fullAlbum);
        else
            delegate.onSearchFullAlbumError("Não foi possível realizar a busca");
    }

    public interface SearchFullAlbumTaskDelegate {
        public void onSearchFullAlbumSuccess(FullAlbum response);
        public void onSearchFullAlbumError(String error);
    }


}
