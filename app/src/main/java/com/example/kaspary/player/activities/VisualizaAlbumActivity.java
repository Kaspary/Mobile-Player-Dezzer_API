package com.example.kaspary.player.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kaspary.player.R;
import com.example.kaspary.player.model.FullAlbum;
import com.example.kaspary.player.task.SearchFullAlbumTask;
import com.squareup.picasso.Picasso;

public class VisualizaAlbumActivity extends AppCompatActivity implements View.OnClickListener, SearchFullAlbumTask.SearchFullAlbumTaskDelegate {

    public static final String ID_ALBUM = "ID_ALBUM";
    private TextView nomeAlbumTextView;
    private TextView musicasTextView;
    private TextView duracaoTextView;
    private TextView seguidoresTextView;
    private ImageView imageAlbumImageView;
    private Button visualizaMusicasAlbumButton;
    private FullAlbum album;
    private long idAlbum;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_album);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Visualiza Álbum");

        nomeAlbumTextView = findViewById(R.id.nome_album_visualiza_album_textview);
        musicasTextView = findViewById(R.id.musicas_visualiza_album_textView);
        duracaoTextView = findViewById(R.id.duracao_visualiza_album_textView);
        seguidoresTextView = findViewById(R.id.seguidores_visualiza_album_textView);
        imageAlbumImageView = findViewById(R.id.imagem_album_visualiza_album_imageview);
        visualizaMusicasAlbumButton = findViewById(R.id.visualizar_musicas_visualisa_albuns_button);

        visualizaMusicasAlbumButton.setOnClickListener(this);

        Intent intent = getIntent();

        idAlbum = intent.getLongExtra(ID_ALBUM, 0);

        search(idAlbum);
    }


    private void search(long busca) {

        SearchFullAlbumTask task = new SearchFullAlbumTask(this);
        progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Albuns", true, false);
        task.execute(busca);
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
        if (v == visualizaMusicasAlbumButton) {
            visualizaMusicas();
        }
    }

    @Override
    public void onSearchFullAlbumSuccess(FullAlbum response) {
        progressDialog.dismiss();
        progressDialog = null;

        album = response;

        nomeAlbumTextView.setText(album.getTitle());
        musicasTextView.setText(album.getNumberTracks() + " Músicas");
        duracaoTextView.setText(album.getMinutsDurationString() + " de Duração");
        seguidoresTextView.setText(album.getFans() + " Seguidores");
        Picasso.with(this).load(album.getCoverMedium()).into(imageAlbumImageView);
    }

    @Override
    public void onSearchFullAlbumError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    private void visualizaMusicas() {
        Intent intent = new Intent(this, VisualizaMusicasAlbumActivity.class);
        intent.putExtra(VisualizaMusicasAlbumActivity.ALBUM, album);
        startActivityForResult(intent, 0);
    }
}
