package com.example.kaspary.player.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kaspary.player.R;
import com.example.kaspary.player.model.ArtistaModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListaArtistaAdapter extends RecyclerView.Adapter<ListaArtistaAdapter.ItemViewHolder> {

    private List<ArtistaModel> artistas;
    private OnClickArtistaRecyclerView holder;

    public ListaArtistaAdapter(List<ArtistaModel> artistas, OnClickArtistaRecyclerView holder){

        this.artistas = artistas;
        this.holder = holder;

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        private TextView nomeArtistaTextView;
        private ImageView imagemImageView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            nomeArtistaTextView= itemView.findViewById(R.id.nome_artista_adapter_textview);
            imagemImageView = itemView.findViewById(R.id.imagem_adapter_imageview);

            itemView.setClickable(true);
            itemView.setOnClickListener(this);
        }

        public void preencher(final ArtistaModel artista) {
            nomeArtistaTextView.setText(artista.getName());
            Picasso.with(context).load(artista.getPictureMedium()).into(imagemImageView);
        }

        @Override
        public void onClick(View v) {

            if(holder != null){
                holder.onClickArtistaRecyclerView(getAdapterPosition());
            }
        }
    }

    @Override
    public int getItemCount() {
        return artistas.size(); // (1)
    }


    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        ArtistaModel artista = artistas.get(position);
        holder.preencher(artista);
    }

    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.lista_artistas, parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(itemView);
        return viewHolder;
    }

    public interface OnClickArtistaRecyclerView {
        void onClickArtistaRecyclerView(int position);
    }

}