package com.example.kaspary.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaspary.player.R;
import com.example.kaspary.player.model.MusicaModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaMusicaAdapter extends RecyclerView.Adapter<ListaMusicaAdapter.ItemViewHolder> {

    private List<MusicaModel> musicas;
    private OnClickMusicaRecyclerView holder;

    public ListaMusicaAdapter(List<MusicaModel> musicas, OnClickMusicaRecyclerView holder){

        this.musicas = musicas;
        this.holder = holder;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView nomeMusicaTextView;
        private TextView nomeArtistaTextView;
        private TextView duracaoMusicaTextView;
        private ImageView imagemImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            nomeMusicaTextView = itemView.findViewById(R.id.nome_musica_adapter_textview);
            nomeArtistaTextView= itemView.findViewById(R.id.nome_artista_adapter_textview);
            duracaoMusicaTextView = itemView.findViewById(R.id.duracao_musica_adapter_textview);
            imagemImageView = itemView.findViewById(R.id.imagem_adapter_imageview);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }
        public void preencher(final MusicaModel musica) {
            nomeMusicaTextView.setText(musica.getName());
            nomeArtistaTextView.setText(musica.getArtist().getName());
            duracaoMusicaTextView.setText(musica.getMinutsDurationString());
            Picasso.with(context).load(musica.getAlbum().getCoverMedium()).into(imagemImageView);
        }

        @Override
        public void onClick(View v) {

            if(holder != null){
                holder.onClickMusicaRecyclerView(getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return musicas.size(); // (1)
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        MusicaModel musica = musicas.get(position);
        holder.preencher(musica);
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lista_musicas, parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    public interface OnClickMusicaRecyclerView {
        void onClickMusicaRecyclerView(int position);
    }
}
