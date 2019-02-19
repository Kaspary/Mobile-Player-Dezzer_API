
package com.example.kaspary.player.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.kaspary.player.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int BUSCA_REQUEST_CODE = 1;
    private Button albumMenuButton;
    private Button artistaMenuButton;
    private Button musicaMenuButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        albumMenuButton = findViewById(R.id.album_menu_button);
        artistaMenuButton = findViewById(R.id.artista_menu_button);
        musicaMenuButton = findViewById(R.id.musica_menu_button);

        albumMenuButton.setOnClickListener(this);
        artistaMenuButton.setOnClickListener(this);
        musicaMenuButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == albumMenuButton){
            album();
        }else if (v == artistaMenuButton){
            artista();
        }else if (v == musicaMenuButton){
            musica();
        }
    }

    private void album() {
        Intent intent = new Intent(this, BuscaAlbumActivity.class);
        startActivityForResult(intent, BUSCA_REQUEST_CODE);
    }

    private void artista() {
        Intent intent = new Intent(this, BuscaArtistaActivity.class);
        startActivityForResult(intent, BUSCA_REQUEST_CODE);
    }

    private void musica() {
        Intent intent = new Intent(this, BuscaMusicaActivity.class);
        startActivityForResult(intent, BUSCA_REQUEST_CODE);
    }
}
