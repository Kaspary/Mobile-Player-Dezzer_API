package com.example.kaspary.player.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaspary.player.R;
import com.example.kaspary.player.model.ArtistaModel;
import com.squareup.picasso.Picasso;

public class VisualizaArtistaActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String ARTISTA = "ARTISTA";
    private TextView nomeArtistaTextView;
    private TextView albunsTextView;
    private TextView seguidoresTextView;
    private ImageView imagemArtistaImageview;
    private Button visualizaAlbunsButton;
    private ArtistaModel artista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualiza_artista);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Visualiza Artista");

        nomeArtistaTextView = findViewById(R.id.nome_artista_visualiza_artista_textview);
        albunsTextView = findViewById(R.id.albuns_visualiza_artista_textView);
        seguidoresTextView = findViewById(R.id.seguidores_visualiza_artista_textView);
        imagemArtistaImageview = findViewById(R.id.imagem_artista_visualiza_artista_imageview);
        visualizaAlbunsButton = findViewById(R.id.visualisa_albuns_visualiza_artista_button);

        visualizaAlbunsButton.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent.getSerializableExtra(ARTISTA) instanceof ArtistaModel){
            artista = (ArtistaModel) intent.getSerializableExtra(ARTISTA);
            nomeArtistaTextView.setText(artista.getName());
            albunsTextView.setText(artista.getNumberAlbum() + " Albuns");
            seguidoresTextView.setText(artista.getNumberFans() + " Seguidores");
            Picasso.with(this).load(artista.getPictureMedium()).into(imagemArtistaImageview);
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
    public void onClick(View v) {
        if(v == visualizaAlbunsButton){
            visualizaAlbuns();
        }
    }

    private void visualizaAlbuns() {
        Intent intent = new Intent(this, VisualizaAlbunsArtistaActivity.class);
        intent.putExtra(VisualizaAlbunsArtistaActivity.ARTISTA, artista);
        startActivityForResult(intent, 0);
    }
}
