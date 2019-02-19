package com.example.kaspary.player.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaspary.player.R;
import com.example.kaspary.player.adapter.ListaAlbunsAdapter;
import com.example.kaspary.player.model.AlbumModel;
import com.example.kaspary.player.model.AlbumOfArtista;
import com.example.kaspary.player.model.ArtistaModel;
import com.example.kaspary.player.task.SearchAlbumOfArtistaTask;

import java.util.ArrayList;
import java.util.List;

public class VisualizaAlbunsArtistaActivity extends AppCompatActivity implements ListaAlbunsAdapter.OnClickAlbunsRecyclerView, SearchAlbumOfArtistaTask.SearchAlbumOfArtistaTaskDelegate {

    public static final String ARTISTA = "ARTISTA";
    private TextView nomeArtistaTextView;
    private RecyclerView listaAlbunsRecyclerView;
    private List<AlbumModel> albuns = new ArrayList<>();
    private ListaAlbunsAdapter listaAlbunsAdapter;
    private ProgressDialog progressDialog;
    private ArtistaModel artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_albuns_artista);

        nomeArtistaTextView = findViewById(R.id.nome_artista_visualisa_albuns_artista_textview);
        listaAlbunsRecyclerView= findViewById(R.id.lista_albuns_recyclerview);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Visualiza Álbuns");


        Intent intent = getIntent();
        if (intent.getSerializableExtra(ARTISTA) instanceof ArtistaModel){
            artista = (ArtistaModel) intent.getSerializableExtra(ARTISTA);
            nomeArtistaTextView.setText(artista.getName());
            search(artista.getId());
        }


        listaAlbunsAdapter = new ListaAlbunsAdapter(albuns, this);
        listaAlbunsRecyclerView.setAdapter(listaAlbunsAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listaAlbunsRecyclerView.setLayoutManager(layoutManager);

    }


    private void search(long busca) {

        SearchAlbumOfArtistaTask task = new SearchAlbumOfArtistaTask(this);
        progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Albuns", true, false);
        task.execute(busca);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                onBackPressed();
                break;
            default:break;
        }
        return true;
    }

    @Override
    public void onClickAlbunsRecyclerView(int position) {
        Intent intent = new Intent(this, VisualizaAlbumActivity.class);
        intent.putExtra(VisualizaAlbumActivity.ID_ALBUM, albuns.get(position).getId());
        startActivityForResult(intent, 0);
    }

    @Override
    public void onSearchAlbumOfArtistaSuccess(AlbumOfArtista response) {
        progressDialog.dismiss();
        progressDialog = null;

        for(int i =0; i<response.getData().size();i++)
            response.getData().get(i).setArtist(artista);

        albuns.clear();
        albuns.addAll(response.getData());
        listaAlbunsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSearchAlbumOfArtistaError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
