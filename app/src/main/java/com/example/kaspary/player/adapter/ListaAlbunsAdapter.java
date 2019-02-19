package com.example.kaspary.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaspary.player.R;
import com.example.kaspary.player.model.AlbumModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaAlbunsAdapter extends RecyclerView.Adapter<ListaAlbunsAdapter.ItemViewHolder> {

    private List<AlbumModel> albuns;
    private OnClickAlbunsRecyclerView holder;

    public ListaAlbunsAdapter(List<AlbumModel> albuns, OnClickAlbunsRecyclerView holder){

        this.albuns = albuns;
        this.holder = holder;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView nomeAlbumTextView;
        private TextView nomeArtistaTextView;
        private ImageView imagemImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            nomeAlbumTextView= itemView.findViewById(R.id.nome_album_adapter_textview);
            nomeArtistaTextView= itemView.findViewById(R.id.nome_artista_adapter_textview);
            imagemImageView = itemView.findViewById(R.id.imagem_adapter_imageview);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }
        public void preencher(final AlbumModel album) {
            nomeAlbumTextView.setText(album.getTitle());
            if(album.getArtist() != null)
                nomeArtistaTextView.setText(album.getArtist().getName());
            Picasso.with(context).load(album.getCoverMedium()).into(imagemImageView);
        }

        @Override
        public void onClick(View v) {

            if(holder != null){
                holder.onClickAlbunsRecyclerView(getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return albuns.size(); // (1)
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        AlbumModel album = albuns.get(position);
        holder.preencher(album);
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lista_albuns, parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    public interface OnClickAlbunsRecyclerView {
        void onClickAlbunsRecyclerView(int position);
    }

}
