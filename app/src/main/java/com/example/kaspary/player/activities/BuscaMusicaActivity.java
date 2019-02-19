package com.example.kaspary.player.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kaspary.player.R;
import com.example.kaspary.player.adapter.ListaMusicaAdapter;
import com.example.kaspary.player.model.MusicaModel;
import com.example.kaspary.player.model.SearchMusicaResponse;
import com.example.kaspary.player.task.SearchMusicaTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BuscaMusicaActivity extends AppCompatActivity implements View.OnClickListener, ListaMusicaAdapter.OnClickMusicaRecyclerView, SearchMusicaTask.SearchMusicaResponseDelegate, SearchView.OnQueryTextListener {

    private RecyclerView listaBuscaMusicasRecyclerView;
    private ListaMusicaAdapter listaMusicaAdapter;
    private List<MusicaModel> musicas = new ArrayList<>();
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_musica);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão

        listaBuscaMusicasRecyclerView = findViewById(R.id.lista_busca_musica_recyclerView);
        getSupportActionBar().setTitle("Busca Música");


        listaMusicaAdapter = new ListaMusicaAdapter(musicas, this);
        listaBuscaMusicasRecyclerView.setAdapter(listaMusicaAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaBuscaMusicasRecyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                onBackPressed();
                break;
            default:
                break;
        }
        return true;
    }


    private void search(String busca) {
        if(progressDialog == null){
            SearchMusicaTask task = new SearchMusicaTask(this);
            progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Músicas", true, false);
            task.execute(busca);
        }
    }

    @Override
    public void onSearchMusicaSuccess(SearchMusicaResponse response) {
        progressDialog.dismiss();
        progressDialog = null;
        try{
            progressDialog.dismiss();
            progressDialog = null;
        } catch (NullPointerException e){
            System.out.println(e);
        }

        musicas.clear();
        musicas.addAll(response.getData());
        listaMusicaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchMusicaError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickMusicaRecyclerView(int position) {
        Intent intent = new Intent(this, PlayMusicaActivity.class);
        intent.putExtra(PlayMusicaActivity.POSITION_MUSICA, position);
        intent.putExtra(PlayMusicaActivity.MUSICAS, (Serializable) musicas);
        startActivityForResult(intent, 0);
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Escreva o nome da Música");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        search(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void onClick(View v) {

    }
}
