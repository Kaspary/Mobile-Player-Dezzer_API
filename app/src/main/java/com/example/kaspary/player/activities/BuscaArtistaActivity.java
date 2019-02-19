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
import com.example.kaspary.player.adapter.ListaArtistaAdapter;
import com.example.kaspary.player.model.ArtistaModel;
import com.example.kaspary.player.model.SearchArtistaResponse;
import com.example.kaspary.player.task.SearchArtistaTask;

import java.util.ArrayList;
import java.util.List;

public class BuscaArtistaActivity extends AppCompatActivity implements View.OnClickListener, ListaArtistaAdapter.OnClickArtistaRecyclerView, SearchArtistaTask.SearchArtistaTaskDelegate, SearchView.OnQueryTextListener{

    private RecyclerView listaBuscaArtistaRecyclerView;
    private ListaArtistaAdapter listaArtistaAdapter;
    private List<ArtistaModel> artistas = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_artista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Busca Artista");

        listaBuscaArtistaRecyclerView = findViewById(R.id.lista_busca_artista_recyclerView);


        listaArtistaAdapter = new ListaArtistaAdapter(artistas, this);
        listaBuscaArtistaRecyclerView.setAdapter(listaArtistaAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaBuscaArtistaRecyclerView.setLayoutManager(layoutManager);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Escreva o nome do Artista");
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public void onClick(View v) {

    }

    private void search(String busca) {
        if(progressDialog == null){
            SearchArtistaTask task = new SearchArtistaTask(this);
            progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Artista", true, false);
            task.execute(busca);
        }
    }


    @Override
    public void onSearchArtistaSuccess(SearchArtistaResponse response) {
        progressDialog.dismiss();
        progressDialog = null;

        artistas.clear();
        artistas.addAll(response.getData());
        listaArtistaAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchArtistaError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickArtistaRecyclerView(int position) {
        Intent intent = new Intent(this, VisualizaArtistaActivity.class);
        intent.putExtra(VisualizaArtistaActivity.ARTISTA, artistas.get(position));
        startActivityForResult(intent, 0);
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
}
