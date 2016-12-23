package com.forksystem.constituicao.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forksystem.constituicao.R;
import com.forksystem.constituicao.databinding.ListaArtigoBinding;
import com.forksystem.constituicao.databinding.ListaFavoritoBinding;
import com.forksystem.constituicao.model.Artigo;
import com.forksystem.constituicao.model.Favorito;
import com.thefuntasty.infinity.InfinityAdapter;

/**
 * Created by sm on 22/11/2016.
 */


public  class FavoritoAdapter extends InfinityAdapter<Favorito,FavoritoAdapter.ViewHolderFavorito> {


    private static final String TAG = Artigo.class.getSimpleName();
    private Context context;

    @Override
    public ViewHolderFavorito onCreateContentViewHolder(ViewGroup parent, int viewType) {
        ListaFavoritoBinding listaFavoritoBinding= DataBindingUtil
                           .inflate(LayoutInflater
                           .from(parent.getContext()),R.layout.lista_favorito,parent,false);

        return  new ViewHolderFavorito(listaFavoritoBinding);
    }

    @Override
    public void onBindContentViewHolder(ViewHolderFavorito holder, int position) {

        ((TextView) holder.favoritoBinding.conteudoFavorito).setText(getContentItem(position).getConteudo());

    }



    public  static class ViewHolderFavorito extends RecyclerView.ViewHolder {

         ListaFavoritoBinding favoritoBinding;


        public ViewHolderFavorito(ListaFavoritoBinding itemView) {
            super(itemView.getRoot());
            favoritoBinding=itemView;


        }

    }



}