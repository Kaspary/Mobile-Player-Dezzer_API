package com.example.kaspary.player.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.OnPlayerProgressListener;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;
import com.example.kaspary.player.R;
import com.example.kaspary.player.model.FullMusica;
import com.example.kaspary.player.model.MusicaModel;
import com.example.kaspary.player.task.SearchFullMusicaTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlayMusicaActivity extends AppCompatActivity implements View.OnClickListener, SearchFullMusicaTask.SearchFullMusicaTaskDelegate, DialogListener, OnPlayerProgressListener {


    public static final String POSITION_MUSICA = "POSITION_MUSICA";
    public static final String MUSICAS = "MUSICAS";
    private TextView nomeMusicaTextView;
    private TextView nomeArtistaTextView;
    private TextView duracaoTextView;
    private TextView posicaoDuracaoTextView;
    private TextView anoMusicaTextVew;
    private ImageView imageAlbumMusicaImageView;
    private ImageButton playPauseButtonImageButton;
    private ImageButton nextButtonImageButton;
    private ImageButton previousButtonImageButton;
    private ProgressBar progressoMusicaSeekBar;
    private boolean pausado = true;
    private ProgressDialog progressDialog;
    private FullMusica musica;
    private int positionMusica;
    private boolean firstPlay = true;
    List<MusicaModel> musicas;

    private DeezerConnect deezerConnect;
    private SessionStore sessionStore;
    private TrackPlayer trackPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_musica);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Play");

        nomeMusicaTextView = findViewById(R.id.nome_musica_play_textview);
        nomeArtistaTextView = findViewById(R.id.nome_artista_play_textview);
        duracaoTextView = findViewById(R.id.total_duracao_play_textView);
        posicaoDuracaoTextView = findViewById(R.id.musica_posicao_play_textView);
        anoMusicaTextVew = findViewById(R.id.ano_play_textView);
        imageAlbumMusicaImageView = findViewById(R.id.imagem_play_imageview);
        playPauseButtonImageButton = findViewById(R.id.play_pause_play_imagebutton);
        nextButtonImageButton = findViewById(R.id.next_play_imagebutton);
        previousButtonImageButton = findViewById(R.id.previous_play_imagebutton);
        progressoMusicaSeekBar = findViewById(R.id.barra_producao_play_appcompatseekbar);
        progressoMusicaSeekBar.setMax(30);

        playPauseButtonImageButton.setOnClickListener(this);
        nextButtonImageButton.setOnClickListener(this);
        previousButtonImageButton.setOnClickListener(this);

        Intent intent = getIntent();

        if(intent.getSerializableExtra(MUSICAS) instanceof List){
            musicas = (List) intent.getSerializableExtra(MUSICAS);
        }

        positionMusica = intent.getIntExtra(POSITION_MUSICA, 0);

        search(musicas.get(positionMusica).getId());
        conectDezzer();
    }


    private void search(long busca) {

        SearchFullMusicaTask task = new SearchFullMusicaTask(this);
        progressDialog = ProgressDialog.show(this, "Aguarde", "Buscando Música", true, false);
        task.execute(busca);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                onBackPressed();
                if(trackPlayer != null){
                    trackPlayer.pause();
                }
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == playPauseButtonImageButton) {
            playPause();
        } else if(v == nextButtonImageButton){
            positionMusica++;
            atualizaMusica(positionMusica);
        } else if(v == previousButtonImageButton){
            positionMusica--;
            atualizaMusica(positionMusica);
        }
    }


    private void atualizaMusica(int position){
        if(position > musicas.size())
            positionMusica = 0;
        if (position < 0)
            positionMusica = musicas.size()-1;

        if(trackPlayer == null){
            Toast.makeText(this, "Player não disponivel", Toast.LENGTH_LONG).show();
            return;
        }
        search(musicas.get(positionMusica).getId());
        trackPlayer.pause();
        progressoMusicaSeekBar.setProgress(0);
        posicaoDuracaoTextView.setText("00:00");
        firstPlay = true;
        if(pausado){
            pausado=false;
        }else{
            pausado=true;
        }
        playPause();

    }

    private void playPause() {

        if(trackPlayer == null){
            Toast.makeText(this, "Player não disponivel", Toast.LENGTH_LONG).show();
            return;
        }

        if (pausado) {
            pausado = false;
            playPauseButtonImageButton.setImageResource(R.drawable.baseline_pause_white_48);
            if(firstPlay){
                trackPlayer.playTrack(musicas.get(positionMusica).getId());
                firstPlay=false;
            }else{
                trackPlayer.play();
            }
        } else {
            pausado = true;
            playPauseButtonImageButton.setImageResource(R.drawable.baseline_play_arrow_white_48);
            trackPlayer.pause();
        }
    }

    @Override
    public void onSearchFullMusicaSuccess(FullMusica response) {
        progressDialog.dismiss();
        progressDialog = null;

        musica = response;

        nomeMusicaTextView.setText(musica.getName());
        nomeArtistaTextView.setText(musica.getArtist().getName());
        duracaoTextView.setText(musica.getMinutsDurationString());
        anoMusicaTextVew.setText(musica.getReleaseDate());
        Picasso.with(this).load(musica.getAlbum().getCoverMedium()).into(imageAlbumMusicaImageView);
    }

    @Override
    public void onSearchFullMusicaError(String error) {
        progressDialog.dismiss();
        progressDialog = null;
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    //methods from deezer
    @Override
    public void onComplete(Bundle bundle) {
        sessionStore.save(deezerConnect, this);
        createTrackPlayer();
    }

    @Override
    public void onCancel() {}

    @Override
    public void onException(Exception e) {}


    private void conectDezzer() {

        deezerConnect = new DeezerConnect(this, "313884");
        sessionStore = new SessionStore();

        if (!sessionStore.restore(deezerConnect, this)) {
            String[] permissoes = new String[]{
                    Permissions.BASIC_ACCESS,
                    Permissions.MANAGE_LIBRARY,
                    Permissions.LISTENING_HISTORY
            };

            deezerConnect.authorize(this, permissoes, this);
        } else {
            createTrackPlayer();
        }
    }

    private void createTrackPlayer() {
        try {
            trackPlayer = new TrackPlayer(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());
            trackPlayer.addOnPlayerProgressListener(this);
        } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
            tooManyPlayersExceptions.printStackTrace();
        } catch (DeezerError deezerError) {
            deezerError.printStackTrace();
        }
    }


    @Override
    public void onPlayerProgress(long l) {

        int progress = ((int) l)/1000;

        progressoMusicaSeekBar.setProgress(progress);
        if(progress<10){
            posicaoDuracaoTextView.setText("00:0"+progress);
        }else{
            posicaoDuracaoTextView.setText("00:"+progress);
            if(progress == 30)
                atualizaMusica(positionMusica++);
        }
    }
}
