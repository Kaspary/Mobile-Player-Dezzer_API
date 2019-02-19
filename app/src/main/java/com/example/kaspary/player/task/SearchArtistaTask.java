package com.example.kaspary.player.task;

import android.os.AsyncTask;

import com.example.kaspary.player.model.SearchAlbumResponse;
import com.example.kaspary.player.model.SearchArtistaResponse;
import com.example.kaspary.player.service.SearchService;
import com.example.kaspary.player.util.RetrofitUtil;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;

public class SearchArtistaTask extends AsyncTask<String, Void, SearchArtistaResponse> {

    public SearchArtistaTaskDelegate delegate;


    public SearchArtistaTask(SearchArtistaTaskDelegate delegate) {
        this.delegate = delegate;
    }


    @Override
    protected SearchArtistaResponse doInBackground(String... strings) {

        SearchService service = RetrofitUtil.getInstance().createSearchService();
        Call<SearchArtistaResponse> call = service.searchArtista(strings[0]);

        try {
            Response<SearchArtistaResponse> response = call.execute();
            if(response.isSuccessful())
                //response.raw().body().string() response em formato string
                return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(SearchArtistaResponse searchArtistaResponse) {

        if(searchArtistaResponse != null)
            delegate.onSearchArtistaSuccess(searchArtistaResponse);
        else
            delegate.onSearchArtistaError("Não foi possível realizar a busca");
    }

    public interface SearchArtistaTaskDelegate {
        public void onSearchArtistaSuccess(SearchArtistaResponse response);
        public void onSearchArtistaError(String error);
    }
}
