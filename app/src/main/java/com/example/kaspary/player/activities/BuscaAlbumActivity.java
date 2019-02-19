package com.example.kaspary.player.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.kaspary.player.R;
import com.example.kaspary.player.adapter.ListaAlbunsAdapter;
import com.example.kaspary.player.model.AlbumModel;
import com.example.kaspary.player.model.SearchAlbumResponse;
import com.example.kaspary.player.task.SearchAlbumTask;

import java.util.ArrayList;
import java.util.List;


public class BuscaAlbumActivity extends AppCompatActivity implements View.OnClickListener, ListaAlbunsAdapter.OnClickAlbunsRecyclerView, SearchAlbumTask.SearchAlbumTaskDelegate, SearchView.OnQueryTextListener {

    private RecyclerView listaBuscaAlbumRecyclerView;
    private ListaAlbunsAdapter listaAlbunsAdapter;
    private List<AlbumModel> albuns = new ArrayList<>();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Busca Álbum");

        listaBuscaAlbumRecyclerView = findViewById(R.id.lista_busca_album_recyclerView);

        listaAlbunsAdapter = new ListaAlbunsAdapter(albuns, this);
        listaBuscaAlbumRecyclerView.setAdapter(listaAlbunsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaBuscaAlbumRecyclerView.setLayoutManager(layoutManager);
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


    @Override
    public void onClick(View v) {
    }


    private void search(String busca) {
        if(progressDialog == null){
            SearchAlbumTask task = new SearchAlbumTask(this);
            progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Albuns", true, false);
            task.execute(busca);
        }
    }


    @Override
    public void onSearchAlbumSuccess(SearchAlbumResponse response) {
        progressDialog.dismiss();
        progressDialog = null;

        albuns.clear();
        albuns.addAll(response.getData());
        listaAlbunsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchAlbumError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Escreva o nome do Álbum");
        searchView.setOnQueryTextListener(this);
        return true;
    }


    @Override
    public void onClickAlbunsRecyclerView(int position) {
        Intent intent = new Intent(this, VisualizaAlbumActivity.class);
        intent.putExtra(VisualizaAlbumActivity.ID_ALBUM, albuns.get(position).getId());
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
