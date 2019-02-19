package com.example.kaspary.player.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kaspary.player.R;
import com.example.kaspary.player.adapter.ListaMusicaAdapter;
import com.example.kaspary.player.model.FullAlbum;

import java.io.Serializable;

public class VisualizaMusicasAlbumActivity extends AppCompatActivity implements ListaMusicaAdapter.OnClickMusicaRecyclerView {

    public static final String ALBUM = "ALBUM";
    private TextView nomeAlbumTextView;
    private RecyclerView listaMusicasRecyclerView;
    private FullAlbum album;
    private ListaMusicaAdapter listaMusicaAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_musicas_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Visualiza Músicas");

        nomeAlbumTextView = findViewById(R.id.nome_album_visualisa_musicas_album_textview);
        listaMusicasRecyclerView= findViewById(R.id.lista_musicas_recyclerview);

        Intent intent = getIntent();


        if(intent.getSerializableExtra(ALBUM) instanceof FullAlbum){
            album = (FullAlbum) intent.getSerializableExtra(ALBUM);

            for(int i =0; i<album.getTracks().getData().size(); i++)
                album.getTracks().getData().get(i).setAlbum(album);

            nomeAlbumTextView. setText(album.getTitle());
            listaMusicaAdapter = new ListaMusicaAdapter(album.getTracks().getData(), this);
            listaMusicasRecyclerView.setAdapter(listaMusicaAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            listaMusicasRecyclerView.setLayoutManager(layoutManager);
        }
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
    public void onClickMusicaRecyclerView(int position) {
        Intent intent = new Intent(this, PlayMusicaActivity.class);
        intent.putExtra(PlayMusicaActivity.POSITION_MUSICA, position);
        intent.putExtra(PlayMusicaActivity.MUSICAS, (Serializable) album.getTracks().getData());
        startActivityForResult(intent, 0);
    }
}
